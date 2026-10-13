export enum TypeRessource {
  CIRCUIT = 'CIRCUIT',
  ACTIVITE = 'ACTIVITE',
  OFFRE_TOURISTIQUE = 'OFFRE_TOURISTIQUE',
  EVENEMENT = 'EVENEMENT',
  PATRIMOINE = 'PATRIMOINE'
}

export enum StatutReservation {
  EN_ATTENTE = 'EN_ATTENTE',
  CONFIRMEE = 'CONFIRMEE',
  ANNULEE = 'ANNULEE'
}

export interface Reservation {
  id: string;
  numero: string;
  userId: string; // Utilisateur connecté
  typeRessource: TypeRessource;
  ressourceId: string;
  ressourceNom?: string; // Ajouté pour faciliter l'affichage frontend
  ressourceImage?: string; // Ajouté pour faciliter l'affichage frontend
  dateReservation: string; // Format ISO YYYY-MM-DD
  datePrestation: string; // Format ISO YYYY-MM-DD
  nombrePersonnes: number;
  montantTotal: number;
  devise: string;
  statut: StatutReservation;
  createdAt: string;
}
