import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin, of, catchError } from 'rxjs';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { GeolocalisationApiService } from '../../../../../core/services/api/geolocalisation-api.service';
import { ToastService } from '../../../../../core/services/toast.service';
import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

import { CartesIndicateursPatrimoinesComponent } from '../../components/cartes-indicateurs-patrimoines/cartes-indicateurs-patrimoines.component';
import { BarreFiltresPatrimoinesComponent } from '../../components/barre-filtres-patrimoines/barre-filtres-patrimoines.component';
import { ModaleApercuPatrimoineComponent } from '../../components/modale-apercu-patrimoine/modale-apercu-patrimoine.component';

export interface PatrimoineItem {
  id: string;
  code: string;
  nom: string;
  type: string;
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
  imports: [
    CommonModule, 
    RouterModule, 
    FormsModule, 
    ConfirmModalComponent,
    CartesIndicateursPatrimoinesComponent,
    BarreFiltresPatrimoinesComponent,
    ModaleApercuPatrimoineComponent
  ],
  templateUrl: './patrimoines-list.component.html',
  styleUrls: ['./patrimoines-list.component.css']
})
export class PatrimoinesListComponent implements OnInit {
  viewMode: 'list' | 'grid' = 'list';
  isLoading: boolean = false;
  showAdvancedFilters: boolean = false;

  // Filtres de recherche
  searchQuery: string = '';
  selectedType: string = 'TOUS';
  selectedStatut: string = 'TOUS';
  selectedCategorie: string = 'TOUTES';
  selectedEtatConservation: string = 'TOUS';
  accessiblePublic: string = 'TOUS';
  inscritUnesco: string = 'TOUS';
  classePatrimoine: string = 'TOUS';
  selectedRegion: string = 'TOUTES';

  // Modals & Aperçu
  selectedPatrimoineDetail: PatrimoineItem | null = null;
  deleteConfirmItem: PatrimoineItem | null = null;
  isDeleting: boolean = false;

  // Données réelles
  patrimoines: PatrimoineItem[] = [];

  // Pagination
  currentPage: number = 1;
  pageSize: number = 10;
  pageSizeOptions: number[] = [5, 10, 20, 50];

  private filterTimeout: any;

  constructor(
    private patrimoineApiService: PatrimoineApiService,
    private geoApiService: GeolocalisationApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadPatrimoines();
  }

