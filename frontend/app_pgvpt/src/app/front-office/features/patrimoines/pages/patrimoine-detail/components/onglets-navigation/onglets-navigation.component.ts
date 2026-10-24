import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

interface NavTab { id: string; label: string; icon: string; }

@Component({
  selector: 'app-onglets-navigation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './onglets-navigation.component.html'
})
export class OngletsNavigationComponent {
  @Input() activeSection: string = 'presentation';
  @Input() scrollProgress: number = 0;
  @Input() hasRecits: boolean = false;
  @Input() hasConservation: boolean = false;
  @Input() hasGalerie: boolean = true;
  @Input() titre: string = '';

  @Output() sectionChange = new EventEmitter<string>();

  /** Onglets affichés dans l'ordre réel des sections de la page. */
  get tabs(): NavTab[] {
    const tabs: NavTab[] = [
      { id: 'presentation', label: 'Aperçu', icon: 'fa-solid fa-binoculars' },
      { id: 'caracteristiques', label: 'Histoire & Patrimoine', icon: 'fa-solid fa-landmark' }
    ];
    if (this.hasRecits) tabs.push({ id: 'recits', label: 'Récits', icon: 'fa-solid fa-book-open' });
    if (this.hasGalerie) tabs.push({ id: 'galerie', label: 'Galerie', icon: 'fa-regular fa-images' });
    if (this.hasConservation) tabs.push({ id: 'conservation', label: 'Conservation', icon: 'fa-solid fa-seedling' });
    tabs.push({ id: 'localisation', label: 'Accès & Carte', icon: 'fa-solid fa-map-location-dot' });
    return tabs;
  }

  selectSection(sectionId: string): void {
    this.sectionChange.emit(sectionId);
  }
}
