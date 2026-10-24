import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-etat-conservation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './etat-conservation.component.html'
})
export class EtatConservationComponent {
  @Input() patrimoine: any;
  @Input() expandedThreatIndex: number = -1;

  @Output() toggleThreat = new EventEmitter<number>();

  onToggleThreat(index: number): void {
    this.toggleThreat.emit(index);
  }

  getMenaceColor(niveau: string): string {
    if (!niveau) return '#666';
    switch (niveau.toUpperCase()) {
      case 'FAIBLE': return '#10B981';
      case 'MOYEN': return '#F59E0B';
      case 'ÉLEVÉ':
      case 'ELEVE': return '#EF4444';
      case 'CRITIQUE': return '#991B1B';
      default: return '#666';
    }
  }

  formatBudget(val: number | string | null | undefined): string {
    if (val === null || val === undefined || val === '') return '';
    const num = Number(val);
    if (isNaN(num)) return String(val);
    const formatted = new Intl.NumberFormat('fr-FR', {
      maximumFractionDigits: 0
    }).format(num);
    return `${formatted.replace(/[\u202F\u00A0]/g, ' ')} FCFA`;
  }

  /**
   * Parse mesuresPrevention string into individual items.
   * Handles bullet separators (•, -, *) and newlines.
   */
  parseMesures(text: string): string[] {
    if (!text) return [];
    // Split by bullet chars or newlines
    const items = text
      .split(/[•\n]/)
      .map(s => s.replace(/^[\s\-\*]+/, '').trim())
      .filter(s => s.length > 0);
    return items;
  }
}
