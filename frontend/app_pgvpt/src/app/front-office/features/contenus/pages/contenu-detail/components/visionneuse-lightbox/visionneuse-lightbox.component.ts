import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-visionneuse-lightbox',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './visionneuse-lightbox.component.html'
})
export class VisionneuseLightboxComponent {
  @Input() lightboxOpen: boolean = false;
  @Input() selectedLightboxMedia: any = null;

  @Output() closeLightboxEvent = new EventEmitter<void>();

  closeLightbox(): void {
    this.closeLightboxEvent.emit();
  }
}
