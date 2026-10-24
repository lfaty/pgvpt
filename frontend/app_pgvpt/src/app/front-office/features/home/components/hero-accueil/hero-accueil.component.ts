import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-hero-accueil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hero-accueil.component.html',
  styleUrl: './hero-accueil.component.css'
})
export class HeroAccueilComponent {
  @Input() heroImages: string[] = [];
  @Input() currentHeroIndex: number = 0;
  @Input() searchQuery: string = '';
  @Input() unescoCount: number = 0;
  @Input() regionsCount: number = 0;
  @Input() totalMedias: number = 0;
  @Input() totalContenus: number = 0;
  @Input() patrimoinesLength: number = 0;

  @Output() searchQueryChange = new EventEmitter<string>();
  @Output() search = new EventEmitter<void>();
  @Output() quickSearchEvent = new EventEmitter<string>();
  @Output() filterUnescoEvent = new EventEmitter<void>();
  @Output() scrollToSectionEvent = new EventEmitter<string>();
  @Output() goToCatalogueEvent = new EventEmitter<void>();

  onQueryChange(value: string): void {
    this.searchQuery = value;
    this.searchQueryChange.emit(value);
  }

  onSearch(): void {
    this.search.emit();
  }

  quickSearch(term: string): void {
    this.quickSearchEvent.emit(term);
  }

  filterUnesco(): void {
    this.filterUnescoEvent.emit();
  }

  scrollToSection(id: string): void {
    this.scrollToSectionEvent.emit(id);
  }

  goToCatalogue(): void {
    this.goToCatalogueEvent.emit();
  }
}
