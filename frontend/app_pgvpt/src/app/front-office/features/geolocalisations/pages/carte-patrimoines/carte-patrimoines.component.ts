import { Component, OnInit, AfterViewInit, OnDestroy, inject, PLATFORM_ID, ElementRef, ViewChild } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import { GeoFacadeService } from '../../../../../core/services/geo-facade.service';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';
import * as L from 'leaflet';

// Configuration icônes Leaflet
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
});

export interface MapCategoryItem {
  key: string;
  label: string;
  icon: string;
  color: string;
  count?: number;
}

@Component({
  selector: 'app-carte-patrimoines',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './carte-patrimoines.component.html',
  styleUrls: ['./carte-patrimoines.component.css']
})
export class CartePatrimoinesComponent implements OnInit, AfterViewInit, OnDestroy {
  private facadeService = inject(PatrimoineFacadeService);
  private geoFacade = inject(GeoFacadeService);
  private platformId = inject(PLATFORM_ID);
  private router = inject(Router);

  @ViewChild('mapContainer') mapContainer!: ElementRef;

  patrimoines: PatrimoineUI[] = [];
  filteredPatrimoines: PatrimoineUI[] = [];
  selectedPatrimoine: PatrimoineUI | null = null;
  loading: boolean = true;
  backendError: boolean = false;

  // Filtres
  searchQuery: string = '';
  selectedCategory: string = 'TOUS';
  selectedRegion: string = 'TOUTES';
  sortBy: 'nom' | 'region' | 'pertinence' = 'pertinence';
  regions: string[] = [];

  // Panneau latéral repliable
  sidebarCollapsed: boolean = false;

  // Vue Mobile (carte vs liste)
  mobileView: 'map' | 'list' = 'map';

  // Styles de carte
  currentTileLayer: 'osm' | 'satellite' | 'topo' = 'osm';
  private tileLayerInstance: L.TileLayer | null = null;

  // Carte Leaflet
  private map: L.Map | null = null;
  private markersMap: Map<string, L.Marker> = new Map();

  // Limites géographiques du Sénégal
  private readonly senegalBounds: L.LatLngBoundsLiteral = [
    [12.30, -17.55],
    [16.70, -11.35]
  ];

  // Catégories avec styles et icônes
  categories: MapCategoryItem[] = [
    { key: 'TOUS', label: 'Tous', icon: 'fa-solid fa-earth-africa', color: '#0b5d5e' },
    { key: 'PATRIMOINE_CULTUREL', label: 'Culturel', icon: 'fa-solid fa-landmark', color: '#0b5d5e' },
    { key: 'PATRIMOINE_NATUREL', label: 'Naturel & Parcs', icon: 'fa-solid fa-leaf', color: '#059669' },
    { key: 'PATRIMOINE_MEMORIEL', label: 'Mémoriel', icon: 'fa-solid fa-dove', color: '#dc2626' },
    { key: 'PATRIMOINE_COLONIAL', label: 'Monuments', icon: 'fa-solid fa-archway', color: '#d97706' },
    { key: 'PATRIMOINE_RELIGIEUX', label: 'Spirituel', icon: 'fa-solid fa-mosque', color: '#7c3aed' }
  ];

