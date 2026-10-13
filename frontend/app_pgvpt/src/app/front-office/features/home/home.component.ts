import { Component, OnInit, OnDestroy, AfterViewInit, inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MockService } from '../../../core/services/mock.service';
import { PatrimoineFacadeService } from '../../../core/services/patrimoine-facade.service';
import { ContenuApiService } from '../../../core/services/api/contenu-api.service';
import { MediaApiService } from '../../../core/services/api/media-api.service';
import { PatrimoineUI } from '../../../core/models/patrimoine.ui';
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
  private facadeService = inject(PatrimoineFacadeService);
  private contenuApi = inject(ContenuApiService);
  private mediaApi = inject(MediaApiService);
  private platformId = inject(PLATFORM_ID);
  private router = inject(Router);
  
  private map: L.Map | null = null;
  private markers: L.Marker[] = [];

  patrimoines: PatrimoineUI[] = [];
  categoriesGroups: { categoryId: string; label: string; items: PatrimoineUI[] }[] = [];
  recentContenus: any[] = [];
  allMedias: any[] = [];
  
  // Statistiques dynamiques du Hero
  unescoCount: number = 0;
  regionsCount: number = 0;
  totalMedias: number = 0;
  totalContenus: number = 0;

  searchQuery: string = '';

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
    'PATRIMOINE_NATUREL': true,
    'PATRIMOINE_CULTUREL': true,
    'PATRIMOINE_MEMORIEL': true,
    'PATRIMOINE_COLONIAL': true,
    'PATRIMOINE_RELIGIEUX': true
  };
  filterRegion = 'Toutes';
  regions: string[] = [];

  // Marker colors by category
  private markerColors: { [key: string]: string } = {
    'PATRIMOINE_NATUREL': '#059669', // vert
    'PATRIMOINE_CULTUREL': '#D97706', // ambre
    'PATRIMOINE_MEMORIEL': '#DC2626', // rouge
    'PATRIMOINE_COLONIAL': '#2563EB', // bleu
    'PATRIMOINE_RELIGIEUX': '#7C3AED'  // violet
  };

  categoriesList = [
    { key: 'PATRIMOINE_CULTUREL', label: 'Culturel', color: '#D97706', icon: 'fa-solid fa-landmark' },
    { key: 'PATRIMOINE_NATUREL', label: 'Naturel', color: '#059669', icon: 'fa-solid fa-leaf' },
    { key: 'PATRIMOINE_MEMORIEL', label: 'Mémoriel', color: '#DC2626', icon: 'fa-solid fa-dove' },
    { key: 'PATRIMOINE_COLONIAL', label: 'Colonial & Urbain', color: '#2563EB', icon: 'fa-solid fa-monument' },
    { key: 'PATRIMOINE_RELIGIEUX', label: 'Spirituel & Religieux', color: '#7C3AED', icon: 'fa-solid fa-mosque' }
  ];

  ngOnInit(): void {
    this.facadeService.getPatrimoines().subscribe((data: PatrimoineUI[]) => {
      this.patrimoines = data;
      
      // Statistiques calculées dynamiquement
      this.unescoCount = data.filter(p => !!p.inscritUnesco).length;
      this.regions = [...new Set(data.map((p: PatrimoineUI) => p.region).filter(Boolean))] as string[];
      this.regionsCount = this.regions.length;

      // Groupement dynamique par catégories
      const groupsMap = new Map<string, PatrimoineUI[]>();
      data.forEach(p => {
        const cat = p.categorie || 'AUTRE';
        if (!groupsMap.has(cat)) groupsMap.set(cat, []);
        groupsMap.get(cat)!.push(p);
      });

      this.categoriesGroups = Array.from(groupsMap.entries()).map(([cat, items]) => ({
        categoryId: cat,
        label: this.facadeService.formatCategorieLabel(cat),
        items
      }));

      if (this.map) {
        this.updateMarkers();
      }

      // Start auto-scroll after the *ngFor loop has rendered the carousels
      setTimeout(() => {
        this.startAutoScroll();
      }, 500);
    });

    // Chargement dynamique des médias pour le compteur et les images des récits
    this.mediaApi.getAll().subscribe({
      next: (medias: any[]) => {
        this.allMedias = medias || [];
        this.totalMedias = this.allMedias.length;
      },
      error: (err) => console.warn('Erreur chargement médias home', err)
    });

    // Chargement dynamique des contenus réels (guides, articles, récits)
    this.contenuApi.getAll().subscribe({
      next: (list: any[]) => {
        const activeList = (list || []).filter(c => c.statut !== 'ARCHIVE');
        this.totalContenus = activeList.length;
        this.recentContenus = activeList.slice(0, 3);
      },
      error: (err) => console.warn('Erreur chargement récits home', err)
    });

    this.intervalId = setInterval(() => {
      this.currentHeroIndex = (this.currentHeroIndex + 1) % this.heroImages.length;
    }, 5000);
  }

  scrollToSection(elementId: string): void {
    if (isPlatformBrowser(this.platformId)) {
      const el = document.getElementById(elementId);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    }
  }

  filterUnesco(): void {
    this.router.navigate(['/patrimoines'], { queryParams: { unesco: 'true' } });
  }

  goToCatalogue(): void {
    this.router.navigate(['/patrimoines']);
  }

  getContenuTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      'ARTICLE': 'Article Historique',
      'HISTORIQUE': 'Article Historique',
      'GUIDE': 'Guide de Visite',
      'GUIDE_VISITE': 'Guide de Visite',
      'TEMOIGNAGE': 'Témoignage Oral',
      'LEGENDE': 'Légende & Mythe',
      'RECIT': 'Récit Vivant',
      'RECETTE': 'Tradition Culinaire',
      'DESCRIPTION': 'Description du Site',
      'ACTUALITE': 'Actualité Culturelle'
    };
    return labels[type] || 'Récit du Patrimoine';
  }

  getContenuTypeIcon(type: string): string {
    const icons: Record<string, string> = {
      'ARTICLE': 'fa-solid fa-newspaper',
      'HISTORIQUE': 'fa-solid fa-landmark',
      'GUIDE': 'fa-solid fa-map-location-dot',
      'GUIDE_VISITE': 'fa-solid fa-map-location-dot',
      'TEMOIGNAGE': 'fa-solid fa-microphone-lines',
      'LEGENDE': 'fa-solid fa-wand-magic-sparkles',
      'RECIT': 'fa-solid fa-book-open',
      'RECETTE': 'fa-solid fa-utensils',
      'DESCRIPTION': 'fa-solid fa-file-lines',
      'ACTUALITE': 'fa-solid fa-bullhorn'
    };
    return icons[type] || 'fa-solid fa-scroll';
  }

  getPatrimoineNom(patrimoineId: string): string {
    if (!patrimoineId) return 'Patrimoine National';
    const found = this.patrimoines.find(p => p.id === patrimoineId);
    return found ? (found.nom || (found as any).nomHistorique || 'Patrimoine du Sénégal') : 'Patrimoine du Sénégal';
  }

  getArticleImage(contenu: any): string {
    // 1. Médias attachés directement
    if (contenu?.medias && contenu.medias.length > 0) {
      const img = contenu.medias.find((m: any) => m.typeMedia === 'IMAGE' || m.type === 'PHOTO' || m.url?.match(/\.(jpeg|jpg|png|webp|avif)/i));
      if (img?.url) return img.url;
    }
    // 2. Recherche via mediaIds dans les médias réels chargés
    if (contenu?.mediaIds && Array.isArray(contenu.mediaIds) && contenu.mediaIds.length > 0 && this.allMedias.length > 0) {
      const foundMedia = this.allMedias.find(m => contenu.mediaIds.includes(m.id));
      if (foundMedia?.url) return foundMedia.url;
    }
    // 3. Image du patrimoine lié
    if (contenu?.patrimoineId) {
      const p = this.patrimoines.find(item => item.id === contenu.patrimoineId);
      if (p?.imageUrl) return p.imageUrl;
      if (p?.photoPrincipale?.urlPhotoPrincipale) return p.photoPrincipale.urlPhotoPrincipale;
      if ((p as any)?.photoPrincipale) return (p as any).photoPrincipale;
    }
    return 'assets/images/patrimoine_1.jpg';
  }

  getReadingTime(text: string): number {
    if (!text) return 3;
    const words = text.split(/\s+/).length;
    return Math.max(2, Math.ceil(words / 200));
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    this.stopAutoScroll();
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      setTimeout(() => this.initMap(), 300);
    }
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
      const catOk = this.filterCategories[p.categorie];
      const regionOk = this.filterRegion === 'Toutes' || p.region === this.filterRegion;
      return catOk && regionOk;
    });

    filtered.forEach(p => {
      if (p.latitude && p.longitude) {
        const color = this.markerColors[p.categorie] || '#0b5d5e';
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
          marker.addTo(this.map as L.Map);
        }
        this.markers.push(marker);
      }
    });
  }

  toggleCategoryFilter(catKey: string): void {
    this.filterCategories[catKey] = !this.filterCategories[catKey];
    this.updateMarkers();
  }

  getCategoryCount(catKey: string): number {
    return this.patrimoines.filter(p => p.categorie === catKey).length;
  }

  get filteredCount(): number {
    return this.patrimoines.filter(p => {
      const catOk = this.filterCategories[p.categorie];
      const regionOk = this.filterRegion === 'Toutes' || p.region === this.filterRegion;
      return catOk && regionOk;
    }).length;
  }

  resetFilters(): void {
    Object.keys(this.filterCategories).forEach(k => this.filterCategories[k] = true);
    this.filterRegion = 'Toutes';
    this.updateMarkers();
  }

  quickSearch(keyword: string): void {
    this.searchQuery = keyword;
    this.onSearch();
  }

  onFilterChange(): void {
    this.updateMarkers();
  }

  getCategoryTheme(categoryId: string): { bgClass: string, badgeClass: string } {
    const themes: any = {
      'PATRIMOINE_NATUREL': { bgClass: 'section--sand', badgeClass: 'badge--green' },
      'PATRIMOINE_CULTUREL': { bgClass: 'section--light', badgeClass: 'badge--amber' },
      'PATRIMOINE_MUSEAL': { bgClass: 'section--light', badgeClass: 'badge--amber' },
      'PATRIMOINE_MEMORIEL': { bgClass: 'section--sand', badgeClass: 'badge--red' },
      'PATRIMOINE_COLONIAL': { bgClass: 'section--light', badgeClass: 'badge--blue' },
      'PATRIMOINE_RELIGIEUX': { bgClass: 'section--sand', badgeClass: 'badge--purple' },
    };
    return themes[categoryId] || { bgClass: 'section--light', badgeClass: 'badge--blue' };
  }

  // ── Carousel Navigation ──
  scrollCarousel(container: HTMLElement, direction: number): void {
    const card = container.querySelector('.patrimoine-mini-card') as HTMLElement;
    if (card) {
      const scrollAmount = card.offsetWidth + 24; 
      
      if (direction === 1 && Math.ceil(container.scrollLeft + container.clientWidth) >= container.scrollWidth - 10) {
        container.scrollTo({ left: 0, behavior: 'smooth' });
      } else {
        container.scrollBy({ left: scrollAmount * direction, behavior: 'smooth' });
      }
    }
  }

  // ── Auto-scroll pour tous les carrousels ──
  private autoScrollInterval: any;

  startAutoScroll(): void {
    this.stopAutoScroll();
    if (isPlatformBrowser(this.platformId)) {
      this.autoScrollInterval = setInterval(() => {
        const carousels = document.querySelectorAll('.patrimoines-grid-carousel');
        carousels.forEach(c => {
          this.scrollCarousel(c as HTMLElement, 1);
        });
      }, 4000);
    }
  }

  stopAutoScroll(): void {
    if (this.autoScrollInterval) {
      clearInterval(this.autoScrollInterval);
      this.autoScrollInterval = null;
    }
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/patrimoines'], { queryParams: { q: this.searchQuery.trim() } });
    } else {
      this.router.navigate(['/patrimoines']);
    }
  }
}
