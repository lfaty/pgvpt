import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MediaApiService } from '../../../../../core/services/api/media-api.service';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { VisualiseurMediaComponent } from './components/visualiseur-media/visualiseur-media.component';
import { MediasAssociesComponent } from './components/medias-associes/medias-associes.component';
import { FicheInventaireMediaComponent } from './components/fiche-inventaire-media/fiche-inventaire-media.component';
import { SiteCulturelRattacheComponent } from './components/site-culturel-rattache/site-culturel-rattache.component';

@Component({
  selector: 'app-media-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    VisualiseurMediaComponent,
    MediasAssociesComponent,
    FicheInventaireMediaComponent,
    SiteCulturelRattacheComponent
  ],
  templateUrl: './media-detail.component.html',
  styleUrls: ['./media-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MediaDetailComponent implements OnInit {
  mediaId: string | null = null;
  patrimoineId: string | null = null;
  
  media: any = null;
  patrimoine: any = null;
  
  loading: boolean = true;
  error: string | null = null;

  relatedMedias: any[] = [];
  zoomLevel: number = 1;
  isFullscreen: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private mediaApi: MediaApiService,
    private patrimoineApi: PatrimoineApiService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.mediaId = params.get('mediaId') || params.get('id');
      this.patrimoineId = params.get('patrimoineId');
      this.zoomLevel = 1;
      
      if (this.mediaId) {
        this.loadMedia(this.mediaId);
      } else {
        this.loading = false;
        this.error = "Identifiant de média non spécifié.";
      }
    });
  }

  loadMedia(id: string): void {
    this.loading = true;
    this.error = null;
    this.zoomLevel = 1;

    this.mediaApi.getById(id).subscribe({
      next: (m: any) => {
        const url = m.url ? (m.url.startsWith('http') || m.url.startsWith('/') ? m.url : '/' + m.url) : '';
        this.media = { ...m, url };
        this.loading = false;

        const effectivePatrimoineId = this.patrimoineId || this.media.patrimoineId;
        if (effectivePatrimoineId) {
          this.patrimoineId = effectivePatrimoineId;
          this.loadPatrimoineAndRelated(effectivePatrimoineId, id);
        }
      },
      error: (err: any) => {
        console.error("Erreur lors de la récupération du média", err);
        this.loading = false;
        this.error = "Impossible de charger les détails de ce média.";
      }
    });
  }

  loadPatrimoineAndRelated(patrimoineId: string, currentMediaId: string): void {
    this.patrimoineApi.getById(patrimoineId).subscribe({
      next: (p: any) => {
        this.patrimoine = p;
      },
      error: (err: any) => {
        console.warn("Impossible de récupérer les détails du patrimoine associé", err);
      }
    });

    this.mediaApi.getByPatrimoineId(patrimoineId).subscribe({
      next: (list: any[]) => {
        this.relatedMedias = (list || [])
          .filter(m => m.id !== currentMediaId)
          .map(m => ({
            ...m,
            url: m.url ? (m.url.startsWith('http') || m.url.startsWith('/') ? m.url : '/' + m.url) : ''
          }))
          .slice(0, 6);
      },
      error: (err: any) => {
        console.warn("Impossible de charger les médias similaires", err);
      }
    });
  }

  zoomIn(): void {
    if (this.zoomLevel < 3) {
      this.zoomLevel = Number((this.zoomLevel + 0.25).toFixed(2));
    }
  }

  zoomOut(): void {
    if (this.zoomLevel > 0.5) {
      this.zoomLevel = Number((this.zoomLevel - 0.25).toFixed(2));
    }
  }

  resetZoom(): void {
    this.zoomLevel = 1;
  }

  toggleFullscreen(container: HTMLElement): void {
    if (!document.fullscreenElement) {
      container.requestFullscreen().then(() => {
        this.isFullscreen = true;
      }).catch(err => {
        console.warn('Plein écran impossible', err);
      });
    } else {
      document.exitFullscreen().then(() => {
        this.isFullscreen = false;
      });
    }
  }

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'PHOTO': return 'Photo';
      case 'VIDEO': return 'Vidéo';
      case 'AUDIO': return 'Audio';
      case 'DOCUMENT': return 'Document';
      case 'PANORAMA_360': return 'Visite à 360°';
      case 'MODELE_3D': return 'Modèle 3D';
      case 'CARTE': return 'Carte';
      case 'ARCHIVE_NUMERISEE': return 'Archive numérisée';
      default: return type || 'Média';
    }
  }

  getTypeIcon(type: string): string {
    switch (type) {
      case 'PHOTO': return 'fa-image';
      case 'VIDEO': return 'fa-video';
      case 'AUDIO': return 'fa-headphones';
      case 'DOCUMENT': return 'fa-file-pdf';
      case 'PANORAMA_360': return 'fa-vr-cardboard';
      case 'MODELE_3D': return 'fa-cube';
      case 'CARTE': return 'fa-map';
      case 'ARCHIVE_NUMERISEE': return 'fa-archive';
      default: return 'fa-photo-film';
    }
  }
}
