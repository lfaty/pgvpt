import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-medias',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './form-medias.component.html',
  styleUrl: './form-medias.component.css'
})
export class FormMediasComponent {
  @Input() parentForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  get mediasArray(): FormArray {
    return this.parentForm.get('medias') as FormArray;
  }

  addMedia(): void {
    this.mediasArray.push(
      this.fb.group({
        type: ['PHOTO', Validators.required],
        nom: ['', Validators.required],
        description: [''],
        url: ['https://...', Validators.required], // Placeholder for actual upload URL
        auteur: [''],
        droitsUtilisation: [''],
        credit: [''],
        langue: [''],
        statut: ['VALIDE']
      })
    );
  }

  removeMedia(index: number): void {
    this.mediasArray.removeAt(index);
  }
}
