import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CircuitUI, CircuitFacadeService, NiveauDifficulte, TypeCircuit } from '../../../../../../../core/services/circuit-facade.service';

@Component({
  selector: 'app-circuit-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './circuit-sidebar.component.html',
  styleUrls: ['./circuit-sidebar.component.css']
})
export class CircuitSidebarComponent {
  @Input() circuit!: CircuitUI;

  private circuitFacade = inject(CircuitFacadeService);

  formatPrix(): string {
    const cout = this.circuit?.coutTotal;
    if (!cout || cout <= 0) return 'Gratuit';
    return new Intl.NumberFormat('fr-FR').format(cout) + ' FCFA';
  }

  getTypeLabel(): string {
    switch (this.circuit?.type) {
      case TypeCircuit.Culturel: return 'Culturel';
      case TypeCircuit.Historique: return 'Historique';
      case TypeCircuit.Nature: return 'Nature & Écotourisme';
      case TypeCircuit.Religieux: return 'Pèlerinage & Spiritualité';
      case TypeCircuit.Gastronomique: return 'Gastronomique';
      case TypeCircuit.Artisanal: return 'Artisanal';
      case TypeCircuit.Mixte: return 'Mixte & Aventure';
      default: return this.circuit?.type || 'Circuit';
    }
  }

  getDifficultyLabel(): string {
    switch (this.circuit?.niveauDifficulte) {
      case NiveauDifficulte.Facile: return 'Facile & Accessible';
      case NiveauDifficulte.Moyen: return 'Niveau Modéré';
      case NiveauDifficulte.Difficile: return 'Parcours Sportif';
      default: return 'Niveau Modéré';
    }
  }

  getDifficultyClass(): string {
    switch (this.circuit?.niveauDifficulte) {
      case NiveauDifficulte.Facile: return 'difficulty-facile';
      case NiveauDifficulte.Difficile: return 'difficulty-difficile';
      default: return 'difficulty-moyen';
    }
  }
}
