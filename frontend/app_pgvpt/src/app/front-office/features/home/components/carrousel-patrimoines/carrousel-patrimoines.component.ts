import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-carrousel-patrimoines',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './carrousel-patrimoines.component.html',
  styleUrl: './carrousel-patrimoines.component.css'
})
export class CarrouselPatrimoinesComponent {
  @Input() categoriesGroups: { categoryId: string; label: string; items: PatrimoineUI[] }[] = [];

  getCategoryTheme(categoryId: string): { bgClass: string, badgeClass: string } {
    const themes: { [key: string]: { bgClass: string, badgeClass: string } } = {
      'PATRIMOINE_CULTUREL': { bgClass: 'group-carousel--amber', badgeClass: 'mini-card-type-badge--amber' },
      'PATRIMOINE_NATUREL': { bgClass: 'group-carousel--green', badgeClass: 'mini-card-type-badge--green' },
      'PATRIMOINE_MEMORIEL': { bgClass: 'group-carousel--red', badgeClass: 'mini-card-type-badge--red' },
      'PATRIMOINE_COLONIAL': { bgClass: 'group-carousel--blue', badgeClass: 'mini-card-type-badge--blue' },
      'PATRIMOINE_IMMATERIEL': { bgClass: 'group-carousel--purple', badgeClass: 'mini-card-type-badge--purple' }
    };
    return themes[categoryId] || { bgClass: 'group-carousel--amber', badgeClass: 'mini-card-type-badge--amber' };
  }

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
}
