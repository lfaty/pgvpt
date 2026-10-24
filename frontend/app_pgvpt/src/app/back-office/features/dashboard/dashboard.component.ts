import { Component, OnInit, AfterViewInit, ElementRef, ViewChild, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PatrimoineFacadeService } from '../../../core/services/patrimoine-facade.service';
import { CircuitFacadeService } from '../../../core/services/circuit-facade.service';
import { ContenuApiService } from '../../../core/services/api/contenu-api.service';

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
  private markers: any[] = [];
  selectedSite: SiteGeo | null = null;
  filtreRegion: string = 'TOUTES';
  activeTab: 'patrimoines' | 'activites' | 'circuits' = 'patrimoines';
  isLoading: boolean = true;

  // ─── LES 4 PILIERS DU TABLEAU DE BORD (DYNAMIQUES) ───
  stats: any[] = [
    {
      titre: 'Patrimoines',
      total: '0',
      sousTitre: '0 publié • 0 en attente',
      icon: 'fas fa-landmark',
      couleur: '#0b5d5e',
      bgCouleur: 'bg-[#0b5d5e]/10',
      textCouleur: 'text-[#0b5d5e]',
      borderCouleur: 'border-[#0b5d5e]/20',
      lien: '/admin/patrimoines',
      evolution: 'En temps réel'
    },
    {
      titre: 'Activités Touristiques',
      total: '0',
      sousTitre: '0 active • 0 inactive',
      icon: 'fas fa-hiking',
      couleur: '#059669',
      bgCouleur: 'bg-emerald-50',
      textCouleur: 'text-emerald-700',
      borderCouleur: 'border-emerald-200',
      lien: '/admin/activites',
      evolution: 'Catalogue actif'
    },
    {
      titre: 'Circuits Découverte',
      total: '0',
      sousTitre: '0 publié • 0 brouillon',
      icon: 'fas fa-route',
      couleur: '#d97706',
      bgCouleur: 'bg-amber-50',
      textCouleur: 'text-amber-700',
      borderCouleur: 'border-amber-200',
      lien: '/admin/circuits',
      evolution: 'Itinéraires'
    },
    {
      titre: 'Récits & Contenus',
      total: '0',
      sousTitre: '0 publié • 0 brouillon',
      icon: 'fas fa-file-alt',
      couleur: '#2563eb',
      bgCouleur: 'bg-blue-50',
      textCouleur: 'text-blue-700',
      borderCouleur: 'border-blue-200',
      lien: '/admin/contenus',
      evolution: 'Éditorial'
    }
  ];

  // ─── DONNÉES RÉELLES CHARGÉES DEPUIS LA BASE ───
  repartitionActivites: any[] = [];
  topActivites: any[] = [];
  sitesGeolocalises: SiteGeo[] = [];
  recentsPatrimoines: any[] = [];
  recentsActivites: any[] = [];
  recentsCircuits: any[] = [];
  regionsDisponibles: string[] = [];

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private patrimoineFacade: PatrimoineFacadeService,
    private circuitFacade: CircuitFacadeService,
    private contenuApi: ContenuApiService
  ) {}

  ngOnInit(): void {
    this.chargerDonneesReelles();
  }

  chargerDonneesReelles(): void {
    this.isLoading = true;

    forkJoin({
      patrimoines: this.patrimoineFacade.getPatrimoines().pipe(catchError(() => of([]))),
      circuits: this.circuitFacade.getCircuits().pipe(catchError(() => of([]))),
      activites: this.circuitFacade.getActivites().pipe(catchError(() => of([]))),
      contenus: this.contenuApi.getAll().pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ patrimoines, circuits, activites, contenus }) => {
        const rawPatrimoines = Array.isArray(patrimoines) ? patrimoines : [];
        const rawCircuits = Array.isArray(circuits) ? circuits : [];
        const rawActivites = Array.isArray(activites) ? activites : [];
        const rawContenus = Array.isArray(contenus) ? contenus : [];

        // 1. Calcul des statistiques Patrimoines
        const totalPat = rawPatrimoines.length;
        const pubPat = rawPatrimoines.filter(p => p.statut === 'PUBLIE').length;
        const attPat = totalPat - pubPat;

        // 2. Calcul des statistiques Activités
        const totalAct = rawActivites.length;
        const actAct = rawActivites.filter(a => a.actif !== false).length;
        const inactAct = totalAct - actAct;

        // 3. Calcul des statistiques Circuits
        const totalCirc = rawCircuits.length;
        const pubCirc = rawCircuits.filter(c => c.statut === 'PUBLIE').length;
        const brouillCirc = totalCirc - pubCirc;

        // 4. Calcul des statistiques Contenus
        const totalCont = rawContenus.length;
        const pubCont = rawContenus.filter(c => c.statut === 'PUBLIE').length;
        const brouillCont = totalCont - pubCont;

        this.stats = [
          {
            titre: 'Patrimoines',
            total: totalPat.toString(),
            sousTitre: `${pubPat} publié${pubPat > 1 ? 's' : ''} • ${attPat} en attente`,
            icon: 'fas fa-landmark',
            couleur: '#0b5d5e',
            bgCouleur: 'bg-[#0b5d5e]/10',
            textCouleur: 'text-[#0b5d5e]',
            borderCouleur: 'border-[#0b5d5e]/20',
            lien: '/admin/patrimoines',
            evolution: `${pubPat} en ligne`
          },
          {
            titre: 'Activités Touristiques',
            total: totalAct.toString(),
            sousTitre: `${actAct} active${actAct > 1 ? 's' : ''} • ${inactAct} inactive${inactAct > 1 ? 's' : ''}`,
            icon: 'fas fa-hiking',
            couleur: '#059669',
            bgCouleur: 'bg-emerald-50',
            textCouleur: 'text-emerald-700',
            borderCouleur: 'border-emerald-200',
            lien: '/admin/activites',
            evolution: `${actAct} active${actAct > 1 ? 's' : ''}`
          },
          {
            titre: 'Circuits Découverte',
            total: totalCirc.toString(),
            sousTitre: `${pubCirc} actif${pubCirc > 1 ? 's' : ''} • ${brouillCirc} en prép.`,
            icon: 'fas fa-route',
            couleur: '#d97706',
            bgCouleur: 'bg-amber-50',
            textCouleur: 'text-amber-700',
            borderCouleur: 'border-amber-200',
            lien: '/admin/circuits',
            evolution: `${pubCirc} en ligne`
          },
          {
            titre: 'Récits & Contenus',
            total: totalCont.toString(),
            sousTitre: `${pubCont} publié${pubCont > 1 ? 's' : ''} • ${brouillCont} brouillon${brouillCont > 1 ? 's' : ''}`,
            icon: 'fas fa-file-alt',
            couleur: '#2563eb',
            bgCouleur: 'bg-blue-50',
            textCouleur: 'text-blue-700',
            borderCouleur: 'border-blue-200',
            lien: '/admin/contenus',
            evolution: `${pubCont} publié${pubCont > 1 ? 's' : ''}`
          }
        ];

        // 5. Répartition dynamique des activités ou catégories de circuits
        if (totalAct > 0) {
          const actColors = ['bg-[#0b5d5e]', 'bg-emerald-600', 'bg-cyan-600', 'bg-amber-600', 'bg-purple-600'];
          const actIcons = ['fas fa-hiking', 'fas fa-leaf', 'fas fa-water', 'fas fa-utensils', 'fas fa-monument'];
          this.repartitionActivites = rawActivites.slice(0, 4).map((a, idx) => ({
            type: a.libelle || a.code || 'Activité',
            total: 1,
            pourcentage: Math.round((1 / totalAct) * 100),
            icon: actIcons[idx % actIcons.length],
            color: actColors[idx % actColors.length]
          }));
        } else {
          this.repartitionActivites = [];
        }

        // 6. Activités phares issues de la base
        this.topActivites = rawActivites.slice(0, 4).map((a, idx) => ({
          titre: a.libelle || 'Activité touristique',
          site: 'Sénégal',
          region: 'Toutes',
          type: a.code || 'Expérience',
          duree: 'Non précisée',
          prix: a.prixInitial ? new Intl.NumberFormat('fr-FR').format(a.prixInitial) + ' FCFA' : 'Sur demande',
          icon: 'fas fa-compass',
          bgBadge: idx % 2 === 0 ? 'bg-[#0b5d5e]/10 text-[#0b5d5e]' : 'bg-emerald-50 text-emerald-700'
        }));

        // 7. Sites géolocalisés réels (uniquement les patrimoines avec coordonnées en base)
        const geolocalises = rawPatrimoines.filter(p => p.latitude && p.longitude && !isNaN(p.latitude) && !isNaN(p.longitude));
        this.sitesGeolocalises = geolocalises.map(p => ({
          name: p.nom || 'Patrimoine',
          type: this.patrimoineFacade.formatCategorieLabel(p.categorie),
          region: p.region || 'Sénégal',
          lat: p.latitude!,
          lng: p.longitude!,
          activitesCount: 0,
          activitesExemples: [p.commune || p.region || 'Site remarquable']
        }));

        // Régions disponibles issues de la base
        const regionsSet = new Set<string>();
        rawPatrimoines.forEach(p => {
          if (p.region) regionsSet.add(p.region.trim());
        });
        this.regionsDisponibles = Array.from(regionsSet).sort();

        // 8. Récents patrimoines
        this.recentsPatrimoines = rawPatrimoines.slice(0, 5).map(p => ({
          nom: p.nom || 'Patrimoine sans nom',
          region: p.region || 'Non renseignée',
          categorie: this.patrimoineFacade.formatCategorieLabel(p.categorie),
          activitesCount: 0,
          statut: p.statut === 'PUBLIE' ? 'Publié' : (p.statut || 'Brouillon'),
          statutBadge: p.statut === 'PUBLIE' 
            ? 'bg-emerald-50 text-emerald-700 border-emerald-200' 
            : 'bg-gray-100 text-gray-700 border-gray-200',
          date: (p as any).dateCreation ? new Date((p as any).dateCreation).toLocaleDateString('fr-FR') : '-'
        }));

        // 9. Récentes activités
        this.recentsActivites = rawActivites.slice(0, 5).map(a => ({
          nom: a.libelle || a.code || 'Activité sans nom',
          site: 'Sénégal',
          region: 'National',
          type: a.code || 'Général',
          duree: 'Flexible',
          prix: a.prixInitial ? new Intl.NumberFormat('fr-FR').format(a.prixInitial) + ' FCFA' : 'Gratuit',
          statut: a.actif !== false ? 'Actif' : 'Inactif',
          statutBadge: a.actif !== false 
            ? 'bg-emerald-50 text-emerald-700 border-emerald-200' 
            : 'bg-gray-100 text-gray-700 border-gray-200'
        }));

        // 10. Récents circuits
        this.recentsCircuits = rawCircuits.slice(0, 5).map(c => ({
          nom: c.nom || 'Circuit sans nom',
          etapes: c.nombreEtapes ? `${c.nombreEtapes} étapes` : 'Itinéraire',
          duree: this.circuitFacade.formatDuree(c.dureeMinutes),
          activitesCount: c.nombreEtapes || 0,
          statut: c.statut === 'PUBLIE' ? 'Publié' : (c.statut || 'Brouillon'),
          statutBadge: c.statut === 'PUBLIE'
            ? 'bg-emerald-50 text-emerald-700 border-emerald-200'
            : 'bg-amber-50 text-amber-700 border-amber-200'
        }));

        this.isLoading = false;

        // Mise à jour de la carte Leaflet
        if (this.map) {
          this.rafraichirMarqueursCarte();
        }
      },
      error: (err) => {
        console.warn('Erreur chargement des données réelles du tableau de bord', err);
        this.isLoading = false;
      }
    });
  }

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

    if (!this.map && this.mapContainer) {
      this.map = L.map(this.mapContainer.nativeElement, {
        zoomControl: true,
        scrollWheelZoom: false
      }).setView([14.4974, -14.4524], 7);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors',
        maxZoom: 18
      }).addTo(this.map);
    }

    this.rafraichirMarqueursCarte(L);

    setTimeout(() => {
      if (this.map) {
        this.map.invalidateSize();
      }
    }, 400);
  }

  private async rafraichirMarqueursCarte(leafletInstance?: any): Promise<void> {
    if (!this.map) return;
    const L = leafletInstance || await import('leaflet');

    // Nettoyer les anciens marqueurs
    this.markers.forEach(m => m.remove());
    this.markers = [];

    this.sitesGeolocalises.forEach(site => {
      const marker = L.marker([site.lat, site.lng]).addTo(this.map);

      const popupContent = `
        <div style="font-family: 'Inter', sans-serif; min-width: 190px;">
          <div style="font-weight: 700; font-size: 13px; color: #0b5d5e; margin-bottom: 2px;">${site.name}</div>
          <div style="font-size: 11px; color: #64748b; margin-bottom: 6px;"><i class="fa-solid fa-location-dot" style="color: #ef4444; margin-right: 4px;"></i>${site.region} • ${site.type}</div>
          <div style="background: #f0fdf4; border: 1px solid #bbf7d0; border-radius: 6px; padding: 4px 8px; font-size: 11px; color: #15803d; font-weight: 600;">
            <i class="fa-solid fa-landmark" style="margin-right: 4px;"></i>${site.activitesExemples.join(', ')}
          </div>
        </div>
      `;

      marker.bindPopup(popupContent);
      marker.on('click', () => {
        this.selectedSite = site;
      });
      this.markers.push(marker);
    });
  }
}
