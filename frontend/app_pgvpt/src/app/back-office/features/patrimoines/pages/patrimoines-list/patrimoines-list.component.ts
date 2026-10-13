import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';

export interface PatrimoineItem {
  id: string;
  code: string;
  nom: string;
  type: 'MONUMENT' | 'SITE_NATUREL' | 'MUSEE' | 'SITE_ARCHEOLOGIQUE';
  typeLabel: string;
  categorie: string;
  statut: 'PUBLIE' | 'EN_VALIDATION' | 'BROUILLON' | 'ARCHIVE';
  imageUrl: string;
  region: string;
  commune: string;
  dateCreation: string;
  isUnesco: boolean;
  activitesCount: number;
  descriptionCourte: string;
  etatConservation?: string;
  accessiblePublic?: boolean;
  classePatrimoine?: boolean;
}

@Component({
  selector: 'app-admin-patrimoines-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './patrimoines-list.component.html',
  styleUrls: ['./patrimoines-list.component.css']
})
export class PatrimoinesListComponent implements OnInit {
  viewMode: 'list' | 'grid' = 'list';

  // Filtres de recherche (alignés avec PatrimoineSearchCriteria)
  searchQuery: string = ''; // q
  selectedType: string = 'TOUS'; // type
  selectedStatut: string = 'TOUS'; // statut
  selectedCategorie: string = 'TOUTES'; // categorie
  selectedEtatConservation: string = 'TOUS'; // etatConservation
  accessiblePublic: string = 'TOUS'; // accessiblePublic (boolean or 'TOUS')
  inscritUnesco: string = 'TOUS'; // inscritUnesco (boolean or 'TOUS')
  classePatrimoine: string = 'TOUS'; // classePatrimoine (boolean or 'TOUS')

  // Filtre supplémentaire (si besoin)
  selectedRegion: string = 'TOUTES';

  // Modals & Aperçu
  selectedPatrimoineDetail: PatrimoineItem | null = null;
  deleteConfirmItem: PatrimoineItem | null = null;
  notificationMessage: string | null = null;

