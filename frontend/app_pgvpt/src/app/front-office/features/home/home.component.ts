import { Component, OnInit, OnDestroy, inject, PLATFORM_ID, ViewEncapsulation } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatrimoineFacadeService } from '../../../core/services/patrimoine-facade.service';
import { ContenuApiService } from '../../../core/services/api/contenu-api.service';
import { MediaApiService } from '../../../core/services/api/media-api.service';
import { PatrimoineUI } from '../../../core/models/patrimoine.ui';
import { CircuitFacadeService, CircuitUI } from '../../../core/services/circuit-facade.service';
import { HeroAccueilComponent } from './components/hero-accueil/hero-accueil.component';
import { BarreCategoriesComponent } from './components/barre-categories/barre-categories.component';
import { CarrouselPatrimoinesComponent } from './components/carrousel-patrimoines/carrousel-patrimoines.component';
import { CarteInteractiveAccueilComponent } from './components/carte-interactive-accueil/carte-interactive-accueil.component';
import { SectionCircuitsComponent } from './components/section-circuits/section-circuits.component';
import { SectionChroniquesComponent } from './components/section-chroniques/section-chroniques.component';
import { SectionEspritTerangaComponent } from './components/section-esprit-teranga/section-esprit-teranga.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    HeroAccueilComponent,
    BarreCategoriesComponent,
    CarrouselPatrimoinesComponent,
    CarteInteractiveAccueilComponent,
    SectionCircuitsComponent,
    SectionChroniquesComponent,
    SectionEspritTerangaComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit, OnDestroy {
  private facadeService = inject(PatrimoineFacadeService);
  private circuitFacade = inject(CircuitFacadeService);
  private contenuApi = inject(ContenuApiService);
  private mediaApi = inject(MediaApiService);
  private platformId = inject(PLATFORM_ID);
  private router = inject(Router);

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
  circuits: CircuitUI[] = [];
  backendError: boolean = false;

  // Hero slider
  heroImages: string[] = [
    'assets/images/hero.jpg',
    'assets/images/circuit_1.jpg',
    'assets/images/circuit_3.jpg',
    'assets/images/patrimoine_2.jpg'
  ];
  currentHeroIndex = 0;
  private intervalId: any;
  private autoScrollInterval: any;

  ngOnInit(): void {
    this.facadeService.getPatrimoines().subscribe({
      next: (data: PatrimoineUI[]) => {
        // RÈGLE STRICTE : seuls les patrimoines au statut PUBLIE sont affichés sur la vitrine
        const published = (data || []).filter(p => p.statut === 'PUBLIE');

        const resolved = published.map(p => {
          let reg = p.region;
          if (reg && reg.toLowerCase().includes('gorée')) {
            reg = 'Dakar';
          }

          return {
            ...p,
            latitude: p.latitude,
            longitude: p.longitude,
            region: reg || 'Sénégal',
            imageUrl: p.imageUrl || 'assets/images/patrimoine_1.jpg'
          } as PatrimoineUI;
        });

        this.patrimoines = resolved;

        // Statistiques calculées dynamiquement sur les patrimoines publiés
        this.unescoCount = resolved.filter(p => !!p.inscritUnesco).length;
        const regions = [...new Set(resolved.map((p: PatrimoineUI) => p.region).filter(Boolean))] as string[];
        this.regionsCount = regions.length;

        // Groupement dynamique par catégories (patrimoines publiés uniquement)
        const groupsMap = new Map<string, PatrimoineUI[]>();
        resolved.forEach(p => {
          const cat = p.categorie || 'AUTRE';
          if (!groupsMap.has(cat)) groupsMap.set(cat, []);
          groupsMap.get(cat)!.push(p);
        });

        this.categoriesGroups = Array.from(groupsMap.entries()).map(([cat, items]) => ({
          categoryId: cat,
          label: this.facadeService.formatCategorieLabel(cat),
          items
        }));

        setTimeout(() => {
          this.startAutoScroll();
        }, 500);
      },
      error: (err) => {
        console.warn('Erreur chargement patrimoines home', err);
        this.backendError = true;
        this.patrimoines = [];
        this.unescoCount = 0;
        this.regionsCount = 0;
        this.categoriesGroups = [];
      }
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

    // Chargement dynamique des circuits réels
    this.circuitFacade.getCircuits().subscribe({
      next: (list) => {
        this.circuits = (list || []).filter(c => c.statut === 'PUBLIE' || !c.statut).slice(0, 3);
      },
      error: (err) => console.warn('Erreur chargement circuits home', err)
    });

    this.intervalId = setInterval(() => {
      this.currentHeroIndex = (this.currentHeroIndex + 1) % this.heroImages.length;
    }, 5000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    this.stopAutoScroll();
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

  quickSearch(keyword: string): void {
    this.searchQuery = keyword;
    this.onSearch();
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/patrimoines'], { queryParams: { q: this.searchQuery.trim() } });
    } else {
      this.router.navigate(['/patrimoines']);
    }
  }

  // ── Carousel Navigation & Auto-scroll ──
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
}
