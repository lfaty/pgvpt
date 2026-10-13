import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ContenuApiService {
  private apiUrl = '/api/v1/contenus';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getByPatrimoineId(patrimoineId: string): Observable<any[]> {
    const params = new HttpParams().set('patrimoineId', patrimoineId);
    return this.http.get<any[]>(this.apiUrl, { params });
  }

  getById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
  // 🚀 Appel POST vers la Gateway
  create(payload: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, payload);
  }
    // ✏️ Mise à jour (PUT)
  update(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, payload);
  }

  // 🗑️ Suppression (DELETE)
  delete(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

}
