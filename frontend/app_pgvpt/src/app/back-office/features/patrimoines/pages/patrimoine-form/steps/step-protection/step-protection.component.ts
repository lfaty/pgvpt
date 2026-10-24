import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TagInputComponent } from '../../../../../../../shared/components/tag-input/tag-input.component';

@Component({
  selector: 'app-step-protection',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, TagInputComponent],
  templateUrl: './step-protection.component.html'
})
export class StepProtectionComponent {
  @Input({ required: true }) form!: FormGroup;
  @Input() statuts: string[] = [];
  @Input() isEditMode: boolean = false;

  @Output() prev = new EventEmitter<void>();
  @Output() submitForm = new EventEmitter<void>();

  constructor(private fb: FormBuilder) {}

  get menacesArray(): FormArray {
    return this.form.get('menaces') as FormArray;
  }

  addMenace(): void {
    const m = this.fb.group({
      type: ['', Validators.required],
      niveau: ['MOYEN', Validators.required],
      description: [''],
      mesuresPrevention: ['']
    });
    this.menacesArray.push(m);
    setTimeout(() => {
      const idx = this.menacesArray.length - 1;
      const el = document.getElementById(`menace-item-${idx}`);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 60);
  }

  removeMenace(index: number): void {
    this.menacesArray.removeAt(index);
  }

  get horairesArray(): FormArray {
    return this.form.get('horaires') as FormArray;
  }

  addHoraire(): void {
    const h = this.fb.group({
      jour: ['LUNDI'],
      ouvert: [true],
      heureOuverture: [''],
      heureFermeture: [''],
      pauseDebut: [''],
      pauseFin: [''],
      surReservation: [false],
      commentaire: ['']
    });
    this.horairesArray.push(h);
    setTimeout(() => {
      const idx = this.horairesArray.length - 1;
      const el = document.getElementById(`horaire-item-${idx}`);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 60);
  }

  removeHoraire(index: number): void {
    this.horairesArray.removeAt(index);
  }

  get expositionsArray(): FormArray {
    return this.form.get('expositions') as FormArray;
  }

  addExposition(): void {
    const e = this.fb.group({
      titre: [''],
      description: [''],
      dateDebut: [''],
      dateFin: [''],
      commissaire: [''],
      lieu: [''],
      expositionPermanente: [false],
      imageMediaId: [null]
    });
    this.expositionsArray.push(e);
    setTimeout(() => {
      const idx = this.expositionsArray.length - 1;
      const el = document.getElementById(`exposition-item-${idx}`);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }, 60);
  }

  removeExposition(index: number): void {
    this.expositionsArray.removeAt(index);
  }

  onPrev(): void {
    this.prev.emit();
  }

  onSubmit(): void {
    this.submitForm.emit();
  }
}
