package com.pgvpt.mapper;

import com.pgvpt.dto.Accessibilite;
import com.pgvpt.dto.CollectionMusee;
import com.pgvpt.dto.Conservation;
import com.pgvpt.dto.Contact;
import com.pgvpt.dto.EspeceProtegee;
import com.pgvpt.dto.Exposition;
import com.pgvpt.dto.HoraireOuverture;
import com.pgvpt.dto.Media;
import com.pgvpt.dto.Menace;
import com.pgvpt.dto.Monument;
import com.pgvpt.dto.MonumentCreate;
import com.pgvpt.dto.MonumentUpdate;
import com.pgvpt.dto.Musee;
import com.pgvpt.dto.MuseeCreate;
import com.pgvpt.dto.MuseeUpdate;
import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.SiteNaturel;
import com.pgvpt.dto.SiteNaturelCreate;
import com.pgvpt.dto.SiteNaturelUpdate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.Tarification;

import com.pgvpt.entities.AccessibiliteEntity;
import com.pgvpt.entities.CollectionMuseeEntity;
import com.pgvpt.entities.ConservationEntity;
import com.pgvpt.entities.ContactEntity;
import com.pgvpt.entities.EspeceProtegeeEntity;
import com.pgvpt.entities.ExpositionEntity;
import com.pgvpt.entities.HoraireOuvertureEntity;
import com.pgvpt.entities.MediaEntity;
import com.pgvpt.entities.MenaceValue;
import com.pgvpt.entities.MonumentEntity;
import com.pgvpt.entities.MuseeEntity;
import com.pgvpt.entities.PatrimoineEntity;
import com.pgvpt.entities.SiteNaturelEntity;
import com.pgvpt.entities.TarificationEntity;
import com.pgvpt.entities.PhotoEntity;

