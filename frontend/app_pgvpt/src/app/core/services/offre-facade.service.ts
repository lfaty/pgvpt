import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, of, catchError } from 'rxjs';
import { OffreTouristique } from '../models/offre-touristique.model';

@Injectable({
  providedIn: 'root'
})
export class OffreFacadeService {
  private readonly apiUrl = '/api/v1/offres';

  constructor(private http: HttpClient) {}

  getAllOffres(): Observable<OffreTouristique[]> {
    return this.http.get<any>(this.apiUrl).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  getOffreById(id: string): Observable<OffreTouristique | undefined> {
    return this.http.get<OffreTouristique>(`${this.apiUrl}/${id}`).pipe(
      catchError(() => of(undefined))
    );
  }
}
