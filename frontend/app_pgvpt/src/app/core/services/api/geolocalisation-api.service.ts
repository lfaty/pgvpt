import { Injectable } from '@angular/core';
import { Observable, delay, map } from 'rxjs';
import { MockService } from '../mock.service';

@Injectable({
  providedIn: 'root'
})
export class GeolocalisationApiService {
  constructor(private mockService: MockService) {}

  getAll(): Observable<any[]> {
    return this.mockService.getMockData<any[]>('geolocalisations.json').pipe(delay(200));
  }

  getByPatrimoineId(patrimoineId: string): Observable<any[]> {
    return this.getAll().pipe(
      map(geos => geos.filter(g => g.patrimoineId === patrimoineId))
    );
  }
}
