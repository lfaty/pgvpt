export interface PatrimoineUI {
  id: string;
  nom: string;
  description: string;
  type: string;
  categorie: string;
  images: string[];
  localisation: {
    region: string;
    departement: string;
    commune: string;
    adresse: string;
    latitude: number;
    longitude: number;
  };
  note: number;
  statut: string;
}
