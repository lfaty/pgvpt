import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-section-chroniques',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './section-chroniques.component.html',
  styleUrl: './section-chroniques.component.css'
})
export class SectionChroniquesComponent {
  @Input() recentContenus: any[] = [];
  @Input() patrimoines: PatrimoineUI[] = [];
  @Input() allMedias: any[] = [];

  getContenuTypeLabel(type: string): string {
    const labels: { [key: string]: string } = {
      'ARTICLE': 'Article',
      'GUIDE': 'Guide de visite',
      'LEGENDE': 'Légende orale',
      'RECIT': 'Récit historique',
      'RECETTE': 'Tradition culinaire',
      'DESCRIPTION': 'Monographie',
      'ACTUALITE': 'Actualité'
    };
    return labels[type] || 'Récit';
  }

  getContenuTypeIcon(type: string): string {
    const icons: { [key: string]: string } = {
      'ARTICLE': 'fa-solid fa-newspaper',
      'GUIDE': 'fa-solid fa-route',
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
    if (contenu?.medias && contenu.medias.length > 0) {
      const img = contenu.medias.find((m: any) => m.typeMedia === 'IMAGE' || m.type === 'PHOTO' || m.url?.match(/\.(jpeg|jpg|png|webp|avif)/i));
      if (img?.url) return img.url;
    }
    if (contenu?.mediaIds && Array.isArray(contenu.mediaIds) && contenu.mediaIds.length > 0 && this.allMedias.length > 0) {
      const foundMedia = this.allMedias.find(m => contenu.mediaIds.includes(m.id));
      if (foundMedia?.url) return foundMedia.url;
    }
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
}
