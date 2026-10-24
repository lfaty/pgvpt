import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { cleanCommune, cleanRegion, cleanZoneTouristique, directionsUrl, hasValue } from '../../patrimoine-display.utils';

interface Service { key: string; label: string; icon: string; }

/**
 * Carte latérale « Préparer votre visite » (sticky) : horaires du jour,
 * localisation administrative, services, langues, contact et itinéraire.
 */
@Component({
  selector: 'app-planifier-visite',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './planifier-visite.component.html'
})
export class PlanifierVisiteComponent {
  @Input() patrimoine: any;
  @Input() geolocalisation: any;
  @Output() navigate = new EventEmitter<string>();

  readonly services: Service[] = [
    { key: 'accessiblePublic', label: 'Ouvert au public', icon: 'fa-solid fa-users' },
    { key: 'guideDisponible', label: 'Guides locaux', icon: 'fa-solid fa-person-hiking' },
    { key: 'guideAudio', label: 'Audio-guide', icon: 'fa-solid fa-headphones' },
    { key: 'parking', label: 'Parking', icon: 'fa-solid fa-square-parking' },
    { key: 'accesTransportPublic', label: 'Transports publics', icon: 'fa-solid fa-bus' },
    { key: 'accesEnfant', label: 'Adapté aux enfants', icon: 'fa-solid fa-child-reaching' },
    { key: 'accessibilitePMR', label: 'Accessible PMR', icon: 'fa-solid fa-wheelchair' },
    { key: 'accesFauteuilRoulant', label: 'Fauteuil roulant', icon: 'fa-brands fa-accessible-icon' }
  ];

  private readonly jours = ['DIMANCHE', 'LUNDI', 'MARDI', 'MERCREDI', 'JEUDI', 'VENDREDI', 'SAMEDI'];

  get acc(): any { return this.patrimoine?.accessibilite || {}; }

  get availableCount(): number {
    return this.services.filter(s => !!this.acc[s.key]).length;
  }

  get sortedServices(): Service[] {
    // Services disponibles d'abord
    return [...this.services].sort((a, b) => Number(!!this.acc[b.key]) - Number(!!this.acc[a.key]));
  }

  get locationRows(): { label: string; value: string; icon: string }[] {
    const g = this.geolocalisation || {};
    const p = this.patrimoine || {};
    const communeClean = cleanCommune(g.commune || p.commune || cleanZoneTouristique(g.zoneTouristiqueNom));
    const rows = [
      { label: 'Région', value: cleanRegion(g.region || p.region), icon: 'fa-solid fa-map' },
      { label: 'Département', value: g.departement || p.departement, icon: 'fa-solid fa-map-pin' },
      { label: 'Commune', value: communeClean, icon: 'fa-solid fa-house-chimney' },
      { label: 'Pôle touristique', value: g.zoneTouristiqueNom, icon: 'fa-solid fa-umbrella-beach' }
    ];
    return rows.filter(r => hasValue(r.value) && r.value !== 'Plusieurs communes');
  }

  /** Horaire du jour courant, si renseigné. */
  get todaySchedule(): any | null {
    const h = this.patrimoine?.horaires;
    if (!Array.isArray(h) || h.length === 0) return null;
    const today = this.jours[new Date().getDay()];
    return h.find((x: any) => (x.jour || '').toUpperCase() === today) || null;
  }

  get itineraireUrl(): string | null {
    return directionsUrl(this.geolocalisation?.latitude, this.geolocalisation?.longitude);
  }

  get gestionnaire(): string | null {
    return this.patrimoine?.organismeGestionnaire || this.patrimoine?.gestionnaire || null;
  }

  hm(v?: string): string { return v ? v.substring(0, 5) : ''; }
}
