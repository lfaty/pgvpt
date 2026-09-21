import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PatrimoineUI } from '../../../../core/models/patrimoine.ui';
@Component({
  selector: 'app-patrimoine-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './patrimoine-card.component.html',
  styleUrl: './patrimoine-card.component.css'
})
export class PatrimoineCardComponent {
  @Input() patrimoine!: PatrimoineUI;
}
