import { Component, OnInit, OnDestroy, AfterViewInit, inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MockService } from '../../core/services/mock.service';
import { PatrimoineUI } from '../../core/models/patrimoine.ui';
import * as L from 'leaflet';

// Fix Leaflet default icon paths for Angular/Webpack
delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
});

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, OnDestroy, AfterViewInit {
  private mockService = inject(MockService);
  private platformId = inject(PLATFORM_ID);
  private map: L.Map | null = null;
  private markers: L.Marker[] = [];

  patrimoines: PatrimoineUI[] = [];
  circuits: any[] = [];
  evenements: any[] = [];
  offres: any[] = [];

  // Hero slider
  heroImages: string[] = [
    'assets/images/hero.jpg',
    'assets/images/circuit_1.jpg',
    'assets/images/circuit_3.jpg',
    'assets/images/patrimoine_2.jpg'
  ];
  currentHeroIndex = 0;
  private intervalId: any;

  // Map filters
  filterCategories: { [key: string]: boolean } = {
    'HISTORIQUE': true,
    'NATUREL': true,
    'CULTUREL': true
  };
  filterRegion = 'Toutes';
  regions: string[] = [];

  // Marker colors by type
  private markerColors: { [key: string]: string } = {
    'HISTORIQUE': '#E74C3C',
    'NATUREL': '#27AE60',
    'CULTUREL': '#F39C12'
  };

  ngOnInit(): void {
    this.mockService.getMockData<PatrimoineUI[]>('patrimoines.json').subscribe(data => {
      this.patrimoines = data;
      this.regions = [...new Set(data.map((p: PatrimoineUI) => p.localisation.region))];
      if (this.map) {
        this.updateMarkers();
      }
    });
    this.mockService.getMockData<any[]>('circuits.json').subscribe(data => {
      this.circuits = data;
    });
    this.mockService.getMockData<any[]>('evenements.json').subscribe(data => {
      this.evenements = data;
    });
    this.mockService.getMockData<any[]>('offres.json').subscribe(data => {
      this.offres = data;
    });

    this.intervalId = setInterval(() => {
      this.currentHeroIndex = (this.currentHeroIndex + 1) % this.heroImages.length;
    }, 5000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      setTimeout(() => this.initMap(), 300);
    }
  }

  private initMap(): void {
    // Les vraies coordonnées extrêmes du Sénégal pour un cadrage intact
    const senegalBounds = L.latLngBounds(
      L.latLng(12.3, -17.53), // Sud-Ouest
      L.latLng(16.69, -11.34) // Nord-Est
    );

    this.map = L.map('senegal-map', {
      zoomControl: false,       // Pas de boutons +/-
      dragging: false,          // Impossible de déplacer la carte
      touchZoom: false,         // Impossible de zoomer au doigt (mobile)
      doubleClickZoom: false,   // Impossible de zoomer au double-clic
      scrollWheelZoom: false,   // Impossible de zoomer avec la molette
      boxZoom: false,
      keyboard: false,
      zoomSnap: 0.1             // Permet un niveau de zoom très précis et non entier (ex: 7.3)
    });

    // Cadrage parfait avec le minimum de marge possible
    this.map.fitBounds(senegalBounds, { padding: [15, 15] });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap'
    }).addTo(this.map);

    setTimeout(() => {
      this.map?.invalidateSize();
      // On s'assure que le cadrage reste parfait après le chargement du DOM
      this.map?.fitBounds(senegalBounds, { padding: [15, 15] });
    }, 200);

    this.updateMarkers();
  }

  updateMarkers(): void {
    if (!this.map) return;

    // Clear existing markers
    this.markers.forEach(m => m.remove());
    this.markers = [];

    const filtered = this.patrimoines.filter(p => {
      const typeOk = this.filterCategories[p.type];
      const regionOk = this.filterRegion === 'Toutes' || p.localisation.region === this.filterRegion;
      return typeOk && regionOk;
    });

    filtered.forEach(p => {
      if (p.localisation.latitude && p.localisation.longitude) {
        const color = this.markerColors[p.type] || '#3498DB';

        const icon = L.divIcon({
          className: 'custom-marker',
          html: `<div style="
            background-color: ${color};
            width: 28px;
            height: 28px;
            border-radius: 50% 50% 50% 0;
            transform: rotate(-45deg);
            border: 3px solid white;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
          "></div>`,
          iconSize: [28, 28],
          iconAnchor: [14, 28],
          popupAnchor: [0, -30]
        });

        const marker = L.marker(
          [p.localisation.latitude, p.localisation.longitude],
          { icon }
        ).addTo(this.map!);

        const popupContent = `
          <div style="min-width: 220px; font-family: 'Inter', sans-serif;">
            <h3 style="margin: 0 0 4px 0; font-size: 16px; font-weight: 700; color: #1a1a1a;">${p.nom}</h3>
            <p style="margin: 0 0 6px 0; font-size: 12px; color: #888;">${p.categorie}</p>
            <p style="margin: 0 0 8px 0; font-size: 12px; color: #666;">${p.localisation.region}, ${p.localisation.departement}</p>
            <img src="${p.images[0]}" alt="${p.nom}" style="width: 100%; height: 100px; object-fit: cover; border-radius: 8px; margin-bottom: 8px;" />
            <a href="/patrimoines/${p.id}" style="color: #2563EB; font-size: 13px; font-weight: 600; text-decoration: none;">Voir détails →</a>
          </div>
        `;

        marker.bindPopup(popupContent, {
          maxWidth: 280,
          className: 'custom-popup'
        });

        this.markers.push(marker);
      }
    });
  }

  onFilterChange(): void {
    this.updateMarkers();
  }
}
