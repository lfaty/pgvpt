import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-form-caracteristiques',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form-caracteristiques.component.html'
})
export class FormCaracteristiquesComponent implements OnInit {
  @Input() parentForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    // Dynamically add form controls if they don't exist
    if (!this.parentForm.get('caracteristiques')) {
      this.parentForm.addControl('caracteristiques', this.fb.group({
        // SITE_NATUREL
        superficie: [''],
        natureSite: [''],
        ecosysteme: [''],
        especesProtegees: this.fb.array([]),

        // MUSEE
        nombreCollections: [null],
        collections: this.fb.array([]),
        expositions: this.fb.array([]),

        // MONUMENT
        styleArchitectural: [''],
        anneeConstruction: [''],
        identiteArchitecte: [''],
        natureMonument: [''],
        materiauxConstruction: this.fb.array([]),
        dimensions: [''],
        commanditaire: [''],
        contexteHistorique: [''],
        personnagesAssocies: this.fb.array([])
      }));
    }
  }

  // --- Chip Management ---
  getArray(name: string): any {
    return this.parentForm.get(`caracteristiques.${name}`);
  }

  addChip(event: Event, arrayName: string): void {
    event.preventDefault();
    const input = event.target as HTMLInputElement;
    const value = input.value.trim();
    if (value) {
      const arr = this.getArray(arrayName);
      if (arr) {
        arr.push(this.fb.control(value));
      }
      input.value = '';
    }
  }

  removeChip(arrayName: string, index: number): void {
    const arr = this.getArray(arrayName);
    if (arr) {
      arr.removeAt(index);
    }
  }

  // --- Complex Objects Management ---
  getFormArray(name: string): any {
    return this.parentForm.get(`caracteristiques.${name}`);
  }

  addEspeceProtegee(): void {
    const arr = this.getFormArray('especesProtegees');
    arr.push(this.fb.group({
      nomCommun: [''],
      nomScientifique: [''],
      type: ['FAUNE'],
      statutConservation: [''],
      description: ['']
    }));
  }

  addCollection(): void {
    const arr = this.getFormArray('collections');
    arr.push(this.fb.group({
      nom: [''],
      description: [''],
      nombreOeuvres: [null],
      themes: [''] // To keep it simple in UI, we'll store a comma separated string and split it in the service later, or just keep it simple
    }));
  }

  addExposition(): void {
    const arr = this.getFormArray('expositions');
    arr.push(this.fb.group({
      titre: [''],
      dateDebut: [''],
      dateFin: [''],
      expositionPermanente: [false],
      description: ['']
    }));
  }

  removeObjectItem(arrayName: string, index: number): void {
    const arr = this.getFormArray(arrayName);
    arr.removeAt(index);
  }

  get currentType(): string {
    return this.parentForm.get('type')?.value;
  }
}
