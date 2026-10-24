import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, HostListener, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import { PatrimoineCardComponent } from '../../components/patrimoine-card/patrimoine-card.component';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';
import { forkJoin, of } from 'rxjs';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

import { EntetePatrimoineComponent } from './components/entete-patrimoine/entete-patrimoine.component';
import { OngletsNavigationComponent } from './components/onglets-navigation/onglets-navigation.component';
import { ApercuPatrimoineComponent } from './components/apercu-patrimoine/apercu-patrimoine.component';
import { CaracteristiquesPatrimoineComponent } from './components/caracteristiques-patrimoine/caracteristiques-patrimoine.component';
import { RecitsPatrimoineComponent } from './components/recits-patrimoine/recits-patrimoine.component';
import { GalerieMediasComponent } from './components/galerie-medias/galerie-medias.component';
import { EtatConservationComponent } from './components/etat-conservation/etat-conservation.component';
import { CarteLocalisationComponent } from './components/carte-localisation/carte-localisation.component';
import { PatrimoinesSimilairesComponent } from './components/patrimoines-similaires/patrimoines-similaires.component';
import { VisionneusePleinEcranComponent } from './components/visionneuse-plein-ecran/visionneuse-plein-ecran.component';
import { ModaleLectureRecitComponent } from './components/modale-lecture-recit/modale-lecture-recit.component';
import { PlanifierVisiteComponent } from './components/planifier-visite/planifier-visite.component';

@Component({
  selector: 'app-patrimoine-detail',
  standalone: true,
  encapsulation: ViewEncapsulation.None,
  imports: [
    CommonModule,
    RouterLink,
    PatrimoineCardComponent,
    FormsModule,
    EntetePatrimoineComponent,
    OngletsNavigationComponent,
    ApercuPatrimoineComponent,
    CaracteristiquesPatrimoineComponent,
    RecitsPatrimoineComponent,
    GalerieMediasComponent,
    EtatConservationComponent,
    CarteLocalisationComponent,
    PatrimoinesSimilairesComponent,
    VisionneusePleinEcranComponent,
    ModaleLectureRecitComponent,
    PlanifierVisiteComponent
  ],
  templateUrl: './patrimoine-detail.component.html',
  styleUrl: './patrimoine-detail.component.css'
})
export class PatrimoineDetailComponent implements OnInit, AfterViewInit, OnDestroy {
  // We keep patrimoineUI for the "Vous aimerez aussi" section which expects PatrimoineUI[]
  similaires: PatrimoineUI[] = [];
  
  // New separate properties for strict compliance with contracts
  patrimoine: any | undefined;
  geolocalisation: any | undefined;
  medias: any[] = [];
  allMedias: any[] = [];
  galerieMedias: any[] = [];
  
  // Contenus éditoriaux (ms-contenu)
  contenus: any[] = [];
  filteredContenus: any[] = [];
  selectedContenuType: string = 'TOUS';
  contenuSearchQuery: string = '';
  selectedContenuForReading: any | null = null;
  readingModalOpen: boolean = false;
  
  // Categorized medias
  photos: any[] = [];
  videos: any[] = [];
  audios: any[] = [];
  documents: any[] = [];
  immersions: any[] = [];

  // Arrays for displaying filtered data
  filteredPhotos: any[] = [];
  filteredVideos: any[] = [];
  filteredAudios: any[] = [];
  filteredDocuments: any[] = [];
  filteredImmersions: any[] = [];

  // Filter Bar state
  activeMediaFilter: string = 'ALL';
  mediaSearchQuery: string = '';
  mediaSortOrder: 'NEWEST' | 'OLDEST' = 'NEWEST';
  mediaTabs: { type: string, label: string, count: number }[] = [];

  mainImage: string | undefined;
  mapUrl: SafeResourceUrl | null = null;

  loading = true;

  activeSection = 'presentation';
  expandedThreatIndex: number = -1;
  private observer: IntersectionObserver | null = null;

  @ViewChild('similarCarousel') similarCarousel!: ElementRef<HTMLElement>;
  private autoScrollInterval: any;
  
