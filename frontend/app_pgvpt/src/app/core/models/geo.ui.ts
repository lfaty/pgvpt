import { FeaturePatrimoine } from '../api/geolocalisation/model/feature-patrimoine';
import { ZoneTouristique } from '../api/geolocalisation/model/zone-touristique';
import { ZoneGeographique } from '../api/geolocalisation/model/zone-geographique';

/**
 * Interface pour un marqueur de patrimoine sur la carte.
 * Conforme à FeaturePatrimoine du backend ms-geolocalisation.
 */
export interface PatrimoineMarker extends FeaturePatrimoine {
  /** Indique si le marqueur est actuellement sélectionné */
  selected?: boolean;
}

/**
 * Interface pour le résumé geo affiché dans le panel carte.
 */
export interface GeoSummary {
  totalPatrimoines: number;
  totalMonuments: number;
  totalMusees: number;
  totalSitesNaturels: number;
  zones: ZoneTouristique[];
}
