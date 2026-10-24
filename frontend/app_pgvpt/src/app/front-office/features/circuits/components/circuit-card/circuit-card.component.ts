import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CircuitUI, CircuitFacadeService, NiveauDifficulte, TypeCircuit } from '../../../../../core/services/circuit-facade.service';

@Component({
  selector: 'app-circuit-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './circuit-card.component.html',
  styleUrl: './circuit-card.component.css'
})
export class CircuitCardComponent {
  @Input() circuit!: CircuitUI;

  private circuitFacade = inject(CircuitFacadeService);

  /** Classe CSS selon le niveau de difficulté */
  getDifficultyClass(): string {
    switch (this.circuit.niveauDifficulte) {
      case NiveauDifficulte.Facile: return 'difficulty-facile';
      case NiveauDifficulte.Moyen: return 'difficulty-moyen';
      case NiveauDifficulte.Difficile: return 'difficulty-difficile';
      default: return 'difficulty-facile';
    }
  }

  /** Label lisible pour le niveau de difficulté */
  getDifficultyLabel(): string {
    switch (this.circuit.niveauDifficulte) {
      case NiveauDifficulte.Facile: return 'Facile';
      case NiveauDifficulte.Moyen: return 'Moyen';
      case NiveauDifficulte.Difficile: return 'Difficile';
      default: return 'Facile';
    }
  }

  /** Label lisible pour le type de circuit */
  getTypeLabel(): string {
    switch (this.circuit.type) {
      case TypeCircuit.Culturel: return 'Culturel';
      case TypeCircuit.Historique: return 'Historique';
      case TypeCircuit.Nature: return 'Nature';
      case TypeCircuit.Religieux: return 'Religieux';
      case TypeCircuit.Gastronomique: return 'Gastronomique';
      case TypeCircuit.Artisanal: return 'Artisanal';
      case TypeCircuit.Mixte: return 'Mixte';
      default: return 'Circuit';
    }
  }

  /** Formate la durée via le service facade */
  formatDuree(): string {
    return this.circuitFacade.formatDuree(this.circuit.dureeMinutes);
  }

  /** Formate le prix en FCFA */
  formatPrix(): string {
    const cout = this.circuit.coutTotal;
    if (!cout || cout <= 0) return 'Gratuit';
    return new Intl.NumberFormat('fr-FR').format(cout) + ' FCFA';
  }
}
