import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-step-identification',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './step-identification.component.html'
})
export class StepIdentificationComponent {
  @Input({ required: true }) form!: FormGroup;
  @Input() categories: string[] = [];
  @Output() next = new EventEmitter<void>();

  onNext(): void {
    this.next.emit();
  }
}
