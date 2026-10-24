import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-modale-apercu-patrimoine',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './modale-apercu-patrimoine.component.html'
})
export class ModaleApercuPatrimoineComponent {
  @Input() item: any | null = null;
  @Output() close = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
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
