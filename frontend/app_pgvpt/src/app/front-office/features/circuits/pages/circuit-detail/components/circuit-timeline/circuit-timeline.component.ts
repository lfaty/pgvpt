import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EtapeCircuitDetail } from '../../../../../../../core/services/circuit-facade.service';

@Component({
  selector: 'app-circuit-timeline',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './circuit-timeline.component.html',
  styleUrls: ['./circuit-timeline.component.css']
})
export class CircuitTimelineComponent {
  @Input() etapes: EtapeCircuitDetail[] = [];
  @Input() activeStepIndex: number = 0;
  @Output() locateStep = new EventEmitter<number>();

  formatDureeEtape(minutes?: number): string {
    if (!minutes) return 'Visite libre';
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    if (h > 0 && m > 0) return `${h}h ${m}min`;
    if (h > 0) return `${h}h`;
    return `${m}min`;
  }

  onLocate(index: number): void {
    this.locateStep.emit(index);
  }
}