  // Données représentatives du patrimoine sénégalais
  patrimoines: PatrimoineItem[] = [
    {
      id: '1',
      code: 'SN-DKR-001',
      nom: 'Île de Gorée',
      type: 'MONUMENT',
      typeLabel: 'Monument Historique',
      categorie: 'Mémoire & Histoire',
      statut: 'PUBLIE',
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e8/Ile_de_Gor%C3%A9e_S%C3%A9n%C3%A9gal.jpg',
      region: 'Dakar',
      commune: 'Gorée',
      dateCreation: '2023-01-15',
      isUnesco: true,
      activitesCount: 4,
      descriptionCourte: 'Symbole de la mémoire de la traite négrière en Afrique, reconnue par l\'UNESCO depuis 1978.'
    },
    {
      id: '2',
      code: 'SN-DKR-002',
      nom: 'Musée des Civilisations Noires',
      type: 'MUSEE',
      typeLabel: 'Musée National',
      categorie: 'Culture & Arts',
      statut: 'PUBLIE',
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/c/cf/Mus%C3%A9e_civilisations_noires_Dakar.jpg',
      region: 'Dakar',
      commune: 'Dakar-Plateau',
      dateCreation: '2023-03-22',
      isUnesco: false,
      activitesCount: 2,
      descriptionCourte: 'Haut-lieu muséal célébrant les arts, civilisations et technologies du continent africain.'
    },
    {
      id: '3',
      code: 'SN-TBC-001',
      nom: 'Parc National du Niokolo-Koba',
      type: 'SITE_NATUREL',
      typeLabel: 'Site Naturel',
      categorie: 'Faune & Flore',
      statut: 'PUBLIE',
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Paysage_du_parc_Niokolo_Koba_du_S%C3%A9n%C3%A9gal.jpg',
      region: 'Tambacounda',
      commune: 'Tambacounda',
      dateCreation: '2023-05-10',
      isUnesco: true,
      activitesCount: 3,
      descriptionCourte: 'Vaste réserve de biosphère abritant les derniers grands mammifères de savane en Afrique de l\'Ouest.'
    },
    {
      id: '4',
      code: 'SN-FTK-001',
      nom: 'Delta du Sine Saloum',
      type: 'SITE_NATUREL',
      typeLabel: 'Site Naturel',
      categorie: 'Mangrove & Écosystème',
      statut: 'PUBLIE',
      imageUrl: 'https://images.unsplash.com/photo-1544644181-1484b3fdfc62?auto=format&fit=crop&w=800&q=80',
      region: 'Fatick',
      commune: 'Toubacouta',
      dateCreation: '2023-07-14',
      isUnesco: true,
      activitesCount: 4,
      descriptionCourte: 'Dédale de bolongs, d\'îles et d\'amas coquilliers ancestraux formant un écosystème amphibie unique.'
    },
    {
      id: '5',
      code: 'SN-STL-001',
      nom: 'Île de Saint-Louis',
      type: 'MONUMENT',
      typeLabel: 'Patrimoine Urbain',
      categorie: 'Architecture Coloniale',
      statut: 'PUBLIE',
      imageUrl: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80',
      region: 'Saint-Louis',
      commune: 'Saint-Louis',
      dateCreation: '2023-08-01',
      isUnesco: true,
      activitesCount: 3,
      descriptionCourte: 'Ancienne capitale de l\'AOF réputée pour son urbanisme colonial, le pont Faidherbe et son festival de jazz.'
    },
    {
      id: '6',
      code: 'SN-STL-002',
      nom: 'Parc National des Oiseaux du Djoudj',
      type: 'SITE_NATUREL',
      typeLabel: 'Site Naturel',
      categorie: 'Ornithologie',
      statut: 'EN_VALIDATION',
      imageUrl: 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80',
      region: 'Saint-Louis',
      commune: 'Ross Béthio',
      dateCreation: '2023-09-05',
      isUnesco: true,
      activitesCount: 2,
      descriptionCourte: 'Troisième réserve ornithologique au monde, sanctuaire de millions de pélicans et d\'oiseaux migrateurs.'
    },
    {
      id: '7',
      code: 'SN-KLC-001',
      nom: 'Cercles Mégalithiques de Sine Ngayène',
      type: 'SITE_ARCHEOLOGIQUE',
      typeLabel: 'Site Archéologique',
      categorie: 'Mégalithes Sénégambiens',
      statut: 'EN_VALIDATION',
      imageUrl: 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80',
      region: 'Kaolack',
      commune: 'Nioro du Rip',
      dateCreation: '2023-11-12',
      isUnesco: true,
      activitesCount: 1,
      descriptionCourte: 'Plus de mille monolithes en latérite dressés formant une nécropole préhistorique monumentale.'
    },
    {
      id: '8',
      code: 'SN-KDG-001',
      nom: 'Chutes de Dindéfélo',
      type: 'SITE_NATUREL',
      typeLabel: 'Site Naturel',
      categorie: 'Cascades & Paysage',
      statut: 'BROUILLON',
      imageUrl: 'https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80',
      region: 'Kédougou',
      commune: 'Dindéfélo',
      dateCreation: '2024-01-20',
      isUnesco: false,
      activitesCount: 3,
      descriptionCourte: 'Impressionnante cascade de 100 mètres nichée au pied des collines du pays Bassari.'
    },
    {
      id: '9',
      code: 'SN-DKR-003',
      nom: 'Maison des Esclaves',
      type: 'MONUMENT',
      typeLabel: 'Monument Historique',
      categorie: 'Lieu de Mémoire',
      statut: 'PUBLIE',
      imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e8/Ile_de_Gor%C3%A9e_S%C3%A9n%C3%A9gal.jpg',
      region: 'Dakar',
      commune: 'Gorée',
      dateCreation: '2024-02-18',
      isUnesco: true,
      activitesCount: 2,
      descriptionCourte: 'L\'un des lieux de commémoration les plus visités au monde, avec sa célèbre "Porte du voyage sans retour".'
    },
    {
      id: '10',
      code: 'SN-DKR-004',
      nom: 'Fort d\'Estrées & Musée Historique',
      type: 'MUSEE',
      typeLabel: 'Musée',
      categorie: 'Histoire Militaire',
      statut: 'BROUILLON',
      imageUrl: 'https://images.unsplash.com/photo-1544644181-1484b3fdfc62?auto=format&fit=crop&w=800&q=80',
      region: 'Dakar',
      commune: 'Gorée',
      dateCreation: '2024-03-01',
      isUnesco: true,
      activitesCount: 1,
      descriptionCourte: 'Ancien fort militaire du XIXe siècle transformé en musée de l\'histoire du Sénégal par l\'IFAN.'
    }
  ];

  constructor(private patrimoineApiService: PatrimoineApiService) {}

  ngOnInit(): void {
    this.loadPatrimoines();
  }

