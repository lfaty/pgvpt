import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CircuitUI, CircuitFacadeService } from '../../../../../core/services/circuit-facade.service';

@Component({
  selector: 'app-section-circuits',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './section-circuits.component.html',
  styleUrl: './section-circuits.component.css'
})
export class SectionCircuitsComponent {
  @Input() circuits: CircuitUI[] = [];

  private circuitFacade = inject(CircuitFacadeService);

  formatDuree(minutes?: number): string {
    return this.circuitFacade.formatDuree(minutes);
  }
}
