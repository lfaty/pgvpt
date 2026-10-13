import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Reservation } from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationFacadeService {
  private readonly mockUrl = 'assets/data/reservations.json';
  
  // We use a BehaviorSubject to hold the current list of reservations in memory
  // so we can simulate adding a new reservation during the session
  private reservationsSubject = new BehaviorSubject<Reservation[]>([]);
  public reservations$ = this.reservationsSubject.asObservable();

  private isLoaded = false;

  constructor(private http: HttpClient) {}

  getAllReservations(): Observable<Reservation[]> {
    if (!this.isLoaded) {
      return this.http.get<Reservation[]>(this.mockUrl).pipe(
        tap(reservations => {
          this.reservationsSubject.next(reservations);
          this.isLoaded = true;
        })
      );
    }
    return this.reservations$;
  }

  createReservation(reservation: Reservation): Observable<Reservation> {
    // Simulate API call for creation
    return new Observable(observer => {
      setTimeout(() => {
        const currentList = this.reservationsSubject.getValue();
        this.reservationsSubject.next([reservation, ...currentList]);
        observer.next(reservation);
        observer.complete();
      }, 800); // simulate network delay
    });
  }
}
