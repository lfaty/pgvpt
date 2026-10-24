import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { 
  CircuitFacadeService, 
  CircuitUI, 
  StatutCircuit, 
  TypeCircuit, 
  NiveauDifficulte 
} from '../../../../../core/services/circuit-facade.service';

import { ToastService } from '../../../../../core/services/toast.service';
import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-admin-circuits-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ConfirmModalComponent],
  templateUrl: './circuits-list.component.html',
  styleUrls: ['./circuits-list.component.css']
})
export class CircuitsListComponent implements OnInit {
  circuits: CircuitUI[] = [];
  filteredCircuits: CircuitUI[] = [];
  isLoading: boolean = true;

  // Filtres
  searchQuery: string = '';
  selectedStatut: string = 'TOUS';
  selectedType: string = 'TOUS';
  selectedDifficulte: string = 'TOUTES';

  // KPIs
  totalCount: number = 0;
  publieCount: number = 0;
  brouillonCount: number = 0;
  archiveCount: number = 0;

  // Modals & Notifications
  deleteConfirmCircuit: CircuitUI | null = null;

  types = [
    { value: 'TOUS', label: 'Tous les types' },
    { value: TypeCircuit.Culturel, label: 'Culturel' },
    { value: TypeCircuit.Historique, label: 'Historique' },
    { value: TypeCircuit.Nature, label: 'Nature' },
    { value: TypeCircuit.Religieux, label: 'Religieux' },
    { value: TypeCircuit.Gastronomique, label: 'Gastronomique' },
    { value: TypeCircuit.Artisanal, label: 'Artisanal' },
    { value: TypeCircuit.Mixte, label: 'Mixte' }
  ];

  difficultes = [
    { value: 'TOUTES', label: 'Toutes les difficultés' },
    { value: NiveauDifficulte.Facile, label: 'Facile' },
    { value: NiveauDifficulte.Moyen, label: 'Moyen' },
    { value: NiveauDifficulte.Difficile, label: 'Difficile' }
  ];

  statuts = [
    { value: 'TOUS', label: 'Tous les statuts' },
    { value: StatutCircuit.Publie, label: 'Publié' },
    { value: StatutCircuit.Brouillon, label: 'Brouillon' },
    { value: StatutCircuit.Archive, label: 'Archivé' }
  ];

  constructor(
    private circuitFacade: CircuitFacadeService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadCircuits();
  }

  loadCircuits(): void {
    this.isLoading = true;
    this.circuitFacade.getCircuits().subscribe({
      next: (data) => {
        this.circuits = data;
        this.updateKpis();
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement des circuits admin:', err);
        this.isLoading = false;
        this.showNotification('Erreur de connexion au service circuits.');
      }
    });
  }

  updateKpis(): void {
    this.totalCount = this.circuits.length;
    this.publieCount = this.circuits.filter(c => c.statut === StatutCircuit.Publie).length;
    this.brouillonCount = this.circuits.filter(c => c.statut === StatutCircuit.Brouillon).length;
    this.archiveCount = this.circuits.filter(c => c.statut === StatutCircuit.Archive).length;
  }

  filterByStatutCard(statut: string): void {
    this.selectedStatut = statut;
    this.applyFilters();
  }

  applyFilters(): void {
    const q = this.searchQuery.trim().toLowerCase();

    this.filteredCircuits = this.circuits.filter(c => {
      // 1. Recherche par texte
      const matchSearch = !q ||
        (c.nom && c.nom.toLowerCase().includes(q)) ||
        (c.description && c.description.toLowerCase().includes(q));

      // 2. Statut
      const matchStatut = this.selectedStatut === 'TOUS' || c.statut === this.selectedStatut;

      // 3. Type
      const matchType = this.selectedType === 'TOUS' || c.type === this.selectedType;

      // 4. Difficulté
      const matchDiff = this.selectedDifficulte === 'TOUTES' || c.niveauDifficulte === this.selectedDifficulte;

      return matchSearch && matchStatut && matchType && matchDiff;
    });
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedStatut = 'TOUS';
    this.selectedType = 'TOUS';
    this.selectedDifficulte = 'TOUTES';
    this.applyFilters();
  }

  // --- Actions Cycle de vie ---

