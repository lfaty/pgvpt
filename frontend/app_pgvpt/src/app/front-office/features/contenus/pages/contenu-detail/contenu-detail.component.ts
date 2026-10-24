import { Component, OnInit, HostListener, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ContenuApiService } from '../../../../../core/services/api/contenu-api.service';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { MediaApiService } from '../../../../../core/services/api/media-api.service';
import { BarreLectureEnteteComponent } from './components/barre-lecture-entete/barre-lecture-entete.component';
import { ChapeauArticleComponent } from './components/chapeau-article/chapeau-article.component';
import { CorpsArticleComponent } from './components/corps-article/corps-article.component';
import { PanneauSiteRattacheComponent } from './components/panneau-site-rattache/panneau-site-rattache.component';
import { RecitsCollectionComponent } from './components/recits-collection/recits-collection.component';
import { VisionneuseLightboxComponent } from './components/visionneuse-lightbox/visionneuse-lightbox.component';

@Component({
  selector: 'app-contenu-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    BarreLectureEnteteComponent,
    ChapeauArticleComponent,
    CorpsArticleComponent,
    PanneauSiteRattacheComponent,
    RecitsCollectionComponent,
    VisionneuseLightboxComponent
  ],
  templateUrl: './contenu-detail.component.html',
  styleUrls: ['./contenu-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ContenuDetailComponent implements OnInit {
  contenuId: string | null = null;
  patrimoineId: string | null = null;

  contenu: any = null;
  patrimoine: any = null;
  associatedMedias: any[] = [];
  otherContenus: any[] = [];

  loading: boolean = true;
  error: string | null = null;
  copiedNotification: boolean = false;

  readingProgress: number = 0;
  fontSizeMode: 'normal' | 'large' = 'normal';
  lightboxOpen: boolean = false;
  selectedLightboxMedia: any = null;

  @HostListener('window:scroll', [])
  onScroll(): void {
    const scrollTop = window.scrollY || document.documentElement.scrollTop;
    const docHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    if (docHeight > 0) {
      this.readingProgress = Math.min(100, Math.max(0, (scrollTop / docHeight) * 100));
    }
  }

  @HostListener('document:keydown.escape')
  onEscape(): void {
    if (this.lightboxOpen) {
      this.closeLightbox();
    }
  }

  toggleFontSize(): void {
    this.fontSizeMode = this.fontSizeMode === 'normal' ? 'large' : 'normal';
  }

  openLightbox(media: any): void {
    this.selectedLightboxMedia = media;
    this.lightboxOpen = true;
    document.body.style.overflow = 'hidden';
  }

  closeLightbox(): void {
    this.lightboxOpen = false;
    this.selectedLightboxMedia = null;
    document.body.style.overflow = 'auto';
  }

  get mainArticleImage(): string {
    if (this.associatedMedias && this.associatedMedias.length > 0 && this.associatedMedias[0].url) {
      return this.associatedMedias[0].url;
    }
    if (this.patrimoine?.photoPrincipale?.urlPhotoPrincipale) {
      return this.patrimoine.photoPrincipale.urlPhotoPrincipale;
    }
    return '/assets/images/activite_saloum.jpg';
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private contenuApi: ContenuApiService,
    private patrimoineApi: PatrimoineApiService,
    private mediaApi: MediaApiService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.contenuId = params.get('contenuId') || params.get('id');
      this.patrimoineId = params.get('patrimoineId');

      if (this.contenuId) {
        this.loadContenu(this.contenuId);
      } else {
        this.loading = false;
        this.error = 'Identifiant du contenu non spécifié.';
      }
    });
  }

  loadContenu(id: string): void {
    this.loading = true;
    this.error = null;

    this.contenuApi.getById(id).subscribe({
      next: (c: any) => {
        this.contenu = c;
        this.loading = false;
        window.scrollTo({ top: 0, behavior: 'smooth' });

        const effectivePatrimoineId = this.patrimoineId || c.patrimoineId;
        if (effectivePatrimoineId) {
          this.patrimoineId = effectivePatrimoineId;
          this.loadPatrimoineAndOthers(effectivePatrimoineId, id);
        }

        // Charger les médias rattachés si disponibles
        if (c.mediaIds && c.mediaIds.length > 0) {
          this.loadAssociatedMedias(c.mediaIds);
        }
      },
      error: (err: any) => {
        console.error('Erreur chargement contenu', err);
        this.loading = false;
        this.error = 'Impossible de charger ce récit. Il a peut-être été déplacé ou archivé.';
      }
    });
  }

  loadPatrimoineAndOthers(patrimoineId: string, currentContenuId: string): void {
    this.patrimoineApi.getById(patrimoineId).pipe(catchError(() => of(null))).subscribe(p => {
      this.patrimoine = p;
    });

    this.contenuApi.getByPatrimoineId(patrimoineId).pipe(catchError(() => of([]))).subscribe(list => {
      this.otherContenus = (list || [])
        .filter((item: any) => item.id !== currentContenuId && item.statut !== 'ARCHIVE')
        .slice(0, 3);
    });
  }

  loadAssociatedMedias(mediaIds: string[]): void {
    const requests = mediaIds.slice(0, 4).map(id =>
      this.mediaApi.getById(id).pipe(catchError(() => of(null)))
    );

    forkJoin(requests).subscribe(results => {
      this.associatedMedias = results
        .filter(m => m !== null)
        .map(m => ({
          ...m,
          url: m.url ? (m.url.startsWith('http') || m.url.startsWith('/') ? m.url : '/' + m.url) : ''
        }));
    });
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

  copyShareUrl(): void {
    if (typeof navigator !== 'undefined' && navigator.clipboard) {
      navigator.clipboard.writeText(window.location.href);
      this.copiedNotification = true;
      setTimeout(() => {
        this.copiedNotification = false;
      }, 3000);
    }
  }

  printArticle(): void {
    if (typeof window !== 'undefined') {
      window.print();
    }
  }
}
