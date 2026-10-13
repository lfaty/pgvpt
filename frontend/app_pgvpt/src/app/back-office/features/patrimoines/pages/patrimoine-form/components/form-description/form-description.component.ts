import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-description',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-description.component.html',
  styleUrl: './form-description.component.css'
})
export class FormDescriptionComponent {
  @Input() parentForm!: FormGroup;
}
