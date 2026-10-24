import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modale-lecture-recit',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modale-lecture-recit.component.html'
})
export class ModaleLectureRecitComponent {
  @Input() isOpen = false;
  @Input() contenu: any = null;

  @Output() close = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
  }

  getReadingTime(text: string): number {
    if (!text) return 2;
    const words = text.trim().split(/\s+/).length;
    return Math.max(1, Math.ceil(words / 200));
  }

  getContenuTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      'ARTICLE': 'Article Historique',
      'GUIDE_VISITE': 'Guide de Visite',
      'RECIT': 'Récit Vivant',
      'CHRONIQUE': 'Chronique',
      'ANECDOTE': 'Anecdote Culturelle',
      'LEGENDE': 'Légende & Mythe',
      'TEMOIGNAGE': 'Témoignage'
    };
    return labels[type] || type || 'Récit';
  }

  getContenuTypeIcon(type: string): string {
    const icons: Record<string, string> = {
      'ARTICLE': 'fa-feather-pointed',
      'GUIDE_VISITE': 'fa-compass',
      'RECIT': 'fa-book-open',
      'CHRONIQUE': 'fa-clock-rotate-left',
      'ANECDOTE': 'fa-lightbulb',
      'LEGENDE': 'fa-dragon',
      'TEMOIGNAGE': 'fa-comments'
    };
    return icons[type] || 'fa-newspaper';
  }
}
