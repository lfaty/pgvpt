import { Component, Input, OnInit, AfterViewInit, OnDestroy, OnChanges, SimpleChanges, inject, PLATFORM_ID, ViewEncapsulation } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import * as L from 'leaflet';

// Fix Leaflet default icon paths for Angular/Webpack
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
});

@Component({
  selector: 'app-carte-interactive-accueil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './carte-interactive-accueil.component.html',
  styleUrl: './carte-interactive-accueil.component.css',
  encapsulation: ViewEncapsulation.None
})
export class CarteInteractiveAccueilComponent implements OnInit, AfterViewInit, OnDestroy, OnChanges {
  @Input() patrimoines: PatrimoineUI[] = [];

  private platformId = inject(PLATFORM_ID);
  private facadeService = inject(PatrimoineFacadeService);

  private map: L.Map | null = null;
  private markers: L.Marker[] = [];

  filterCategories: { [key: string]: boolean } = {
    'PATRIMOINE_NATUREL': true,
    'PATRIMOINE_CULTUREL': true,
    'PATRIMOINE_MEMORIEL': true,
    'PATRIMOINE_COLONIAL': true,
    'PATRIMOINE_RELIGIEUX': true
  };

  filterRegion: string = 'Toutes';
  regions: string[] = [];

  readonly categoriesList = [
    { key: 'PATRIMOINE_CULTUREL', label: 'Culturel', color: '#D97706', icon: 'fa-solid fa-landmark' },
    { key: 'PATRIMOINE_NATUREL', label: 'Naturel', color: '#059669', icon: 'fa-solid fa-leaf' },
    { key: 'PATRIMOINE_MEMORIEL', label: 'Mémoriel', color: '#DC2626', icon: 'fa-solid fa-dove' },
    { key: 'PATRIMOINE_COLONIAL', label: 'Colonial & Urbain', color: '#2563EB', icon: 'fa-solid fa-monument' },
    { key: 'PATRIMOINE_RELIGIEUX', label: 'Spirituel & Religieux', color: '#7C3AED', icon: 'fa-solid fa-mosque' }
  ];

  private readonly markerColors: { [key: string]: string } = {
    'PATRIMOINE_NATUREL': '#059669',
    'PATRIMOINE_CULTUREL': '#D97706',
    'PATRIMOINE_MEMORIEL': '#DC2626',
    'PATRIMOINE_MUSEAL': '#DC2626',
    'PATRIMOINE_HISTORIQUE': '#D97706',
    'PATRIMOINE_COLONIAL': '#2563EB',
    'PATRIMOINE_RELIGIEUX': '#7C3AED'
  };

