import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-corps-article',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './corps-article.component.html'
})
export class CorpsArticleComponent {
  @Input() contenu: any = null;
  @Input() patrimoine: any = null;
  @Input() associatedMedias: any[] = [];
  @Input() fontSizeMode: 'normal' | 'large' = 'normal';

  @Output() openLightboxEvent = new EventEmitter<any>();

  openLightbox(m: any): void {
    this.openLightboxEvent.emit(m);
  }
}