  scrollProgress: number = 0;

  // Lightbox state
  lightboxOpen = false;
  currentLightboxIndex = 0;
  currentLightboxMedias: any[] = [];

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const scrollTop = window.scrollY || document.documentElement.scrollTop;
    const docHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    if (docHeight > 0) {
      this.scrollProgress = (scrollTop / docHeight) * 100;
    }
    this.updateActiveSectionOnScroll();
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Escape') {
      if (this.lightboxOpen) this.closeLightbox();
      if (this.readingModalOpen) this.closeContenuReader();
      return;
    }
    if (!this.lightboxOpen) return;
    if (event.key === 'ArrowRight') this.nextLightboxMedia(new Event('keyboard'));
    if (event.key === 'ArrowLeft') this.prevLightboxMedia(new Event('keyboard'));
  }

  openLightbox(medias: any[], index: number): void {
    this.currentLightboxMedias = medias;
    this.currentLightboxIndex = index;
    this.lightboxOpen = true;
    document.body.style.overflow = 'hidden';
  }

  closeLightbox(): void {
    this.lightboxOpen = false;
    document.body.style.overflow = 'auto';
  }

  nextLightboxMedia(event: Event): void {
    event.stopPropagation();
    if (this.currentLightboxMedias.length > 0) {
      this.currentLightboxIndex = (this.currentLightboxIndex + 1) % this.currentLightboxMedias.length;
    }
  }

  prevLightboxMedia(event: Event): void {
    event.stopPropagation();
    if (this.currentLightboxMedias.length > 0) {
      this.currentLightboxIndex = (this.currentLightboxIndex - 1 + this.currentLightboxMedias.length) % this.currentLightboxMedias.length;
    }
  }

  constructor(
    private route: ActivatedRoute,
    public facadeService: PatrimoineFacadeService,
    private el: ElementRef,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.loadPatrimoine(id);
    });
  }

  loadPatrimoine(id: string): void {
    this.loading = true;
    
    forkJoin({
      patrimoine: this.facadeService.getPatrimoineRaw(id),
      geolocalisation: this.facadeService.getGeolocalisation(id),
      medias: this.facadeService.getMedias(id),
      contenus: this.facadeService.getContenus(id),
      similaires: this.facadeService.getSimilaires(id, 3)
    }).subscribe({
      next: (data) => {
        this.patrimoine = data.patrimoine;
        this.geolocalisation = data.geolocalisation;
        
        if (this.geolocalisation && this.geolocalisation.latitude && this.geolocalisation.longitude) {
           const lat = this.geolocalisation.latitude;
           const lon = this.geolocalisation.longitude;
           const url = `https://www.openstreetmap.org/export/embed.html?bbox=${lon-0.1},${lat-0.1},${lon+0.1},${lat+0.1}&layer=mapnik&marker=${lat},${lon}`;
           this.mapUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
        }
        
        this.medias = (data.medias || []).map(m => ({ ...m, url: m.url.startsWith('http') || m.url.startsWith('/') ? m.url : '/' + m.url }));
        this.allMedias = this.medias;
        
        // Remove note from similaires to respect data contract
        this.similaires = (data.similaires || []).map(s => {
          const sCopy = { ...s };
          delete sCopy.note;
          return sCopy;
        });
        
        // La photo principale provient uniquement de ms-patrimoine (photoPrincipale)
        let mainImageUrl = (this.patrimoine as any).photoPrincipale?.urlPhotoPrincipale || '';
        
        this.mainImage = mainImageUrl || '/assets/images/activite_saloum.jpg';

        // TOUS les médias récupérés vont dans la galerie, sans exception
        this.galerieMedias = [...this.medias];

        // Categorize medias
        this.photos = this.galerieMedias.filter(m => ['PHOTO', 'CARTE', 'ARCHIVE_NUMERISEE'].includes(m.type));
        this.videos = this.galerieMedias.filter(m => m.type === 'VIDEO');
        this.audios = this.galerieMedias.filter(m => m.type === 'AUDIO');
        this.documents = this.galerieMedias.filter(m => m.type === 'DOCUMENT');
        this.immersions = this.galerieMedias.filter(m => ['PANORAMA_360', 'MODELE_3D'].includes(m.type));

        this.updateMediaTabs();
        this.applyMediaFilters();

        // Initialisation des contenus éditoriaux
        this.contenus = (data.contenus || []).filter((c: any) => c.statut !== 'ARCHIVE');
        this.applyContenuFilters();

        this.loading = false;
        this.setupBioCarousel();
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: () => {
        this.loading = false;
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    });
  }

  getMediaUrl(mediaId: string): string | undefined {
    if (!mediaId) return undefined;
    const media = this.allMedias.find(m => m.id === mediaId);
    return media ? media.url : undefined;
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.startAutoScroll();
    }, 1000);
  }
  
  bioPages: string[][] = [];
  currentBioPage = 0;
  bioInterval: any;

  scrollToSection(sectionId: string): void {
    this.activeSection = sectionId;
    const el = document.getElementById(sectionId);
    if (el) {
      const navOffset = 70;
      const elementPosition = el.getBoundingClientRect().top;
      const offsetPosition = elementPosition + window.pageYOffset - navOffset;

      window.scrollTo({
        top: offsetPosition,
        behavior: 'smooth'
      });
    }
  }

  changePage(id: string): void {
    this.scrollToSection(id);
  }

  updateActiveSectionOnScroll(): void {
    const sectionIds = ['presentation', 'recits', 'caracteristiques', 'galerie', 'conservation', 'localisation'];
    
    // Check if scrolled near the bottom of the page
    if ((window.innerHeight + window.scrollY) >= (document.documentElement.scrollHeight - 60)) {
      for (let i = sectionIds.length - 1; i >= 0; i--) {
        const el = document.getElementById(sectionIds[i]);
        if (el) {
          this.activeSection = sectionIds[i];
          return;
        }
      }
    }

    for (let i = sectionIds.length - 1; i >= 0; i--) {
      const id = sectionIds[i];
      const el = document.getElementById(id);
      if (el) {
        const rect = el.getBoundingClientRect();
        if (rect.top <= 150) {
          this.activeSection = id;
          break;
        }
      }
    }
  }

  get biodiversiteList(): string[] {
    if (!this.patrimoine || !this.patrimoine.biodiversite) return [];
    return this.patrimoine.biodiversite.split(',').map((s: string) => s.trim()).filter((s: string) => s.length > 0);
  }

  setupBioCarousel() {
    this.clearBioCarousel();
    const list = this.biodiversiteList;
    if (list.length === 0) return;
    
    this.bioPages = [];
    for (let i = 0; i < list.length; i += 2) {
      if (i + 1 < list.length) {
        this.bioPages.push([list[i], list[i + 1]]);
      } else if (list.length > 1) {
        // Pair the last odd item with the first item to have exactly 2
        this.bioPages.push([list[i], list[0]]);
      } else {
        // Only 1 item total
        this.bioPages.push([list[0], list[0]]);
      }
    }
    
    this.currentBioPage = 0;
    if (this.bioPages.length > 1) {
      this.bioInterval = setInterval(() => {
        this.currentBioPage = (this.currentBioPage + 1) % this.bioPages.length;
      }, 5000);
    }
  }

  clearBioCarousel() {
    if (this.bioInterval) {
      clearInterval(this.bioInterval);
      this.bioInterval = null;
    }
  }

  getBiodiversiteImage(itemName: string): string {
    return this.mainImage || '/assets/images/patrimoine_1.jpg';
  }

  toggleThreat(index: number): void {
    if (this.expandedThreatIndex === index) {
      this.expandedThreatIndex = -1;
    } else {
      this.expandedThreatIndex = index;
    }
  }

  getMenaceColor(niveau: string): string {
    if (!niveau) return '#666';
    switch (niveau.toUpperCase()) {
      case 'FAIBLE': return '#10B981'; // Green
      case 'MOYEN': return '#F59E0B'; // Yellow/Orange
      case 'ÉLEVÉ':
      case 'ELEVE': return '#EF4444'; // Red
      case 'CRITIQUE': return '#991B1B'; // Dark red
      default: return '#666';
    }
  }

  ngOnDestroy(): void {
    this.pauseAutoScroll();
    this.clearBioCarousel();
  }

  startAutoScroll(): void {
    if (this.autoScrollInterval) return;
    this.autoScrollInterval = setInterval(() => {
      this.scrollSimilarCarousel(1);
    }, 4000);
  }

  pauseAutoScroll(): void {
    if (this.autoScrollInterval) {
      clearInterval(this.autoScrollInterval);
      this.autoScrollInterval = null;
    }
  }

  scrollSimilarCarousel(direction: number): void {
    if (this.similarCarousel && this.similarCarousel.nativeElement) {
      const carousel = this.similarCarousel.nativeElement;
      const cardWidth = 320; 
      const newScrollPosition = carousel.scrollLeft + (cardWidth * direction);
      
      if (direction === 1 && newScrollPosition >= (carousel.scrollWidth - carousel.clientWidth - 10)) {
        carousel.scrollTo({ left: 0, behavior: 'smooth' });
      } else if (direction === -1 && carousel.scrollLeft <= 0) {
        carousel.scrollTo({ left: carousel.scrollWidth, behavior: 'smooth' });
      } else {
        carousel.scrollTo({ left: newScrollPosition, behavior: 'smooth' });
      }
    }
  }

  formatEnum(value: string | undefined | null): string {
    if (!value || value === '-') return '-';
    // Special case for PUBLIE
    if (value === 'PUBLIE') return 'Publié';
    
    return value
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  }

  // --- LOGIQUE BARRE DE FILTRES MULTIMÉDIAS ---
  updateMediaTabs() {
    this.mediaTabs = [
      { type: 'PHOTO', label: 'Photos', count: this.galerieMedias.filter(m => m.type === 'PHOTO').length },
      { type: 'VIDEO', label: 'Vidéos', count: this.galerieMedias.filter(m => m.type === 'VIDEO').length },
      { type: 'AUDIO', label: 'Audios', count: this.galerieMedias.filter(m => m.type === 'AUDIO').length },
      { type: 'DOCUMENT', label: 'Documents', count: this.galerieMedias.filter(m => m.type === 'DOCUMENT').length },
      { type: 'PANORAMA_360', label: 'Visites à 360°', count: this.galerieMedias.filter(m => m.type === 'PANORAMA_360').length },
      { type: 'MODELE_3D', label: 'Modèles 3D', count: this.galerieMedias.filter(m => m.type === 'MODELE_3D').length },
      { type: 'CARTE', label: 'Cartes', count: this.galerieMedias.filter(m => m.type === 'CARTE').length },
      { type: 'ARCHIVE_NUMERISEE', label: 'Archives numérisées', count: this.galerieMedias.filter(m => m.type === 'ARCHIVE_NUMERISEE').length }
    ];
  }

  setMediaFilter(type: string) {
    this.activeMediaFilter = type;
    this.applyMediaFilters();
  }

  applyMediaFilters() {
    let filtered = [...this.galerieMedias];

    // 1. Filtrer par type exact (si ce n'est pas ALL)
    if (this.activeMediaFilter !== 'ALL') {
      filtered = filtered.filter(m => m.type === this.activeMediaFilter);
    }

    // 2. Recherche textuelle
    if (this.mediaSearchQuery && this.mediaSearchQuery.trim() !== '') {
      const query = this.mediaSearchQuery.toLowerCase().trim();
      filtered = filtered.filter(m => 
        (m.nom && m.nom.toLowerCase().includes(query)) ||
        (m.description && m.description.toLowerCase().includes(query))
      );
    }

    // 3. Tri
    // Si on a un champ date, on l'utilise. Sinon on se rabat sur l'ordre de base.
    if (this.mediaSortOrder === 'NEWEST') {
      // Assuming IDs are chronologically increasing if they are timestamp-based or just reverse the array
      filtered.reverse(); 
    } else {
      // Keep natural order (oldest first if returned in that order)
      // No action needed as they are already in natural order before reversing
    }

    // Update specific arrays for the template
    this.filteredPhotos = filtered.filter(m => ['PHOTO', 'CARTE', 'ARCHIVE_NUMERISEE'].includes(m.type));
    this.filteredVideos = filtered.filter(m => m.type === 'VIDEO');
    this.filteredAudios = filtered.filter(m => m.type === 'AUDIO');
    this.filteredDocuments = filtered.filter(m => m.type === 'DOCUMENT');
    this.filteredImmersions = filtered.filter(m => ['PANORAMA_360', 'MODELE_3D'].includes(m.type));
  }

  // --- LOGIQUE RÉCITS & GUIDES ÉDITORIAUX (ms-contenu) ---
  openContenuReader(contenu: any): void {
    this.selectedContenuForReading = contenu;
    this.readingModalOpen = true;
    document.body.style.overflow = 'hidden';
  }

  closeContenuReader(): void {
    this.readingModalOpen = false;
    this.selectedContenuForReading = null;
    document.body.style.overflow = 'auto';
  }

  setContenuTypeFilter(type: string): void {
    this.selectedContenuType = type;
    this.applyContenuFilters();
  }

  applyContenuFilters(): void {
    let filtered = [...this.contenus];
    if (this.selectedContenuType !== 'TOUS') {
      filtered = filtered.filter(c => c.type === this.selectedContenuType);
    }
    if (this.contenuSearchQuery && this.contenuSearchQuery.trim()) {
      const q = this.contenuSearchQuery.toLowerCase().trim();
      filtered = filtered.filter(c =>
        (c.titre && c.titre.toLowerCase().includes(q)) ||
        (c.resume && c.resume.toLowerCase().includes(q)) ||
        (c.corps && c.corps.toLowerCase().includes(q)) ||
        (c.motsCles && c.motsCles.some((k: string) => k.toLowerCase().includes(q)))
      );
    }
    this.filteredContenus = filtered;
  }

  get uniqueContenuTypes(): { type: string; label: string; count: number }[] {
    const types = Array.from(new Set(this.contenus.map(c => c.type)));
    return types.map(t => ({
      type: t,
      label: this.getContenuTypeLabel(t),
      count: this.contenus.filter(c => c.type === t).length
    }));
  }

  getReadingTime(text: string): number {
    if (!text) return 2;
    const words = text.trim().split(/\s+/).length;
    return Math.max(1, Math.ceil(words / 200));
  }

  getContenuTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      'ARTICLE': 'Article Historique',
      'GUIDE_VISITE': 'Guide de Visite',
      'RECIT': 'Récit Vivant',
      'CHRONIQUE': 'Chronique',
      'ANECDOTE': 'Anecdote Culturelle',
      'LEGENDE': 'Légende & Mythe',
      'TEMOIGNAGE': 'Témoignage'
    };
    return labels[type] || type || 'Récit';
  }

  getContenuTypeIcon(type: string): string {
    const icons: Record<string, string> = {
      'ARTICLE': 'fa-feather-pointed',
      'GUIDE_VISITE': 'fa-compass',
      'RECIT': 'fa-book-open',
      'CHRONIQUE': 'fa-clock-rotate-left',
      'ANECDOTE': 'fa-lightbulb',
      'LEGENDE': 'fa-dragon',
      'TEMOIGNAGE': 'fa-comments'
    };
    return icons[type] || 'fa-newspaper';
  }

  getMediaUrlForContenu(c: any): string | null {
    if (c.mediaIds && c.mediaIds.length > 0) {
      const media = this.allMedias.find(m => m.id === c.mediaIds[0]) || this.medias.find(m => m.id === c.mediaIds[0]);
      if (media && media.url) return media.url;
    }
    return this.mainImage || null;
  }
}
