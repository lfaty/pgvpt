import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map, of, forkJoin, catchError } from 'rxjs';
import { MarqueurPatrimoine, PatrimoineMarker, CriteresRechercheSpatiale, SyntheseGeographique, GeoSummary } from '../models/geo.ui';
import { FeaturePatrimoine } from '../openapi/geolocalisation/model/feature-patrimoine';

/**
 * Facade service pour ms-geolocalisation connecté aux endpoints réels.
 */
@Injectable({
  providedIn: 'root'
})
export class GeoFacadeService {

  private cachedFeatures: MarqueurPatrimoine[] | null = null;

  constructor(private http: HttpClient) {}

  /**
   * Charger les marqueurs géographiques des sites patrimoniaux.
   * Appelle GET /api/v1/cartographie/patrimoines avec filtres optionnels (?region=...&type=...&categorie=...).
   */
  getMarqueursPatrimoine(criteres?: CriteresRechercheSpatiale): Observable<MarqueurPatrimoine[]> {
    let params = new HttpParams();
    if (criteres?.region && criteres.region !== 'TOUTES') {
      params = params.set('region', criteres.region);
    }
    if (criteres?.type && criteres.type !== 'TOUS') {
      params = params.set('type', criteres.type);
    }
    if (criteres?.categorie && criteres.categorie !== 'TOUS') {
      params = params.set('categorie', criteres.categorie);
    }

    return this.http.get<FeaturePatrimoine[]>('/api/v1/geolocalisations/cartographie/patrimoines', { params }).pipe(
      map(features => {
        const list = Array.isArray(features) ? features : [];
        return list.map(f => ({ ...f, selected: false } as MarqueurPatrimoine));
      }),
      catchError(err => {
        console.warn('Erreur chargement marqueurs cartographie', err);
        return of([]);
      })
    );
  }

  /**
   * Obtenir les features patrimoines pour affichage cartographique (avec cache mémoire).
   */
  getFeaturePatrimoines(): Observable<MarqueurPatrimoine[]> {
    if (this.cachedFeatures) {
      return of(this.cachedFeatures);
    }

    return this.getMarqueursPatrimoine().pipe(
      map(markers => {
        this.cachedFeatures = markers;
        return markers;
      })
    );
  }

  /**
   * Filtrer les features par type de patrimoine.
   */
  getFeaturesByType(type: string): Observable<PatrimoineMarker[]> {
    return this.getFeaturePatrimoines().pipe(
      map(features => features.filter(f => f.type === type))
    );
  }

  /**
   * Filtrer les features par catégorie.
   */
  getFeaturesByCategorie(categorie: string): Observable<PatrimoineMarker[]> {
    return this.getFeaturePatrimoines().pipe(
      map(features => features.filter(f => f.categorie === categorie))
    );
  }

  /**
   * Rechercher les patrimoines dans un rayon.
   */
  searchInRadius(lat: number, lng: number, radiusKm: number): Observable<PatrimoineMarker[]> {
    return this.getFeaturePatrimoines().pipe(
      map(features => features.filter(f => {
        if (!f.latitude || !f.longitude) return false;
        const dist = this.haversineDistance(lat, lng, f.latitude, f.longitude);
        return dist <= radiusKm;
      }))
    );
  }

  /**
   * Obtenir un résumé géographique des patrimoines.
   */
  getGeoSummary(): Observable<GeoSummary> {
    return forkJoin({
      features: this.getFeaturePatrimoines(),
      zones: this.getZonesTouristiques()
    }).pipe(
      map(({ features, zones }) => ({
        totalPatrimoines: features.length,
        totalMonuments: features.filter(f => f.type === 'MONUMENT').length,
        totalMusees: features.filter(f => f.type === 'MUSEE').length,
        totalSitesNaturels: features.filter(f => f.type === 'SITE_NATUREL').length,
        zones
      }))
    );
  }

  /**
   * Obtenir les zones touristiques.
   */
  getZonesTouristiques(): Observable<any[]> {
    return this.http.get<any>('/api/v1/geolocalisations/zones-touristiques').pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  /**
   * Obtenir les zones géographiques.
   */
  getZonesGeographiques(): Observable<any[]> {
    return this.http.get<any>('/api/v1/geolocalisations/zones-geographiques').pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  /**
   * Obtenir les régions.
   */
  getRegions(): Observable<any[]> {
    return this.http.get<any>('/api/v1/geolocalisations/regions').pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  /**
   * Calcul de distance Haversine (simulation locale).
   * En production, appellera GET /geolocalisations/distance
   */
  private haversineDistance(lat1: number, lon1: number, lat2: number, lon2: number): number {
    const R = 6371; // Rayon de la Terre en km
    const dLat = this.toRad(lat2 - lat1);
    const dLon = this.toRad(lon2 - lon1);
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
              Math.cos(this.toRad(lat1)) * Math.cos(this.toRad(lat2)) *
              Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  private toRad(deg: number): number {
    return deg * (Math.PI / 180);
  }

  /** Invalide le cache */
  invalidateCache(): void {
    this.cachedFeatures = null;
  }
}