  ngOnInit(): void {
    this.chargerPatrimoines();
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      setTimeout(() => {
        this.initMap();
      }, 150);
    }
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
      this.map = null;
    }
  }

  chargerPatrimoines(): void {
    this.loading = true;
    this.facadeService.getPatrimoines().subscribe({
      next: (data) => {
        // Uniquement les patrimoines avec de véritables coordonnées géographiques en base
        const resolved = data.map(p => ({
          ...p,
          latitude: p.latitude,
          longitude: p.longitude,
          region: p.region || 'Sénégal',
          imageUrl: p.imageUrl || 'assets/images/patrimoine_1.jpg'
        })).filter(p => p.latitude != null && p.longitude != null && !isNaN(p.latitude) && !isNaN(p.longitude));

        // Déduplication stricte par ID et par nom normalisé pour garantir un affichage unique
        const seenIds = new Set<string>();
        const seenNoms = new Set<string>();
        this.patrimoines = resolved.filter(p => {
          const id = p.id;
          const nom = (p.nom || '').trim().toLowerCase();
          if (id && seenIds.has(id)) return false;
          if (nom && seenNoms.has(nom)) return false;
          if (id) seenIds.add(id);
          if (nom) seenNoms.add(nom);
          return true;
        });

        this.extraireRegionsEtRafraichir();
      },
      error: (err) => {
        console.warn('Erreur chargement des patrimoines sur la carte :', err);
        this.backendError = true;
        this.patrimoines = [];
        this.extraireRegionsEtRafraichir();
      }
    });
  }

  private extraireRegionsEtRafraichir(): void {
    const regionSet = new Set<string>();
    this.patrimoines.forEach(p => {
      if (p.region) regionSet.add(p.region.trim());
    });
    this.regions = Array.from(regionSet).sort();

    // Mettre à jour les compteurs des catégories
    this.categories.forEach(cat => {
      if (cat.key === 'TOUS') {
        cat.count = this.patrimoines.length;
      } else {
        cat.count = this.patrimoines.filter(p => this.matchCategorie(p, cat.key)).length;
      }
    });

    this.appliquerFiltres();
    this.loading = false;
    if (this.map) {
      this.rafraichirMarqueurs();
    }
  }

  private initMap(): void {
    const el = document.getElementById('map-geolocalisation');
    if (!el || this.map) return;

    this.map = L.map('map-geolocalisation', {
      center: [14.4974, -14.4524],
      zoom: 7,
      zoomControl: false,
      attributionControl: false
    });

    // Attribution discrète
    L.control.attribution({ position: 'bottomright', prefix: false }).addTo(this.map);

    this.appliquerFondCarte(this.currentTileLayer);

    // Ajustement des dimensions
    setTimeout(() => {
      this.map?.invalidateSize();
      if (this.patrimoines.length > 0) {
        this.rafraichirMarqueurs();
      }
    }, 200);
  }

  appliquerFondCarte(style: 'osm' | 'satellite' | 'topo'): void {
    if (!this.map) return;
    this.currentTileLayer = style;

    if (this.tileLayerInstance) {
      this.tileLayerInstance.remove();
    }

    let url = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    let attribution = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';

    if (style === 'satellite') {
      url = 'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}';
      attribution = '&copy; Esri & Maxar';
    } else if (style === 'topo') {
      url = 'https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}';
      attribution = '&copy; Esri & contributors';
    }

    this.tileLayerInstance = L.tileLayer(url, {
      attribution,
      maxZoom: 19
    }).addTo(this.map);
  }

  appliquerFiltres(): void {
    let result = [...this.patrimoines];

    // Recherche plein texte
    if (this.searchQuery.trim()) {
      const q = this.searchQuery.toLowerCase().trim();
      result = result.filter(p =>
        (p.nom && p.nom.toLowerCase().includes(q)) ||
        (p.description && p.description.toLowerCase().includes(q)) ||
        (p.commune && p.commune.toLowerCase().includes(q)) ||
        (p.region && p.region.toLowerCase().includes(q))
      );
    }

    // Catégorie
    if (this.selectedCategory !== 'TOUS') {
      result = result.filter(p => this.matchCategorie(p, this.selectedCategory));
    }

    // Région
    if (this.selectedRegion !== 'TOUTES') {
      result = result.filter(p => p.region?.toLowerCase() === this.selectedRegion.toLowerCase());
    }

    // Tri
    if (this.sortBy === 'nom') {
      result.sort((a, b) => (a.nom || '').localeCompare(b.nom || ''));
    } else if (this.sortBy === 'region') {
      result.sort((a, b) => (a.region || '').localeCompare(b.region || ''));
    }

    this.filteredPatrimoines = result;

    if (this.map) {
      this.rafraichirMarqueurs();
    }
  }

  private matchCategorie(p: PatrimoineUI, catKey: string): boolean {
    const c = (p.categorie || '').toUpperCase();
    const t = (p.type || '').toUpperCase();
    if (catKey === 'PATRIMOINE_CULTUREL') {
      return c.includes('CULTUREL') || t.includes('CULTUREL') || c.includes('HISTORIQUE');
    }
    if (catKey === 'PATRIMOINE_NATUREL') {
      return c.includes('NATUREL') || t.includes('NATUREL') || t.includes('PARC');
    }
    if (catKey === 'PATRIMOINE_MEMORIEL') {
      return c.includes('MEMORIEL') || c.includes('ESCLAVE') || c.includes('HISTORIQUE');
    }
    if (catKey === 'PATRIMOINE_COLONIAL') {
      return c.includes('COLONIAL') || t.includes('MONUMENT') || t.includes('URBAIN');
    }
    if (catKey === 'PATRIMOINE_RELIGIEUX') {
      return c.includes('RELIGIEUX') || c.includes('SPIRITUEL') || t.includes('MOSQUEE') || t.includes('EGLISE');
    }
    return c === catKey.toUpperCase() || t === catKey.toUpperCase();
  }

  private getCategoryMeta(categorie?: string, type?: string): { color: string, icon: string, label: string } {
    const c = (categorie || '').toUpperCase();
    const t = (type || '').toUpperCase();

    if (c.includes('NATUREL') || t.includes('NATUREL')) {
      return { color: '#059669', icon: 'fa-leaf', label: 'Naturel' };
    }
    if (c.includes('MEMORIEL') || c.includes('ESCLAVE')) {
      return { color: '#dc2626', icon: 'fa-dove', label: 'Mémoriel' };
    }
    if (c.includes('COLONIAL') || t.includes('MONUMENT')) {
      return { color: '#d97706', icon: 'fa-archway', label: 'Monument' };
    }
    if (c.includes('RELIGIEUX') || c.includes('SPIRITUEL')) {
      return { color: '#7c3aed', icon: 'fa-mosque', label: 'Spirituel' };
    }
    return { color: '#0b5d5e', icon: 'fa-landmark', label: 'Culturel' };
  }

  private rafraichirMarqueurs(): void {
    if (!this.map) return;

    // Retirer les anciens marqueurs
    this.markersMap.forEach(marker => marker.remove());
    this.markersMap.clear();

    const bounds: L.LatLngTuple[] = [];

    this.filteredPatrimoines.forEach(p => {
      if (p.latitude && p.longitude && p.id) {
        const lat = p.latitude;
        const lng = p.longitude;
        bounds.push([lat, lng]);

        const meta = this.getCategoryMeta(p.categorie, p.type);
        const isSelected = this.selectedPatrimoine?.id === p.id;

        const customIcon = L.divIcon({
          className: 'custom-map-pin',
          html: `
            <div class="pin-marker-container ${isSelected ? 'is-selected' : ''}" style="--pin-color: ${meta.color}">
              <div class="pin-marker-body">
                <i class="fa-solid ${meta.icon}"></i>
              </div>
              <div class="pin-pulse"></div>
            </div>
          `,
          iconSize: [38, 44],
          iconAnchor: [19, 44],
          popupAnchor: [0, -46]
        });

        const marker = L.marker([lat, lng], { icon: customIcon }).addTo(this.map!);

        // Popup riche & moderne
        const ratingBadge = p.note ? `<span class="geoloc-popup-rating"><i class="fa-solid fa-star"></i> ${p.note}</span>` : '';
        const unescoBadge = p.inscritUnesco ? `<span class="geoloc-popup-unesco"><i class="fa-solid fa-certificate"></i> UNESCO</span>` : '';

        const popupContent = `
          <div class="geoloc-popup-card">
            <div class="geoloc-popup-img-wrapper">
              <img src="${p.imageUrl || 'assets/images/patrimoine_1.jpg'}" alt="${p.nom}" class="geoloc-popup-img" />
              <div class="geoloc-popup-overlay"></div>
              <span class="geoloc-popup-badge" style="background-color: ${meta.color}">${meta.label}</span>
              ${ratingBadge}
              ${unescoBadge}
            </div>
            <div class="geoloc-popup-body">
              <div class="geoloc-popup-loc">
                <i class="fa-solid fa-location-dot"></i>
                <span>${p.region || 'Sénégal'}${p.commune ? ' • ' + p.commune : ''}</span>
              </div>
              <h4 class="geoloc-popup-title">${p.nom}</h4>
              <p class="geoloc-popup-desc">${(p.description || '').slice(0, 85)}${(p.description || '').length > 85 ? '...' : ''}</p>
              <div class="geoloc-popup-footer">
                <a href="/patrimoines/${p.id}" class="geoloc-popup-btn">
                  <span>Découvrir le site</span>
                  <i class="fa-solid fa-arrow-right"></i>
                </a>
              </div>
            </div>
          </div>
        `;

        marker.bindPopup(popupContent, {
          maxWidth: 290,
          className: 'geoloc-custom-popup',
          closeButton: true
        });

        marker.on('click', () => {
          this.selectionnerPatrimoine(p, false);
        });

        this.markersMap.set(p.id, marker);
      }
    });

    // Ajustement de la caméra
    if (bounds.length > 0) {
      if (bounds.length === 1) {
        this.map.setView(bounds[0], 12);
      } else {
        this.map.fitBounds(bounds, { padding: [50, 50], maxZoom: 13 });
      }
    }
  }

  selectionnerPatrimoine(p: PatrimoineUI, fly: boolean = true): void {
    this.selectedPatrimoine = p;
    this.setMobileView('map');

    if (fly && this.map && p.latitude && p.longitude) {
      this.map.flyTo([p.latitude, p.longitude], 13, { duration: 1.0 });
    }

    if (p.id) {
      const marker = this.markersMap.get(p.id);
      if (marker) {
        setTimeout(() => {
          marker.openPopup();
        }, fly ? 350 : 50);
      }

      // Défilement automatique dans la liste
      setTimeout(() => {
        const cardEl = document.getElementById(`card-patrimoine-${p.id}`);
        if (cardEl) {
          cardEl.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        }
      }, 100);
    }
  }

  fermerApercuFlottant(): void {
    this.selectedPatrimoine = null;
    if (this.map) {
      this.map.closePopup();
    }
  }

  recentrerSenegal(): void {
    if (this.map) {
      this.map.fitBounds(this.senegalBounds, { padding: [30, 30] });
    }
  }

  zoomIn(): void {
    this.map?.zoomIn();
  }

  zoomOut(): void {
    this.map?.zoomOut();
  }

  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
    setTimeout(() => {
      this.map?.invalidateSize();
    }, 250);
  }

  setCategory(catKey: string): void {
    this.selectedCategory = catKey;
    this.appliquerFiltres();
  }

  setMobileView(view: 'map' | 'list'): void {
    this.mobileView = view;
    if (view === 'map' && this.map) {
      setTimeout(() => {
        this.map?.invalidateSize();
      }, 150);
    }
  }

  reinitialiserFiltres(): void {
    this.searchQuery = '';
    this.selectedCategory = 'TOUS';
    this.selectedRegion = 'TOUTES';
    this.sortBy = 'pertinence';
    this.appliquerFiltres();
    this.recentrerSenegal();
  }

  getBadgeColor(p: PatrimoineUI): string {
    return this.getCategoryMeta(p.categorie, p.type).color;
  }

  getBadgeLabel(p: PatrimoineUI): string {
    return this.getCategoryMeta(p.categorie, p.type).label;
  }
}
