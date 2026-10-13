import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, switchMap, forkJoin, of } from 'rxjs';
import { Circuit } from '../api/circuit/model/circuit';
import { EtapeCircuit } from '../api/circuit/model/etape-circuit';
import { PatrimoineFacadeService } from './patrimoine-facade.service';

export interface EtapeCircuitDetail extends EtapeCircuit {
  patrimoineDetail?: any; // The full patrimoine object
}

export interface CircuitUI extends Circuit {
  etapes?: EtapeCircuitDetail[];
  imageUrl?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CircuitFacadeService {

  private circuitsUrl = 'assets/data/circuits.json';
  private etapesUrl = 'assets/data/etapes-circuit.json';

  constructor(
    private http: HttpClient,
    private patrimoineFacade: PatrimoineFacadeService
  ) { }

  /**
   * Retrieves all circuits with a preview image (from first step or fallback)
   */
  getCircuits(): Observable<CircuitUI[]> {
    return this.http.get<Circuit[]>(this.circuitsUrl).pipe(
      switchMap(circuits => {
        const observables = circuits.map(c => 
          this.getCircuitDetail(c.id!).pipe(
            map(detail => {
              if (detail && detail.etapes && detail.etapes.length > 0 && detail.etapes[0].patrimoineDetail?.photos) {
                return { ...c, imageUrl: detail.etapes[0].patrimoineDetail.photos[0].url } as CircuitUI;
              }
              // Fallback image
              return { ...c, imageUrl: 'assets/images/circuit_1.jpg' } as CircuitUI;
            })
          )
        );
        return forkJoin(observables);
      })
    );
  }

  /**
   * Retrieves a single circuit by its ID (basic info)
   */
  getCircuitById(id: string): Observable<Circuit | undefined> {
    return this.http.get<Circuit[]>(this.circuitsUrl).pipe(
      map(circuits => circuits.find(c => c.id === id))
    );
  }

  /**
   * Retrieves steps (étapes) for a specific circuit ID
   */
  getEtapesByCircuitId(circuitId: string): Observable<EtapeCircuit[]> {
    return this.http.get<EtapeCircuit[]>(this.etapesUrl).pipe(
      map(etapes => etapes.filter(e => e.circuitId === circuitId).sort((a, b) => a.ordre - b.ordre))
    );
  }

  /**
   * Retrieves a full circuit with its steps, and joins the Patrimoine data for each step.
   */
  getCircuitDetail(id: string): Observable<CircuitUI | undefined> {
    return this.getCircuitById(id).pipe(
      switchMap(circuit => {
        if (!circuit) return of(undefined);
        
        return this.getEtapesByCircuitId(id).pipe(
          switchMap(etapes => {
            if (etapes.length === 0) {
              const detail: CircuitUI = { ...circuit, etapes: [] };
              return of(detail);
            }

            // Fetch patrimoine detail for each etape
            const etapesObservables = etapes.map(etape => 
              this.patrimoineFacade.getPatrimoineById(etape.patrimoineId).pipe(
                map(patrimoine => ({
                  ...etape,
                  patrimoineDetail: patrimoine
                } as EtapeCircuitDetail))
              )
            );

            return forkJoin(etapesObservables).pipe(
              map(etapesDetails => ({
                ...circuit,
                etapes: etapesDetails,
                imageUrl: etapesDetails[0]?.patrimoineDetail?.photos?.[0]?.url || 'assets/images/circuit_1.jpg'
              } as CircuitUI))
            );
          })
        );
      })
    );
  }
}
