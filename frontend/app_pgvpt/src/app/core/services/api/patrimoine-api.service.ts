import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PatrimoineApiService {

  // On définit l'adresse du backend (relative pour utiliser le proxy.conf.json)
  private apiUrl = '/api/v1/patrimoines';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any[]> {
    // Fait un appel GET sur /api/v1/patrimoines et extrait le tableau "content" de la pagination Spring
    return this.http.get<any>(this.apiUrl).pipe(
      map(response => response.content || [])
    );
  }

  // Recherche avec filtres et pagination
  search(params: any): Observable<any> {
    let httpParams = new HttpParams();
    
    // Ajout dynamique des paramètres non vides
    Object.keys(params).forEach(key => {
      if (params[key] !== null && params[key] !== undefined && params[key] !== '' && params[key] !== 'TOUS' && params[key] !== 'TOUTES') {
        httpParams = httpParams.set(key, params[key]);
      }
    });

    return this.http.get<any>(this.apiUrl, { params: httpParams });
  }

  getById(id: string): Observable<any> {
    // Fait un appel GET sur http://localhost:8080/api/v1/patrimoines/{id}
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // 3. Créer un nouveau patrimoine (Envoi via POST)
  create(payload: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, payload);
  }

  // 4. Mettre à jour un patrimoine existant (Envoi via PUT)
  update(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, payload);
  }

  // 5. Mettre à jour spécifiquement la photo principale (Hero Image)
  updatePhotoPrincipale(id: string, photoData: { nomPhotoPrincipale: string, urlPhotoPrincipale: string }): Observable<any> {
    // API endpoint based on PhotosApi contract: /api/v1/patrimoines/{id}/photo
    return this.http.put<any>(`${this.apiUrl}/${id}/photo`, photoData);
  }

  // Supprimer un patrimoine
  delete(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

}
