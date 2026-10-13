import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, forkJoin, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MediaApiService {
  
  private apiUrl = '/api/v1/medias';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getByPatrimoineId(patrimoineId: string): Observable<any[]> {
    let params = new HttpParams().set('patrimoineId', patrimoineId);
    return this.http.get<any[]>(this.apiUrl, { params });
  }

  getById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createMedia(media: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, media);
  }

  updateMedia(id: string, media: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, media);
  }

  saveMediasBatch(medias: any[]): Observable<any[]> {
    if (!medias || medias.length === 0) {
      return of([]);
    }
    const requests = medias.map(m => {
       const copy = { ...m };
       
       // On ne sauvegarde jamais une URL "blob:" en base de données, c'est éphémère !
       // On simule donc un chemin serveur valide pour l'API (java.net.URI)
       if (copy.url && copy.url.startsWith('blob:')) {
          const extension = copy.type === 'VIDEO' ? '.mp4' : (copy.type === 'DOCUMENT' ? '.pdf' : '.jpg');
          copy.url = `http://localhost:8080/assets/uploads/simulated_${Date.now()}${extension}`;
       }

       const id = copy.id;
       if (id && id.length >= 36) {
          // UUID existant => UPDATE
          delete copy.id; 
          return this.updateMedia(id, copy);
       } else {
          // Temporaire => CREATE
          delete copy.id;
          return this.createMedia(copy);
       }
    });
    return forkJoin(requests);
  }

  deleteMedia(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
