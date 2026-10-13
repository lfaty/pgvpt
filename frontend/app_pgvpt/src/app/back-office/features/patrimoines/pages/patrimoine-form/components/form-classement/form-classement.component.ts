import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-classement',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form-classement.component.html',
  styleUrl: './form-classement.component.css'
})
export class FormClassementComponent {
  @Input() parentForm!: FormGroup;
}
