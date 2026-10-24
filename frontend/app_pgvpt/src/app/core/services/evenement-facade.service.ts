import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin, map, of, catchError } from 'rxjs';
import { PatrimoineUI } from '../models/patrimoine.ui';
import { PatrimoineFacadeService } from './patrimoine-facade.service';

export interface SessionEvenementUI {
  id?: string;
  evenementId?: string;
  dateDebut?: string;
  dateFin?: string;
  capaciteMax?: number;
  placesReservees?: number;
  statut?: string;
}

export interface EvenementUI {
  id?: string;
  titre?: string;
  nom?: string;
  description?: string;
  patrimoineId?: string;
  type?: string;
  statut?: string;
  dateDebut?: string;
  dateFin?: string;
  sessions?: SessionEvenementUI[];
  patrimoineDetail?: PatrimoineUI;
  imageUrl?: string;
}

@Injectable({
  providedIn: 'root'
})
export class EvenementFacadeService {
  private apiUrl = '/api/v1/evenements';

  constructor(
    private http: HttpClient,
    private patrimoineFacade: PatrimoineFacadeService
  ) {}

  getEvenements(): Observable<EvenementUI[]> {
    return forkJoin({
      evenements: this.http.get<any>(this.apiUrl).pipe(
        map(res => Array.isArray(res) ? res : (res?.content || [])),
        catchError(() => of([]))
      ),
      patrimoines: this.patrimoineFacade.getPatrimoines().pipe(catchError(() => of([])))
    }).pipe(
      map(({ evenements, patrimoines }) => {
        return evenements.map((evt: any) => {
          const patrimoine = evt.patrimoineId ? patrimoines.find(p => p.id === evt.patrimoineId) : undefined;
          
          return {
            ...evt,
            sessions: evt.sessions || [],
            patrimoineDetail: patrimoine,
            imageUrl: evt.imageUrl || evt.image || 'assets/images/patrimoine_1.jpg'
          } as EvenementUI;
        });
      })
    );
  }

  getEvenementDetail(id: string): Observable<EvenementUI | undefined> {
    return this.http.get<EvenementUI>(`${this.apiUrl}/${id}`).pipe(
      catchError(() => of(undefined))
    );
  }
}