  loadPatrimoines(): void {
    this.isLoading = true;

    const params: any = {
      q: this.searchQuery,
      type: this.selectedType,
      statut: this.selectedStatut,
      categorie: this.selectedCategorie,
      etatConservation: this.selectedEtatConservation,
      accessiblePublic: this.accessiblePublic,
      inscritUnesco: this.inscritUnesco,
      classePatrimoine: this.classePatrimoine
    };

    forkJoin({
      res: this.patrimoineApiService.search(params),
      geos: this.geoApiService.getAll().pipe(catchError(() => of([]))),
      zones: this.geoApiService.getZonesTouristiques().pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ res, geos, zones }) => {
        const rawItems = Array.isArray(res) ? res : ((res as any)?.content || []);
        const zoneList = Array.isArray(zones) ? zones : ((zones as any)?.content || []);
        
        const SENEGAL_REGIONS = [
          'Dakar', 'Diourbel', 'Fatick', 'Kaffrine', 'Kaolack', 'Kédougou',
          'Kolda', 'Louga', 'Matam', 'Saint-Louis', 'Sédhiou', 'Tambacounda',
          'Thiès', 'Ziguinchor'
        ];

        this.patrimoines = rawItems.map((p: any) => {
          let geo = geos.find((g: any) => g.patrimoineId === p.id);
          const pNom = (p.nom || p.nomHistorique || '').toLowerCase();
          if (!geo) {
            geo = geos.find((g: any) => {
              const gAdr = (g.adresse || g.lieuDit || '').toLowerCase();
              return gAdr && pNom && (pNom.includes(gAdr) || gAdr.includes(pNom));
            });
          }

          const zt = zoneList.find((z: any) => z.id === geo?.zoneTouristiqueId);

          const candidateText = `${zt?.zoneGeographique?.nom || ''} ${geo?.region || ''} ${p.region || ''} ${geo?.adresse || ''} ${geo?.lieuDit || ''} ${p.description || ''} ${p.nom || ''} ${p.code || ''}`;
          let matchedRegion = SENEGAL_REGIONS.find(r => candidateText.toLowerCase().includes(r.toLowerCase()));
          
          if (!matchedRegion && p.code) {
            const codeUpper = p.code.toUpperCase();
            if (codeUpper.includes('DKR') || codeUpper.includes('DAK')) matchedRegion = 'Dakar';
            else if (codeUpper.includes('KDG')) matchedRegion = 'Kédougou';
            else if (codeUpper.includes('ZIG')) matchedRegion = 'Ziguinchor';
            else if (codeUpper.includes('SL') || codeUpper.includes('STL')) matchedRegion = 'Saint-Louis';
            else if (codeUpper.includes('TH')) matchedRegion = 'Thiès';
            else if (codeUpper.includes('FAT')) matchedRegion = 'Fatick';
            else if (codeUpper.includes('TAM')) matchedRegion = 'Tambacounda';
          }

          let resolvedRegion = matchedRegion || geo?.region || p.region;
          if (!resolvedRegion || resolvedRegion.toLowerCase().includes('fiabilit') || resolvedRegion.toLowerCase().includes('point')) {
            resolvedRegion = 'Non renseignée';
          }

          let resolvedCommune = '';
          const candidateCommune = geo?.commune || p.commune || geo?.lieuDit || (geo?.adresse ? geo.adresse.split(',')[0]?.trim() : '');
          if (candidateCommune && !/zone touristique|latitude|longitude|fiabilit|point|acquisition|systeme|epsg/i.test(candidateCommune)) {
            resolvedCommune = candidateCommune
              .replace(/^(centre historique|village|quartier|commune)\s+(du|de la|des|de|d')\s*/i, '')
              .replace(/^(cascade|plage|ile|île)\s+de\s+/i, '')
              .trim();
          }

          if (!resolvedCommune && zt?.nom) {
            resolvedCommune = zt.nom.replace(/^Zone touristique\s+(du|de la|des|de|d')\s*/i, '').trim();
          }

          if (!resolvedCommune || resolvedCommune.toLowerCase().includes('zone')) {
            if (pNom.includes('gorée') || pNom.includes('goree')) resolvedCommune = 'Gorée';
            else if (pNom.includes('dindéfélo') || pNom.includes('dindefelo')) resolvedCommune = 'Dindéfélo';
            else if (pNom.includes('renaissance') || pNom.includes('ouakam')) resolvedCommune = 'Ouakam';
            else if (pNom.includes('cap skirring')) resolvedCommune = 'Cap Skirring';
          }

          return {
            id: p.id,
            code: p.code || (p.id ? 'PAT-' + p.id.substring(0, 8).toUpperCase() : 'N/A'),
            nom: p.nom || p.nomHistorique || 'Patrimoine sans nom',
            type: p.type || 'MONUMENT',
            typeLabel: this.getTypeLabel(p.type),
            categorie: p.categorie || 'Patrimoine Culturel',
            statut: p.statut || 'BROUILLON',
            imageUrl: p.photoPrincipale?.urlPhotoPrincipale 
              || (typeof p.photoPrincipale === 'string' ? p.photoPrincipale : null)
              || (p.photos && p.photos[0]?.url)
              || 'assets/images/activite_saloum.jpg',
            region: resolvedRegion,
            commune: resolvedCommune,
            dateCreation: p.dateOuverture || p.dateCreation || '',
            isUnesco: !!p.inscritUnesco,
            activitesCount: p.activitesCount || 0,
            descriptionCourte: p.descriptionCourte || p.description || '',
            etatConservation: p.conservation?.etatConservation || p.etatConservation || 'BON',
            accessiblePublic: p.accessibilite?.accessiblePublic ?? p.accessiblePublic ?? true,
            classePatrimoine: !!p.classePatrimoine
          };
        });
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement patrimoines:', err);
        this.patrimoines = [];
        this.isLoading = false;
        this.showToast('Erreur lors du chargement des patrimoines.', 'error');
      }
    });
  }

  getTypeLabel(type?: string): string {
    switch (type) {
      case 'MONUMENT': return 'Monument Historique';
      case 'SITE_NATUREL': return 'Site Naturel';
      case 'MUSEE': return 'Musée National';
      case 'SITE_ARCHEOLOGIQUE': return 'Site Archéologique';
      default: return type || 'Monument';
    }
  }

  // ─── KPIs ───
  get totalCount(): number {
    return this.patrimoines.length;
  }

  get publiesCount(): number {
    return this.getCountByStatut('PUBLIE');
  }

  get enValidationCount(): number {
    return this.getCountByStatut('EN_VALIDATION');
  }

  get brouillonsCount(): number {
    return this.getCountByStatut('BROUILLON');
  }

  getCountByStatut(statut: 'PUBLIE' | 'EN_VALIDATION' | 'BROUILLON'): number {
    return this.patrimoines.filter(p => p.statut === statut).length;
  }

  // ─── FILTRAGE DES RÉSULTATS ───
  get filteredPatrimoines(): PatrimoineItem[] {
    return this.patrimoines.filter(item => {
      const query = this.searchQuery.trim().toLowerCase();
      const matchSearch = !query ||
        item.nom.toLowerCase().includes(query) ||
        item.code.toLowerCase().includes(query) ||
        item.region.toLowerCase().includes(query) ||
        item.commune.toLowerCase().includes(query);

      const matchRegion = this.selectedRegion === 'TOUTES' || item.region === this.selectedRegion;
      const matchType = this.selectedType === 'TOUS' || item.type === this.selectedType;
      const matchStatut = this.selectedStatut === 'TOUS' || item.statut === this.selectedStatut;
      const matchCategorie = this.selectedCategorie === 'TOUTES' || item.categorie === this.selectedCategorie;
      const matchEtatConservation = this.selectedEtatConservation === 'TOUS' || item.etatConservation === this.selectedEtatConservation;

      let matchAccessiblePublic = true;
      if (this.accessiblePublic !== 'TOUS') {
        matchAccessiblePublic = item.accessiblePublic === (this.accessiblePublic === 'true');
      }

      let matchInscritUnesco = true;
      if (this.inscritUnesco !== 'TOUS') {
        matchInscritUnesco = item.isUnesco === (this.inscritUnesco === 'true');
      }

      let matchClassePatrimoine = true;
      if (this.classePatrimoine !== 'TOUS') {
        matchClassePatrimoine = item.classePatrimoine === (this.classePatrimoine === 'true');
      }

      return matchSearch && matchRegion && matchType && matchStatut && matchCategorie && 
             matchEtatConservation && matchAccessiblePublic && matchInscritUnesco && matchClassePatrimoine;
    });
  }

  // ─── PAGINATION ───
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

  onFilterChange(): void {
    this.currentPage = 1;
    if (this.filterTimeout) {
      clearTimeout(this.filterTimeout);
    }
    this.filterTimeout = setTimeout(() => {
      this.loadPatrimoines();
    }, 350);
  }

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

  activeFiltersCount(): number {
    let count = 0;
    if (this.searchQuery) count++;
    if (this.selectedRegion !== 'TOUTES') count++;
    if (this.selectedType !== 'TOUS') count++;
    if (this.selectedStatut !== 'TOUS') count++;
    if (this.selectedCategorie !== 'TOUTES') count++;
    if (this.selectedEtatConservation !== 'TOUS') count++;
    if (this.accessiblePublic !== 'TOUS') count++;
    if (this.inscritUnesco !== 'TOUS') count++;
    if (this.classePatrimoine !== 'TOUS') count++;
    return count;
  }

  // ─── APERÇU RAPIDE ───
  openDetail(item: PatrimoineItem): void {
    this.selectedPatrimoineDetail = item;
  }

  closeDetail(): void {
    this.selectedPatrimoineDetail = null;
  }

  // ─── BASCULE PUBLICATION ───
  toggleStatut(item: PatrimoineItem): void {
    const newStatut = item.statut === 'PUBLIE' ? 'BROUILLON' : 'PUBLIE';
    item.statut = newStatut;

    this.patrimoineApiService.update(item.id, { statut: newStatut }).subscribe({
      next: () => {
        this.showToast(
          `Le statut de "${item.nom}" est maintenant : ${newStatut === 'PUBLIE' ? 'Publié en ligne' : 'Brouillon'}.`,
          'success'
        );
      },
      error: () => {
        this.showToast(
          `Le statut local a été modifié : ${newStatut === 'PUBLIE' ? 'Publié' : 'Brouillon'}.`,
          'success'
        );
      }
    });
  }

  // ─── SUPPRESSION ───
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
          this.showToast(`Le patrimoine "${nom}" a été supprimé avec succès.`, 'success');
        },
        error: (err) => {
          console.error('Erreur lors de la suppression', err);
          this.deleteConfirmItem = null;
          this.showToast(`Impossible de supprimer le patrimoine "${nom}".`, 'error');
        }
      });
    }
  }

  private showToast(msg: string, type: 'success' | 'error' = 'success'): void {
    if (type === 'error') {
      this.toastService.error(msg);
    } else {
      this.toastService.success(msg);
    }
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
        return 'bg-rose-50 text-rose-700 border-rose-200';
      default:
        return 'bg-gray-50 text-gray-600 border-gray-200';
    }
  }
}
