package com.pgvpt.service.impl;

import com.pgvpt.client.GeolocalisationClient;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.EtapeCircuitEntity;
import com.pgvpt.record.GeolocalisationResponse;
import com.pgvpt.repository.CircuitRepository;
import com.pgvpt.repository.EtapeCircuitRepository;
import com.pgvpt.service.ItineraireOptimizationService;
import com.pgvpt.viewModel.EtapeAvecCoordonneesViewModel;
import com.pgvpt.viewModel.EtapeOptimiseeViewModel;
import com.pgvpt.viewModel.ItineraireOptimiseViewModel;
import com.pgvpt.viewModel.OptimisationItineraireRequestViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pgvpt.enums.MethodeEnum.*;

@Service
@RequiredArgsConstructor
public class ItineraireOptimizationServiceImpl implements ItineraireOptimizationService {

    private final CircuitRepository circuitRepository;
    private final EtapeCircuitRepository etapeRepository;
    private final GeolocalisationClient geolocalisationClient;

    @Override
    @Transactional (readOnly = true)
    public ItineraireOptimiseViewModel optimiser(UUID circuitId, OptimisationItineraireRequestViewModel request) {

        // 1. Vérifier l'existence du circuit
        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        // 2. Récupérer les étapes
        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);

        if (etapes.isEmpty()) {
            throw new BusinessException("Impossible d'optimiser un circuit sans étape.");
        }

        if (etapes.size() == 1) {
            return construireResultat(circuitId, etapes, request);
        }

        // 3. Récupérer les coordonnées géographiques
        List<EtapeAvecCoordonneesViewModel> points = chargerCoordonnees(etapes);

        // 4. Calculer la matrice des distances
        Map<UUID, Map<UUID, Double>> distances = calculerMatriceDistances(points);

        // 5. Appliquer la stratégie d'optimisation
        List<EtapeAvecCoordonneesViewModel> etapesOptimisees = appliquerStrategie(points, distances, request);

        // 6. Recalculer ordre, distances et coûts
        recalculerEtapes(etapesOptimisees, distances);

        // 7. Persister le nouvel ordre
        sauvegarderNouvelOrdre(etapesOptimisees);

