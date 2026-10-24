import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule } from '@angular/forms';
import { TagInputComponent } from '../../../../../../../shared/components/tag-input/tag-input.component';

@Component({
  selector: 'app-step-caracteristiques',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TagInputComponent],
  templateUrl: './step-caracteristiques.component.html'
})
export class StepCaracteristiquesComponent {
  @Input({ required: true }) form!: FormGroup;
  @Output() next = new EventEmitter<void>();
  @Output() prev = new EventEmitter<void>();
  @Output() goToStep = new EventEmitter<number>();

  constructor(private fb: FormBuilder) {}

  get collectionsArray(): FormArray {
    return this.form.get('collections') as FormArray;
  }

  addCollection(): void {
    const c = this.fb.group({
      nom: [''],
      description: [''],
      nombreOeuvres: [null],
      periode: [''],
      origine: [''],
      themes: ['']
    });
    this.collectionsArray.push(c);
    setTimeout(() => {
      const idx = this.collectionsArray.length - 1;
      const el = document.getElementById(`collection-item-${idx}`);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 60);
  }

  removeCollection(index: number): void {
    this.collectionsArray.removeAt(index);
  }

  get especesProtegeesArray(): FormArray {
    return this.form.get('especesProtegees') as FormArray;
  }

  addEspeceProtegee(): void {
    const ep = this.fb.group({
      nomCommun: [''],
      nomScientifique: [''],
      nomLocal: [''],
      type: ['FAUNE'],
      statutConservation: [''],
      niveauProtection: [''],
      description: [''],
      photoMediaId: [null]
    });
    this.especesProtegeesArray.push(ep);
    setTimeout(() => {
      const idx = this.especesProtegeesArray.length - 1;
      const el = document.getElementById(`espece-item-${idx}`);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 60);
  }

  removeEspeceProtegee(index: number): void {
    this.especesProtegeesArray.removeAt(index);
  }

  onPrev(): void {
    this.prev.emit();
  }

  onNext(): void {
    this.next.emit();
  }

  onGoToStep(step: number): void {
    this.goToStep.emit(step);
  }
}
