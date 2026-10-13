import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule, FormControl, FormArray } from '@angular/forms';

@Component({
  selector: 'app-form-valeur-patrimoniale',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form-valeur-patrimoniale.component.html'
})
export class FormValeurPatrimonialeComponent {
  @Input() parentForm!: FormGroup;

  getArray(name: string): any {
    return this.parentForm.get(name);
  }

  addChip(event: Event, arrayName: string): void {
    event.preventDefault();
    const input = event.target as HTMLInputElement;
    const value = input.value.trim();
    if (value) {
      const arr = this.getArray(arrayName) as FormArray;
      if (arr) {
        arr.push(new FormControl(value));
      }
      input.value = '';
    }
  }

  removeChip(arrayName: string, index: number): void {
    const arr = this.getArray(arrayName) as FormArray;
    if (arr) {
      arr.removeAt(index);
    }
  }

  // --- Logic for Languages ---
  showLangDropdown = false;

  availableLanguages = [
    'Français',
    'Wolof',
    'Pulaar',
    'Sérère',
    'Diola',
    'Mandingue',
    'Anglais',
    'Portugais',
    'Espagnol',
    'Arabe'
  ];

  addLanguage(langName: string): void {
    const arr = this.getArray('langues') as FormArray;
    if (arr && !arr.value.includes(langName)) {
      arr.push(new FormControl(langName));
    }
    this.showLangDropdown = false;
  }
}
