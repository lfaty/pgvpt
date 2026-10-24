import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, switchMap, forkJoin, of, catchError, shareReplay, tap } from 'rxjs';
import { PatrimoineFacadeService } from './patrimoine-facade.service';
import { PatrimoineUI } from '../models/patrimoine.ui';
import { 
  Circuit, 
  CircuitCreateRequest,
  CircuitUpdateRequest,
  EtapeCircuit, 
  EtapeCircuitCreateRequest,
  EtapeCircuitUpdateRequest,
  ReordonnerEtapesRequest,
  OrdreEtape,
  CalculDistanceCircuitResponse,
  CalculDureeCircuitResponse,
  CalculPrixCircuitResponse,
  NiveauDifficulte, 
  TypeCircuit, 
  StatutCircuit, 
  Activite,
  ActiviteCreateRequest,
  ActiviteUpdateRequest
} from '../openapi/circuit';

// Réexport des types OpenAPI pour utilisation directe dans les composants
export { 
  Circuit, 
  CircuitCreateRequest,
  CircuitUpdateRequest,
  EtapeCircuit, 
  EtapeCircuitCreateRequest,
  EtapeCircuitUpdateRequest,
  ReordonnerEtapesRequest,
  OrdreEtape,
  CalculDistanceCircuitResponse,
  CalculDureeCircuitResponse,
  CalculPrixCircuitResponse,
  NiveauDifficulte, 
  TypeCircuit, 
  StatutCircuit, 
  Activite,
  ActiviteCreateRequest,
  ActiviteUpdateRequest
};

export interface EtapeCircuitDetail extends EtapeCircuit {
  patrimoineDetail?: PatrimoineUI;
  activiteDetail?: Activite;
  latitude?: number;
  longitude?: number;
}

export interface CircuitUI extends Circuit {
  etapes?: EtapeCircuitDetail[];
  imageUrl?: string;
  pointsGps?: { lat: number; lng: number; label: string; ordre: number }[];
}

@Injectable({
  providedIn: 'root'
})
export class CircuitFacadeService {

  private circuitsUrl = '/api/v1/circuits';
  private circuits$: Observable<CircuitUI[]> | null = null;
  private activites$: Observable<Activite[]> | null = null;

  constructor(
    private http: HttpClient,
    private patrimoineFacade: PatrimoineFacadeService
  ) { }

  invalidateCircuitsCache(): void {
    this.circuits$ = null;
  }

  invalidateActivitesCache(): void {
    this.activites$ = null;
  }

  /**
   * Récupère la liste de tous les circuits avec photo et données prêtes pour l'affichage,
   * enrichis avec leurs étapes pour calculer automatiquement nombreEtapes, dureeMinutes, distanceKm et coutTotal.
   */
  getCircuits(forceRefresh: boolean = false): Observable<CircuitUI[]> {
    if (this.circuits$ && !forceRefresh) {
      return this.circuits$;
    }

    this.circuits$ = this.http.get<any>(this.circuitsUrl).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      switchMap((circuits: Circuit[]) => {
        if (!circuits || circuits.length === 0) return of([]);

        const enrichedObservables = circuits.map((c, index) => {
          if (!c.id) {
            return of({
              ...c,
              imageUrl: 'assets/images/activite_saloum.jpg',
              nombreEtapes: c.nombreEtapes ?? 0,
              dureeMinutes: c.dureeMinutes ?? 0,
              distanceKm: c.distanceKm ?? 0,
              coutTotal: c.coutTotal ?? 0
            } as CircuitUI);
          }

          return this.getEtapesByCircuitId(c.id).pipe(
            map(etapes => {
              const defaultImages = [
                'assets/images/activite_saloum.jpg',
                'assets/images/activite_goree.jpg',
                'assets/images/activite_bandia.jpg',
                'assets/images/pont_faidherbe.jpg'
              ];
              const fallbackImage = defaultImages[index % defaultImages.length];

              const totalDuree = (c.dureeMinutes && c.dureeMinutes > 0)
                ? c.dureeMinutes
                : etapes.reduce((acc, e) => acc + (e.dureeMinutes || 0), 0);

              const totalDistance = (c.distanceKm && c.distanceKm > 0)
                ? c.distanceKm
                : etapes.reduce((acc, e) => acc + (e.distanceDepuisPrecedenteKm || 0), 0);

              const totalPrix = (c.coutTotal && c.coutTotal > 0)
                ? c.coutTotal
                : etapes.reduce((acc, e) => acc + (e.prix || 0), 0);

              return {
                ...c,
                imageUrl: fallbackImage,
                etapes: etapes,
                nombreEtapes: c.nombreEtapes ?? etapes.length,
                dureeMinutes: totalDuree,
                distanceKm: totalDistance,
                coutTotal: totalPrix
              } as CircuitUI;
            }),
            catchError(() => of({
              ...c,
              imageUrl: 'assets/images/activite_saloum.jpg',
              nombreEtapes: c.nombreEtapes ?? 0,
              dureeMinutes: c.dureeMinutes ?? 0,
              distanceKm: c.distanceKm ?? 0,
              coutTotal: c.coutTotal ?? 0
            } as CircuitUI))
          );
        });

        return forkJoin(enrichedObservables);
      }),
      shareReplay(1)
    );

