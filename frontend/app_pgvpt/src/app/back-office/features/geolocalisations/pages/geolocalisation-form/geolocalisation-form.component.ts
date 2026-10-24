import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { forkJoin, of, Subscription, switchMap, throwError, catchError } from 'rxjs';
import { GeolocalisationApiService } from '../../../../../core/services/api/geolocalisation-api.service';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { ToastService } from '../../../../../core/services/toast.service';

@Component({
  selector: 'app-geolocalisation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './geolocalisation-form.component.html',
  styleUrls: ['./geolocalisation-form.component.css']
})
export class GeolocalisationFormComponent implements OnInit, AfterViewInit, OnDestroy {
  patrimoineId: string | null = null;
  patrimoine: any | null = null;
  nomPatrimoine: string = '';
  patrimoinesList: any[] = [];
  existingGeoId: string | null = null;
  isSaving: boolean = false;
  locForm!: FormGroup;
  accessForm!: FormGroup;
  
  notificationMessage: string | null = null;
  copiedSuccess: boolean = false;
  showJsonPayload: boolean = false;
  jsonPayload: string = '';
  jsonTypeTitle: string = 'Coordonnées de géolocalisation';
  payloadTab: 'geo' | 'points' | 'all' = 'geo';

  zonesTouristiques: any[] = [];
  pointsAcces: any[] = [];
  editingPointId: string | null = null;
  
