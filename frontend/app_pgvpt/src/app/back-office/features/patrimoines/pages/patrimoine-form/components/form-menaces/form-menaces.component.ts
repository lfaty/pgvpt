import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-menaces',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './form-menaces.component.html',
  styleUrl: './form-menaces.component.css'
})
export class FormMenacesComponent {
  @Input() parentForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  get menacesArray(): FormArray {
    return this.parentForm.get('menaces') as FormArray;
  }

  addMenace(): void {
    const menaceGroup = this.fb.group({
      type: ['', Validators.required],
      niveau: ['MOYEN', Validators.required],
      description: [''],
      mesuresPrevention: ['']
    });
    this.menacesArray.push(menaceGroup);
  }

  removeMenace(index: number): void {
    this.menacesArray.removeAt(index);
  }
}
