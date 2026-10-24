import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-visionneuse-plein-ecran',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './visionneuse-plein-ecran.component.html'
})
export class VisionneusePleinEcranComponent {
  @Input() isOpen = false;
  @Input() medias: any[] = [];
  @Input() currentIndex = 0;

  @Output() close = new EventEmitter<void>();
  @Output() next = new EventEmitter<Event>();
  @Output() prev = new EventEmitter<Event>();

  onClose(): void {
    this.close.emit();
  }

  onNext(event: Event): void {
    this.next.emit(event);
  }

  onPrev(event: Event): void {
    this.prev.emit(event);
  }
}
