import { Injectable } from '@angular/core';
import { MockService } from './mock.service';
import { Observable, forkJoin, map } from 'rxjs';
import { Evenement } from '../api/evenement/model/evenement';
import { SessionEvenement } from '../api/evenement/model/session-evenement';
import { PatrimoineUI } from '../models/patrimoine.ui';
import { PatrimoineFacadeService } from './patrimoine-facade.service';

export interface SessionEvenementUI extends SessionEvenement {
  evenementId?: string;
}

export interface EvenementUI extends Evenement {
  sessions?: SessionEvenementUI[];
  patrimoineDetail?: PatrimoineUI;
  imageUrl?: string;
}

@Injectable({
  providedIn: 'root'
})
export class EvenementFacadeService {
  constructor(
    private mockService: MockService,
    private patrimoineFacade: PatrimoineFacadeService
  ) {}

  getEvenements(): Observable<EvenementUI[]> {
    return forkJoin({
      evenements: this.mockService.getMockData<any[]>('evenements.json'),
      sessions: this.mockService.getMockData<SessionEvenementUI[]>('sessions-evenement.json'),
      patrimoines: this.patrimoineFacade.getPatrimoines()
    }).pipe(
      map(({ evenements, sessions, patrimoines }) => {
        return evenements.map(evt => {
          const evtSessions = sessions.filter(s => s.evenementId === evt.id);
          const patrimoine = evt.patrimoineId ? patrimoines.find(p => p.id === evt.patrimoineId) : undefined;
          
          return {
            ...evt,
            sessions: evtSessions,
            patrimoineDetail: patrimoine,
            imageUrl: evt.image // Map specific JSON property to UI model if needed
          } as EvenementUI;
        });
      })
    );
  }

  getEvenementDetail(id: string): Observable<EvenementUI | undefined> {
    return this.getEvenements().pipe(
      map(evenements => evenements.find(e => e.id === id))
    );
  }
}