  loadPatrimoines(): void {
    const params = {
      q: this.searchQuery,
      type: this.selectedType,
      statut: this.selectedStatut,
      categorie: this.selectedCategorie,
      etatConservation: this.selectedEtatConservation,
      accessiblePublic: this.accessiblePublic,
      inscritUnesco: this.inscritUnesco,
      classePatrimoine: this.classePatrimoine
    };

    this.patrimoineApiService.search(params).subscribe({
      next: (res) => {
        if (res && res.content) {
          this.patrimoines = res.content.map((p: any) => ({
            id: p.id,
            code: p.code || 'N/A',
            nom: p.nom,
            type: p.type,
            typeLabel: p.type === 'MONUMENT' ? 'Monument' : (p.type === 'MUSEE' ? 'Musée' : 'Site Naturel'),
            categorie: p.categorie || 'N/A',
            statut: p.statut || 'BROUILLON',
            imageUrl: p.photoPrincipale?.urlPhotoPrincipale || 'https://via.placeholder.com/800x600?text=Pas+d%27image',
            region: p.localisation?.region || 'N/A',
            commune: p.localisation?.commune || 'N/A',
            dateCreation: p.dateOuverture || 'N/A',
            isUnesco: !!p.inscritUnesco,
            activitesCount: 0,
            descriptionCourte: p.descriptionCourte || p.description || '',
            etatConservation: p.conservation?.etatConservation || 'TOUS',
            accessiblePublic: p.accessibilite?.accessiblePublic,
            classePatrimoine: p.classePatrimoine
          }));
        } else {
           // Fallback si pas de DB
           // console.log('Pas de données');
        }
      },
      error: (err) => {
        console.error('Erreur de chargement des patrimoines', err);
      }
    });
  }

  // ─── CALCUL DES KPIs RAPIDES ───
  get totalCount(): number {
    return this.patrimoines.length;
  }

  getCountByStatut(statut: 'PUBLIE' | 'EN_VALIDATION' | 'BROUILLON'): number {
    return this.patrimoines.filter(p => p.statut === statut).length;
  }

  // ─── FILTRAGE DES RÉSULTATS ───
  get filteredPatrimoines(): PatrimoineItem[] {
    return this.patrimoines.filter(item => {
      // Filtre Recherche
      const query = this.searchQuery.trim().toLowerCase();
      const matchSearch = !query ||
        item.nom.toLowerCase().includes(query) ||
        item.code.toLowerCase().includes(query) ||
        item.region.toLowerCase().includes(query) ||
        item.commune.toLowerCase().includes(query);

      // Filtre Région
      const matchRegion = this.selectedRegion === 'TOUTES' || item.region === this.selectedRegion;

      // Filtre Type
      const matchType = this.selectedType === 'TOUS' || item.type === this.selectedType;

      // Filtre Statut
      const matchStatut = this.selectedStatut === 'TOUS' || item.statut === this.selectedStatut;

      // Filtre Catégorie
      const matchCategorie = this.selectedCategorie === 'TOUTES' || item.categorie === this.selectedCategorie;

      // Filtre Etat Conservation
      const matchEtatConservation = this.selectedEtatConservation === 'TOUS' || item.etatConservation === this.selectedEtatConservation;

      // Filtre Accessible Public
      let matchAccessiblePublic = true;
      if (this.accessiblePublic !== 'TOUS') {
        matchAccessiblePublic = item.accessiblePublic === (this.accessiblePublic === 'true');
      }

      // Filtre Inscrit UNESCO
      let matchInscritUnesco = true;
      if (this.inscritUnesco !== 'TOUS') {
        matchInscritUnesco = item.isUnesco === (this.inscritUnesco === 'true');
      }

      // Filtre Classé Patrimoine
      let matchClassePatrimoine = true;
      if (this.classePatrimoine !== 'TOUS') {
        matchClassePatrimoine = item.classePatrimoine === (this.classePatrimoine === 'true');
      }

      return matchSearch && matchRegion && matchType && matchStatut && matchCategorie && matchEtatConservation && matchAccessiblePublic && matchInscritUnesco && matchClassePatrimoine;
    });
  }

  // ─── GESTION DE LA PAGINATION ───
  currentPage: number = 1;
  pageSize: number = 5;
  pageSizeOptions: number[] = [5, 10, 20];

  get totalPages(): number {
    return Math.ceil(this.filteredPatrimoines.length / this.pageSize) || 1;
  }

