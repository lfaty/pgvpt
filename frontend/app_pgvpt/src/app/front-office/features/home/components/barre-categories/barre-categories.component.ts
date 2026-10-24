import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-barre-categories',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './barre-categories.component.html',
  styleUrl: './barre-categories.component.css'
})
export class BarreCategoriesComponent {}
