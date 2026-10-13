import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-geolocalisation',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './form-geolocalisation.component.html',
  styleUrl: './form-geolocalisation.component.css'
})
export class FormGeolocalisationComponent {
  @Input() parentForm!: FormGroup;
}
