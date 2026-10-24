import { Injectable } from '@angular/core';
import { Observable, forkJoin, map, of, catchError, delay, switchMap, shareReplay } from 'rxjs';
import { PatrimoineUI } from '../models/patrimoine.ui';
import { PatrimoineApiService } from './api/patrimoine-api.service';
import { GeolocalisationApiService } from './api/geolocalisation-api.service';
import { MediaApiService } from './api/media-api.service';
import { ContenuApiService } from './api/contenu-api.service';

/**
 * Facade service pour orchestrer ms-patrimoine, ms-geolocalisation et ms-media.
 */
@Injectable({
  providedIn: 'root'
})
export class PatrimoineFacadeService {

  private cachedPatrimoines: PatrimoineUI[] | null = null;
  private patrimoines$: Observable<PatrimoineUI[]> | null = null;

  constructor(
    private patrimoineApi: PatrimoineApiService,
    private geolocalisationApi: GeolocalisationApiService,
    private mediaApi: MediaApiService,
    private contenuApi: ContenuApiService
  ) {}

  /**
   * Charge tous les patrimoines avec résolution géo et médias.
   */
  getPatrimoines(forceRefresh: boolean = false): Observable<PatrimoineUI[]> {
    if (this.cachedPatrimoines && !forceRefresh) {
      return of(this.cachedPatrimoines);
    }

    if (!this.patrimoines$ || forceRefresh) {
      this.patrimoines$ = forkJoin({
        patrimoines: this.patrimoineApi.getAll(),
        geolocalisations: this.geolocalisationApi.getAll().pipe(catchError(() => of([]))),
        zonesTouristiques: this.geolocalisationApi.getZonesTouristiques().pipe(catchError(() => of([])))
      }).pipe(
        map(({ patrimoines, geolocalisations, zonesTouristiques }) => {
        const zoneList = Array.isArray(zonesTouristiques) ? zonesTouristiques : ((zonesTouristiques as any)?.content || []);
        const result = patrimoines.map(p => {
          let geo = geolocalisations.find(g => g.patrimoineId === p.id);
          
          const pNom = (p.nom || (p as any).nomHistorique || (p as any).nomLocal || '').toLowerCase();
          if (!geo) {
            // Recherche intelligente par nom ou lieu
            geo = geolocalisations.find(g => {
              const gAdr = (g.adresse || g.lieuDit || '').toLowerCase();
              return gAdr && pNom && (pNom.includes(gAdr) || gAdr.includes(pNom));
            });
          }

          const resolvedLat = geo?.latitude ?? (p as any).latitude ?? null;
          const resolvedLng = geo?.longitude ?? (p as any).longitude ?? null;

          // Association avec la zone touristique
          const zt = zoneList.find((z: any) => z.id === geo?.zoneTouristiqueId);

          // Résolution propre de la région parmi les 14 régions officielles du Sénégal
          const SENEGAL_REGIONS = [
            'Dakar', 'Diourbel', 'Fatick', 'Kaffrine', 'Kaolack', 'Kédougou',
            'Kolda', 'Louga', 'Matam', 'Saint-Louis', 'Sédhiou', 'Tambacounda',
            'Thiès', 'Ziguinchor'
          ];
          const rawCandidate = `${zt?.zoneGeographique?.nom || ''} ${geo?.region || ''} ${p.region || ''} ${geo?.adresse || ''} ${geo?.lieuDit || ''} ${p.description || ''} ${p.nom || ''}`;
          const matchedRegion = SENEGAL_REGIONS.find(r => rawCandidate.toLowerCase().includes(r.toLowerCase()));
          
          let resolvedRegion = matchedRegion || '';
          if (!resolvedRegion) {
            const raw = geo?.region || p.region || (geo?.adresse ? geo.adresse.split(',').pop()?.trim() : '');
            if (raw && !raw.toLowerCase().includes('fiabilit') && !raw.toLowerCase().includes('point')) {
              resolvedRegion = raw;
            } else {
              resolvedRegion = 'Sénégal';
            }
          }

          // Résolution propre de la commune
          let resolvedCommune = '';
          const candidateCommune = geo?.commune || (p as any).commune || geo?.lieuDit || (geo?.adresse ? geo.adresse.split(',')[0]?.trim() : '');
          if (candidateCommune && !/zone touristique|latitude|longitude|fiabilit|point/i.test(candidateCommune)) {
            resolvedCommune = candidateCommune
              .replace(/^(centre historique|village|quartier|commune)\s+(du|de la|des|de|d')\s*/i, '')
              .replace(/^(cascade|plage|ile|île)\s+de\s+/i, '')
              .trim();
          }
          if (!resolvedCommune && zt?.nom) {
            resolvedCommune = zt.nom.replace(/^Zone touristique\s+(du|de la|des|de|d')\s*/i, '').trim();
          }
          if (!resolvedCommune || resolvedCommune.toLowerCase().includes('zone')) {
            if (pNom.includes('gorée') || pNom.includes('goree')) resolvedCommune = 'Gorée';
            else if (pNom.includes('dindéfélo') || pNom.includes('dindefelo')) resolvedCommune = 'Dindéfélo';
            else if (pNom.includes('renaissance') || pNom.includes('ouakam')) resolvedCommune = 'Ouakam';
            else if (pNom.includes('cap skirring')) resolvedCommune = 'Cap Skirring';
          }

          const img = p?.photoPrincipale?.urlPhotoPrincipale || (typeof p?.photoPrincipale === 'string' ? p.photoPrincipale : null);

          return {
            ...p,
            nom: p.nom || (p as any).nomHistorique || 'Patrimoine du Sénégal',
            region: resolvedRegion,
            departement: geo?.departement || zt?.departement?.nom || (p as any).departement || resolvedRegion,
            commune: resolvedCommune,
            note: (p as any).note ?? null,
            latitude: resolvedLat,
            longitude: resolvedLng,
            imageUrl: img || 'assets/images/patrimoine_1.jpg'
          } as PatrimoineUI;
        });

        this.cachedPatrimoines = result;
        return result;
      }),
      shareReplay(1)
    );
  }

  return this.patrimoines$;
}

  /**
   * Obtenir un patrimoine par son ID.
   */
  getPatrimoineById(id: string): Observable<PatrimoineUI | undefined> {
    return this.getPatrimoines().pipe(
      map(patrimoines => patrimoines.find(p => p.id === id))
    );
  }

  /**
   * Filtrer les patrimoines par type.
   */
  getPatrimoinesByType(type: string): Observable<PatrimoineUI[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => patrimoines.filter(p => p.type === type))
    );
  }

  /**
   * Filtrer les patrimoines par catégorie.
   */
  getPatrimoinesByCategorie(categorie: string): Observable<PatrimoineUI[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => patrimoines.filter(p => p.categorie === categorie))
    );
  }

