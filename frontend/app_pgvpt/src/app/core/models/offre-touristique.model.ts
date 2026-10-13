export enum CategorieOffre {
  HEBERGEMENT = 'HEBERGEMENT',
  RESTAURATION = 'RESTAURATION',
  VISITE = 'VISITE',
  CIRCUIT = 'CIRCUIT',
  EXPERIENCE = 'EXPERIENCE',
  ARTISANAT = 'ARTISANAT',
  TRANSPORT = 'TRANSPORT',
  LOISIR = 'LOISIR',
  ECOTOURISME = 'ECOTOURISME',
  CULTURE = 'CULTURE'
}

export enum StatutOffre {
  BROUILLON = 'BROUILLON',
  PUBLIEE = 'PUBLIEE',
  DEPUBLIEE = 'DEPUBLIEE',
  EXPIREE = 'EXPIREE',
  ARCHIVEE = 'ARCHIVEE'
}

export interface OffreTouristique {
  id: string;
  nom: string;
  description?: string;
  categorie: CategorieOffre;
  zoneTouristiqueId: string;
  acteurId: string;
  circuitId?: string;
  prix: number;
  devise: string;
  dureeMinutes?: number;
  capaciteMax?: number;
  dateDebutValidite?: string;
  dateFinValidite?: string;
  statut: StatutOffre;
  actif: boolean;
  
  // Champs optionnels pour affichage premium
  imageUrl?: string;
  rating?: number;
}
