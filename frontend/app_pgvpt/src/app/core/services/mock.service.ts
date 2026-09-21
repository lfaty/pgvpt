import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MockService {

  constructor(private http: HttpClient) { }

  /**
   * Charge un fichier JSON statique depuis le dossier assets/data
   * @param filename Nom du fichier (ex: 'patrimoines.json')
   */
  public getMockData<T>(filename: string): Observable<T> {
    return this.http.get<T>(`/assets/data/${filename}`);
  }
}