  /**
   * Recherche textuelle sur nom, description, historique.
   */
  searchPatrimoines(query: string): Observable<PatrimoineUI[]> {
    const q = query.toLowerCase().trim();
    if (!q) return this.getPatrimoines();

    return this.getPatrimoines().pipe(
      map(patrimoines => patrimoines.filter(p =>
        p.nom?.toLowerCase().includes(q) ||
        p.description?.toLowerCase().includes(q) ||
        p.historique?.toLowerCase().includes(q) ||
        p.descriptionCourte?.toLowerCase().includes(q) ||
        p.region?.toLowerCase().includes(q) ||
        p.commune?.toLowerCase().includes(q)
      ))
    );
  }

  /**
   * Filtrage avancé multi-critères.
   */
  filterPatrimoines(filters: {
    q?: string;
    type?: string;
    categorie?: string;
    region?: string;
    inscritUnesco?: boolean;
    etatConservation?: string;
  }): Observable<PatrimoineUI[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => {
        return patrimoines.filter(p => {
          if (filters.q) {
            const q = filters.q.toLowerCase();
            const matchText = p.nom?.toLowerCase().includes(q) ||
              p.description?.toLowerCase().includes(q) ||
              p.descriptionCourte?.toLowerCase().includes(q);
            if (!matchText) return false;
          }
          if (filters.type && p.type !== filters.type) return false;
          if (filters.categorie && p.categorie !== filters.categorie) return false;
          if (filters.region && p.region !== filters.region) return false;
          if (filters.inscritUnesco !== undefined && p.inscritUnesco !== filters.inscritUnesco) return false;
          if (filters.etatConservation && p.conservation?.etat !== filters.etatConservation) return false;
          return true;
        });
      })
    );
  }

  /**
   * Obtenir les patrimoines similaires (même type, excluant le courant).
   */
  getSimilaires(patrimoineId: string, limit: number = 3): Observable<PatrimoineUI[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => {
        const current = patrimoines.find(p => p.id === patrimoineId);
        if (!current) return [];
        return patrimoines
          .filter(p => p.type === current.type && p.id !== patrimoineId)
          .slice(0, limit);
      })
    );
  }

  /**
   * Obtenir les catégories uniques avec compteurs.
   */
  getCategoriesWithCount(): Observable<{ categorie: string; count: number; label: string }[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => {
        const counts = new Map<string, number>();
        patrimoines.forEach(p => {
          const cat = p.categorie || 'AUTRE';
          counts.set(cat, (counts.get(cat) || 0) + 1);
        });
        return Array.from(counts.entries()).map(([categorie, count]) => ({
          categorie,
          count,
          label: this.formatCategorieLabel(categorie)
        }));
      })
    );
  }

  /**
   * Obtenir les régions uniques.
   */
  getRegions(): Observable<string[]> {
    return this.getPatrimoines().pipe(
      map(patrimoines => {
        const regions = new Set<string>();
        patrimoines.forEach(p => {
          if (p.region) regions.add(p.region);
        });
        return Array.from(regions).sort();
      })
    );
  }

  /**
   * Formate un label lisible pour une catégorie enum.
   */
  formatCategorieLabel(categorie: string): string {
    const labels: Record<string, string> = {
      'PATRIMOINE_NATUREL': 'Naturel',
      'PATRIMOINE_HISTORIQUE': 'Historique',
      'PATRIMOINE_CULTUREL': 'Culturel',
      'PATRIMOINE_ARCHITECTURAL': 'Architectural',
      'PATRIMOINE_RELIGIEUX': 'Religieux',
      'PATRIMOINE_COLONIAL': 'Colonial',
      'PATRIMOINE_ARCHEOLOGIQUE': 'Archéologique',
      'PATRIMOINE_MUSEAL': 'Muséal',
      'PATRIMOINE_MEMORIEL': 'Mémoriel',
      'PATRIMOINE_MARITIME': 'Maritime',
      'PATRIMOINE_RURAL': 'Rural',
      'AUTRE': 'Autre'
    };
    return labels[categorie] || categorie;
  }

  /**
   * Formate un label lisible pour un type.
   */
  formatTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      'SITE_NATUREL': 'Site Naturel',
      'MUSEE': 'Musée',
      'MONUMENT': 'Monument'
    };
    return labels[type] || type;
  }

  /**
   * Retourne les patrimoines RAW (sans fusion).
   */
  getPatrimoinesRaw(): Observable<any[]> {
    return this.patrimoineApi.getAll();
  }

  getPatrimoineRaw(id: string): Observable<any | undefined> {
    return this.patrimoineApi.getById(id);
  }

  getGeolocalisation(patrimoineId: string): Observable<any | undefined> {
    return this.geolocalisationApi.getByPatrimoineId(patrimoineId).pipe(
      map(geos => geos && geos.length > 0 ? geos[0] : undefined),
      switchMap(geo => {
        if (!geo) return of(undefined);
        return this.enrichGeolocalisation(geo);
      }),
      catchError(() => of(undefined))
    );
  }

  enrichGeolocalisation(geo: any): Observable<any> {
    if (!geo) return of(geo);

    const patrimoineId = geo.patrimoineId;
    const pts$ = patrimoineId
      ? this.geolocalisationApi.getPointsAcces(patrimoineId).pipe(catchError(() => of([])))
      : of([]);

    const zt$ = geo.zoneTouristiqueId
      ? this.geolocalisationApi.getZoneTouristiqueById(geo.zoneTouristiqueId)
      : of(null);

    const vil$ = geo.villageId
      ? this.geolocalisationApi.getVillageById(geo.villageId)
      : of(null);

    return forkJoin({ zt: zt$, vil: vil$, pts: pts$ }).pipe(
      switchMap(({ zt, vil, pts }) => {
        const deptId = zt?.departement?.id || zt?.departementId;
        const dept$ = deptId
          ? this.geolocalisationApi.getDepartementById(deptId)
          : of(zt?.departement || null);

        return forkJoin({ dept: dept$ }).pipe(
          map(({ dept }) => {
            const enriched = { ...geo };

            enriched.pointsAcces = pts || [];
            enriched.zoneTouristique = zt;
            enriched.zoneTouristiqueNom = zt?.nom || '';

            // 1. Département
            let deptNom = dept?.nom || zt?.departement?.nom || enriched.departement || '';

            // 2. Région
            let regionNom = dept?.region?.nom || '';
            if (!regionNom && zt?.zoneGeographique?.nom) {
              // Nettoyer le préfixe "Zone géographique de ..."
              regionNom = zt.zoneGeographique.nom.replace(/^Zone géographique (du|de la|des|de|d')\s*/i, '').trim();
            }

            // Déduction contextuelle si département absent
            if (!deptNom && regionNom) {
              if (['Dakar', 'Kédougou', 'Saint-Louis', 'Fatick', 'Kaolack', 'Kaffrine', 'Louga', 'Matam', 'Tambacounda', 'Kolda', 'Sédhiou', 'Ziguinchor', 'Thiès', 'Diourbel'].includes(regionNom)) {
                deptNom = regionNom;
              }
            }

            // 3. Commune
            let communeNom = vil?.commune?.nom || vil?.nom || zt?.commune?.nom || enriched.commune || '';
            if (!communeNom && zt?.nom) {
              communeNom = zt.nom.replace(/^Zone touristique (du|de la|des|de|d')\s*/i, '').trim();
            }

            // Fallback basé sur l'adresse et le lieuDit si non résolus
            if (!regionNom && enriched.adresse) {
              const parts = enriched.adresse.split(',').map((p: string) => p.trim());
              if (parts.length > 1) {
                regionNom = parts[parts.length - 1];
              }
            }
            if (!communeNom && (enriched.lieuDit || enriched.adresse)) {
              communeNom = enriched.lieuDit || (enriched.adresse ? enriched.adresse.split(',').pop()?.trim() : '');
            }

            // Assainissement de la commune
            if (communeNom) {
              if (/zone touristique|latitude|longitude|fiabilit|point/i.test(communeNom)) {
                communeNom = '';
              } else {
                communeNom = communeNom
                  .replace(/^(centre historique|village|quartier|commune)\s+(du|de la|des|de|d')\s*/i, '')
                  .replace(/^(cascade|plage|ile|île)\s+de\s+/i, '')
                  .trim();
              }
            }

            enriched.region = regionNom || enriched.region || '-';
            enriched.departement = deptNom || enriched.departement || '-';
            enriched.commune = communeNom || enriched.commune || '-';

            return enriched;
          })
        );
      }),
      catchError(() => of(geo))
    );
  }

  getMedias(patrimoineId: string): Observable<any[]> {
    return this.mediaApi.getByPatrimoineId(patrimoineId).pipe(catchError(() => of([])));
  }

  getAllMedias(): Observable<any[]> {
    return this.mediaApi.getAll().pipe(catchError(() => of([])));
  }

  getContenus(patrimoineId: string): Observable<any[]> {
    return this.contenuApi.getByPatrimoineId(patrimoineId).pipe(catchError(() => of([])));
  }

  getAllContenus(): Observable<any[]> {
    return this.contenuApi.getAll().pipe(catchError(() => of([])));
  }

  /** Invalide le cache (utile après une future mise à jour). */
  invalidateCache(): void {
    this.cachedPatrimoines = null;
    this.patrimoines$ = null;
  }
}
