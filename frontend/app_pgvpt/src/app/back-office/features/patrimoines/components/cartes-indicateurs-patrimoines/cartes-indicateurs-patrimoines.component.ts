import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cartes-indicateurs-patrimoines',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cartes-indicateurs-patrimoines.component.html'
})
export class CartesIndicateursPatrimoinesComponent {
  @Input() totalCount: number = 0;
  @Input() publiesCount: number = 0;
  @Input() enValidationCount: number = 0;
  @Input() brouillonsCount: number = 0;
  @Input() selectedStatut: string = 'TOUS';

  @Output() statutChange = new EventEmitter<string>();

  onSelectStatut(statut: string): void {
    this.statutChange.emit(statut);
  }
}
