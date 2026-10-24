import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import { ContenuApiService } from '../../../../../core/services/api/contenu-api.service';

import { ToastService } from '../../../../../core/services/toast.service';
import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-contenus-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ConfirmModalComponent],
  templateUrl: './contenus-list.component.html',
  styleUrls: ['./contenus-list.component.css']
})
export class ContenusListComponent implements OnInit {
  contenus: any[] = [];
  filteredContenus: any[] = [];
  loading = true;

  // Filtres et recherche
  searchQuery: string = '';
  selectedType: string = 'TOUS';
  selectedStatut: string = 'TOUS';
  selectedLangue: string = 'TOUTES';

  // Mode d'affichage (Tableau ou Cartes)
  viewMode: 'list' | 'grid' = 'list';

  // Modal d'aperçu rapide (Lecture complète)
  previewContenu: any = null;

  // Modal de confirmation de suppression
  deleteModalContenu: any = null;
  isDeleting: boolean = false;

  types = [
    'ARTICLE', 'DESCRIPTION', 'HISTORIQUE', 'RECIT', 'TEMOIGNAGE', 'GUIDE_VISITE', 'ACTUALITE'
  ];
  statuts = [
    'PUBLIE', 'VALIDE', 'EN_REVISION', 'BROUILLON', 'ARCHIVE'
  ];

  constructor(
    private facade: PatrimoineFacadeService,
    private contenuApi: ContenuApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.chargerContenus();
  }

