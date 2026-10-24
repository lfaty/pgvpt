import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-patrimoine-status-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './status-modal.component.html'
})
export class StatusModalComponent {
  @Input({ required: true }) saveStatus: 'idle' | 'saving' | 'success' | 'error' = 'idle';
  @Input() isEditMode: boolean = false;
  @Input() patrimoineId: string | null = null;
  @Input() savedNom: string = '';
  @Input() savedType: string = '';
  @Input() savedCode: string = '';
  @Input() errorMessage: string = '';

  @Output() close = new EventEmitter<void>();
  @Output() createAnother = new EventEmitter<void>();
  @Output() navigateToPatrimoines = new EventEmitter<void>();
  @Output() navigateToModule = new EventEmitter<string>();

  onClose(): void {
    this.close.emit();
  }

  onCreateAnother(): void {
    this.createAnother.emit();
  }

  onNavigateToPatrimoines(): void {
    this.navigateToPatrimoines.emit();
  }

  onNavigateTo(module: string): void {
    this.navigateToModule.emit(module);
  }
}
