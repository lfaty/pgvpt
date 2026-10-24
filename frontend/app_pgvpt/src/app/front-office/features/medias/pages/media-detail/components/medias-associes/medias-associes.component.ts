import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-medias-associes',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './medias-associes.component.html'
})
export class MediasAssociesComponent {
  @Input() relatedMedias: any[] = [];
  @Input() patrimoineId: string | null = null;

  getTypeLabel(type: string): string {
    switch (type) {
      case 'PHOTO': return 'Photo';
      case 'VIDEO': return 'Vidéo';
      case 'AUDIO': return 'Audio';
      case 'DOCUMENT': return 'Document';
      case 'PANORAMA_360': return 'Visite à 360°';
      case 'MODELE_3D': return 'Modèle 3D';
      case 'CARTE': return 'Carte';
      case 'ARCHIVE_NUMERISEE': return 'Archive';
      default: return type || 'Média';
    }
  }
}
