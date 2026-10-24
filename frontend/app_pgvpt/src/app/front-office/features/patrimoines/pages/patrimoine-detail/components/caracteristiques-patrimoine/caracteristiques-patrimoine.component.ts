import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { hasValue, humanize } from '../../patrimoine-display.utils';

interface TimelineItem { titre: string; texte: string; icon: string; }

@Component({
  selector: 'app-caracteristiques-patrimoine',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './caracteristiques-patrimoine.component.html'
})
export class CaracteristiquesPatrimoineComponent {
  @Input() patrimoine: any;
  @Input() geolocalisation: any;
  @Input() mainImage: string | undefined;

  readonly hasValue = hasValue;
  readonly humanize = humanize;

  get biodiversiteList(): string[] {
    if (!this.patrimoine || !this.patrimoine.biodiversite) return [];
    return this.patrimoine.biodiversite.split(',').map((s: string) => s.trim()).filter((s: string) => s.length > 0);
  }

  getBiodiversiteImage(itemName: string): string {
    return this.mainImage || '/assets/images/patrimoine_1.jpg';
  }

  get isNaturel(): boolean {
    return ['SITE_NATUREL', 'PATRIMOINE_NATUREL'].includes(this.patrimoine?.type);
  }

  /** Repères historiques présentés sous forme de frise. */
  get timeline(): TimelineItem[] {
    const p = this.patrimoine || {};
    return [
      { titre: 'Histoire du lieu', texte: p.historique, icon: 'fa-solid fa-scroll' },
      { titre: 'Contexte historique', texte: p.contexteHistorique, icon: 'fa-solid fa-book' },
      { titre: 'Portée historique', texte: p.importanceHistorique, icon: 'fa-solid fa-monument' }
    ].filter(i => hasValue(i.texte));
  }

  get hasReconnaissance(): boolean {
    const p = this.patrimoine || {};
    return !!p.inscritUnesco || [p.classePatrimoine, p.protectionJuridique, p.organismeGestionnaire, p.gestionnaire, p.proprietaire].some(hasValue);
  }

  get hasSpecifique(): boolean {
    const p = this.patrimoine || {};
    if (p.type === 'MONUMENT') return [p.styleArchitectural, p.natureMonument, p.dimensions, p.anneeConstruction, p.architecte, p.commanditaire, p.materiauxConstruction, p.personnagesAssocies].some(hasValue);
    if (p.type === 'MUSEE') return [p.nombreCollections, p.nombreOeuvres, p.capaciteAccueil, p.museographie, p.collections].some(hasValue);
    if (this.isNaturel) return [p.superficie, p.natureSite, p.ecosysteme, p.biodiversite, p.especesProtegees, p.zoneProtegee].some(hasValue);
    return false;
  }

  get hasBonASavoir(): boolean {
    const a = this.patrimoine?.accessibilite;
    return !!a && (hasValue(a.conditionsAcces) || hasValue(a.restrictions));
  }

  /** Icône contextuelle pour chaque consigne du site. */
  regleIcon(r: string): string {
    const t = (r || '').toLowerCase();
    if (t.includes('déchet')) return 'fa-solid fa-recycle';
    if (t.includes('faune') || t.includes('flore')) return 'fa-solid fa-leaf';
    if (t.includes('guide')) return 'fa-solid fa-person-hiking';
    if (t.includes('sécurité') || t.includes('sentier')) return 'fa-solid fa-shoe-prints';
    if (t.includes('dégrad')) return 'fa-solid fa-hand';
    return 'fa-solid fa-circle-check';
  }
}
