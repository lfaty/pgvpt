import { Component, OnInit, OnDestroy, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { CircuitFacadeService, CircuitUI } from '../../../../../core/services/circuit-facade.service';

import { CircuitHeroComponent } from './components/circuit-hero/circuit-hero.component';
import { CircuitMapComponent } from './components/circuit-map/circuit-map.component';
import { CircuitTimelineComponent } from './components/circuit-timeline/circuit-timeline.component';
import { CircuitSidebarComponent } from './components/circuit-sidebar/circuit-sidebar.component';

@Component({
  selector: 'app-circuit-detail',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule,
    CircuitHeroComponent,
    CircuitMapComponent,
    CircuitTimelineComponent,
    CircuitSidebarComponent
  ],
  templateUrl: './circuit-detail.component.html',
  styleUrls: ['./circuit-detail.component.css']
})
export class CircuitDetailComponent implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private circuitFacade = inject(CircuitFacadeService);

  @ViewChild(CircuitMapComponent) circuitMap?: CircuitMapComponent;

  circuit?: CircuitUI;
  isLoading: boolean = true;
  hasError: boolean = false;
  errorMessage: string = '';

  activeStepIndex: number = 0;
  private sub?: Subscription;

  ngOnInit(): void {
    this.sub = this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.chargerCircuit(id);
      } else {
        this.hasError = true;
        this.errorMessage = 'Identifiant de circuit manquant.';
        this.isLoading = false;
      }
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  chargerCircuit(id: string): void {
    this.isLoading = true;
    this.hasError = false;

    this.circuitFacade.getCircuitById(id).subscribe({
      next: (data) => {
        if (!data) {
          this.hasError = true;
          this.errorMessage = 'Circuit non trouvé.';
          this.isLoading = false;
          return;
        }

        this.circuit = data;
        this.corrigerCoordonneesEtapes();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement circuit:', err);
        this.hasError = true;
        this.errorMessage = 'Impossible de contacter le service de circuits.';
        this.isLoading = false;
      }
    });
  }

  private corrigerCoordonneesEtapes(): void {
    if (!this.circuit || !this.circuit.etapes) return;

    this.circuit.etapes.forEach(etape => {
      if (!etape.latitude && etape.patrimoineDetail?.latitude) {
        etape.latitude = etape.patrimoineDetail.latitude;
      }
      if (!etape.longitude && etape.patrimoineDetail?.longitude) {
        etape.longitude = etape.patrimoineDetail.longitude;
      }
    });
  }

  onLocateStep(index: number): void {
    this.activeStepIndex = index;
    this.circuitMap?.centrerEtape(index);
  }
}
