import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, of, catchError } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { Reservation } from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationFacadeService {
  private readonly apiUrl = '/api/v1/reservations';
  
  private reservationsSubject = new BehaviorSubject<Reservation[]>([]);
  public reservations$ = this.reservationsSubject.asObservable();

  private isLoaded = false;

  constructor(private http: HttpClient) {}

  getAllReservations(): Observable<Reservation[]> {
    if (!this.isLoaded) {
      return this.http.get<any>(this.apiUrl).pipe(
        map(res => Array.isArray(res) ? res : (res?.content || [])),
        tap(reservations => {
          this.reservationsSubject.next(reservations);
          this.isLoaded = true;
        }),
        catchError(() => of([]))
      );
    }
    return this.reservations$;
  }

  createReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation).pipe(
      tap(created => {
        const currentList = this.reservationsSubject.getValue();
        this.reservationsSubject.next([created, ...currentList]);
      })
    );
  }
}
