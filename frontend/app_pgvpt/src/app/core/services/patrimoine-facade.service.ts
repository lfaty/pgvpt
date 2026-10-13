import { Injectable } from '@angular/core';
import { Observable, forkJoin, map, of, catchError, delay } from 'rxjs';
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

  constructor(
    private patrimoineApi: PatrimoineApiService,
    private geolocalisationApi: GeolocalisationApiService,
    private mediaApi: MediaApiService,
    private contenuApi: ContenuApiService
  ) {}

  /**
   * Charge tous les patrimoines avec résolution géo et médias.
   */
  getPatrimoines(): Observable<PatrimoineUI[]> {
    if (this.cachedPatrimoines) {
      return of(this.cachedPatrimoines);
    }

    return forkJoin({
      patrimoines: this.patrimoineApi.getAll().pipe(catchError(() => of([]))),
      geolocalisations: this.geolocalisationApi.getAll().pipe(catchError(() => of([])))
    }).pipe(
      map(({ patrimoines, geolocalisations }) => {
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

          // Détection régionale par défaut si pas encore relié à ms-geolocalisation
          const isGoree = pNom.includes('gor') || pNom.includes('esclave');
          const isSaintLouis = pNom.includes('saint-louis') || pNom.includes('faidherbe');
          const isSaloum = pNom.includes('saloum') || pNom.includes('sine');

          const resolvedLat = geo?.latitude ?? (isGoree ? 14.6666 : (isSaintLouis ? 16.032 : (isSaloum ? 13.8 : null)));
          const resolvedLng = geo?.longitude ?? (isGoree ? -17.3986 : (isSaintLouis ? -16.505 : (isSaloum ? -16.5 : null)));
          const resolvedRegion = geo?.region || (geo?.adresse ? geo.adresse.split(',').pop()?.trim() : (isGoree ? 'Dakar' : (isSaintLouis ? 'Saint-Louis' : (isSaloum ? 'Fatick' : (p.region || 'Dakar')))));

          const randomNote = (Math.random() * (5 - 4) + 4).toFixed(1);
          const img = p?.photoPrincipale?.urlPhotoPrincipale || (typeof p?.photoPrincipale === 'string' ? p.photoPrincipale : null);

          return {
            ...p,
            nom: p.nom || (p as any).nomHistorique || 'Patrimoine du Sénégal',
            region: resolvedRegion,
            departement: geo?.departement || '',
            commune: geo?.commune || '',
            note: parseFloat(randomNote),
            latitude: resolvedLat,
            longitude: resolvedLng,
            imageUrl: img || 'assets/images/patrimoine_1.jpg'
          } as PatrimoineUI;
        });

        this.cachedPatrimoines = result;
        return result;
      })
    );
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
    return this.patrimoineApi.getAll().pipe(catchError(() => of([])));
  }

  getPatrimoineRaw(id: string): Observable<any | undefined> {
    return this.patrimoineApi.getById(id).pipe(catchError(() => of(undefined)));
  }

  getGeolocalisation(patrimoineId: string): Observable<any | undefined> {
    return this.geolocalisationApi.getByPatrimoineId(patrimoineId).pipe(
      map(geos => geos && geos.length > 0 ? geos[0] : undefined),
      catchError(() => of(undefined))
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
  }
}
