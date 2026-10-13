import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activite } from '../models/activite.model';

@Injectable({
  providedIn: 'root'
})
export class ActiviteFacadeService {
  private readonly mockUrl = 'assets/data/activites.json';

  constructor(private http: HttpClient) {}

  getAllActivites(): Observable<Activite[]> {
    return this.http.get<Activite[]>(this.mockUrl);
  }

  getActiviteById(id: string): Observable<Activite | undefined> {
    return new Observable(observer => {
      this.getAllActivites().subscribe({
        next: (activites) => {
          observer.next(activites.find(a => a.id === id));
          observer.complete();
        },
        error: (err) => observer.error(err)
      });
    });
  }
}
