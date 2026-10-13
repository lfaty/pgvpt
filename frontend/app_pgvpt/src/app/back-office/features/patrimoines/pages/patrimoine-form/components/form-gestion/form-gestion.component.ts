import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-gestion',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-gestion.component.html',
  styleUrl: './form-gestion.component.css'
})
export class FormGestionComponent {
  @Input() parentForm!: FormGroup;
}
