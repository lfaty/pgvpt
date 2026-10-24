import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { decouverteTitre, hasValue, humanize } from '../../patrimoine-display.utils';

interface Fact { label: string; value: string; icon: string; }
interface Highlight { titre: string; texte: string; icon: string; accent: string; }

@Component({
  selector: 'app-apercu-patrimoine',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './apercu-patrimoine.component.html'
})
export class ApercuPatrimoineComponent {
  @Input() patrimoine: any;
  @Input() geolocalisation: any;
  @Input() mainImage: string | undefined;

  descriptionOuverte = false;
  readonly DESCRIPTION_LIMIT = 420;

  get titre(): string {
    return decouverteTitre(this.patrimoine?.nom);
  }

  /** Chiffres clés « En un coup d'œil » — uniquement les valeurs significatives (pas de statut interne). */
  get facts(): Fact[] {
    const p = this.patrimoine || {};
    const facts: Fact[] = [
      { label: 'Type de site', value: humanize(p.type), icon: 'fa-solid fa-mountain-sun' },
      { label: 'Catégorie', value: humanize(p.categorie), icon: 'fa-solid fa-layer-group' },
      { label: 'Spécificité', value: humanize(p.sousCategorie), icon: 'fa-solid fa-tag' }
    ];
    if (hasValue(p.periode) || hasValue(p.siecle)) {
      const epoque = [humanize(p.periode), hasValue(p.siecle) ? `${p.siecle}e siècle` : ''].filter(Boolean).join(' · ');
      facts.push({ label: 'Époque', value: epoque, icon: 'fa-solid fa-hourglass-half' });
    }
    if (hasValue(p.superficie)) facts.push({ label: 'Superficie', value: `${p.superficie} ha`, icon: 'fa-solid fa-expand' });
    if (hasValue(p.anneeConstruction)) facts.push({ label: 'Construction', value: String(p.anneeConstruction), icon: 'fa-solid fa-trowel-bricks' });
    if (p.inscritUnesco) facts.push({ label: 'Reconnaissance', value: 'UNESCO', icon: 'fa-solid fa-award' });
    return facts.filter(f => hasValue(f.value));
  }

  /** Les raisons de visiter, présentées comme des cartes éditoriales. */
  get highlights(): Highlight[] {
    const p = this.patrimoine || {};
    const items: Highlight[] = [
      { titre: 'Pourquoi y aller', texte: p.importanceTouristique, icon: 'fa-solid fa-person-hiking', accent: '#D16933' },
      { titre: 'Valeur patrimoniale', texte: p.valeurPatrimoniale, icon: 'fa-solid fa-gem', accent: '#087F5B' },
      { titre: 'Culture & communautés', texte: p.importanceCulturelle, icon: 'fa-solid fa-people-group', accent: '#7C3AED' },
      { titre: 'Dimension spirituelle', texte: p.valeurSpirituelle, icon: 'fa-solid fa-hands-praying', accent: '#C9A227' }
    ];
    return items.filter(i => hasValue(i.texte));
  }

  get descriptionLongue(): boolean {
    return (this.patrimoine?.description || '').length > this.DESCRIPTION_LIMIT;
  }

  toList(v: any): string[] {
    if (!hasValue(v)) return [];
    return Array.isArray(v) ? v.filter(hasValue) : [String(v)];
  }
}