import com.pgvpt.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class PatrimoineMapper {

    // ============================================================
    // POLYMORPHIC DISPATCHERS (Ajoutés pour simplifier le Service)
    // ============================================================

    public PatrimoineEntity toEntity(PatrimoineCreate dto) {
        if (dto instanceof SiteNaturelCreate site) {
            return toSiteNaturelEntity(site);
        } else if (dto instanceof MuseeCreate musee) {
            return toMuseeEntity(musee);
        } else if (dto instanceof MonumentCreate monument) {
            return toMonumentEntity(monument);
        }
        throw new InvalidRequestException("Le type concret de patrimoine est obligatoire");
    }

    public Patrimoine toDto(PatrimoineEntity entity) {
        if (entity == null) return null;
        if (entity instanceof SiteNaturelEntity site) return toSiteNaturelDto(site);
        if (entity instanceof MuseeEntity musee) return toMuseeDto(musee);
        if (entity instanceof MonumentEntity monument) return toMonumentDto(monument);
        throw new InvalidRequestException("Type d'entité patrimoine non supporté : " + entity.getClass().getSimpleName());
    }

    // ============================================================
    // CREATE DTO -> ENTITY
    // ============================================================

    public MonumentEntity toMonumentEntity(MonumentCreate dto) {

        if (dto == null) {
            return null;
        }

        MonumentEntity entity = new MonumentEntity();

        mapPatrimoineFields(dto, entity);

        // Champs spécifiques Monument
        entity.setStyleArchitectural(dto.getStyleArchitectural());
        entity.setAnneeConstruction(dto.getAnneeConstruction());
        entity.setIdentiteArchitecte(dto.getIdentiteArchitecte());

        if (dto.getNatureMonument() != null) {
            entity.setNatureMonument(
                    dto.getNatureMonument().getValue()
            );
        }

        return entity;
    }

    public MuseeEntity toMuseeEntity(MuseeCreate dto) {

        if (dto == null) {
            return null;
        }

        MuseeEntity entity = new MuseeEntity();

        mapPatrimoineFields(dto, entity);

        // Champs spécifiques Musée
        entity.setNombreCollections(dto.getNombreCollections());

        /*
        entity.setNombreOeuvres(dto.getNombreOeuvres());

        entity.setTypesCollections(
                dto.getTypesCollections() != null
                        ? new ArrayList<>(dto.getTypesCollections())
                        : new ArrayList<>()
        );

        entity.setServicesMusee(
                dto.getServicesMusee() != null
                        ? new ArrayList<>(dto.getServicesMusee())
                        : new ArrayList<>()
        );

        entity.setCapaciteAccueil(dto.getCapaciteAccueil());

        entity.setMuseographie(dto.getMuseographie());
        */

        // Collections du musée
        if (dto.getCollections() != null) {

            for (CollectionMusee dtoCollection : dto.getCollections()) {

                CollectionMuseeEntity collectionEntity =
                        toCollectionMuseeEntity(dtoCollection);

                collectionEntity.setMusee(entity);

                entity.getCollections().add(collectionEntity);
            }
        }

        // Expositions
        if (dto.getExpositions() != null) {

            for (Exposition dtoExposition : dto.getExpositions()) {

                ExpositionEntity expositionEntity =
                        toExpositionEntity(dtoExposition);

                expositionEntity.setMusee(entity);

                entity.getExpositions().add(expositionEntity);
            }
        }

        return entity;
    }

    public SiteNaturelEntity toSiteNaturelEntity(
            SiteNaturelCreate dto) {

        if (dto == null) {
            return null;
        }

        SiteNaturelEntity entity = new SiteNaturelEntity();

        mapPatrimoineFields(dto, entity);

        // Champs spécifiques Site Naturel
        entity.setSuperficie(dto.getSuperficie());

        if (dto.getNatureSite() != null) {
            entity.setNatureSite(
                    dto.getNatureSite().getValue()
            );
        }

        entity.setEcosysteme(dto.getEcosysteme());

        /*
        entity.setBiodiversite(dto.getBiodiversite());

        entity.setZoneProtegee(dto.getZoneProtegee());
        */

        /*
        entity.setCategorieProtection(
                dto.getCategorieProtection()
        );

        entity.setRisquesEnvironnementaux(
                dto.getRisquesEnvironnementaux() != null
                        ? new ArrayList<>(dto.getRisquesEnvironnementaux())
                        : new ArrayList<>()
        );

        entity.setRessourcesNaturelles(
                dto.getRessourcesNaturelles() != null
                        ? new ArrayList<>(dto.getRessourcesNaturelles())
                        : new ArrayList<>()
        );

        entity.setActivitesEcotouristiques(
                dto.getActivitesEcotouristiques() != null
                        ? new ArrayList<>(dto.getActivitesEcotouristiques())
                        : new ArrayList<>()
        );

        entity.setCapaciteAccueil(dto.getCapaciteAccueil());
        */

        // Espèces protégées
        if (dto.getEspecesProtegees() != null) {

            for (EspeceProtegee dtoEspece
                    : dto.getEspecesProtegees()) {

                EspeceProtegeeEntity especeEntity =
                        toEspeceProtegeeEntity(dtoEspece);

                especeEntity.setSiteNaturel(entity);

                entity.getEspecesProtegees().add(especeEntity);
            }
        }

        return entity;
    }

    // ============================================================
    // CHAMPS COMMUNS
    // ============================================================

    private void mapPatrimoineFields(
            PatrimoineCreate dto,
            PatrimoineEntity entity) {

        // L'id n'est volontairement PAS mappé sur CREATE.
        // Il doit être généré par JPA.

        entity.setCode(dto.getCode());
        entity.setNom(dto.getNom());
        entity.setNomLocal(dto.getNomLocal());
        entity.setNomHistorique(dto.getNomHistorique());

        entity.setType(dto.getType());

        if (dto.getCategorie() != null) {
            entity.setCategorie(
                    dto.getCategorie().getValue()
            );
        }

        entity.setSousCategorie(
                dto.getSousCategorie()
        );

        if (dto.getPeriode() != null) {
            entity.setPeriode(
                    dto.getPeriode().getValue()
            );
        }

        entity.setSiecle(dto.getSiecle());
        entity.setDateOuverture(dto.getDateOuverture());

        entity.setDescription(dto.getDescription());
        entity.setDescriptionCourte(dto.getDescriptionCourte());
        entity.setHistorique(dto.getHistorique());

        entity.setImportanceHistorique(
                dto.getImportanceHistorique()
        );

        entity.setImportanceCulturelle(
                dto.getImportanceCulturelle()
        );

        entity.setImportanceTouristique(
                dto.getImportanceTouristique()
        );

        entity.setValeurPatrimoniale(
                dto.getValeurPatrimoniale()
        );

        entity.setValeurSpirituelle(
                dto.getValeurSpirituelle()
        );

        // Listes simples
        entity.setTraditionsAssociees(
                dto.getTraditionsAssociees() != null
                        ? new ArrayList<>(
                                dto.getTraditionsAssociees()
                        )
                        : new ArrayList<>()
        );

        entity.setLangues(
                dto.getLangues() != null
                        ? new ArrayList<>(dto.getLangues())
                        : new ArrayList<>()
        );

        entity.setRecommandationsVisiteur(
                dto.getRecommandationsVisiteur() != null
                        ? new ArrayList<>(
                                dto.getRecommandationsVisiteur()
                        )
                        : new ArrayList<>()
        );

        entity.setReglesVisite(
                dto.getReglesVisite() != null
                        ? new ArrayList<>(dto.getReglesVisite())
                        : new ArrayList<>()
        );

        entity.setActivitesTouristiques(
                dto.getActivitesTouristiques() != null
                        ? new ArrayList<>(
                                dto.getActivitesTouristiques()
                        )
                        : new ArrayList<>()
        );

        // Classement
        entity.setClassePatrimoine(
                dto.getClassePatrimoine()
        );

        entity.setReferenceClassement(
                dto.getReferenceClassement()
        );

        entity.setDateClassement(
                dto.getDateClassement()
        );

        entity.setInscritUnesco(
                dto.getInscritUnesco()
        );

        entity.setNomSiteUnesco(
                dto.getNomSiteUnesco()
        );

        entity.setDateInscriptionUnesco(
                dto.getDateInscriptionUnesco()
        );

        entity.setProtectionJuridique(
                dto.getProtectionJuridique()
        );

        // Gestion
        entity.setOrganismeGestionnaire(
                dto.getOrganismeGestionnaire()
        );

        entity.setProprietaire(
                dto.getProprietaire()
        );

        entity.setGestionnaire(
                dto.getGestionnaire()
        );

        // Contact
        if (dto.getContact() != null) {
            entity.setContact(
                    toContactEntity(dto.getContact())
            );
        }

        // Site Web
        entity.setSiteWeb(
                dto.getSiteWeb() != null
                        ? dto.getSiteWeb().toString()
                        : null
        );

        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());

        // Accessibilité
        if (dto.getAccessibilite() != null) {
            entity.setAccessibilite(
                    toAccessibiliteEntity(
                            dto.getAccessibilite()
                    )
            );
        }

        // Tarification
        if (dto.getTarification() != null) {
            entity.setTarification(
                    toTarificationEntity(
                            dto.getTarification()
                    )
            );
        }

        // Etat de conservation
        if (dto.getEtatConservation() != null) {
            entity.setEtatConservation(
                    dto.getEtatConservation().getValue()
            );
        }

        // Conservation détaillée
        if (dto.getConservation() != null) {
            entity.setConservation(
                    toConservationEntity(
                            dto.getConservation()
                    )
            );
        }

        // Visite
        entity.setMeilleurePeriodeVisite(
                dto.getMeilleurePeriodeVisite()
        );

        entity.setDureeVisiteEstimeeMinutes(
                dto.getDureeVisiteEstimeeMinutes()
        );

        // Horaires
        if (dto.getHoraires() != null) {

            for (HoraireOuverture dtoHoraire
                    : dto.getHoraires()) {

                HoraireOuvertureEntity horaireEntity =
                        toHoraireEntity(dtoHoraire);

                horaireEntity.setPatrimoine(entity);

                entity.getHoraires().add(horaireEntity);
            }
        }

        // Photos
        if (dto.getPhotos() != null) {

            for (com.pgvpt.dto.Photo dtoPhoto
                    : dto.getPhotos()) {

                PhotoEntity photoEntity =
                        toPhotoEntity(dtoPhoto);

                photoEntity.setPatrimoine(entity);

                entity.getPhotos().add(photoEntity);
            }
        }

        // Médias
        if (dto.getMedias() != null) {

            for (Media dtoMedia
                    : dto.getMedias()) {

                MediaEntity mediaEntity =
                        toMediaEntity(dtoMedia);

                mediaEntity.setPatrimoine(entity);

                entity.getMedias().add(mediaEntity);
            }
        }

        // Menaces
        if (dto.getMenaces() != null) {

            for (Menace dtoMenace
                    : dto.getMenaces()) {

                entity.getMenaces().add(
                        toMenaceValue(dtoMenace)
                );
            }
        }
    }

    // ============================================================
    // CONTACT
    // ============================================================

    private ContactEntity toContactEntity(
            Contact dto) {

        ContactEntity entity = new ContactEntity();

        entity.setNom(dto.getNom());
        entity.setFonction(dto.getFonction());
        entity.setTelephone(dto.getTelephone());
        entity.setEmail(dto.getEmail());

        entity.setSiteWeb(
                dto.getSiteWeb() != null
                        ? dto.getSiteWeb().toString()
                        : null
        );

        return entity;
    }

    // ============================================================
    // ACCESSIBILITE
    // ============================================================

    private AccessibiliteEntity toAccessibiliteEntity(
            Accessibilite dto) {

        AccessibiliteEntity entity =
                new AccessibiliteEntity();

        entity.setAccessiblePublic(
                dto.getAccessiblePublic()
        );

        entity.setAccessibilitePMR(
                dto.getAccessibilitePMR()
        );

        entity.setAccesFauteuilRoulant(
                dto.getAccesFauteuilRoulant()
        );

        entity.setAccesTransportPublic(
                dto.getAccesTransportPublic()
        );

        entity.setParking(
                dto.getParking()
        );

        entity.setGuideDisponible(
                dto.getGuideDisponible()
        );

        entity.setGuideAudio(
                dto.getGuideAudio()
        );

        entity.setLangueVisite(
                dto.getLangueVisite() != null
                        ? new ArrayList<>(dto.getLangueVisite())
                        : new ArrayList<>()
        );

        entity.setAccesEnfant(
                dto.getAccesEnfant()
        );

        entity.setConditionsAcces(
                dto.getConditionsAcces()
        );

        entity.setRestrictions(
                dto.getRestrictions() != null
                        ? new ArrayList<>(dto.getRestrictions())
                        : new ArrayList<>()
        );

        return entity;
    }

    // ============================================================
    // TARIFICATION
    // ============================================================

    private TarificationEntity toTarificationEntity(
            Tarification dto) {

        TarificationEntity entity =
                new TarificationEntity();

        entity.setGratuit(dto.getGratuit());
        entity.setDevise(dto.getDevise());
        entity.setConditionsTarifaires(
                dto.getConditionsTarifaires()
        );

        entity.setTarifNormal(
                dto.getTarifNormal() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifNormal()
                        )
                        : null
        );

        entity.setTarifEnfant(
                dto.getTarifEnfant() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifEnfant()
                        )
                        : null
        );

        entity.setTarifEtudiant(
                dto.getTarifEtudiant() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifEtudiant()
                        )
                        : null
        );

        entity.setTarifResident(
                dto.getTarifResident() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifResident()
                        )
                        : null
        );

        entity.setTarifGroupe(
                dto.getTarifGroupe() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifGroupe()
                        )
                        : null
        );

        entity.setTarifGuide(
                dto.getTarifGuide() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getTarifGuide()
                        )
                        : null
        );

        return entity;
    }

    // ============================================================
    // CONSERVATION
    // ============================================================

    private ConservationEntity toConservationEntity(
            Conservation dto) {

        ConservationEntity entity =
                new ConservationEntity();

        if (dto.getEtat() != null) {
            entity.setEtat(
                    dto.getEtat().getValue()
            );
        }

        entity.setDateEvaluation(
                dto.getDateEvaluation()
        );

        entity.setEvaluateur(
                dto.getEvaluateur()
        );

        entity.setDescriptionEtat(
                dto.getDescriptionEtat()
        );

        entity.setDegradations(
                dto.getDegradations() != null
                        ? new ArrayList<>(dto.getDegradations())
                        : new ArrayList<>()
        );

        entity.setCausesDegradation(
                dto.getCausesDegradation() != null
                        ? new ArrayList<>(
                                dto.getCausesDegradation()
                        )
                        : new ArrayList<>()
        );

        entity.setTravauxNecessaires(
                dto.getTravauxNecessaires() != null
                        ? new ArrayList<>(
                                dto.getTravauxNecessaires()
                        )
                        : new ArrayList<>()
        );

        entity.setDernierTravaux(
                dto.getDernierTravaux()
        );

        entity.setDateDerniersTravaux(
                dto.getDateDerniersTravaux()
        );

        entity.setOrganismeConservation(
                dto.getOrganismeConservation()
        );

        entity.setBudgetEstime(
                dto.getBudgetEstime() != null
                        ? java.math.BigDecimal.valueOf(
                                dto.getBudgetEstime()
                        )
                        : null
        );

        entity.setRecommandations(
                dto.getRecommandations() != null
                        ? new ArrayList<>(
                                dto.getRecommandations()
                        )
                        : new ArrayList<>()
        );

        return entity;
    }

    // ============================================================
    // HORAIRE
    // ============================================================

    private HoraireOuvertureEntity toHoraireEntity(
            HoraireOuverture dto) {

        HoraireOuvertureEntity entity =
                new HoraireOuvertureEntity();

        if (dto.getJour() != null) {
            entity.setJour(
                    dto.getJour().getValue()
            );
        }

        entity.setOuvert(dto.getOuvert());

        if (dto.getHeureOuverture() != null) {
            entity.setHeureOuverture(java.time.LocalTime.parse(dto.getHeureOuverture()));
        }

        if (dto.getHeureFermeture() != null) {
            entity.setHeureFermeture(java.time.LocalTime.parse(dto.getHeureFermeture()));
        }

        if (dto.getPauseDebut() != null) {
            entity.setPauseDebut(java.time.LocalTime.parse(dto.getPauseDebut()));
        }

        if (dto.getPauseFin() != null) {
            entity.setPauseFin(java.time.LocalTime.parse(dto.getPauseFin()));
        }

        entity.setSurReservation(
                dto.getSurReservation()
        );

        entity.setCommentaire(
                dto.getCommentaire()
        );

        return entity;
    }

    // ============================================================
    // PHOTO
    // ============================================================

    private PhotoEntity toPhotoEntity(
            com.pgvpt.dto.Photo dto) {

        PhotoEntity entity = new PhotoEntity();

        // L'id du DTO n'est pas utilisé pour CREATE.
        // JPA le génère.

        entity.setUrl(
                dto.getUrl() != null
                        ? dto.getUrl().toString()
                        : null
        );

        entity.setMiniatureUrl(
                dto.getMiniatureUrl() != null
                        ? dto.getMiniatureUrl().toString()
                        : null
        );

        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());
        entity.setAuteur(dto.getAuteur());
        entity.setDatePrise(dto.getDatePrise());
        entity.setDroits(dto.getDroits());
        entity.setLicence(dto.getLicence());
        entity.setPhotoPrincipale(dto.getPhotoPrincipale());
        entity.setOrdre(dto.getOrdre());

        return entity;
    }

    // ============================================================
    // MEDIA
    // ============================================================

    private MediaEntity toMediaEntity(
            Media dto) {

        MediaEntity entity = new MediaEntity();

        entity.setType(
                dto.getType() != null
                        ? dto.getType().getValue()
                        : null
        );

        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());

        entity.setUrl(
                dto.getUrl() != null
                        ? dto.getUrl().toString()
                        : null
        );

        entity.setMiniatureUrl(
                dto.getMiniatureUrl() != null
                        ? dto.getMiniatureUrl().toString()
                        : null
        );

        entity.setDuree(dto.getDuree());
        entity.setAuteur(dto.getAuteur());
        entity.setLicence(dto.getLicence());

        return entity;
    }

    // ============================================================
    // MENACE
    // ============================================================

    private MenaceValue toMenaceValue(
            Menace dto) {

        MenaceValue value = new MenaceValue();

        value.setType(dto.getType());
        value.setNiveau(dto.getNiveau() != null ? dto.getNiveau().getValue() : null);
        value.setDescription(dto.getDescription());
        value.setMesuresPrevention(
                dto.getMesuresPrevention()
        );

        return value;
    }

    // ============================================================
    // COLLECTION MUSEE
    // ============================================================

    private CollectionMuseeEntity toCollectionMuseeEntity(
            CollectionMusee dto) {

        CollectionMuseeEntity entity =
                new CollectionMuseeEntity();

        entity.setNom(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setNombreOeuvres(
                dto.getNombreOeuvres()
        );
        entity.setPeriode(dto.getPeriode());
        entity.setOrigine(dto.getOrigine());

        entity.setThemes(
                dto.getThemes() != null
                        ? new ArrayList<>(dto.getThemes())
                        : new ArrayList<>()
        );

        return entity;
    }

    // ============================================================
    // EXPOSITION
    // ============================================================

    private ExpositionEntity toExpositionEntity(
            Exposition dto) {

        ExpositionEntity entity =
                new ExpositionEntity();

        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setCommissaire(dto.getCommissaire());
        entity.setLieu(dto.getLieu());
        entity.setExpositionPermanente(
                dto.getExpositionPermanente()
        );

        entity.setImageUrl(
                dto.getImageUrl() != null
                        ? dto.getImageUrl().toString()
                        : null
        );

        return entity;
    }

    // ============================================================
    // ESPECE PROTEGEE
    // ============================================================

    private EspeceProtegeeEntity toEspeceProtegeeEntity(
            EspeceProtegee dto) {

        EspeceProtegeeEntity entity =
                new EspeceProtegeeEntity();

        entity.setNomCommun(
                dto.getNomCommun()
        );

        entity.setNomScientifique(
                dto.getNomScientifique()
        );

        entity.setNomLocal(
                dto.getNomLocal()
        );

        entity.setType(dto.getType() != null ? dto.getType().getValue() : null);

        entity.setStatutConservation(
                dto.getStatutConservation()
        );

        entity.setNiveauProtection(
                dto.getNiveauProtection()
        );

        entity.setDescription(
                dto.getDescription()
        );

        entity.setPhotoUrl(
                dto.getPhotoUrl() != null
                        ? dto.getPhotoUrl().toString()
                        : null
        );

        return entity;
    }

    // ============================================================
    // ENTITY -> DTO
    // ============================================================

    public Monument toMonumentDto(
            MonumentEntity entity) {

        if (entity == null) {
            return null;
        }

        Monument dto = new Monument();

        mapCommonEntityToDto(entity, dto);

        dto.setStyleArchitectural(
                entity.getStyleArchitectural()
        );

        dto.setAnneeConstruction(
                entity.getAnneeConstruction()
        );

        /*
        dto.setAnneeRenovation(
                entity.getAnneeRenovation()
        );

        dto.setArchitecte(
                entity.getArchitecte()
        );
        */

        dto.setIdentiteArchitecte(
                entity.getIdentiteArchitecte()
        );

        if (entity.getNatureMonument() != null) {
            dto.setNatureMonument(
                    nullOnInvalid(() -> com.pgvpt.dto.NatureMonument.fromValue(entity.getNatureMonument()))
            );
        }

        /*
        dto.setDimensions(entity.getDimensions());
        dto.setCommanditaire(entity.getCommanditaire());
        dto.setContexteHistorique(
                entity.getContexteHistorique()
        );

        dto.setMateriauxConstruction(
                entity.getMateriauxConstruction()
        );

        dto.setPersonnagesAssocies(
                entity.getPersonnagesAssocies()
        );
        */

        return dto;
    }

    public Musee toMuseeDto(
            MuseeEntity entity) {

        if (entity == null) {
            return null;
        }

        Musee dto = new Musee();

        mapCommonEntityToDto(entity, dto);

        dto.setNombreCollections(
                entity.getNombreCollections()
        );

        /*
        dto.setNombreOeuvres(
                entity.getNombreOeuvres()
        );

        dto.setTypesCollections(
                entity.getTypesCollections()
        );

        dto.setServicesMusee(
                entity.getServicesMusee()
        );

        dto.setCapaciteAccueil(
                entity.getCapaciteAccueil()
        );

        dto.setMuseographie(
                entity.getMuseographie()
        );
        */

        if (entity.getCollections() != null) {

            List<CollectionMusee> collections =
                    new ArrayList<>();

            for (CollectionMuseeEntity collectionEntity
                    : entity.getCollections()) {

                collections.add(
                        toCollectionMuseeDto(
                                collectionEntity
                        )
                );
            }

            dto.setCollections(collections);
        }

        if (entity.getExpositions() != null) {

            List<Exposition> expositions =
                    new ArrayList<>();

            for (ExpositionEntity expositionEntity
                    : entity.getExpositions()) {

                expositions.add(
                        toExpositionDto(
                                expositionEntity
                        )
                );
            }

            dto.setExpositions(expositions);
        }

        return dto;
    }

    public SiteNaturel toSiteNaturelDto(
            SiteNaturelEntity entity) {

        if (entity == null) {
            return null;
        }

        SiteNaturel dto = new SiteNaturel();

        mapCommonEntityToDto(entity, dto);

        dto.setSuperficie(
                entity.getSuperficie()
        );

        if (entity.getNatureSite() != null) {
            dto.setNatureSite(
                    nullOnInvalid(() -> com.pgvpt.dto.NatureSiteNaturel.fromValue(entity.getNatureSite()))
            );
        }

        dto.setEcosysteme(
                entity.getEcosysteme()
        );

        dto.setBiodiversite(
                entity.getBiodiversite()
        );

        dto.setZoneProtegee(
                entity.getZoneProtegee()
        );

        dto.setCategorieProtection(
                entity.getCategorieProtection()
        );

        dto.setRisquesEnvironnementaux(
                entity.getRisquesEnvironnementaux()
        );

        dto.setRessourcesNaturelles(
                entity.getRessourcesNaturelles()
        );

        dto.setActivitesEcotouristiques(
                entity.getActivitesEcotouristiques()
        );

        dto.setCapaciteAccueil(
                entity.getCapaciteAccueil()
        );

        if (entity.getEspecesProtegees() != null) {

            List<EspeceProtegee> especes =
                    new ArrayList<>();

            for (EspeceProtegeeEntity especeEntity
                    : entity.getEspecesProtegees()) {

                especes.add(
                        toEspeceProtegeeDto(
                                especeEntity
                        )
                );
            }

            dto.setEspecesProtegees(especes);
        }

        return dto;
    }

    // ============================================================
    // COMMON ENTITY -> DTO
    // ============================================================

    private void mapCommonEntityToDto(
            PatrimoineEntity entity,
            Patrimoine dto) {

        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setNom(entity.getNom());
        dto.setNomLocal(entity.getNomLocal());
        dto.setNomHistorique(entity.getNomHistorique());
        dto.setType(entity.getType());

        if (entity.getCategorie() != null) {
            dto.setCategorie(
                    nullOnInvalid(() -> com.pgvpt.dto.CategoriePatrimoine.fromValue(entity.getCategorie()))
            );
        }

        dto.setSousCategorie(
                entity.getSousCategorie()
        );

        if (entity.getPeriode() != null) {
            dto.setPeriode(
                    nullOnInvalid(() -> com.pgvpt.dto.PeriodeHistorique.fromValue(entity.getPeriode()))
            );
        }

        dto.setSiecle(entity.getSiecle());
        dto.setDateOuverture(entity.getDateOuverture());

        dto.setDescription(entity.getDescription());
        dto.setDescriptionCourte(
                entity.getDescriptionCourte()
        );
        dto.setHistorique(entity.getHistorique());

        dto.setImportanceHistorique(
                entity.getImportanceHistorique()
        );

        dto.setImportanceCulturelle(
                entity.getImportanceCulturelle()
        );

        dto.setImportanceTouristique(
                entity.getImportanceTouristique()
        );

        dto.setValeurPatrimoniale(
                entity.getValeurPatrimoniale()
        );

        dto.setValeurSpirituelle(
                entity.getValeurSpirituelle()
        );

        dto.setTraditionsAssociees(
                entity.getTraditionsAssociees()
        );

        dto.setLangues(
                entity.getLangues()
        );

        if (entity.getEtatConservation() != null) {
            dto.setEtatConservation(
                    nullOnInvalid(() -> com.pgvpt.dto.EtatConservation.fromValue(entity.getEtatConservation()))
            );
        }

        if (entity.getStatut() != null) {
            dto.setStatut(
                    nullOnInvalid(() -> com.pgvpt.dto.StatutPatrimoine.fromValue(entity.getStatut()))
            );
        }

        dto.setClassePatrimoine(
                entity.getClassePatrimoine()
        );

        dto.setReferenceClassement(
                entity.getReferenceClassement()
        );

        dto.setDateClassement(
                entity.getDateClassement()
        );

        dto.setInscritUnesco(
                entity.getInscritUnesco()
        );

        dto.setNomSiteUnesco(
                entity.getNomSiteUnesco()
        );

        dto.setDateInscriptionUnesco(
                entity.getDateInscriptionUnesco()
        );

        dto.setProtectionJuridique(
                entity.getProtectionJuridique()
        );

        dto.setOrganismeGestionnaire(
                entity.getOrganismeGestionnaire()
        );

        dto.setProprietaire(
                entity.getProprietaire()
        );

        dto.setGestionnaire(
                entity.getGestionnaire()
        );

        dto.setSiteWeb(
                entity.getSiteWeb() != null
                        ? java.net.URI.create(
                                entity.getSiteWeb()
                        )
                        : null
        );

        dto.setEmail(entity.getEmail());
        dto.setTelephone(entity.getTelephone());

        dto.setMeilleurePeriodeVisite(
                entity.getMeilleurePeriodeVisite()
        );

        dto.setDureeVisiteEstimeeMinutes(
                entity.getDureeVisiteEstimeeMinutes()
        );

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setPublishedAt(entity.getPublishedAt());

        // Contact
        if (entity.getContact() != null) {
            dto.setContact(
                    toContactDto(
                            entity.getContact()
                    )
            );
        }

        // Accessibilité
        if (entity.getAccessibilite() != null) {
            dto.setAccessibilite(
                    toAccessibiliteDto(
                            entity.getAccessibilite()
                    )
            );
        }

        // Tarification
        if (entity.getTarification() != null) {
            dto.setTarification(
                    toTarificationDto(
                            entity.getTarification()
                    )
            );
        }

        // Conservation
        if (entity.getConservation() != null) {
            dto.setConservation(
                    toConservationDto(
                            entity.getConservation()
                    )
            );
        }

        // Horaires
        if (entity.getHoraires() != null) {

            List<HoraireOuverture> horaires =
                    new ArrayList<>();

            for (HoraireOuvertureEntity horaireEntity
                    : entity.getHoraires()) {

                horaires.add(
                        toHoraireDto(
                                horaireEntity
                        )
                );
            }

            dto.setHoraires(horaires);
        }

        // Photos
        if (entity.getPhotos() != null) {

            List<com.pgvpt.dto.Photo> photos =
                    new ArrayList<>();

            for (PhotoEntity photoEntity
                    : entity.getPhotos()) {

                photos.add(
                        toPhotoDto(
                                photoEntity
                        )
                );
            }

            dto.setPhotos(photos);
        }

        // Médias
        if (entity.getMedias() != null) {

            List<Media> medias =
                    new ArrayList<>();

            for (MediaEntity mediaEntity
                    : entity.getMedias()) {

                medias.add(
                        toMediaDto(
                                mediaEntity
                        )
                );
            }

            dto.setMedias(medias);
        }

        // Menaces
        if (entity.getMenaces() != null) {

            List<Menace> menaces =
                    new ArrayList<>();

            for (MenaceValue menaceValue
                    : entity.getMenaces()) {

                menaces.add(
                        toMenaceDto(menaceValue)
                );
            }

            dto.setMenaces(menaces);
        }

        dto.setRecommandationsVisiteur(
                entity.getRecommandationsVisiteur()
        );

        dto.setReglesVisite(
                entity.getReglesVisite()
        );

        dto.setActivitesTouristiques(
                entity.getActivitesTouristiques()
        );
    }

    // ============================================================
    // OBJECTS -> DTO
    // ============================================================

    private Contact toContactDto(
            ContactEntity entity) {

        Contact dto = new Contact();

        dto.setNom(entity.getNom());
        dto.setFonction(entity.getFonction());
        dto.setTelephone(entity.getTelephone());
        dto.setEmail(entity.getEmail());

        dto.setSiteWeb(
                entity.getSiteWeb() != null
                        ? java.net.URI.create(
                                entity.getSiteWeb()
                        )
                        : null
        );

        return dto;
    }

    private Accessibilite toAccessibiliteDto(
            AccessibiliteEntity entity) {

        Accessibilite dto =
                new Accessibilite();

        dto.setAccessiblePublic(
                entity.getAccessiblePublic()
        );

        dto.setAccessibilitePMR(
                entity.getAccessibilitePMR()
        );

        dto.setAccesFauteuilRoulant(
                entity.getAccesFauteuilRoulant()
        );

        dto.setAccesTransportPublic(
                entity.getAccesTransportPublic()
        );

        dto.setParking(
                entity.getParking()
        );

        dto.setGuideDisponible(
                entity.getGuideDisponible()
        );

        dto.setGuideAudio(
                entity.getGuideAudio()
        );

        dto.setLangueVisite(
                entity.getLangueVisite()
        );

        dto.setAccesEnfant(
                entity.getAccesEnfant()
        );

        dto.setConditionsAcces(
                entity.getConditionsAcces()
        );

        dto.setRestrictions(
                entity.getRestrictions()
        );

        return dto;
    }

    private Tarification toTarificationDto(
            TarificationEntity entity) {

        Tarification dto =
                new Tarification();

        dto.setGratuit(entity.getGratuit());
        dto.setDevise(entity.getDevise());

        dto.setTarifNormal(
                entity.getTarifNormal() != null
                        ? entity.getTarifNormal().doubleValue()
                        : null
        );

        dto.setTarifEnfant(
                entity.getTarifEnfant() != null
                        ? entity.getTarifEnfant().doubleValue()
                        : null
        );

        dto.setTarifEtudiant(
                entity.getTarifEtudiant() != null
                        ? entity.getTarifEtudiant().doubleValue()
                        : null
        );

        dto.setTarifResident(
                entity.getTarifResident() != null
                        ? entity.getTarifResident().doubleValue()
                        : null
        );

        dto.setTarifGroupe(
                entity.getTarifGroupe() != null
                        ? entity.getTarifGroupe().doubleValue()
                        : null
        );

        dto.setTarifGuide(
                entity.getTarifGuide() != null
                        ? entity.getTarifGuide().doubleValue()
                        : null
        );

        dto.setConditionsTarifaires(
                entity.getConditionsTarifaires()
        );

        return dto;
    }

    private Conservation toConservationDto(
            ConservationEntity entity) {

        Conservation dto =
                new Conservation();

        if (entity.getEtat() != null) {
            dto.setEtat(
                    nullOnInvalid(() -> com.pgvpt.dto.EtatConservation.fromValue(entity.getEtat()))
            );
        }

        dto.setDateEvaluation(
                entity.getDateEvaluation()
        );

        dto.setEvaluateur(
                entity.getEvaluateur()
        );

        dto.setDescriptionEtat(
                entity.getDescriptionEtat()
        );

        dto.setDegradations(
                entity.getDegradations()
        );

        dto.setCausesDegradation(
                entity.getCausesDegradation()
        );

        dto.setTravauxNecessaires(
                entity.getTravauxNecessaires()
        );

        dto.setDernierTravaux(
                entity.getDernierTravaux()
        );

        dto.setDateDerniersTravaux(
                entity.getDateDerniersTravaux()
        );

        dto.setOrganismeConservation(
                entity.getOrganismeConservation()
        );

        dto.setBudgetEstime(
                entity.getBudgetEstime() != null
                        ? entity.getBudgetEstime().doubleValue()
                        : null
        );

        dto.setRecommandations(
                entity.getRecommandations()
        );

        return dto;
    }

    private HoraireOuverture toHoraireDto(
            HoraireOuvertureEntity entity) {

        HoraireOuverture dto =
                new HoraireOuverture();

        if (entity.getJour() != null) {
            dto.setJour(
                    nullOnInvalid(() -> com.pgvpt.dto.JourSemaine.fromValue(entity.getJour()))
            );
        }

        dto.setOuvert(entity.getOuvert());

        dto.setHeureOuverture(
                entity.getHeureOuverture() != null
                        ? entity.getHeureOuverture().toString()
                        : null
        );

        dto.setHeureFermeture(
                entity.getHeureFermeture() != null
                        ? entity.getHeureFermeture().toString()
                        : null
        );

        dto.setPauseDebut(
                entity.getPauseDebut() != null
                        ? entity.getPauseDebut().toString()
                        : null
        );

        dto.setPauseFin(
                entity.getPauseFin() != null
                        ? entity.getPauseFin().toString()
                        : null
        );

        dto.setSurReservation(
                entity.getSurReservation()
        );

        dto.setCommentaire(
                entity.getCommentaire()
        );

        return dto;
    }

    private com.pgvpt.dto.Photo toPhotoDto(
            PhotoEntity entity) {

        com.pgvpt.dto.Photo dto =
                new com.pgvpt.dto.Photo();

        dto.setId(entity.getId());

        dto.setUrl(
                entity.getUrl() != null
                        ? java.net.URI.create(
                                entity.getUrl()
                        )
                        : null
        );

        dto.setMiniatureUrl(
                entity.getMiniatureUrl() != null
                        ? java.net.URI.create(
                                entity.getMiniatureUrl()
                        )
                        : null
        );

        dto.setTitre(entity.getTitre());
        dto.setDescription(entity.getDescription());
        dto.setAuteur(entity.getAuteur());
        dto.setDatePrise(entity.getDatePrise());
        dto.setDroits(entity.getDroits());
        dto.setLicence(entity.getLicence());
        dto.setPhotoPrincipale(
                entity.getPhotoPrincipale()
        );
        dto.setOrdre(entity.getOrdre());

        return dto;
    }

    private Media toMediaDto(
            MediaEntity entity) {

        Media dto = new Media();

        dto.setId(entity.getId());

        if (entity.getType() != null) {
            dto.setType(
                    nullOnInvalid(() -> com.pgvpt.dto.TypeMedia.fromValue(entity.getType()))
            );
        }

        dto.setTitre(entity.getTitre());
        dto.setDescription(entity.getDescription());

        dto.setUrl(
                entity.getUrl() != null
                        ? java.net.URI.create(
                                entity.getUrl()
                        )
                        : null
        );

        dto.setMiniatureUrl(
                entity.getMiniatureUrl() != null
                        ? java.net.URI.create(
                                entity.getMiniatureUrl()
                        )
                        : null
        );

        dto.setDuree(entity.getDuree());
        dto.setAuteur(entity.getAuteur());
        dto.setLicence(entity.getLicence());

        return dto;
    }

    private Menace toMenaceDto(
            MenaceValue value) {

        Menace dto = new Menace();

        dto.setType(value.getType());
        dto.setNiveau(value.getNiveau() != null ? nullOnInvalid(() -> com.pgvpt.dto.Menace.NiveauEnum.fromValue(value.getNiveau())) : null);
        dto.setDescription(value.getDescription());
        dto.setMesuresPrevention(
                value.getMesuresPrevention()
        );

        return dto;
    }

    private CollectionMusee toCollectionMuseeDto(
            CollectionMuseeEntity entity) {

        CollectionMusee dto =
                new CollectionMusee();

        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setDescription(
                entity.getDescription()
        );
        dto.setNombreOeuvres(
                entity.getNombreOeuvres()
        );
        dto.setPeriode(entity.getPeriode());
        dto.setOrigine(entity.getOrigine());
        dto.setThemes(entity.getThemes());

        return dto;
    }

    private Exposition toExpositionDto(
            ExpositionEntity entity) {

        Exposition dto =
                new Exposition();

        dto.setId(entity.getId());
        dto.setTitre(entity.getTitre());
        dto.setDescription(
                entity.getDescription()
        );
        dto.setDateDebut(
                entity.getDateDebut()
        );
        dto.setDateFin(
                entity.getDateFin()
        );
        dto.setCommissaire(
                entity.getCommissaire()
        );
        dto.setLieu(entity.getLieu());
        dto.setExpositionPermanente(
                entity.getExpositionPermanente()
        );

        dto.setImageUrl(
                entity.getImageUrl() != null
                        ? java.net.URI.create(
                                entity.getImageUrl()
                        )
                        : null
        );

        return dto;
    }

    private EspeceProtegee toEspeceProtegeeDto(
            EspeceProtegeeEntity entity) {

        EspeceProtegee dto =
                new EspeceProtegee();

        dto.setId(entity.getId());
        dto.setNomCommun(
                entity.getNomCommun()
        );
        dto.setNomScientifique(
                entity.getNomScientifique()
        );
        dto.setNomLocal(
                entity.getNomLocal()
        );
        dto.setType(entity.getType() != null ? nullOnInvalid(() -> com.pgvpt.dto.EspeceProtegee.TypeEnum.fromValue(entity.getType())) : null);
        dto.setStatutConservation(
                entity.getStatutConservation()
        );
        dto.setNiveauProtection(
                entity.getNiveauProtection()
        );
        dto.setDescription(
                entity.getDescription()
        );

        dto.setPhotoUrl(
                entity.getPhotoUrl() != null
                        ? java.net.URI.create(
                                entity.getPhotoUrl()
                        )
                        : null
        );

        return dto;
    }
    // ============================================================
    // LOGIQUE DE MISE À JOUR (UPDATE)
    // ============================================================

    public void updateMonumentEntity(com.pgvpt.dto.MonumentUpdate dto, MonumentEntity entity) {
        if (dto == null) return;
        updatePatrimoineFields(dto, entity);
        if (dto.getStyleArchitectural() != null) entity.setStyleArchitectural(dto.getStyleArchitectural());
        if (dto.getAnneeConstruction() != null) entity.setAnneeConstruction(dto.getAnneeConstruction());
        if (dto.getIdentiteArchitecte() != null) entity.setIdentiteArchitecte(dto.getIdentiteArchitecte());
        if (dto.getNatureMonument() != null) entity.setNatureMonument(dto.getNatureMonument().getValue());
    }

    public void updateMuseeEntity(com.pgvpt.dto.MuseeUpdate dto, MuseeEntity entity) {
        if (dto == null) return;
        updatePatrimoineFields(dto, entity);
        if (dto.getNombreCollections() != null) entity.setNombreCollections(dto.getNombreCollections());
        if (dto.getCollections() != null) replaceCollections(dto.getCollections(), entity);
        if (dto.getExpositions() != null) replaceExpositions(dto.getExpositions(), entity);
    }

    public void updateSiteNaturelEntity(com.pgvpt.dto.SiteNaturelUpdate dto, SiteNaturelEntity entity) {
        if (dto == null) return;
        updatePatrimoineFields(dto, entity);
        if (dto.getSuperficie() != null) entity.setSuperficie(dto.getSuperficie());
        if (dto.getNatureSite() != null) entity.setNatureSite(dto.getNatureSite().getValue());
        if (dto.getEcosysteme() != null) entity.setEcosysteme(dto.getEcosysteme());
        if (dto.getEspecesProtegees() != null) replaceEspecesProtegees(dto.getEspecesProtegees(), entity);
    }

    private void updatePatrimoineFields(com.pgvpt.dto.PatrimoineUpdate dto, PatrimoineEntity entity) {
        if (dto == null) return;

        if (dto.getCode() != null) entity.setCode(dto.getCode());
        if (dto.getNom() != null) entity.setNom(dto.getNom());
        if (dto.getNomLocal() != null) entity.setNomLocal(dto.getNomLocal());
        if (dto.getNomHistorique() != null) entity.setNomHistorique(dto.getNomHistorique());
        if (dto.getCategorie() != null) entity.setCategorie(dto.getCategorie().getValue());
        if (dto.getSousCategorie() != null) entity.setSousCategorie(dto.getSousCategorie());
        if (dto.getPeriode() != null) entity.setPeriode(dto.getPeriode().getValue());
        if (dto.getSiecle() != null) entity.setSiecle(dto.getSiecle());
        if (dto.getDateOuverture() != null) entity.setDateOuverture(dto.getDateOuverture());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getDescriptionCourte() != null) entity.setDescriptionCourte(dto.getDescriptionCourte());
        if (dto.getHistorique() != null) entity.setHistorique(dto.getHistorique());
        if (dto.getImportanceHistorique() != null) entity.setImportanceHistorique(dto.getImportanceHistorique());
        if (dto.getImportanceCulturelle() != null) entity.setImportanceCulturelle(dto.getImportanceCulturelle());
        if (dto.getImportanceTouristique() != null) entity.setImportanceTouristique(dto.getImportanceTouristique());
        if (dto.getValeurPatrimoniale() != null) entity.setValeurPatrimoniale(dto.getValeurPatrimoniale());
        if (dto.getValeurSpirituelle() != null) entity.setValeurSpirituelle(dto.getValeurSpirituelle());
        if (dto.getClassePatrimoine() != null) entity.setClassePatrimoine(dto.getClassePatrimoine());
        if (dto.getReferenceClassement() != null) entity.setReferenceClassement(dto.getReferenceClassement());
        if (dto.getDateClassement() != null) entity.setDateClassement(dto.getDateClassement());
        if (dto.getInscritUnesco() != null) entity.setInscritUnesco(dto.getInscritUnesco());
        if (dto.getNomSiteUnesco() != null) entity.setNomSiteUnesco(dto.getNomSiteUnesco());
        if (dto.getDateInscriptionUnesco() != null) entity.setDateInscriptionUnesco(dto.getDateInscriptionUnesco());
        if (dto.getProtectionJuridique() != null) entity.setProtectionJuridique(dto.getProtectionJuridique());
        if (dto.getOrganismeGestionnaire() != null) entity.setOrganismeGestionnaire(dto.getOrganismeGestionnaire());
        if (dto.getProprietaire() != null) entity.setProprietaire(dto.getProprietaire());
        if (dto.getGestionnaire() != null) entity.setGestionnaire(dto.getGestionnaire());
        
        if (dto.getContact() != null) entity.setContact(toContactEntity(dto.getContact()));
        if (dto.getSiteWeb() != null) entity.setSiteWeb(dto.getSiteWeb().toString());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getTelephone() != null) entity.setTelephone(dto.getTelephone());
        if (dto.getAccessibilite() != null) entity.setAccessibilite(toAccessibiliteEntity(dto.getAccessibilite()));
        if (dto.getTarification() != null) entity.setTarification(toTarificationEntity(dto.getTarification()));
        
        if (dto.getEtatConservation() != null) entity.setEtatConservation(dto.getEtatConservation().getValue());
        if (dto.getConservation() != null) entity.setConservation(toConservationEntity(dto.getConservation()));
        
        if (dto.getMeilleurePeriodeVisite() != null) entity.setMeilleurePeriodeVisite(dto.getMeilleurePeriodeVisite());
        if (dto.getDureeVisiteEstimeeMinutes() != null) entity.setDureeVisiteEstimeeMinutes(dto.getDureeVisiteEstimeeMinutes());
        
        if (dto.getStatut() != null) entity.setStatut(dto.getStatut().getValue());
        
        if (dto.getTraditionsAssociees() != null) entity.setTraditionsAssociees(new ArrayList<>(dto.getTraditionsAssociees()));
        if (dto.getLangues() != null) entity.setLangues(new ArrayList<>(dto.getLangues()));
        if (dto.getRecommandationsVisiteur() != null) entity.setRecommandationsVisiteur(new ArrayList<>(dto.getRecommandationsVisiteur()));
        if (dto.getReglesVisite() != null) entity.setReglesVisite(new ArrayList<>(dto.getReglesVisite()));
        if (dto.getActivitesTouristiques() != null) entity.setActivitesTouristiques(new ArrayList<>(dto.getActivitesTouristiques()));
        if (dto.getMenaces() != null) entity.setMenaces(dto.getMenaces().stream().map(this::toMenaceValue).collect(java.util.stream.Collectors.toCollection(ArrayList::new)));
        if (dto.getHoraires() != null) replaceHoraires(dto.getHoraires(), entity);
        if (dto.getPhotos() != null) replacePhotos(dto.getPhotos(), entity);
        if (dto.getMedias() != null) replaceMedias(dto.getMedias(), entity);
    }

    private void replaceHoraires(List<HoraireOuverture> dtos, PatrimoineEntity entity) {
        entity.getHoraires().clear();
        for (HoraireOuverture dto : dtos) { HoraireOuvertureEntity value = toHoraireEntity(dto); value.setPatrimoine(entity); entity.getHoraires().add(value); }
    }

    private void replacePhotos(List<com.pgvpt.dto.Photo> dtos, PatrimoineEntity entity) {
        entity.getPhotos().clear();
        for (com.pgvpt.dto.Photo dto : dtos) { PhotoEntity value = toPhotoEntity(dto); value.setPatrimoine(entity); entity.getPhotos().add(value); }
    }

    private void replaceMedias(List<Media> dtos, PatrimoineEntity entity) {
        entity.getMedias().clear();
        for (Media dto : dtos) { MediaEntity value = toMediaEntity(dto); value.setPatrimoine(entity); entity.getMedias().add(value); }
    }

    private void replaceCollections(List<CollectionMusee> dtos, MuseeEntity entity) {
        entity.getCollections().clear();
        for (CollectionMusee dto : dtos) { CollectionMuseeEntity value = toCollectionMuseeEntity(dto); value.setMusee(entity); entity.getCollections().add(value); }
    }

    private void replaceExpositions(List<Exposition> dtos, MuseeEntity entity) {
        entity.getExpositions().clear();
        for (Exposition dto : dtos) { ExpositionEntity value = toExpositionEntity(dto); value.setMusee(entity); entity.getExpositions().add(value); }
    }

    private void replaceEspecesProtegees(List<EspeceProtegee> dtos, SiteNaturelEntity entity) {
        entity.getEspecesProtegees().clear();
        for (EspeceProtegee dto : dtos) { EspeceProtegeeEntity value = toEspeceProtegeeEntity(dto); value.setSiteNaturel(entity); entity.getEspecesProtegees().add(value); }
    }

    private <T> T nullOnInvalid(Supplier<T> converter) {
        try {
            return converter.get();
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