  ngOnInit(): void {
    this.extractRegions();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patrimoines']) {
      this.extractRegions();
      if (this.map) {
        this.updateMarkers();
      }
    }
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      setTimeout(() => this.initMap(), 300);
    }
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
      this.map = null;
    }
  }

  private extractRegions(): void {
    const published = (this.patrimoines || []).filter(p => p.statut === 'PUBLIE');
    this.regions = [...new Set(published.map(p => p.region).filter(Boolean))] as string[];
  }

  private initMap(): void {
    const senegalBounds = L.latLngBounds(
      L.latLng(12.3, -17.53),
      L.latLng(16.69, -11.34)
    );

    this.map = L.map('senegal-map', {
      zoomControl: true,
      dragging: true,
      touchZoom: true,
      doubleClickZoom: true,
      scrollWheelZoom: false,
      boxZoom: false,
      zoomSnap: 0.2
    });

    this.map.fitBounds(senegalBounds, { padding: [20, 20] });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap'
    }).addTo(this.map);

    setTimeout(() => {
      this.map?.invalidateSize();
      this.map?.fitBounds(senegalBounds, { padding: [20, 20] });
    }, 200);

    this.updateMarkers();
  }

  updateMarkers(): void {
    if (!this.map) return;

    this.markers.forEach(m => m.remove());
    this.markers = [];

    const filtered = this.patrimoines.filter(p => {
      if (p.statut !== 'PUBLIE') return false;
      const groupKey = this.getCategoryGroupKey(p.categorie);
      const catOk = !!this.filterCategories[groupKey];
      const regionOk = this.filterRegion === 'Toutes' || p.region === this.filterRegion;
      return catOk && regionOk;
    });

    filtered.forEach(p => {
      if (p.latitude && p.longitude) {
        const groupKey = this.getCategoryGroupKey(p.categorie);
        const color = this.markerColors[p.categorie] || this.markerColors[groupKey] || '#0b5d5e';
        const imgUrl = p.imageUrl || (p as any).photoPrincipale || 'assets/images/patrimoine_1.jpg';
        const catLabel = this.facadeService.formatCategorieLabel(p.categorie);

        const icon = L.divIcon({
          className: 'custom-heritage-marker-wrapper',
          html: `<div class="pulse-marker" style="background-color: ${color};">
                   <div class="marker-core"></div>
                 </div>`,
          iconSize: [24, 24],
          iconAnchor: [12, 12]
        });

        const popupHtml = `
          <div class="heritage-map-popup">
            <div class="popup-thumb-wrap">
              <img src="${imgUrl}" alt="${p.nom}">
              <span class="popup-badge" style="background-color: ${color};">${catLabel}</span>
            </div>
            <div class="popup-content-wrap">
              <h4 class="popup-title">${p.nom}</h4>
              <p class="popup-region">
                <i class="fa-solid fa-location-dot" style="margin-right:4px;color:#D16933;"></i>
                ${p.region || 'Sénégal'}
              </p>
              <a href="/patrimoines/${p.id}" class="popup-cta">
                Explorer ce trésor <i class="fa-solid fa-arrow-right" style="margin-left:4px;"></i>
              </a>
            </div>
          </div>
        `;

        const marker = L.marker([p.latitude, p.longitude], { icon })
          .bindPopup(popupHtml, {
            maxWidth: 260,
            className: 'luxury-leaflet-popup'
          });

        if (this.map) {
          marker.addTo(this.map);
        }
        this.markers.push(marker);
      }
    });
  }

  getCategoryGroupKey(cat?: string): string {
    if (!cat) return 'PATRIMOINE_CULTUREL';
    if (cat === 'PATRIMOINE_MUSEAL' || cat === 'PATRIMOINE_MEMORIEL') return 'PATRIMOINE_MEMORIEL';
    if (cat === 'PATRIMOINE_HISTORIQUE' || cat === 'PATRIMOINE_ARCHITECTURAL' || cat === 'PATRIMOINE_ARCHEOLOGIQUE') return 'PATRIMOINE_CULTUREL';
    if (cat === 'PATRIMOINE_MARITIME' || cat === 'PATRIMOINE_RURAL') return 'PATRIMOINE_NATUREL';
    return cat;
  }

  getCategoryCount(catKey: string): number {
    return this.patrimoines.filter(p => p.statut === 'PUBLIE' && this.getCategoryGroupKey(p.categorie) === catKey).length;
  }

  get filteredCount(): number {
    return this.patrimoines.filter(p => {
      if (p.statut !== 'PUBLIE') return false;
      const groupKey = this.getCategoryGroupKey(p.categorie);
      const catOk = !!this.filterCategories[groupKey];
      const regionOk = this.filterRegion === 'Toutes' || p.region === this.filterRegion;
      return catOk && regionOk;
    }).length;
  }

  toggleCategoryFilter(catKey: string): void {
    this.filterCategories[catKey] = !this.filterCategories[catKey];
    this.updateMarkers();
  }

  onRegionChange(value: string): void {
    this.filterRegion = value;
    this.updateMarkers();
  }

  resetFilters(): void {
    Object.keys(this.filterCategories).forEach(k => this.filterCategories[k] = true);
    this.filterRegion = 'Toutes';
    this.updateMarkers();
  }
}