  chargerContenus(): void {
    this.loading = true;
    this.facade.getAllContenus().subscribe({
      next: (data) => {
        this.contenus = data || [];
        this.applyFilters();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des contenus', err);
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredContenus = this.contenus.filter(c => {
      // Filtre recherche texte
      const q = this.searchQuery.trim().toLowerCase();
      const matchQuery = !q || 
        (c.titre && c.titre.toLowerCase().includes(q)) ||
        (c.resume && c.resume.toLowerCase().includes(q)) ||
        (c.corps && c.corps.toLowerCase().includes(q)) ||
        (Array.isArray(c.motsCles) && c.motsCles.some((k: string) => k.toLowerCase().includes(q)));

      // Filtre statut
      const matchStatut = this.selectedStatut === 'TOUS' || c.statut === this.selectedStatut;

      // Filtre type
      const matchType = this.selectedType === 'TOUS' || c.type === this.selectedType;

      // Filtre langue
      const matchLangue = this.selectedLangue === 'TOUTES' || (c.langue && c.langue.toUpperCase() === this.selectedLangue);

      return matchQuery && matchStatut && matchType && matchLangue;
    });
  }

  filterByStatutCard(statut: string): void {
    if (this.selectedStatut === statut) {
      this.selectedStatut = 'TOUS';
    } else {
      this.selectedStatut = statut;
    }
    this.applyFilters();
  }

  hasActiveFilters(): boolean {
    return !!this.searchQuery || this.selectedType !== 'TOUS' || this.selectedStatut !== 'TOUS' || this.selectedLangue !== 'TOUTES';
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedType = 'TOUS';
    this.selectedStatut = 'TOUS';
    this.selectedLangue = 'TOUTES';
    this.applyFilters();
  }

  getCountByStatut(statut: string): number {
    return this.contenus.filter(c => c.statut === statut).length;
  }

  // Suppression avec modal personnalisé
  openDeleteModal(contenu: any): void {
    this.deleteModalContenu = contenu;
  }

  closeDeleteModal(): void {
    this.deleteModalContenu = null;
    this.isDeleting = false;
  }

  confirmSuppression(): void {
    if (!this.deleteModalContenu) return;
    this.isDeleting = true;
    const id = this.deleteModalContenu.id;

    this.contenuApi.delete(id).subscribe({
      next: () => {
        this.contenus = this.contenus.filter(c => c.id !== id);
        this.applyFilters();
        this.closeDeleteModal();
        this.showToast('Contenu supprimé avec succès.');
      },
      error: (err) => {
        this.isDeleting = false;
        console.error('Erreur lors de la suppression', err);
        this.toastService.error("Une erreur est survenue lors de la suppression : " + (err?.error?.message || err?.message || 'Erreur inconnue'));
      }
    });
  }

  showToast(message: string): void {
    this.toastService.success(message);
  }

  getStatutBadgeClass(statut: string): string {
    switch (statut) {
      case 'PUBLIE': return 'bg-emerald-100 text-emerald-800 border-emerald-200';
      case 'VALIDE': return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'EN_REVISION': return 'bg-amber-100 text-amber-800 border-amber-200';
      case 'BROUILLON': return 'bg-gray-100 text-gray-700 border-gray-200';
      case 'ARCHIVE': return 'bg-rose-100 text-rose-800 border-rose-200';
      default: return 'bg-gray-100 text-gray-700 border-gray-200';
    }
  }

  getTypeBadgeClass(type: string): string {
    switch (type) {
      case 'ARTICLE':
      case 'ARTICLE_HISTORIQUE': return 'bg-purple-100 text-purple-800 border-purple-200';
      case 'GUIDE_VISITE': return 'bg-teal-100 text-teal-800 border-teal-200';
      case 'HISTORIQUE': return 'bg-amber-100 text-amber-900 border-amber-200';
      case 'RECIT':
      case 'LEGENDE_MYTHE': return 'bg-indigo-100 text-indigo-800 border-indigo-200';
      case 'TEMOIGNAGE': return 'bg-pink-100 text-pink-800 border-pink-200';
      case 'ACTUALITE':
      case 'EVENEMENT_CULTUREL': return 'bg-sky-100 text-sky-800 border-sky-200';
      case 'RECETTE_TRADITIONNELLE': return 'bg-orange-100 text-orange-800 border-orange-200';
      case 'BIOGRAPHIE': return 'bg-emerald-100 text-emerald-800 border-emerald-200';
      default: return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  }

  getTypeIcon(type: string): string {
    switch (type) {
      case 'ARTICLE':
      case 'ARTICLE_HISTORIQUE': return 'fa-scroll';
      case 'GUIDE_VISITE': return 'fa-compass';
      case 'HISTORIQUE': return 'fa-landmark';
      case 'RECIT':
      case 'LEGENDE_MYTHE': return 'fa-feather-alt';
      case 'TEMOIGNAGE': return 'fa-comment-dots';
      case 'ACTUALITE':
      case 'EVENEMENT_CULTUREL': return 'fa-calendar-star';
      case 'RECETTE_TRADITIONNELLE': return 'fa-utensils';
      case 'BIOGRAPHIE': return 'fa-user-tie';
      default: return 'fa-file-alt';
    }
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'ARTICLE_HISTORIQUE': return 'Article Historique';
      case 'GUIDE_VISITE': return 'Guide de Visite';
      case 'LEGENDE_MYTHE': return 'Légende & Mythe';
      case 'RECETTE_TRADITIONNELLE': return 'Recette Traditionnelle';
      case 'BIOGRAPHIE': return 'Biographie';
      case 'EVENEMENT_CULTUREL': return 'Événement Culturel';
      case 'ARTICLE': return 'Article';
      case 'DESCRIPTION': return 'Description';
      case 'HISTORIQUE': return 'Historique';
      case 'RECIT': return 'Récit';
      case 'TEMOIGNAGE': return 'Témoignage';
      case 'ACTUALITE': return 'Actualité';
      default: return type || 'Article';
    }
  }

  getStatutLabel(statut: string): string {
    switch (statut) {
      case 'PUBLIE': return 'Publié';
      case 'VALIDE': return 'Validé';
      case 'EN_REVISION': return 'En révision';
      case 'BROUILLON': return 'Brouillon';
      case 'ARCHIVE': return 'Archivé';
      default: return statut || 'Inconnu';
    }
  }

  getLanguageLabel(langue: string): string {
    switch (langue?.toUpperCase()) {
      case 'FR': return 'Français (FR)';
      case 'EN': return 'English (EN)';
      case 'WO': return 'Wolof (WO)';
      default: return langue || 'FR';
    }
  }

  // Modal d'aperçu de contenu
  openPreview(contenu: any): void {
    this.previewContenu = contenu;
  }

  closePreview(): void {
    this.previewContenu = null;
  }
}