  get paginatedPatrimoines(): PatrimoineItem[] {
    if (this.currentPage > this.totalPages) {
      this.currentPage = 1;
    }
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredPatrimoines.slice(start, start + this.pageSize);
  }

  get startIndex(): number {
    if (this.filteredPatrimoines.length === 0) return 0;
    return (this.currentPage - 1) * this.pageSize + 1;
  }

  get endIndex(): number {
    return Math.min(this.currentPage * this.pageSize, this.filteredPatrimoines.length);
  }

  get pagesArray(): number[] {
    const pages: number[] = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  changePageSize(newSize: number): void {
    this.pageSize = Number(newSize);
    this.currentPage = 1;
  }

  filterTimeout: any;

  onFilterChange(): void {
    this.currentPage = 1;
    // Debounce pour éviter d'inonder l'API à chaque touche
    if (this.filterTimeout) {
      clearTimeout(this.filterTimeout);
    }
    this.filterTimeout = setTimeout(() => {
      this.loadPatrimoines();
    }, 400);
  }

  // ─── ACTIONS ───
  toggleView(mode: 'list' | 'grid'): void {
    this.viewMode = mode;
  }

  filterByStatutCard(statut: string): void {
    this.currentPage = 1;
    if (this.selectedStatut === statut) {
      this.selectedStatut = 'TOUS';
    } else {
      this.selectedStatut = statut;
    }
    this.loadPatrimoines();
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedRegion = 'TOUTES';
    this.selectedType = 'TOUS';
    this.selectedStatut = 'TOUS';
    this.selectedCategorie = 'TOUTES';
    this.selectedEtatConservation = 'TOUS';
    this.accessiblePublic = 'TOUS';
    this.inscritUnesco = 'TOUS';
    this.classePatrimoine = 'TOUS';
    this.currentPage = 1;
    this.loadPatrimoines();
  }

  hasActiveFilters(): boolean {
    return this.searchQuery !== '' ||
           this.selectedRegion !== 'TOUTES' ||
           this.selectedType !== 'TOUS' ||
           this.selectedStatut !== 'TOUS' ||
           this.selectedCategorie !== 'TOUTES' ||
           this.selectedEtatConservation !== 'TOUS' ||
           this.accessiblePublic !== 'TOUS' ||
           this.inscritUnesco !== 'TOUS' ||
           this.classePatrimoine !== 'TOUS';
  }

  // Aperçu Rapide
  openDetail(item: PatrimoineItem): void {
    this.selectedPatrimoineDetail = item;
  }

  closeDetail(): void {
    this.selectedPatrimoineDetail = null;
  }

  // Publication / Dépublication
  toggleStatut(item: PatrimoineItem): void {
    if (item.statut === 'PUBLIE') {
      item.statut = 'BROUILLON';
      this.showNotification(`Le patrimoine "${item.nom}" a été repassé en brouillon.`);
    } else {
      item.statut = 'PUBLIE';
      this.showNotification(`Le patrimoine "${item.nom}" est maintenant publié en ligne.`);
    }
  }

  // Suppression
  confirmDelete(item: PatrimoineItem): void {
    this.deleteConfirmItem = item;
  }

  cancelDelete(): void {
    this.deleteConfirmItem = null;
  }

  executeDelete(): void {
    if (this.deleteConfirmItem) {
      const nom = this.deleteConfirmItem.nom;
      const id = this.deleteConfirmItem.id;
      
      this.patrimoineApiService.delete(id).subscribe({
        next: () => {
          this.patrimoines = this.patrimoines.filter(p => p.id !== id);
          this.deleteConfirmItem = null;
          this.showNotification(`Le patrimoine "${nom}" a été supprimé avec succès.`);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression', err);
          this.showNotification(`Erreur lors de la suppression du patrimoine "${nom}".`);
        }
      });
    }
  }

  private showNotification(msg: string): void {
    this.notificationMessage = msg;
    setTimeout(() => {
      this.notificationMessage = null;
    }, 3500);
  }

  getStatutBadgeClass(statut: string): string {
    switch (statut) {
      case 'PUBLIE':
        return 'bg-emerald-50 text-emerald-700 border-emerald-200';
      case 'EN_VALIDATION':
        return 'bg-amber-50 text-amber-700 border-amber-200';
      case 'BROUILLON':
        return 'bg-gray-100 text-gray-700 border-gray-200';
      case 'ARCHIVE':
        return 'bg-red-50 text-red-700 border-red-200';
      default:
        return 'bg-gray-50 text-gray-600 border-gray-200';
    }
  }
}
