import { Component, Input, Output, EventEmitter, OnInit, AfterViewInit, OnDestroy, OnChanges, SimpleChanges, inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { EtapeCircuitDetail } from '../../../../../../../core/services/circuit-facade.service';
import * as L from 'leaflet';

@Component({
  selector: 'app-circuit-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './circuit-map.component.html',
  styleUrls: ['./circuit-map.component.css']
})
export class CircuitMapComponent implements OnInit, AfterViewInit, OnDestroy, OnChanges {
  @Input() etapes: EtapeCircuitDetail[] = [];
  @Input() activeStepIndex: number = 0;
  @Output() activeStepIndexChange = new EventEmitter<number>();

  private platformId = inject(PLATFORM_ID);

  private map?: L.Map;
  private polyline?: L.Polyline;
  private markers: L.Marker[] = [];

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      setTimeout(() => this.initMap(), 300);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['etapes'] && this.map) {
      this.tracerItineraire();
    }
    if (changes['activeStepIndex'] && !changes['activeStepIndex'].firstChange) {
      this.highlightStep(this.activeStepIndex);
    }
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
      this.map = undefined;
    }
  }

  private initMap(): void {
    const el = document.getElementById('circuit-map');
    if (!el || !isPlatformBrowser(this.platformId)) return;

    if (this.map) {
      this.map.remove();
      this.map = undefined;
    }

    const firstCoord = this.etapes?.find(e => e.latitude != null && e.longitude != null);
    const centerLat = firstCoord?.latitude ?? 14.4974;
    const centerLng = firstCoord?.longitude ?? -14.4524;

    this.map = L.map('circuit-map', {
      center: [centerLat, centerLng],
      zoom: 10,
      zoomControl: true,
      scrollWheelZoom: false
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
      maxZoom: 19
    }).addTo(this.map);

    this.tracerItineraire();
  }

  private formatDureeMinutes(minutes: number): string {
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    if (h > 0 && m > 0) return `${h}h ${m}min`;
    if (h > 0) return `${h}h`;
    return `${m}min`;
  }

  tracerItineraire(): void {
    if (!this.map || !this.etapes || this.etapes.length === 0) return;

    this.markers.forEach(m => m.remove());
    this.markers = [];
    if (this.polyline) {
      this.polyline.remove();
      this.polyline = undefined;
    }

    const latlngs: L.LatLngTuple[] = [];

    this.etapes.forEach((etape, index) => {
      if (etape.latitude != null && etape.longitude != null) {
        const coord: L.LatLngTuple = [etape.latitude, etape.longitude];
        latlngs.push(coord);

        const isFirst = index === 0;
        const isLast = index === this.etapes.length - 1;
        const pinClass = isFirst ? 'pin-start' : (isLast ? 'pin-end' : 'pin-regular');

        const customIcon = L.divIcon({
          className: 'circuit-step-marker',
          html: `
            <div class="circuit-pin ${pinClass}">
              <span class="pin-number">${etape.ordre}</span>
            </div>
          `,
          iconSize: [36, 36],
          iconAnchor: [18, 36],
          popupAnchor: [0, -36]
        });

        const popupContent = `
          <div class="circuit-popup">
            <div class="popup-header">
              <span class="popup-step">Étape ${etape.ordre}</span>
              <strong class="popup-title">${etape.patrimoineDetail?.nom || 'Étape ' + etape.ordre}</strong>
            </div>
            ${etape.patrimoineDetail?.imageUrl ? `
              <div class="popup-img-wrapper">
                <img src="${etape.patrimoineDetail.imageUrl}" alt="${etape.patrimoineDetail.nom}" class="popup-img" />
              </div>
            ` : ''}
            <div class="popup-info">
              ${etape.dureeMinutes ? `<span><i class="fa-solid fa-clock"></i> ${this.formatDureeMinutes(etape.dureeMinutes)}</span>` : ''}
              ${etape.distanceDepuisPrecedenteKm ? `<span><i class="fa-solid fa-route"></i> +${etape.distanceDepuisPrecedenteKm} km</span>` : ''}
            </div>
          </div>
        `;

        const marker = L.marker(coord, { icon: customIcon })
          .addTo(this.map!)
          .bindPopup(popupContent);

        marker.on('click', () => {
          this.activeStepIndex = index;
          this.activeStepIndexChange.emit(index);
        });

        this.markers.push(marker);
      }
    });

    if (latlngs.length > 1) {
      this.polyline = L.polyline(latlngs, {
        color: '#1E3A8A',
        weight: 5,
        opacity: 0.85,
        dashArray: '8, 8',
        lineCap: 'round',
        lineJoin: 'round'
      }).addTo(this.map);

      this.map.fitBounds(this.polyline.getBounds(), { padding: [50, 50] });
    } else if (latlngs.length === 1) {
      this.map.setView(latlngs[0], 12);
    }
  }

  centrerEtape(index: number): void {
    this.activeStepIndex = index;
    this.activeStepIndexChange.emit(index);
    this.highlightStep(index);
  }

  private highlightStep(index: number): void {
    const etape = this.etapes?.[index];
    if (this.map && etape?.latitude != null && etape?.longitude != null) {
      this.map.flyTo([etape.latitude, etape.longitude], 14, { duration: 1 });
      this.markers[index]?.openPopup();
    }
  }

  recentrerTout(): void {
    if (this.map && this.polyline) {
      this.map.fitBounds(this.polyline.getBounds(), { padding: [50, 50] });
    }
  }
}
