import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-recits-patrimoine',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './recits-patrimoine.component.html'
})
export class RecitsPatrimoineComponent {
  @Input() patrimoine: any;
  @Input() contenus: any[] = [];
  @Input() filteredContenus: any[] = [];
  @Input() uniqueContenuTypes: { type: string; label: string; count: number }[] = [];
  @Input() selectedContenuType: string = 'TOUS';
  @Input() contenuSearchQuery: string = '';
  @Input() allMedias: any[] = [];
  @Input() medias: any[] = [];
  @Input() mainImage: string | undefined;

  @Output() contenuTypeChange = new EventEmitter<string>();
  @Output() searchQueryChange = new EventEmitter<string>();
  @Output() selectContenu = new EventEmitter<any>();

  setContenuTypeFilter(type: string): void {
    this.selectedContenuType = type;
    this.contenuTypeChange.emit(type);
  }

  onSearchChange(): void {
    this.searchQueryChange.emit(this.contenuSearchQuery);
  }

  resetFilters(): void {
    this.selectedContenuType = 'TOUS';
    this.contenuSearchQuery = '';
    this.contenuTypeChange.emit('TOUS');
    this.searchQueryChange.emit('');
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

  getMediaUrlForContenu(c: any): string | null {
    if (c.mediaIds && c.mediaIds.length > 0) {
      const media = this.allMedias.find(m => m.id === c.mediaIds[0]) || this.medias.find(m => m.id === c.mediaIds[0]);
      if (media && media.url) return media.url;
    }
    return this.mainImage || null;
  }
}
