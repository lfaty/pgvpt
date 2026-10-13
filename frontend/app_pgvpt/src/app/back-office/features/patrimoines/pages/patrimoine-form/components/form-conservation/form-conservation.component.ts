import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-conservation',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-conservation.component.html',
  styleUrl: './form-conservation.component.css'
})
export class FormConservationComponent {
  @Input() parentForm!: FormGroup;
}
