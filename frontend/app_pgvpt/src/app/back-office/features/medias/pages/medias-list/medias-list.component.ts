import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MediaApiService } from '../../../../../core/services/api/media-api.service';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';

import { ToastService } from '../../../../../core/services/toast.service';
import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-medias-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ConfirmModalComponent],
  templateUrl: './medias-list.component.html',
  styleUrls: ['./medias-list.component.css']
})
export class MediasListComponent implements OnInit {
  medias: any[] = [];
  filteredMedias: any[] = [];
  patrimoinesMap: { [id: string]: string } = {};
  patrimoinesList: any[] = [];
  loading = true;

  // Filtres
  searchQuery: string = '';
  selectedType: string = 'TOUS';
  selectedStatut: string = 'TOUS';
  selectedPatrimoine: string = 'TOUS';

  // Mode d'affichage (Cartes ou Tableau)
  viewMode: 'grid' | 'list' = 'grid';

  // Modal Lightbox (Aperçu grand écran)
  previewMedia: any = null;

  // Modal de confirmation de suppression
  deleteModalMedia: any = null;
  isDeleting: boolean = false;

  mediaTypes = ['PHOTO', 'VIDEO', 'AUDIO', 'DOCUMENT', 'PANORAMA_360', 'MODELE_3D', 'CARTE', 'ARCHIVE_NUMERISEE'];
  statuts = ['PUBLIE', 'VALIDE', 'BROUILLON', 'ARCHIVE'];

  constructor(
    private mediaApi: MediaApiService,
    private patrimoineApi: PatrimoineApiService,
    private sanitizer: DomSanitizer,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.chargerDonnees();
  }

  chargerDonnees(): void {
    this.loading = true;
    
    // 1. Charger les patrimoines pour mapper nom <-> id
    this.patrimoineApi.getAll().subscribe({
      next: (patrimoines: any[]) => {
        this.patrimoinesList = patrimoines || [];
        this.patrimoinesList.forEach(p => {
          this.patrimoinesMap[p.id] = p.nom;
        });

        // 2. Charger tous les médias de ms-media
        this.mediaApi.getAll().subscribe({
          next: (data: any[]) => {
            this.medias = data || [];
            this.applyFilters();
            this.loading = false;
          },
          error: (err) => {
            console.error('Erreur chargement des médias', err);
            this.loading = false;
          }
        });
      },
      error: (err) => {
        console.error('Erreur chargement des patrimoines', err);
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredMedias = this.medias.filter(m => {
      // Recherche textuelle
      const q = this.searchQuery.trim().toLowerCase();
      const matchQuery = !q ||
        (m.nom && m.nom.toLowerCase().includes(q)) ||
        (m.description && m.description.toLowerCase().includes(q)) ||
        (m.auteur && m.auteur.toLowerCase().includes(q)) ||
        (m.credit && m.credit.toLowerCase().includes(q)) ||
        (this.getPatrimoineNom(m.patrimoineId).toLowerCase().includes(q));

      // Filtre type
      const matchType = this.selectedType === 'TOUS' || m.type === this.selectedType;

      // Filtre statut
      const matchStatut = this.selectedStatut === 'TOUS' || m.statut === this.selectedStatut;

      // Filtre patrimoine
      const matchPatrimoine = this.selectedPatrimoine === 'TOUS' || m.patrimoineId === this.selectedPatrimoine;

      return matchQuery && matchType && matchStatut && matchPatrimoine;
    });
  }

  filterByTypeCard(type: string): void {
    if (this.selectedType === type) {
      this.selectedType = 'TOUS';
    } else {
      this.selectedType = type;
    }
    this.applyFilters();
  }

  hasActiveFilters(): boolean {
    return !!this.searchQuery || this.selectedType !== 'TOUS' || this.selectedStatut !== 'TOUS' || this.selectedPatrimoine !== 'TOUS';
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedType = 'TOUS';
    this.selectedStatut = 'TOUS';
    this.selectedPatrimoine = 'TOUS';
    this.applyFilters();
  }

  getPatrimoineNom(patrimoineId: string): string {
    return this.patrimoinesMap[patrimoineId] || 'Site non spécifié';
  }

  getCountByType(type: string): number {
    return this.medias.filter(m => m.type === type).length;
  }

  // Lightbox
  openPreview(media: any): void {
    this.previewMedia = media;
  }

  closePreview(): void {
    this.previewMedia = null;
  }

  // Suppression
  openDeleteModal(media: any): void {
    this.deleteModalMedia = media;
  }

  closeDeleteModal(): void {
    this.deleteModalMedia = null;
    this.isDeleting = false;
  }

  confirmSuppression(): void {
    if (!this.deleteModalMedia) return;
    this.isDeleting = true;
    const id = this.deleteModalMedia.id;

    this.mediaApi.deleteMedia(id).subscribe({
      next: () => {
        this.medias = this.medias.filter(m => m.id !== id);
        this.applyFilters();
        this.closeDeleteModal();
        this.showToast('Média supprimé avec succès.');
      },
      error: (err) => {
        this.isDeleting = false;
        console.error('Erreur lors de la suppression', err);
        this.toastService.error("Erreur lors de la suppression du média.");
      }
    });
  }

  showToast(msg: string): void {
    this.toastService.success(msg);
  }

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getTypeBadgeClass(type: string): string {
    switch (type) {
      case 'PHOTO': return 'bg-purple-100 text-purple-800 border-purple-200';
      case 'VIDEO': return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'DOCUMENT': return 'bg-amber-100 text-amber-800 border-amber-200';
      case 'AUDIO': return 'bg-rose-100 text-rose-800 border-rose-200';
      case 'MODELE_3D': return 'bg-teal-100 text-teal-800 border-teal-200';
      case 'PANORAMA_360': return 'bg-indigo-100 text-indigo-800 border-indigo-200';
      default: return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  }

  getTypeIcon(type: string): string {
    switch (type) {
      case 'PHOTO': return 'fa-camera';
      case 'VIDEO': return 'fa-video';
      case 'DOCUMENT': return 'fa-file-pdf';
      case 'AUDIO': return 'fa-volume-up';
      case 'MODELE_3D': return 'fa-cube';
      case 'PANORAMA_360': return 'fa-vr-cardboard';
      default: return 'fa-image';
    }
  }

  getStatutBadgeClass(statut: string): string {
    switch (statut) {
      case 'PUBLIE': return 'bg-emerald-100 text-emerald-800 border-emerald-200';
      case 'VALIDE': return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'BROUILLON': return 'bg-gray-100 text-gray-700 border-gray-200';
      case 'ARCHIVE': return 'bg-rose-100 text-rose-800 border-rose-200';
      default: return 'bg-gray-100 text-gray-700 border-gray-200';
    }
  }
}