        // 8. Construire la réponse
        return construireResultatOptimise(circuitId, etapesOptimisees, request);
    }

    private ItineraireOptimiseViewModel construireResultat(
            UUID circuitId,
            List<EtapeCircuitEntity> etapes,
            OptimisationItineraireRequestViewModel request) {

        if (etapes == null || etapes.isEmpty()) {
            throw new BusinessException("Aucune étape disponible pour construire l'itinéraire.");
        }

        // 1. Recalcul de l'ordre
        for (int i = 0; i < etapes.size(); i++) {
            etapes.get(i).setOrdre(i + 1);
        }

        // 2. Distance totale
        double distanceTotaleKm = etapes.stream()
                .map(EtapeCircuitEntity::getDistanceDepuisPrecedenteKm)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        // 3. Durée totale
        int dureeTotaleMinutes = etapes.stream()
                .map(EtapeCircuitEntity::getDureeMinutes)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        // 4. Coût total
        double coutTotal = etapes.stream()
                .map(EtapeCircuitEntity::getPrix)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        // 5. Calcul du score d'optimisation
        double score = calculerScore(distanceTotaleKm, dureeTotaleMinutes, coutTotal, request);

        // 6. Transformation des étapes
        List<EtapeOptimiseeViewModel> etapesOptimisees = etapes.stream().map(this::toEtapeOptimisee).toList();

        // 7. Construction du résultat
        return ItineraireOptimiseViewModel.builder()
                .circuitId(circuitId)
                .methode(request.getMethode())
                .distanceTotaleKm(distanceTotaleKm)
                .dureeTotaleMinutes(dureeTotaleMinutes)
                .coutTotal(coutTotal)
                .scoreOptimisation(score)
                .etapes(etapesOptimisees)
                .build();
    }

    private double calculerScore(double distanceKm, int dureeMinutes, double cout, OptimisationItineraireRequestViewModel request) {

        double poidsDistance = Optional.ofNullable(request.getPoidsDistance()).orElse(1.0);

        double poidsDuree = Optional.ofNullable(request.getPoidsDuree()).orElse(1.0);

        double poidsCout = Optional.ofNullable(request.getPoidsCout()).orElse(1.0);

        return (poidsDistance * distanceKm) + (poidsDuree * dureeMinutes) + (poidsCout * cout);
    }

    private EtapeOptimiseeViewModel toEtapeOptimisee(EtapeCircuitEntity etape) {

        return EtapeOptimiseeViewModel.builder()
                .ordre(etape.getOrdre())
                .patrimoineId(etape.getPatrimoineId())
                .activite(etape.getActivite())
                .distanceDepuisPrecedenteKm(etape.getDistanceDepuisPrecedenteKm())
                .dureeMinutes(etape.getDureeMinutes())
                .cout(etape.getPrix())
                .build();
    }

    private List<EtapeAvecCoordonneesViewModel> chargerCoordonnees(
            List<EtapeCircuitEntity> etapes) {

        return etapes.stream()
                .map(etape -> {

                    GeolocalisationResponse geo = geolocalisationClient.getByPatrimoine(etape.getPatrimoineId());

                    if (geo == null || geo.latitude() == null || geo.longitude() == null) {

                        throw new BusinessException("Aucune géolocalisation disponible pour le patrimoine : " + etape.getPatrimoineId());
                    }

                    return new EtapeAvecCoordonneesViewModel(etape, geo.latitude(), geo.longitude());}).toList();
    }

    private Map<UUID, Map<UUID, Double>> calculerMatriceDistances(List<EtapeAvecCoordonneesViewModel> points) {

        Map<UUID, Map<UUID, Double>> matrice = new HashMap<>();

        for (EtapeAvecCoordonneesViewModel origine : points) {

            UUID origineId = origine.getEtape().getPatrimoineId();

            Map<UUID, Double> distancesOrigine = new HashMap<>();

            for (EtapeAvecCoordonneesViewModel destination : points) {

                UUID destinationId = destination.getEtape().getPatrimoineId();

                double distance = haversine(origine.getLatitude(), origine.getLongitude(), destination.getLatitude(), destination.getLongitude());

                distancesOrigine.put(destinationId, distance);
            }

            matrice.put(origineId, distancesOrigine);
        }

        return matrice;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {

        final double R = 6371.0;

        double dLat = Math.toRadians(lat2 - lat1);

        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private List<EtapeAvecCoordonneesViewModel> appliquerStrategie(
            List<EtapeAvecCoordonneesViewModel> points,
            Map<UUID, Map<UUID, Double>> distances,
            OptimisationItineraireRequestViewModel request) {

        return switch (request.getMethode()) {

            case DISTANCE_MINIMALE -> optimiserParDistance(points, distances);

            case DUREE_MINIMALE -> optimiserParDuree(points, distances);

            case COUT_MINIMAL -> optimiserParCout(points);

            case MULTICRITERE -> optimiserMulticritere(points, distances, request);
        };
    }

    private List<EtapeAvecCoordonneesViewModel> optimiserParDistance(
            List<EtapeAvecCoordonneesViewModel> points,
            Map<UUID, Map<UUID, Double>> distances) {

        List<EtapeAvecCoordonneesViewModel> resultat = new ArrayList<>();

        Set<UUID> visites = new HashSet<>();

        EtapeAvecCoordonneesViewModel courant = points.get(0);

        resultat.add(courant);

        visites.add(courant.getEtape().getPatrimoineId());

        while (resultat.size() < points.size()) {

            UUID courantId = courant.getEtape().getPatrimoineId();

            EtapeAvecCoordonneesViewModel prochaine = points.stream().filter(point ->
                                    !visites.contains(point.getEtape().getPatrimoineId())
                            )
                            .min(Comparator.comparingDouble(
                                            point ->
                                                    distances.get(courantId).get(point.getEtape().getPatrimoineId())
                                    )
                            ).orElseThrow();

            resultat.add(prochaine);

            visites.add(prochaine.getEtape().getPatrimoineId());

            courant = prochaine;
        }

        return resultat;
    }

    private List<EtapeAvecCoordonneesViewModel> optimiserParDuree(
            List<EtapeAvecCoordonneesViewModel> points,
            Map<UUID, Map<UUID, Double>> distances) {

        return points.stream()
                .sorted(Comparator.comparing(point ->
                                Optional.ofNullable(point.getEtape().getDureeMinutes()).orElse(0)))
                .toList();
    }

    private List<EtapeAvecCoordonneesViewModel> optimiserParCout(
            List<EtapeAvecCoordonneesViewModel> points) {

        return points.stream()
                .sorted(Comparator.comparingDouble(
                                point ->
                                        Optional.ofNullable(point.getEtape().getPrix()).orElse(0.0)))
                .toList();
    }

    private List<EtapeAvecCoordonneesViewModel> optimiserMulticritere(
            List<EtapeAvecCoordonneesViewModel> points,
            Map<UUID, Map<UUID, Double>> distances,
            OptimisationItineraireRequestViewModel request) {

        double poidsDistance = valeur(request.getPoidsDistance(), 1.0);

        double poidsDuree = valeur(request.getPoidsDuree(), 1.0);

        double poidsCout = valeur(request.getPoidsCout(), 1.0);

        return points.stream()
                .sorted(Comparator.comparingDouble(
                                point -> {
                                    EtapeCircuitEntity etape = point.getEtape();

                                    double distance = Optional.ofNullable(etape.getDistanceDepuisPrecedenteKm()).orElse(0.0);

                                    double duree = Optional.ofNullable(etape.getDureeMinutes()).orElse(0);

                                    double cout = Optional.ofNullable(etape.getPrix()).orElse(0.0);

                                    return poidsDistance * distance + poidsDuree * duree + poidsCout * cout;
                                }
                        )
                ).toList();
    }

    private double valeur(Double valeur, double valeurParDefaut) {

        return valeur == null
                ? valeurParDefaut
                : valeur;
    }

    private void recalculerEtapes(List<EtapeAvecCoordonneesViewModel> etapes, Map<UUID, Map<UUID, Double>> distances) {

        for (int i = 0; i < etapes.size(); i++) {
            EtapeCircuitEntity etape = etapes.get(i).getEtape();
            etape.setOrdre(i + 1);

            if (i == 0) {
                etape.setDistanceDepuisPrecedenteKm(0.0);
                continue;
            }

            UUID precedentId = etapes.get(i - 1).getEtape().getPatrimoineId();

            UUID courantId = etape.getPatrimoineId();

            double distance = distances.get(precedentId).get(courantId);

            etape.setDistanceDepuisPrecedenteKm(distance);
        }
    }

    private void sauvegarderNouvelOrdre(List<EtapeAvecCoordonneesViewModel> etapes) {

        // Phase 1 : ordres temporaires
        int ordreTemporaire = 100000;

        for (EtapeAvecCoordonneesViewModel item : etapes) {

            EtapeCircuitEntity etape = item.getEtape();

            etape.setOrdre(ordreTemporaire++);
        }

        etapeRepository.saveAll(etapes.stream().map(EtapeAvecCoordonneesViewModel::getEtape).toList());

        // Phase 2 : vrais ordres
        for (int i = 0; i < etapes.size(); i++) {
            etapes.get(i).getEtape().setOrdre(i + 1);
        }

        etapeRepository.saveAll(etapes.stream().map(EtapeAvecCoordonneesViewModel::getEtape).toList());
    }

    private ItineraireOptimiseViewModel construireResultatOptimise(
            UUID circuitId,
            List<EtapeAvecCoordonneesViewModel> etapes,
            OptimisationItineraireRequestViewModel request) {

        double distanceTotale =
                etapes.stream()
                        .map(EtapeAvecCoordonneesViewModel::getEtape)
                        .map(EtapeCircuitEntity::getDistanceDepuisPrecedenteKm)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();

        int dureeTotale =
                etapes.stream()
                        .map(EtapeAvecCoordonneesViewModel::getEtape)
                        .map(EtapeCircuitEntity::getDureeMinutes)
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::intValue)
                        .sum();

        double coutTotal =
                etapes.stream()
                        .map(EtapeAvecCoordonneesViewModel::getEtape)
                        .map(EtapeCircuitEntity::getPrix)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();

        ItineraireOptimiseViewModel resultat = new ItineraireOptimiseViewModel();

        resultat.setCircuitId(circuitId);
        resultat.setMethode(request.getMethode());
        resultat.setDistanceTotaleKm(distanceTotale);
        resultat.setDureeTotaleMinutes(dureeTotale);
        resultat.setCoutTotal(coutTotal);

        resultat.setEtapes(etapes.stream().map(this::toEtapeOptimisee).toList());

        return resultat;
    }


    private EtapeOptimiseeViewModel toEtapeOptimisee(EtapeAvecCoordonneesViewModel item) {

        EtapeCircuitEntity etape = item.getEtape();

        return EtapeOptimiseeViewModel.builder()
                .ordre(etape.getOrdre())
                .patrimoineId(etape.getPatrimoineId())
                .activite(etape.getActivite())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .distanceDepuisPrecedenteKm(etape.getDistanceDepuisPrecedenteKm())
                .dureeMinutes(etape.getDureeMinutes())
                .cout(etape.getPrix())
                .build();
    }
}
