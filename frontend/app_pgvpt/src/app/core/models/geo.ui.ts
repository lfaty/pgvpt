import { FeaturePatrimoine } from '../openapi/geolocalisation/model/feature-patrimoine';
import { ZoneTouristique } from '../openapi/geolocalisation/model/zone-touristique';
import { ZoneGeographique } from '../openapi/geolocalisation/model/zone-geographique';

/**
 * Repère visuel d'un site patrimonial sur la carte Leaflet / OpenStreetMap.
 * Correspond à l'objet FeaturePatrimoine retourné par le microservice géolocalisation.
 */
export interface MarqueurPatrimoine extends FeaturePatrimoine {
  /** Indique si le marqueur est actuellement cliqué / sélectionné par le visiteur */
  selected?: boolean;
  region?: string;
  commune?: string;
  imageUrl?: string;
  photoPrincipale?: string;
}

/** Alias de compatibilité pour le code existant */
export type PatrimoineMarker = MarqueurPatrimoine;

/**
 * Point d'accès pratique pour les visiteurs se rendant sur un site touristique
 * (Entrée principale, parking, embarcadère de pirogue/chaloupe, gare).
 */
export interface PointAccesSite {
  id?: string;
  patrimoineId?: string;
  nom: string;
  type: 'ENTREE_PRINCIPALE' | 'ENTREE_SECONDAIRE' | 'PARKING' | 'EMBARCADERE' | 'GARE' | 'ARRET_BUS' | 'PISTE_ACCES' | 'AUTRE';
  description?: string;
  latitude: number;
  longitude: number;
  adresse?: string;
  distanceMetres?: number;
  accessiblePMR?: boolean;
  parkingDisponible?: boolean;
  transportPublic?: boolean;
  horaires?: string;
}

/**
 * Critères de recherche spatiale utilisés pour filtrer la carte
 * (Recherche par rayon 'Autour de moi' ou par région).
 */
export interface CriteresRechercheSpatiale {
  latitude?: number;
  longitude?: number;
  rayonKm?: number;
  region?: string;
  type?: string;
  categorie?: string;
  page?: number;
  size?: number;
}

/**
 * Synthèse chiffrée affichée dans le bandeau de la carte interactive
 * (nombre de monuments, musées, sites naturels documentés au Sénégal).
 */
export interface SyntheseGeographique {
  totalPatrimoines: number;
  totalMonuments: number;
  totalMusees: number;
  totalSitesNaturels: number;
  zones?: ZoneTouristique[];
}

/** Alias de compatibilité */
export type GeoSummary = SyntheseGeographique;
