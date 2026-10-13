import { Patrimoine } from '../api/patrimoine/model/patrimoine';
import { Monument } from '../api/patrimoine/model/monument';
import { Musee } from '../api/patrimoine/model/musee';
import { SiteNaturel } from '../api/patrimoine/model/site-naturel';

export type PatrimoineComplet = Patrimoine | Monument | Musee | SiteNaturel;

/**
 * Extension UI du modèle Patrimoine.
 * Ajoute les informations de localisation résolues (region, departement, commune)
 * qui sont dérivées de ms-geolocalisation, et un champ 'note' purement UI.
 *
 * Note: 'note' n'existe pas dans le Backend. C'est une donnée purement frontend
 * qui sera alimentée par ms-avis quand ce microservice sera connecté.
 */
export type PatrimoineUI = PatrimoineComplet & {
  /** Note moyenne — sera fournie par ms-avis plus tard. Purement UI pour le moment. */
  note?: number;
  /** Nom de la région — résolu depuis ms-geolocalisation */
  region?: string;
  /** Nom du département — résolu depuis ms-geolocalisation */
  departement?: string;
  /** Nom de la commune — résolu depuis ms-geolocalisation */
  commune?: string;
  /** Image principale — résolue depuis photoPrincipale (ms-patrimoine) */
  imageUrl?: string;
  /** ID du media choisi comme Hero par l'admin (Mock data extension) */
  heroMediaId?: string;
  /** Latitude — résolue depuis ms-geolocalisation */
  latitude?: number;
  /** Longitude — résolue depuis ms-geolocalisation */
  longitude?: number;
  /** Contact Conservateur — résolu depuis ms-patrimoine (contacts) */
  contactConservateur?: {
    id?: string;
    nom: string;
    fonction?: string;
    telephone?: string;
    email?: string;
    siteWeb?: string;
    organisme?: string;
  };
  sousCategorie?: string;
  periode?: string;
  siecle?: string;
  nomHistorique?: string;
  dateOuverture?: string;
  classePatrimoine?: string;
  referenceClassement?: string;
  dateClassement?: string;
  protectionJuridique?: string;
  importanceHistorique?: string;
  importanceCulturelle?: string;
  importanceTouristique?: string;
  valeurSpirituelle?: string;
  traditionsAssociees?: string[];
  langues?: string[];
  typeTerritoire?: string;
  ecosystemes?: string;
  altitudeMoyenne?: string;
  reseauHydrographique?: string;
  especesRecensees?: string;
  paysages?: any[];
  habitats?: any;
  faune?: any[];
  flore?: any[];
  climat?: any;
  hydrographieInfo?: any;
  territoire?: any;
  categoriesExperiences?: any[];
  activitesPrincipales?: any[];
  experiencesUniques?: any[];
  conditionsConseils?: string[];
  infosPratiquesExperiences?: any;
};