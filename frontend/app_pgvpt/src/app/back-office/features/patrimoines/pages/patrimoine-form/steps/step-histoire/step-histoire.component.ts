import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TagInputComponent } from '../../../../../../../shared/components/tag-input/tag-input.component';

@Component({
  selector: 'app-step-histoire',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TagInputComponent],
  templateUrl: './step-histoire.component.html'
})
export class StepHistoireComponent {
  @Input({ required: true }) form!: FormGroup;
  @Output() next = new EventEmitter<void>();
  @Output() prev = new EventEmitter<void>();

  onPrev(): void {
    this.prev.emit();
  }

  onNext(): void {
    this.next.emit();
  }
}
