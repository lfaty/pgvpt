import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OffreTouristique } from '../models/offre-touristique.model';

@Injectable({
  providedIn: 'root'
})
export class OffreFacadeService {
  private readonly mockUrl = 'assets/data/offres-touristiques.json';

  constructor(private http: HttpClient) {}

  getAllOffres(): Observable<OffreTouristique[]> {
    return this.http.get<OffreTouristique[]>(this.mockUrl);
  }

  getOffreById(id: string): Observable<OffreTouristique | undefined> {
    return new Observable(observer => {
      this.getAllOffres().subscribe({
        next: (offres) => {
          observer.next(offres.find(o => o.id === id));
          observer.complete();
        },
        error: (err) => observer.error(err)
      });
    });
  }
}
