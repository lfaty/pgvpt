import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-patrimoine-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './patrimoine-card.component.html',
  styleUrl: './patrimoine-card.component.css'
})
export class PatrimoineCardComponent {
  @Input() patrimoine!: PatrimoineUI;

  /** Formate les enums de catégorie en labels lisibles et courts */
  formatCategorie(cat: string | undefined): string {
    if (!cat) return 'Patrimoine';
    const labels: Record<string, string> = {
      'PATRIMOINE_NATUREL': 'Naturel',
      'PATRIMOINE_CULTUREL': 'Culturel',
      'PATRIMOINE_HISTORIQUE': 'Historique',
      'PATRIMOINE_ARCHITECTURAL': 'Architectural',
      'PATRIMOINE_RELIGIEUX': 'Religieux',
      'PATRIMOINE_COLONIAL': 'Colonial',
      'PATRIMOINE_ARCHEOLOGIQUE': 'Archéologique',
      'PATRIMOINE_MUSEAL': 'Muséal',
      'PATRIMOINE_MEMORIEL': 'Mémoriel',
      'PATRIMOINE_MARITIME': 'Maritime',
      'PATRIMOINE_RURAL': 'Rural'
    };
    return labels[cat] || cat.replace('PATRIMOINE_', '').replace(/_/g, ' ');
  }
}
