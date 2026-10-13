import { Injectable } from '@angular/core';
import { MockService } from './mock.service';
import { Observable, map, delay, of, forkJoin } from 'rxjs';
import { PatrimoineMarker, GeoSummary } from '../models/geo.ui';
import { FeaturePatrimoine } from '../api/geolocalisation/model/feature-patrimoine';

/**
 * Facade service pour ms-geolocalisation.
 *
 * PHASE ACTUELLE : retourne des données mockées.
 * PHASE FUTURE  : remplacer les appels MockService par les appels API réels.
 */
@Injectable({
  providedIn: 'root'
})
export class GeoFacadeService {

  private cachedFeatures: PatrimoineMarker[] | null = null;

  constructor(private mockService: MockService) {}

  /**
   * Obtenir les features patrimoines pour affichage cartographique.
   * Correspond à GET /cartographie/patrimoines dans le backend.
   */
  getFeaturePatrimoines(): Observable<PatrimoineMarker[]> {
    if (this.cachedFeatures) {
      return of(this.cachedFeatures);
    }

    return this.mockService.getMockData<FeaturePatrimoine[]>('geo/feature-patrimoines.json').pipe(
      delay(300),
      map(features => {
        const markers = features.map(f => ({ ...f, selected: false } as PatrimoineMarker));
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
   * Rechercher les patrimoines dans un rayon (simulation locale).
   * En production, appellera GET /geolocalisations/recherche/rayon
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
      zones: this.mockService.getMockData<any[]>('geo/zones-touristiques.json')
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
    return this.mockService.getMockData<any[]>('geo/zones-touristiques.json');
  }

  /**
   * Obtenir les zones géographiques.
   */
  getZonesGeographiques(): Observable<any[]> {
    return this.mockService.getMockData<any[]>('geo/zones-geographiques.json');
  }

  /**
   * Obtenir les régions.
   */
  getRegions(): Observable<any[]> {
    return this.mockService.getMockData<any[]>('geo/regions.json');
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
