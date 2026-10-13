import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule, FormArray, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-form-horaires',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form-horaires.component.html',
  styleUrl: './form-horaires.component.css'
})
export class FormHorairesComponent implements OnInit {
  @Input() parentForm!: FormGroup;

  joursSemaine = [
    { code: 'LUNDI', label: 'Lundi' },
    { code: 'MARDI', label: 'Mardi' },
    { code: 'MERCREDI', label: 'Mercredi' },
    { code: 'JEUDI', label: 'Jeudi' },
    { code: 'VENDREDI', label: 'Vendredi' },
    { code: 'SAMEDI', label: 'Samedi' },
    { code: 'DIMANCHE', label: 'Dimanche' }
  ];

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    const horairesArray = this.parentForm.get('horaires') as FormArray;
    
    // Initialiser les 7 jours de la semaine si le tableau est vide
    if (horairesArray.length === 0) {
      this.joursSemaine.forEach(jour => {
        horairesArray.push(this.fb.group({
          jour: [jour.code],
          ouvert: [true],
          heureOuverture: ['09:00'],
          heureFermeture: ['18:00'],
          pauseDebut: [''],
          pauseFin: [''],
          surReservation: [false],
          commentaire: ['']
        }));
      });
    }
  }

  get horairesFormArray() {
    return this.parentForm.get('horaires') as FormArray;
  }
}
