import { Component, Input, Output, EventEmitter, ElementRef, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-visualiseur-media',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './visualiseur-media.component.html'
})
export class VisualiseurMediaComponent {
  private sanitizer = inject(DomSanitizer);

  @Input() media: any = null;
  @Input() zoomLevel: number = 1;
  @Input() isFullscreen: boolean = false;

  @Output() zoomInEvent = new EventEmitter<void>();
  @Output() zoomOutEvent = new EventEmitter<void>();
  @Output() resetZoomEvent = new EventEmitter<void>();
  @Output() toggleFullscreenEvent = new EventEmitter<HTMLElement>();

  @ViewChild('viewerStage') viewerStage!: ElementRef<HTMLElement>;

  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  zoomIn(): void {
    this.zoomInEvent.emit();
  }

  zoomOut(): void {
    this.zoomOutEvent.emit();
  }

  resetZoom(): void {
    this.resetZoomEvent.emit();
  }

  toggleFullscreen(): void {
    if (this.viewerStage) {
      this.toggleFullscreenEvent.emit(this.viewerStage.nativeElement);
    }
  }
}
