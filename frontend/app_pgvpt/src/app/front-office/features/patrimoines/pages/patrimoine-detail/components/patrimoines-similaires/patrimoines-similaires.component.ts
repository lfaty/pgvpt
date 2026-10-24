import { Component, Input, ViewChild, ElementRef, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PatrimoineCardComponent } from '../../../../components/patrimoine-card/patrimoine-card.component';
import { PatrimoineUI } from '../../../../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-patrimoines-similaires',
  standalone: true,
  imports: [CommonModule, RouterLink, PatrimoineCardComponent],
  templateUrl: './patrimoines-similaires.component.html'
})
export class PatrimoinesSimilairesComponent implements AfterViewInit, OnDestroy {
  @Input() similaires: PatrimoineUI[] = [];

  @ViewChild('similarCarousel') similarCarousel!: ElementRef<HTMLElement>;
  private autoScrollInterval: any;

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.startAutoScroll();
    }, 1000);
  }

  ngOnDestroy(): void {
    this.pauseAutoScroll();
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
}