  toggleStatut(circuit: CircuitUI): void {
    if (!circuit.id) return;

    if (circuit.statut === StatutCircuit.Publie) {
      this.circuitFacade.depublierCircuit(circuit.id).subscribe({
        next: () => {
          (circuit as any).statut = StatutCircuit.Brouillon;
          this.updateKpis();
          this.applyFilters();
          this.showNotification(`Le circuit "${circuit.nom}" a été retiré de la publication.`);
        },
        error: () => this.showNotification('Impossible de dépublier le circuit.')
      });
    } else {
      this.circuitFacade.publierCircuit(circuit.id).subscribe({
        next: () => {
          (circuit as any).statut = StatutCircuit.Publie;
          this.updateKpis();
          this.applyFilters();
          this.showNotification(`Le circuit "${circuit.nom}" est maintenant en ligne !`);
        },
        error: () => this.showNotification('Impossible de publier le circuit.')
      });
    }
  }

  archiver(circuit: CircuitUI): void {
    if (!circuit.id) return;

    this.circuitFacade.archiverCircuit(circuit.id).subscribe({
      next: () => {
        (circuit as any).statut = StatutCircuit.Archive;
        this.updateKpis();
        this.applyFilters();
        this.showNotification(`Le circuit "${circuit.nom}" a été archivé.`);
      },
      error: () => this.showNotification('Impossible d\'archiver le circuit.')
    });
  }

  // --- Suppression ---

  demanderSuppression(circuit: CircuitUI): void {
    this.deleteConfirmCircuit = circuit;
  }

  annulerSuppression(): void {
    this.deleteConfirmCircuit = null;
  }

  confirmerSuppression(): void {
    if (!this.deleteConfirmCircuit?.id) return;
    const id = this.deleteConfirmCircuit.id;
    const nom = this.deleteConfirmCircuit.nom;

    this.circuitFacade.deleteCircuit(id).subscribe({
      next: () => {
        this.circuits = this.circuits.filter(c => c.id !== id);
        this.deleteConfirmCircuit = null;
        this.updateKpis();
        this.applyFilters();
        this.showNotification(`Le circuit "${nom}" a été supprimé.`);
      },
      error: () => {
        this.deleteConfirmCircuit = null;
        this.showNotification('Erreur lors de la suppression du circuit.');
      }
    });
  }

  // --- Utilitaires d'affichage ---

  showNotification(msg: string): void {
    if (msg.toLowerCase().includes('erreur')) {
      this.toastService.error(msg);
    } else {
      this.toastService.success(msg);
    }
  }

  formatDuree(minutes?: number): string {
    return this.circuitFacade.formatDuree(minutes);
  }

  formatPrix(cout?: number): string {
    if (!cout || cout <= 0) return 'Gratuit';
    return new Intl.NumberFormat('fr-FR').format(cout) + ' FCFA';
  }

  getTypeLabel(type?: string): string {
    switch (type) {
      case TypeCircuit.Culturel: return 'Culturel';
      case TypeCircuit.Historique: return 'Historique';
      case TypeCircuit.Nature: return 'Nature';
      case TypeCircuit.Religieux: return 'Religieux';
      case TypeCircuit.Gastronomique: return 'Gastronomique';
      case TypeCircuit.Artisanal: return 'Artisanal';
      case TypeCircuit.Mixte: return 'Mixte';
      default: return type || 'Circuit';
    }
  }

  getDifficultyLabel(diff?: string): string {
    switch (diff) {
      case NiveauDifficulte.Facile: return 'Facile';
      case NiveauDifficulte.Moyen: return 'Moyen';
      case NiveauDifficulte.Difficile: return 'Difficile';
      default: return diff || 'Facile';
    }
  }

  getDifficultyClass(diff?: string): string {
    switch (diff) {
      case NiveauDifficulte.Facile: return 'diff-facile';
      case NiveauDifficulte.Moyen: return 'diff-moyen';
      case NiveauDifficulte.Difficile: return 'diff-difficile';
      default: return 'diff-facile';
    }
  }

  getStatutBadgeClass(statut?: string): string {
    switch (statut) {
      case StatutCircuit.Publie: return 'badge-publie';
      case StatutCircuit.Brouillon: return 'badge-brouillon';
      case StatutCircuit.Archive: return 'badge-archive';
      default: return 'badge-brouillon';
    }
  }
}
