import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SafeResourceUrl } from '@angular/platform-browser';
import { directionsUrl, formatCoords, humanize } from '../../patrimoine-display.utils';

@Component({
  selector: 'app-carte-localisation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './carte-localisation.component.html'
})
export class CarteLocalisationComponent {
  @Input() mapUrl: SafeResourceUrl | null = null;
  @Input() geolocalisation: any;

  get coordsLabel(): string {
    return formatCoords(this.geolocalisation?.latitude, this.geolocalisation?.longitude);
  }

  get methodeLabel(): string {
    return humanize(this.geolocalisation?.methodeAcquisition);
  }

  get itineraireUrl(): string | null {
    return directionsUrl(this.geolocalisation?.latitude, this.geolocalisation?.longitude);
  }

  pointItineraire(pt: any): string | null {
    return directionsUrl(pt?.latitude, pt?.longitude);
  }

  formatFiabilite(niveau?: string): string {
    switch (niveau) {
      case 'TRES_ELEVE': return 'Très élevé';
      case 'ELEVE': return 'Élevé';
      case 'MOYEN': return 'Moyen';
      case 'FAIBLE': return 'Faible';
      default: return niveau || 'Non renseigné';
    }
  }

  getFiabiliteBadgeClass(niveau?: string): string {
    switch (niveau) {
      case 'TRES_ELEVE': return 'bg-emerald-50 text-emerald-800 border border-emerald-200';
      case 'ELEVE': return 'bg-blue-50 text-blue-800 border border-blue-200';
      case 'MOYEN': return 'bg-amber-50 text-amber-800 border border-amber-200';
      case 'FAIBLE': return 'bg-rose-50 text-rose-800 border border-rose-200';
      default: return 'bg-gray-100 text-gray-700 border border-gray-200';
    }
  }

  getPointAccesIcon(type?: string): string {
    switch (type) {
      case 'ENTREE_PRINCIPALE': return 'fa-solid fa-door-open';
      case 'ENTREE_SECONDAIRE': return 'fa-solid fa-door-closed';
      case 'PARKING': return 'fa-solid fa-square-parking';
      case 'EMBARCADERE':
      case 'PORT': return 'fa-solid fa-ship';
      case 'QUAI':
      case 'GARE': return 'fa-solid fa-train-subway';
      case 'ARRET_BUS': return 'fa-solid fa-bus';
      case 'AEROPORT': return 'fa-solid fa-plane-arrival';
      case 'ACCUEIL': return 'fa-solid fa-circle-info';
      default: return 'fa-solid fa-location-dot';
    }
  }

  getPointAccesLabel(type?: string): string {
    switch (type) {
      case 'ENTREE_PRINCIPALE': return 'Entrée Principale';
      case 'ENTREE_SECONDAIRE': return 'Entrée Secondaire';
      case 'PARKING': return 'Parking';
      case 'EMBARCADERE': return 'Embarcadère';
      case 'PORT': return 'Port';
      case 'QUAI': return 'Quai';
      case 'GARE': return 'Gare';
      case 'ARRET_BUS': return 'Arrêt de bus';
      case 'AEROPORT': return 'Aéroport';
      case 'ACCUEIL': return 'Centre d\'accueil';
      default: return type || 'Accès';
    }
  }
}
