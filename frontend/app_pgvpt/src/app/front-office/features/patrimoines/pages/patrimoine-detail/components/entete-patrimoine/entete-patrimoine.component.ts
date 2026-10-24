import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PatrimoineFacadeService } from '../../../../../../../core/services/patrimoine-facade.service';
import { directionsUrl, hasValue, humanize, locationParts } from '../../patrimoine-display.utils';

@Component({
  selector: 'app-entete-patrimoine',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './entete-patrimoine.component.html'
})
export class EntetePatrimoineComponent implements OnChanges {
  @Input() patrimoine: any;
  @Input() geolocalisation: any;
  @Input() mainImage: string | undefined;
  @Input() mediaCount = 0;

  /** Demande au parent de faire défiler jusqu'à une section. */
  @Output() navigate = new EventEmitter<string>();

  isFavori = false;
  toast: string | null = null;
  private toastTimer: any;

  private static readonly FAVORIS_KEY = 'pgvpt_favoris';

  constructor(public facadeService: PatrimoineFacadeService) {}

  ngOnChanges(): void {
    this.isFavori = this.readFavoris().includes(this.patrimoine?.id);
  }

  get locationLabel(): string {
    return locationParts(this.geolocalisation, this.patrimoine).join(' · ');
  }

  get itineraireUrl(): string | null {
    return directionsUrl(this.geolocalisation?.latitude, this.geolocalisation?.longitude);
  }

  get typeLabel(): string {
    return humanize(this.patrimoine?.type);
  }

  get categorieLabel(): string {
    return this.patrimoine?.categorie ? this.facadeService.formatCategorieLabel(this.patrimoine.categorie) : '';
  }

  /** Noms alternatifs réellement différents du nom principal. */
  get altNames(): { label: string; value: string }[] {
    const nom = (this.patrimoine?.nom || '').trim().toLowerCase();
    const out: { label: string; value: string }[] = [];
    const local = this.patrimoine?.nomLocal;
    const histo = this.patrimoine?.nomHistorique;
    if (hasValue(local) && local.trim().toLowerCase() !== nom) out.push({ label: 'Nom local', value: local });
    if (hasValue(histo) && histo.trim().toLowerCase() !== nom && histo !== local) out.push({ label: 'Anciennement', value: histo });
    return out;
  }

  toggleFavori(): void {
    const list = this.readFavoris();
    const id = this.patrimoine?.id;
    if (!id) return;
    const idx = list.indexOf(id);
    if (idx >= 0) list.splice(idx, 1); else list.push(id);
    localStorage.setItem(EntetePatrimoineComponent.FAVORIS_KEY, JSON.stringify(list));
    this.isFavori = idx < 0;
    this.showToast(this.isFavori ? 'Ajouté à vos favoris' : 'Retiré de vos favoris');
  }

  async partager(): Promise<void> {
    const data = { title: this.patrimoine?.nom, text: this.patrimoine?.descriptionCourte || '', url: window.location.href };
    try {
      if ((navigator as any).share) {
        await (navigator as any).share(data);
        return;
      }
      await navigator.clipboard.writeText(window.location.href);
      this.showToast('Lien copié dans le presse-papiers');
    } catch {
      /* partage annulé par l'utilisateur */
    }
  }

  private readFavoris(): string[] {
    try {
      return JSON.parse(localStorage.getItem(EntetePatrimoineComponent.FAVORIS_KEY) || '[]');
    } catch {
      return [];
    }
  }

  private showToast(msg: string): void {
    this.toast = msg;
    clearTimeout(this.toastTimer);
    this.toastTimer = setTimeout(() => (this.toast = null), 2500);
  }
}
