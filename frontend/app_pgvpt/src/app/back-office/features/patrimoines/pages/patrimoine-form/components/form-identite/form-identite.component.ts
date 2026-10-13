import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-identite',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form-identite.component.html'
})
export class FormIdentiteComponent {
  @Input() parentForm!: FormGroup;
  @Input() sousCategories: string[] = [];

  categories = [
    { value: 'PATRIMOINE_NATUREL', label: 'Patrimoine Naturel' },
    { value: 'PATRIMOINE_HISTORIQUE', label: 'Patrimoine Historique' },
    { value: 'PATRIMOINE_CULTUREL', label: 'Patrimoine Culturel' },
    { value: 'PATRIMOINE_ARCHITECTURAL', label: 'Patrimoine Architectural' },
    { value: 'PATRIMOINE_RELIGIEUX', label: 'Patrimoine Religieux' },
    { value: 'PATRIMOINE_COLONIAL', label: 'Patrimoine Colonial' },
    { value: 'PATRIMOINE_ARCHEOLOGIQUE', label: 'Patrimoine Archéologique' },
    { value: 'PATRIMOINE_MUSEAL', label: 'Patrimoine Muséal' },
    { value: 'PATRIMOINE_MEMORIEL', label: 'Patrimoine Mémoriel' },
    { value: 'PATRIMOINE_MARITIME', label: 'Patrimoine Maritime' },
    { value: 'PATRIMOINE_RURAL', label: 'Patrimoine Rural' },
    { value: 'AUTRE', label: 'Autre' }
  ];

  periodes = [
    { value: 'PREHISTORIQUE', label: 'Préhistorique' },
    { value: 'ANTIQUITE', label: 'Antiquité' },
    { value: 'MEDIEVALE', label: 'Médiévale' },
    { value: 'PRECOLONIALE', label: 'Précoloniale' },
    { value: 'COLONIALE', label: 'Coloniale' },
    { value: 'POSTCOLONIALE', label: 'Postcoloniale' },
    { value: 'CONTEMPORAINE', label: 'Contemporaine' },
    { value: 'INDETERMINEE', label: 'Indéterminée' }
  ];

  get isNaturel(): boolean {
    return this.parentForm?.get('type')?.value === 'SITE_NATUREL';
  }
}