  // Leaflet map vars
  map: any;
  marker: any;
  private routeSub!: Subscription;
  
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private geoService: GeolocalisationApiService,
    private patrimoineApi: PatrimoineApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initLocalisationForms();
    this.routeSub = this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id && id !== this.patrimoineId) {
        this.patrimoineId = id;
        this.loadExistingData();
      } else if (!this.patrimoineId && id) {
        this.patrimoineId = id;
        this.loadExistingData();
      }
    });
  }

  ngAfterViewInit(): void {
    if (typeof window !== 'undefined') {
      import('leaflet').then(L => {
        this.initMap(L);
      });
    }
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
    if (this.map) {
      this.map.remove();
    }
  }

  initMap(L: any): void {
    const defaultLat = 14.6666;
    const defaultLng = -17.3986;

    this.map = L.map('map').setView([defaultLat, defaultLng], 15);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    this.marker = L.marker([defaultLat, defaultLng], { draggable: true }).addTo(this.map);

    this.marker.on('dragend', () => {
      const position = this.marker.getLatLng();
      this.locForm.patchValue({
        latitude: parseFloat(position.lat.toFixed(6)),
        longitude: parseFloat(position.lng.toFixed(6))
      }, { emitEvent: false });
    });

    this.map.on('click', (e: any) => {
      this.marker.setLatLng(e.latlng);
      this.locForm.patchValue({
        latitude: parseFloat(e.latlng.lat.toFixed(6)),
        longitude: parseFloat(e.latlng.lng.toFixed(6))
      }, { emitEvent: false });
    });
  }

  updateMarkerPosition(lat: number, lng: number): void {
    if (this.marker && this.map) {
      const newLatLng = { lat, lng };
      this.marker.setLatLng(newLatLng);
      this.map.panTo(newLatLng);
    }
  }

  initLocalisationForms(): void {
    this.locForm = this.fb.group({
      zoneTouristiqueId: ['', Validators.required],
      adresse: [''],
      source: ['GPS'],
      latitude: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
      methodeAcquisition: ['GPS'],
      niveauFiabilite: ['ELEVE']
    });

    this.accessForm = this.fb.group({
      type: ['ENTREE_PRINCIPALE', Validators.required],
      nom: ['', [Validators.required, Validators.minLength(2)]],
      latitude: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
      description: [''],
      accessiblePMR: [false],
      parkingDisponible: [false],
      transportPublic: [false]
    });

    this.locForm.get('latitude')?.valueChanges.subscribe(lat => {
      const lng = this.locForm.get('longitude')?.value;
      if (lat && lng && !isNaN(lat) && !isNaN(lng)) {
        this.updateMarkerPosition(Number(lat), Number(lng));
      }
    });

    this.locForm.get('longitude')?.valueChanges.subscribe(lng => {
      const lat = this.locForm.get('latitude')?.value;
      if (lat && lng && !isNaN(lat) && !isNaN(lng)) {
        this.updateMarkerPosition(Number(lat), Number(lng));
      }
    });
  }

  addPointAccesItem(): void {
    if (this.accessForm.invalid) {
      this.accessForm.markAllAsTouched();
      return;
    }
    const val = this.accessForm.value;
    const newItem = {
      id: this.editingPointId || Date.now().toString(),
      type: val.type,
      nom: val.nom,
      latitude: Number(val.latitude),
      longitude: Number(val.longitude),
      description: val.description,
      accessiblePMR: !!val.accessiblePMR,
      parkingDisponible: !!val.parkingDisponible,
      transportPublic: !!val.transportPublic
    };

    if (this.editingPointId) {
      const idx = this.pointsAcces.findIndex(p => p.id === this.editingPointId);
      if (idx > -1) {
        this.pointsAcces[idx] = newItem;
      }
      this.editingPointId = null;
    } else {
      this.pointsAcces.push(newItem);
    }
    this.accessForm.reset({
      type: 'ENTREE_SECONDAIRE',
      accessiblePMR: false,
      parkingDisponible: false,
      transportPublic: false
    });
  }

  editPointAccesItem(id: string): void {
    const item = this.pointsAcces.find(p => p.id === id);
    if (item) {
      this.editingPointId = id;
      this.accessForm.patchValue({
        type: item.type,
        nom: item.nom,
        latitude: item.latitude ?? item.coordonnees?.latitude,
        longitude: item.longitude ?? item.coordonnees?.longitude,
        description: item.description,
        accessiblePMR: !!item.accessiblePMR,
        parkingDisponible: !!item.parkingDisponible,
        transportPublic: !!item.transportPublic
      });
    }
  }

  removePointAccesItem(id: string): void {
    this.pointsAcces = this.pointsAcces.filter(p => p.id !== id);
    if (this.editingPointId === id) {
      this.editingPointId = null;
      this.accessForm.reset();
    }
  }

  onSelectPatrimoineChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    if (select?.value && select.value !== this.patrimoineId) {
      this.router.navigate(['/admin/patrimoines', select.value, 'localisation']);
    }
  }

  loadExistingData(): void {
    // 1. Liste des patrimoines pour le sélecteur / référence
    this.patrimoineApi.getAll().subscribe({
      next: (list) => {
        this.patrimoinesList = Array.isArray(list) ? list : [];
        if (this.patrimoineId && !this.patrimoine) {
          const found = this.patrimoinesList.find(p => p.id === this.patrimoineId);
          if (found) {
            this.patrimoine = found;
            this.nomPatrimoine = found.nom || '';
          }
        }
      },
      error: () => {
        this.patrimoinesList = [];
      }
    });

    // 2. Détails du patrimoine spécifique
    if (this.patrimoineId) {
      this.patrimoineApi.getById(this.patrimoineId).subscribe({
        next: (p) => {
          this.patrimoine = p;
          this.nomPatrimoine = p?.nom || '';
        },
        error: (err) => {
          console.warn('Impossible de charger les détails du patrimoine :', err);
        }
      });
    }

    // 3. Zones Touristiques
    this.geoService.getZonesTouristiques().subscribe({
      next: (data) => {
        this.zonesTouristiques = Array.isArray(data) ? data : [];
      },
      error: () => {
        this.zonesTouristiques = [];
      }
    });

    if (!this.patrimoineId) return;

    // 4. Géolocalisation existante
    this.geoService.getByPatrimoineId(this.patrimoineId).subscribe(geos => {
      if (geos && geos.length > 0) {
        const geo = geos[0];
        this.existingGeoId = geo.id;
        this.locForm.patchValue({
          zoneTouristiqueId: geo.zoneTouristiqueId || geo.zoneTouristique?.id || '',
          adresse: geo.adresse || '',
          source: geo.source || 'GPS',
          latitude: geo.latitude,
          longitude: geo.longitude,
          methodeAcquisition: geo.methodeAcquisition || 'GPS',
          niveauFiabilite: geo.niveauFiabilite || 'ELEVE'
        });
        if (geo.latitude && geo.longitude) {
          this.updateMarkerPosition(geo.latitude, geo.longitude);
        }
      }
    });

    // 5. Points d'accès
    this.geoService.getPointsAcces(this.patrimoineId).subscribe(pts => {
      if (pts && pts.length > 0) {
        this.pointsAcces = pts.map(p => ({
          id: p.id,
          nom: p.nom,
          type: p.type,
          latitude: p.latitude,
          longitude: p.longitude,
          description: p.description,
          adresse: p.adresse,
          accessiblePMR: !!p.accessiblePMR,
          parkingDisponible: !!p.parkingDisponible,
          transportPublic: !!p.transportPublic
        }));
      }
    });
  }

  copyJson(): void {
    navigator.clipboard.writeText(this.jsonPayload).then(() => {
      this.copiedSuccess = true;
      setTimeout(() => this.copiedSuccess = false, 2000);
    });
  }

  buildGeoPayload(): Record<string, any> {
    const locVal = this.locForm.value;
    return {
      patrimoineId: this.patrimoineId,
      zoneTouristiqueId: locVal.zoneTouristiqueId || null,
      latitude: Number(locVal.latitude),
      longitude: Number(locVal.longitude),
      adresse: locVal.adresse || null,
      source: locVal.source || 'GPS',
      methodeAcquisition: locVal.methodeAcquisition || 'GPS',
      niveauFiabilite: locVal.niveauFiabilite || 'ELEVE',
      systemeReference: 'WGS84',
      codeEpsg: 4326
    };
  }

  buildPointsPayload(): any[] {
    return this.pointsAcces.map(p => ({
      nom: p.nom,
      type: p.type,
      latitude: Number(p.latitude),
      longitude: Number(p.longitude),
      description: p.description || null,
      accessiblePMR: !!p.accessiblePMR,
      parkingDisponible: !!p.parkingDisponible,
      transportPublic: !!p.transportPublic
    }));
  }

  setPayloadTab(tab: 'geo' | 'points' | 'all'): void {
    this.payloadTab = tab;
    this.updateJsonDisplay();
  }

  updateJsonDisplay(): void {
    const geo = this.buildGeoPayload();
    const points = this.buildPointsPayload();

    if (this.payloadTab === 'geo') {
      this.jsonTypeTitle = 'Coordonnées de géolocalisation';
      this.jsonPayload = JSON.stringify(geo, null, 2);
    } else if (this.payloadTab === 'points') {
      this.jsonTypeTitle = `Points d'accès (${points.length} défini${points.length > 1 ? 's' : ''})`;
      this.jsonPayload = JSON.stringify(points, null, 2);
    } else {
      this.jsonTypeTitle = 'Payload combiné (Géolocalisation & Points d\'accès)';
      this.jsonPayload = JSON.stringify({ geolocalisation: geo, pointsAcces: points }, null, 2);
    }
  }

  generateJson(): void {
    if (this.locForm.invalid) {
      this.notificationMessage = "Veuillez remplir les informations de géolocalisation correctement.";
      setTimeout(() => this.notificationMessage = null, 3000);
      return;
    }
    this.updateJsonDisplay();
    this.showJsonPayload = true;
    this.notificationMessage = "Localisation et points d'accès prêts ! (JSON généré)";
    setTimeout(() => this.notificationMessage = null, 3000);
  }

  saveLocalisation(): void {
    if (this.locForm.invalid) {
      this.locForm.markAllAsTouched();
      this.toastService.warning('Veuillez remplir les champs obligatoires de géolocalisation.');
      this.notificationMessage = "Veuillez remplir les champs obligatoires de géolocalisation.";
      setTimeout(() => this.notificationMessage = null, 4000);
      return;
    }

    this.isSaving = true;
    const geoPayload = this.buildGeoPayload();
    const pointsPayload = this.buildPointsPayload();

    // Pour un patrimoine, on utilise l'endpoint dédié PATCH /geolocalisations/patrimoines/{id}/geolocalisation
    // qui met à jour directement l'enregistrement lié sans contrainte de doublon.
    // Si aucun enregistrement n'existe encore (404), on bascule sur la création POST.
    const saveGeo$ = this.patrimoineId
      ? this.geoService.patchByPatrimoineId(this.patrimoineId, geoPayload).pipe(
          catchError((err) => {
            if (err?.status === 404) {
              return this.geoService.create(geoPayload);
            }
            return throwError(() => err);
          })
        )
      : (this.existingGeoId
          ? this.geoService.update(this.existingGeoId, geoPayload)
          : this.geoService.create(geoPayload));

    saveGeo$.subscribe({
      next: (savedGeo) => {
        if (savedGeo?.id) {
          this.existingGeoId = savedGeo.id;
        }

        if (pointsPayload.length > 0 && this.patrimoineId) {
          const savePoints$ = pointsPayload.map(pt =>
            this.geoService.createPointAcces(this.patrimoineId!, pt)
          );
          forkJoin(savePoints$).subscribe({
            next: () => {
              this.isSaving = false;
              this.toastService.success('Géolocalisation et points d\'accès enregistrés avec succès !');
              this.notificationMessage = "Géolocalisation et points d'accès enregistrés avec succès !";
              setTimeout(() => this.notificationMessage = null, 4000);
            },
            error: (err) => {
              this.isSaving = false;
              console.warn("Points d'accès non persistés :", err);
              this.toastService.success('Géolocalisation enregistrée avec succès !');
              this.notificationMessage = "Géolocalisation enregistrée avec succès !";
              setTimeout(() => this.notificationMessage = null, 4000);
            }
          });
        } else {
          this.isSaving = false;
          this.toastService.success('Géolocalisation enregistrée avec succès !');
          this.notificationMessage = "Géolocalisation enregistrée avec succès !";
          setTimeout(() => this.notificationMessage = null, 4000);
        }
      },
      error: (err) => {
        this.isSaving = false;
        console.warn("Erreur API :", err);
        this.toastService.warning('Erreur lors de l\'enregistrement de la géolocalisation.');
        this.notificationMessage = "Erreur lors de l'enregistrement de la géolocalisation.";
        setTimeout(() => this.notificationMessage = null, 4000);
      }
    });
  }

  getZoneRegionLabel(zt: any): string {
    if (!zt) return '';
    const geoNom = zt.zoneGeographique?.nom || '';
    if (geoNom) {
      return geoNom.replace(/^Zone g[ée]ographique\s+(du|de la|des|de|d')\s*/i, '').trim();
    }
    return zt.code || '';
  }

  get selectedZoneInfo(): { nom: string; region: string } | null {
    const id = this.locForm?.get('zoneTouristiqueId')?.value;
    if (!id || !this.zonesTouristiques) return null;
    const found = this.zonesTouristiques.find((z: any) => z.id === id);
    if (!found) return null;
    return {
      nom: found.nom || 'Zone touristique',
      region: this.getZoneRegionLabel(found)
    };
  }
}
