import { Component, OnInit, AfterViewInit, ElementRef, ViewChild, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';

export interface SiteGeo {
  name: string;
  type: string;
  region: string;
  lat: number;
  lng: number;
  activitesCount: number;
  activitesExemples: string[];
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild('mapContainer') mapContainer!: ElementRef;

  private map: any;
  selectedSite: SiteGeo | null = null;
  filtreRegion: string = 'TOUTES';
  activeTab: 'patrimoines' | 'activites' | 'circuits' = 'patrimoines';

  // ─── LES 4 PILIERS DU TABLEAU DE BORD PGVPT ───
  stats = [
    {
      titre: 'Patrimoines',
      total: '48',
      sousTitre: '38 publiés • 10 en attente',
      icon: 'fas fa-landmark',
      couleur: '#0b5d5e',
      bgCouleur: 'bg-[#0b5d5e]/10',
      textCouleur: 'text-[#0b5d5e]',
      borderCouleur: 'border-[#0b5d5e]/20',
      lien: '/admin/patrimoines',
      evolution: '+3 ce mois'
    },
    {
      titre: 'Activités Touristiques',
      total: '65',
      sousTitre: '58 disponibles • 7 saisonnières',
      icon: 'fas fa-hiking',
      couleur: '#059669',
      bgCouleur: 'bg-emerald-50',
      textCouleur: 'text-emerald-700',
      borderCouleur: 'border-emerald-200',
      lien: '/admin/activites',
      evolution: '+8 cette saison'
    },
    {
      titre: 'Circuits Découverte',
      total: '14',
      sousTitre: '12 actifs • 2 en préparation',
      icon: 'fas fa-route',
      couleur: '#d97706',
      bgCouleur: 'bg-amber-50',
      textCouleur: 'text-amber-700',
      borderCouleur: 'border-amber-200',
      lien: '/admin/circuits',
      evolution: '5 grandes régions'
    },
    {
      titre: 'Réservations du Mois',
      total: '182',
      sousTitre: '148 confirmées • 34 en cours',
      icon: 'fas fa-calendar-check',
      couleur: '#2563eb',
      bgCouleur: 'bg-blue-50',
      textCouleur: 'text-blue-700',
      borderCouleur: 'border-blue-200',
      lien: '/admin/reservations',
      evolution: '+18% vs mois préc.'
    }
  ];

  // ─── RÉPARTITION DES ACTIVITÉS ───
  repartitionActivites = [
    { type: 'Culture & Histoire', total: 26, pourcentage: 40, icon: 'fas fa-monument', color: 'bg-[#0b5d5e]' },
    { type: 'Écotourisme & Nature', total: 22, pourcentage: 34, icon: 'fas fa-leaf', color: 'bg-emerald-600' },
    { type: 'Aventure & Nautique', total: 11, pourcentage: 17, icon: 'fas fa-water', color: 'bg-cyan-600' },
    { type: 'Artisanat & Gastronomie', total: 6, pourcentage: 9, icon: 'fas fa-utensils', color: 'bg-amber-600' }
  ];

  // ─── TOP ACTIVITÉS LES PLUS DEMANDÉES ───
  topActivites = [
    {
      titre: 'Visite historique de la Maison des Esclaves',
      site: 'Île de Gorée',
      region: 'Dakar',
      type: 'Culture & Histoire',
      duree: '2h30',
      prix: '15 000 FCFA',
      reservations: 64,
      icon: 'fas fa-landmark',
      bgBadge: 'bg-[#0b5d5e]/10 text-[#0b5d5e]'
    },
    {
      titre: 'Safari éco-guidé & observation faune',
      site: 'Niokolo-Koba',
      region: 'Tambacounda',
      type: 'Écotourisme & Nature',
      duree: '1 journée',
      prix: '45 000 FCFA',
      reservations: 42,
      icon: 'fas fa-paw',
      bgBadge: 'bg-emerald-50 text-emerald-700'
    },
    {
      titre: 'Excursion en pirogue traditionnelle dans les bolongs',
      site: 'Delta du Saloum',
      region: 'Fatick',
      type: 'Aventure & Nautique',
      duree: '4h00',
      prix: '22 000 FCFA',
      reservations: 38,
      icon: 'fas fa-water',
      bgBadge: 'bg-cyan-50 text-cyan-700'
    },
    {
      titre: 'Circuit architectural en calèche coloniale',
      site: 'Île de Saint-Louis',
      region: 'Saint-Louis',
      type: 'Culture & Histoire',
      duree: '1h30',
      prix: '12 000 FCFA',
      reservations: 30,
      icon: 'fas fa-horse',
      bgBadge: 'bg-amber-50 text-amber-700'
    }
  ];

  // ─── SITES GÉOLOCALISÉS (SÉNÉGAL) AVEC ACTIVITÉS ───
  sitesGeolocalises: SiteGeo[] = [
    {
      name: 'Île de Gorée',
      type: 'Monument Historique',
      region: 'Dakar',
      lat: 14.6666,
      lng: -17.3986,
      activitesCount: 4,
      activitesExemples: ['Maison des Esclaves', 'Tour de l\'île en pirogue', 'Musée Historique']
    },
    {
      name: 'Parc National du Niokolo-Koba',
      type: 'Site Naturel UNESCO',
      region: 'Tambacounda',
      lat: 13.0699,
      lng: -12.7214,
      activitesCount: 3,
      activitesExemples: ['Safari Faune Sauvage', 'Bivouac Écologique', 'Observation Lions & Éléphants']
    },
    {
      name: 'Delta du Sine Saloum',
      type: 'Réserve de Biosphère',
      region: 'Fatick',
      lat: 14.1500,
      lng: -16.5000,
      activitesCount: 4,
      activitesExemples: ['Pirogue dans les Mangroves', 'Dégustation d\'huîtres', 'Écovillage Mar Lodj']
    },
    {
      name: 'Parc National des Oiseaux du Djoudj',
      type: 'Site Naturel',
      region: 'Saint-Louis',
      lat: 16.4167,
      lng: -16.2500,
      activitesCount: 2,
      activitesExemples: ['Observation des Pélicans', 'Balade fluviale au crépuscule']
    },
    {
      name: 'Île de Saint-Louis',
      type: 'Patrimoine Mondial UNESCO',
      region: 'Saint-Louis',
      lat: 16.0333,
      lng: -16.5000,
      activitesCount: 3,
      activitesExemples: ['Visite en calèche', 'Musée du CRDS', 'Balade sur le pont Faidherbe']
    },
    {
      name: 'Mégalithes de Sine Ngayène',
      type: 'Site Archéologique',
      region: 'Kaolack',
      lat: 13.6833,
      lng: -15.5333,
      activitesCount: 1,
      activitesExemples: ['Visite guidée archéologique & contes locaux']
    }
  ];

  // ─── DERNIERS PATRIMOINES ───
  recentsPatrimoines = [
    {
      nom: 'Île de Gorée',
      region: 'Dakar',
      categorie: 'Monument Historique',
      activitesCount: 4,
      statut: 'Publié',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200',
      date: '04 Oct. 2026'
    },
    {
      nom: 'Delta du Saloum',
      region: 'Fatick',
      categorie: 'Site Naturel',
      activitesCount: 4,
      statut: 'Publié',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200',
      date: '02 Oct. 2026'
    },
    {
      nom: 'Fort d\'Estrées',
      region: 'Dakar',
      categorie: 'Musée',
      activitesCount: 2,
      statut: 'En validation',
      statutBadge: 'bg-amber-50 text-amber-700 border-amber-200',
      date: '01 Oct. 2026'
    },
    {
      nom: 'Chutes de Dindéfélo',
      region: 'Kédougou',
      categorie: 'Site Naturel',
      activitesCount: 3,
      statut: 'Brouillon',
      statutBadge: 'bg-gray-100 text-gray-700 border-gray-200',
      date: '28 Sept. 2026'
    }
  ];

  // ─── DERNIÈRES ACTIVITÉS CRÉÉES ───
  recentsActivites = [
    {
      nom: 'Randonnée aux Chutes de Dindéfélo',
      site: 'Chutes de Dindéfélo',
      region: 'Kédougou',
      type: 'Écotourisme',
      duree: '3h00',
      prix: '15 000 FCFA',
      statut: 'Actif',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200'
    },
    {
      nom: 'Pêche traditionnelle aux Îles du Saloum',
      site: 'Delta du Saloum',
      region: 'Fatick',
      type: 'Aventure & Pêche',
      duree: '5h00',
      prix: '30 000 FCFA',
      statut: 'Actif',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200'
    },
    {
      nom: 'Atelier de teinture et batik artisanal',
      site: 'Île de Saint-Louis',
      region: 'Saint-Louis',
      type: 'Artisanat & Culture',
      duree: '2h00',
      prix: '10 000 FCFA',
      statut: 'En révision',
      statutBadge: 'bg-amber-50 text-amber-700 border-amber-200'
    },
    {
      nom: 'Observation nocturne des tortues marines',
      site: 'Îles de la Madeleine',
      region: 'Dakar',
      type: 'Nature & Faune',
      duree: '3h00',
      prix: '25 000 FCFA',
      statut: 'Saisonnier',
      statutBadge: 'bg-cyan-50 text-cyan-700 border-cyan-200'
    }
  ];

  // ─── DERNIERS CIRCUITS ───
  recentsCircuits = [
    {
      nom: 'La Route Historique des Comptoirs',
      etapes: 'Dakar → Saint-Louis → Podor',
      duree: '4 jours',
      activitesCount: 8,
      statut: 'Actif',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200'
    },
    {
      nom: 'Immersion Terroir du Sine Saloum',
      etapes: 'Joal → Ndangane → Mar Lodj → Toubacouta',
      duree: '3 jours',
      activitesCount: 6,
      statut: 'Actif',
      statutBadge: 'bg-emerald-50 text-emerald-700 border-emerald-200'
    },
    {
      nom: 'Le Grand Safari Oriental',
      etapes: 'Tambacounda → Niokolo-Koba → Dindéfélo',
      duree: '5 jours',
      activitesCount: 7,
      statut: 'En préparation',
      statutBadge: 'bg-amber-50 text-amber-700 border-amber-200'
    }
  ];

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.initMap();
    }
  }

  setTab(tab: 'patrimoines' | 'activites' | 'circuits'): void {
    this.activeTab = tab;
  }

  filtrerParRegion(region: string): void {
    this.filtreRegion = region;
    if (this.map) {
      if (region === 'TOUTES') {
        this.map.setView([14.4974, -14.4524], 7);
      } else {
        const site = this.sitesGeolocalises.find(s => s.region.toLowerCase() === region.toLowerCase());
        if (site) {
          this.map.setView([site.lat, site.lng], 10);
          this.selectedSite = site;
        }
      }
    }
  }

  private async initMap(): Promise<void> {
    const L = await import('leaflet');

    const iconRetinaUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png';
    const iconUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png';
    const shadowUrl = 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png';

    const customIcon = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41]
    });
    L.Marker.prototype.options.icon = customIcon;

    this.map = L.map(this.mapContainer.nativeElement, {
      zoomControl: true,
      scrollWheelZoom: false
    }).setView([14.4974, -14.4524], 7);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
      maxZoom: 18
    }).addTo(this.map);

    this.sitesGeolocalises.forEach(site => {
      const marker = L.marker([site.lat, site.lng]).addTo(this.map);

      const popupContent = `
        <div style="font-family: 'Inter', sans-serif; min-width: 190px;">
          <div style="font-weight: 700; font-size: 13px; color: #0b5d5e; margin-bottom: 2px;">${site.name}</div>
          <div style="font-size: 11px; color: #64748b; margin-bottom: 6px;"><i class="fa-solid fa-location-dot" style="color: #ef4444; margin-right: 4px;"></i>${site.region} • ${site.type}</div>
          <div style="background: #f0fdf4; border: 1px solid #bbf7d0; border-radius: 6px; padding: 4px 8px; font-size: 11px; color: #15803d; font-weight: 600;">
            <i class="fa-solid fa-compass" style="margin-right: 4px;"></i>${site.activitesCount} activité(s) disponible(s)
          </div>
        </div>
      `;

      marker.bindPopup(popupContent);
      marker.on('click', () => {
        this.selectedSite = site;
      });
    });

    setTimeout(() => {
      if (this.map) {
        this.map.invalidateSize();
      }
    }, 400);
  }
}
