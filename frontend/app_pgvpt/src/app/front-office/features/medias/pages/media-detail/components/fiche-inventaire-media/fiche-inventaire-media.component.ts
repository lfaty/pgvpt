import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-fiche-inventaire-media',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fiche-inventaire-media.component.html'
})
export class FicheInventaireMediaComponent {
  @Input() media: any = null;

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