    return this.circuits$;
  }

  /**
   * Récupère un circuit par son ID
   */
  getCircuitById(id: string): Observable<Circuit | undefined> {
    return this.http.get<Circuit>(`${this.circuitsUrl}/${id}`);
  }

  /**
   * Récupère les étapes d'un circuit triées par leur ordre (1, 2, 3...)
   */
  getEtapesByCircuitId(circuitId: string): Observable<EtapeCircuit[]> {
    return this.http.get<any>(`${this.circuitsUrl}/${circuitId}/etapes`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      map((etapes: EtapeCircuit[]) => etapes.sort((a, b) => a.ordre - b.ordre))
    );
  }

  /**
   * Récupère les activités disponibles (/api/v1/circuits/activites)
   */
  getActivites(forceRefresh: boolean = false): Observable<Activite[]> {
    if (this.activites$ && !forceRefresh) {
      return this.activites$;
    }

    this.activites$ = this.http.get<any>(`${this.circuitsUrl}/activites`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      shareReplay(1)
    );

    return this.activites$;
  }

  /**
   * Formate une durée en minutes en texte convivial (ex: 150 -> "2h 30min")
   */
  formatDuree(minutes?: number): string {
    if (!minutes || minutes <= 0) return 'Non précisée';
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    if (h === 0) return `${m} min`;
    if (m === 0) return `${h}h`;
    return `${h}h ${m}min`;
  }

  /**
   * Récupère un circuit complet avec toutes ses étapes détaillées,
   * les patrimoines associés et les coordonnées GPS pour la carte Leaflet.
   */
  getCircuitDetail(id: string): Observable<CircuitUI | undefined> {
    return this.getCircuitById(id).pipe(
      switchMap(circuit => {
        if (!circuit) return of(undefined);

        return this.getEtapesByCircuitId(id).pipe(
          switchMap(etapes => {
            if (etapes.length === 0) {
              const detail: CircuitUI = { 
                ...circuit, 
                etapes: [], 
                pointsGps: [],
                imageUrl: 'assets/images/activite_saloum.jpg' 
              };
              return of(detail);
            }

            // Charger les détails du patrimoine pour chaque étape
            const etapesObservables = etapes.map(etape => 
              this.patrimoineFacade.getPatrimoineById(etape.patrimoineId).pipe(
                map(patrimoine => {
                  return {
                    ...etape,
                    patrimoineDetail: patrimoine,
                    latitude: patrimoine?.latitude,
                    longitude: patrimoine?.longitude
                  } as EtapeCircuitDetail;
                }),
                catchError(() => of({ ...etape } as EtapeCircuitDetail))
              )
            );

            return forkJoin(etapesObservables).pipe(
              map(etapesDetails => {
                // Construction des points GPS ordonnés pour le tracé de la carte
                const pointsGps = etapesDetails
                  .filter(e => e.latitude != null && e.longitude != null)
                  .map(e => ({
                    lat: e.latitude!,
                    lng: e.longitude!,
                    label: `${e.ordre}. ${e.patrimoineDetail?.nom || 'Étape ' + e.ordre}`,
                    ordre: e.ordre
                  }));

                // Image de couverture : photo principale du 1er patrimoine ou image par défaut
                const coverImage = etapesDetails[0]?.patrimoineDetail?.imageUrl
                  || (etapesDetails[0]?.patrimoineDetail as any)?.photos?.[0]?.url
                  || 'assets/images/activite_saloum.jpg';

                return {
                  ...circuit,
                  etapes: etapesDetails,
                  pointsGps: pointsGps,
                  imageUrl: coverImage
                } as CircuitUI;
              })
            );
          })
        );
      })
    );
  }

  // =============================================
  // ADMINISTRATION & CRUD CIRCUITS
  // =============================================

  /**
   * Créer un nouveau circuit (POST /api/v1/circuits)
   */
  createCircuit(circuit: CircuitCreateRequest): Observable<Circuit> {
    return this.http.post<Circuit>(this.circuitsUrl, circuit).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Mettre à jour un circuit existant (PUT /api/v1/circuits/{id})
   */
  updateCircuit(id: string, circuit: CircuitUpdateRequest): Observable<Circuit> {
    return this.http.put<Circuit>(`${this.circuitsUrl}/${id}`, circuit).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Supprimer un circuit (DELETE /api/v1/circuits/{id})
   */
  deleteCircuit(id: string): Observable<void> {
    return this.http.delete<void>(`${this.circuitsUrl}/${id}`).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Publier un circuit (PATCH /api/v1/circuits/{id}/publier)
   */
  publierCircuit(id: string): Observable<Circuit> {
    return this.http.patch<Circuit>(`${this.circuitsUrl}/${id}/publier`, {}).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Dépublier un circuit (PATCH /api/v1/circuits/{id}/depublier)
   */
  depublierCircuit(id: string): Observable<Circuit> {
    return this.http.patch<Circuit>(`${this.circuitsUrl}/${id}/depublier`, {}).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Archiver un circuit (PATCH /api/v1/circuits/{id}/archiver)
   */
  archiverCircuit(id: string): Observable<Circuit> {
    return this.http.patch<Circuit>(`${this.circuitsUrl}/${id}/archiver`, {}).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  // =============================================
  // GESTION DES ÉTAPES DU CIRCUIT
  // =============================================

  /**
   * Ajouter une étape à un circuit (POST /api/v1/circuits/{circuitId}/etapes)
   */
  createEtape(circuitId: string, etape: EtapeCircuitCreateRequest): Observable<EtapeCircuit> {
    return this.http.post<EtapeCircuit>(`${this.circuitsUrl}/${circuitId}/etapes`, etape).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Mettre à jour une étape (PUT /api/v1/circuits/etapes/{id})
   */
  updateEtape(etapeId: string, etape: EtapeCircuitUpdateRequest): Observable<EtapeCircuit> {
    return this.http.put<EtapeCircuit>(`${this.circuitsUrl}/etapes/${etapeId}`, etape).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Supprimer une étape (DELETE /api/v1/circuits/etapes/{id})
   */
  deleteEtape(etapeId: string): Observable<void> {
    return this.http.delete<void>(`${this.circuitsUrl}/etapes/${etapeId}`).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  /**
   * Réordonner les étapes d'un circuit (PATCH /api/v1/circuits/{circuitId}/etapes/reordonner)
   */
  reordonnerEtapes(circuitId: string, ordres: OrdreEtape[]): Observable<EtapeCircuit[]> {
    const body: ReordonnerEtapesRequest = { etapes: ordres };
    return this.http.patch<EtapeCircuit[]>(`${this.circuitsUrl}/${circuitId}/etapes/reordonner`, body).pipe(tap(() => this.invalidateCircuitsCache()));
  }

  // =============================================
  // CALCULS AUTOMATIQUES
  // =============================================

  /**
   * Calculer la durée totale d'un circuit (GET /api/v1/circuits/{id}/duree)
   */
  calculerDuree(id: string): Observable<CalculDureeCircuitResponse> {
    return this.http.get<CalculDureeCircuitResponse>(`${this.circuitsUrl}/${id}/duree`);
  }

  /**
   * Calculer la distance totale d'un circuit (GET /api/v1/circuits/{id}/distance)
   */
  calculerDistance(id: string): Observable<CalculDistanceCircuitResponse> {
    return this.http.get<CalculDistanceCircuitResponse>(`${this.circuitsUrl}/${id}/distance`);
  }

  /**
   * Calculer le prix total d'un circuit (GET /api/v1/circuits/{id}/prix)
   */
  calculerPrix(id: string): Observable<CalculPrixCircuitResponse> {
    return this.http.get<CalculPrixCircuitResponse>(`${this.circuitsUrl}/${id}/prix`);
  }

  // =============================================
  // GESTION DES ACTIVITÉS TOURISTIQUES
  // =============================================

  /**
   * Créer une nouvelle activité (POST /api/v1/circuits/activites)
   */
  createActivite(activite: ActiviteCreateRequest): Observable<Activite> {
    return this.http.post<Activite>(`${this.circuitsUrl}/activites`, activite).pipe(tap(() => this.invalidateActivitesCache()));
  }

  /**
   * Mettre à jour une activité (PUT /api/v1/circuits/activites/{id})
   */
  updateActivite(id: string, activite: ActiviteUpdateRequest): Observable<Activite> {
    return this.http.put<Activite>(`${this.circuitsUrl}/activites/${id}`, activite).pipe(tap(() => this.invalidateActivitesCache()));
  }

  /**
   * Supprimer une activité (DELETE /api/v1/circuits/activites/{id})
   */
  deleteActivite(id: string): Observable<void> {
    return this.http.delete<void>(`${this.circuitsUrl}/activites/${id}`).pipe(tap(() => this.invalidateActivitesCache()));
  }

  /**
   * Activer une activité (PATCH /api/v1/circuits/activites/{id}/activer)
   */
  activerActivite(id: string): Observable<Activite> {
    return this.http.patch<Activite>(`${this.circuitsUrl}/activites/${id}/activer`, {}).pipe(tap(() => this.invalidateActivitesCache()));
  }

  /**
   * Désactiver une activité (PATCH /api/v1/circuits/activites/{id}/desactiver)
   */
  desactiverActivite(id: string): Observable<Activite> {
    return this.http.patch<Activite>(`${this.circuitsUrl}/activites/${id}/desactiver`, {}).pipe(tap(() => this.invalidateActivitesCache()));
  }
}

