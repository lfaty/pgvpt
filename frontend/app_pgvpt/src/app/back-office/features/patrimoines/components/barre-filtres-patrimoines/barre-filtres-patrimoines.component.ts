import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-barre-filtres-patrimoines',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './barre-filtres-patrimoines.component.html'
})
export class BarreFiltresPatrimoinesComponent {
  @Input() searchQuery: string = '';
  @Input() selectedRegion: string = 'TOUTES';
  @Input() selectedType: string = 'TOUS';
  @Input() selectedStatut: string = 'TOUS';
  @Input() selectedCategorie: string = 'TOUTES';
  @Input() selectedEtatConservation: string = 'TOUS';
  @Input() accessiblePublic: string = 'TOUS';
  @Input() inscritUnesco: string = 'TOUS';
  @Input() classePatrimoine: string = 'TOUS';
  @Input() viewMode: 'list' | 'grid' = 'list';
  @Input() showAdvancedFilters: boolean = false;
  @Input() activeFiltersCount: number = 0;
  @Input() hasActiveFilters: boolean = false;

  @Output() searchQueryChange = new EventEmitter<string>();
  @Output() selectedRegionChange = new EventEmitter<string>();
  @Output() selectedTypeChange = new EventEmitter<string>();
  @Output() selectedStatutChange = new EventEmitter<string>();
  @Output() selectedCategorieChange = new EventEmitter<string>();
  @Output() selectedEtatConservationChange = new EventEmitter<string>();
  @Output() accessiblePublicChange = new EventEmitter<string>();
  @Output() inscritUnescoChange = new EventEmitter<string>();
  @Output() classePatrimoineChange = new EventEmitter<string>();
  @Output() viewModeChange = new EventEmitter<'list' | 'grid'>();
  @Output() showAdvancedFiltersChange = new EventEmitter<boolean>();
  @Output() filterChange = new EventEmitter<void>();
  @Output() resetFiltersEvent = new EventEmitter<void>();

  onSearchChange(value: string): void {
    this.searchQuery = value;
    this.searchQueryChange.emit(value);
    this.filterChange.emit();
  }

  clearSearch(): void {
    this.searchQuery = '';
    this.searchQueryChange.emit('');
    this.filterChange.emit();
  }

  toggleAdvancedFilters(): void {
    this.showAdvancedFilters = !this.showAdvancedFilters;
    this.showAdvancedFiltersChange.emit(this.showAdvancedFilters);
  }

  setViewMode(mode: 'list' | 'grid'): void {
    this.viewMode = mode;
    this.viewModeChange.emit(mode);
  }

  onRegionChange(val: string): void {
    this.selectedRegion = val;
    this.selectedRegionChange.emit(val);
    this.filterChange.emit();
  }

  onTypeChange(val: string): void {
    this.selectedType = val;
    this.selectedTypeChange.emit(val);
    this.filterChange.emit();
  }

  onCategorieChange(val: string): void {
    this.selectedCategorie = val;
    this.selectedCategorieChange.emit(val);
    this.filterChange.emit();
  }

  onEtatConservationChange(val: string): void {
    this.selectedEtatConservation = val;
    this.selectedEtatConservationChange.emit(val);
    this.filterChange.emit();
  }

  onStatutChange(val: string): void {
    this.selectedStatut = val;
    this.selectedStatutChange.emit(val);
    this.filterChange.emit();
  }

  onAccessiblePublicChange(val: string): void {
    this.accessiblePublic = val;
    this.accessiblePublicChange.emit(val);
    this.filterChange.emit();
  }

  onInscritUnescoChange(val: string): void {
    this.inscritUnesco = val;
    this.inscritUnescoChange.emit(val);
    this.filterChange.emit();
  }

  onClassePatrimoineChange(val: string): void {
    this.classePatrimoine = val;
    this.classePatrimoineChange.emit(val);
    this.filterChange.emit();
  }

  onReset(): void {
    this.resetFiltersEvent.emit();
  }
}
