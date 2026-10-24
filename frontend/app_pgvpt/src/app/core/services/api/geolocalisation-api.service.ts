import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map, of, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeolocalisationApiService {
  private apiUrl = '/api/v1/geolocalisations';

  constructor(private http: HttpClient) {}

  getAll(departement?: string, commune?: string): Observable<any[]> {
    let params = new HttpParams();
    if (departement) params = params.set('departement', departement);
    if (commune) params = params.set('commune', commune);

    return this.http.get<any>(this.apiUrl, { params }).pipe(
      map(res => {
        if (Array.isArray(res)) return res;
        if (res && Array.isArray(res.content)) return res.content;
        return [];
      }),
      catchError(() => of([]))
    );
  }

  getByPatrimoineId(patrimoineId: string): Observable<any[]> {
    return this.http.get<any>(`${this.apiUrl}/patrimoines/${patrimoineId}/geolocalisation`).pipe(
      map(geo => geo ? [geo] : []),
      catchError(() => of([]))
    );
  }

  getZoneTouristiqueById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/zones-touristiques/${id}`).pipe(catchError(() => of(null)));
  }

  getVillageById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/villages/${id}`).pipe(catchError(() => of(null)));
  }

  getCommuneById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/communes/${id}`).pipe(catchError(() => of(null)));
  }

  getDepartementById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/departements/${id}`).pipe(catchError(() => of(null)));
  }

  getById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  create(payload: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, payload);
  }

  update(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, payload);
  }

  patch(id: string, payload: any): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${id}`, payload);
  }

  patchByPatrimoineId(patrimoineId: string, payload: any): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/patrimoines/${patrimoineId}/geolocalisation`, payload);
  }

  updateByPatrimoineId(patrimoineId: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/patrimoines/${patrimoineId}/geolocalisation`, payload);
  }

  delete(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // --- Points d'accès ---

  getPointsAcces(patrimoineId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/patrimoines/${patrimoineId}/points-acces`).pipe(
      catchError(() => of([]))
    );
  }

  createPointAcces(patrimoineId: string, payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/patrimoines/${patrimoineId}/points-acces`, payload);
  }

  updatePointAcces(pointId: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/points-acces/${pointId}`, payload);
  }

  deletePointAcces(pointId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/points-acces/${pointId}`);
  }

  // --- Zones Touristiques ---

  getZonesTouristiques(): Observable<any[]> {
    return this.http.get<any>(`${this.apiUrl}/zones-touristiques`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createZoneTouristique(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/zones-touristiques`, payload);
  }

  updateZoneTouristique(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/zones-touristiques/${id}`, payload);
  }

  deleteZoneTouristique(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/zones-touristiques/${id}`);
  }

  // --- Zones Géographiques ---

  getZonesGeographiques(): Observable<any[]> {
    return this.http.get<any>(`${this.apiUrl}/zones-geographiques`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createZoneGeographique(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/zones-geographiques`, payload);
  }

  updateZoneGeographique(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/zones-geographiques/${id}`, payload);
  }

  deleteZoneGeographique(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/zones-geographiques/${id}`);
  }

  // --- Référentiel Administratif ---

  getPays(): Observable<any[]> {
    return this.http.get<any>(`${this.apiUrl}/pays`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  getPaysById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/pays/${id}`).pipe(catchError(() => of(null)));
  }

  createPays(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/pays`, payload);
  }

  updatePays(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/pays/${id}`, payload);
  }

  deletePays(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/pays/${id}`);
  }

  getRegions(): Observable<any[]> {
    return this.http.get<any>(`${this.apiUrl}/regions`).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  getRegionById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/regions/${id}`).pipe(catchError(() => of(null)));
  }

  createRegion(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/regions`, payload);
  }

  updateRegion(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/regions/${id}`, payload);
  }

  deleteRegion(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/regions/${id}`);
  }

  getDepartements(regionId?: string): Observable<any[]> {
    let params = new HttpParams();
    if (regionId) params = params.set('regionId', regionId);
    return this.http.get<any>(`${this.apiUrl}/departements`, { params }).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createDepartement(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/departements`, payload);
  }

  updateDepartement(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/departements/${id}`, payload);
  }

  deleteDepartement(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/departements/${id}`);
  }

  getCommunes(departementId?: string): Observable<any[]> {
    let params = new HttpParams();
    if (departementId) params = params.set('departementId', departementId);
    return this.http.get<any>(`${this.apiUrl}/communes`, { params }).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createCommune(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/communes`, payload);
  }

  updateCommune(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/communes/${id}`, payload);
  }

  deleteCommune(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/communes/${id}`);
  }

  getVillages(communeId?: string): Observable<any[]> {
    let params = new HttpParams();
    if (communeId) params = params.set('communeId', communeId);
    return this.http.get<any>(`${this.apiUrl}/villages`, { params }).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createVillage(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/villages`, payload);
  }

  updateVillage(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/villages/${id}`, payload);
  }

  deleteVillage(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/villages/${id}`);
  }

  getQuartiers(communeId?: string): Observable<any[]> {
    let params = new HttpParams();
    if (communeId) params = params.set('communeId', communeId);
    return this.http.get<any>(`${this.apiUrl}/quartiers`, { params }).pipe(
      map(res => Array.isArray(res) ? res : (res?.content || [])),
      catchError(() => of([]))
    );
  }

  createQuartier(payload: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/quartiers`, payload);
  }

  updateQuartier(id: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/quartiers/${id}`, payload);
  }

  deleteQuartier(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/quartiers/${id}`);
  }
}
