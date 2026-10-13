import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-accessibilite',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-accessibilite.component.html',
  styleUrl: './form-accessibilite.component.css'
})
export class FormAccessibiliteComponent {
  @Input() parentForm!: FormGroup;
}
