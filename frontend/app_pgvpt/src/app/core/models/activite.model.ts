export interface Activite {
  id: string;
  code: string;
  libelle: string;
  description: string;
  prixInitial: number;
  actif: boolean;
  
  // Champs optionnels pour l'affichage (non présents stricto sensu dans le backend,
  // mais utiles pour un rendu premium)
  imageUrl?: string;
  dureeEstimee?: string;
}
