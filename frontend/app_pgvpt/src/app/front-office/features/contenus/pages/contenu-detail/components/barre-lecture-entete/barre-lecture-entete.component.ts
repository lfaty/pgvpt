import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-barre-lecture-entete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './barre-lecture-entete.component.html'
})
export class BarreLectureEnteteComponent {
  @Input() contenu: any = null;
  @Input() patrimoine: any = null;
  @Input() fontSizeMode: 'normal' | 'large' = 'normal';

  @Output() toggleFontSizeEvent = new EventEmitter<void>();

  toggleFontSize(): void {
    this.toggleFontSizeEvent.emit();
  }
}
