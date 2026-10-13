import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-geolocalisation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './geolocalisation-form.component.html',
  styleUrls: ['./geolocalisation-form.component.css']
})
export class GeolocalisationFormComponent implements OnInit, AfterViewInit, OnDestroy {
  patrimoineId: string | null = null;
  locForm!: FormGroup;
  accessForm!: FormGroup;
  
  notificationMessage: string | null = null;
  copiedSuccess: boolean = false;
  showJsonPayload: boolean = false;
  jsonPayload: string = '';
  jsonTypeTitle: string = 'Coordonnées de géolocalisation';

  pointsAcces: any[] = [];
  editingPointId: string | null = null;
  
  // Leaflet map vars
  map: any;
  marker: any;
  
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patrimoineId = this.route.snapshot.paramMap.get('id');
    this.initLocalisationForms();
  }

  ngAfterViewInit(): void {
    if (typeof window !== 'undefined') {
      import('leaflet').then(L => {
        this.initMap(L);
      });
    }
  }

  ngOnDestroy(): void {
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
      zoneGeographiqueId: ['uuid-ouest'],
      zoneTouristiqueId: ['uuid-zt-goree', Validators.required],
      source: ['GPS'],
      latitude: [14.6666, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [-17.3986, [Validators.required, Validators.min(-180), Validators.max(180)]],
      methodeAcquisition: ['GPS'],
      niveauFiabilite: ['ELEVE']
    });

    this.accessForm = this.fb.group({
      type: ['ENTREE_SECONDAIRE', Validators.required],
      nom: ['', [Validators.required, Validators.minLength(2)]],
      latitude: [14.6668, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [-17.3988, [Validators.required, Validators.min(-180), Validators.max(180)]],
      description: [''],
      accessiblePMR: [false],
      parkingDisponible: [false]
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
      coordonnees: { latitude: val.latitude, longitude: val.longitude },
      description: val.description,
      accessiblePMR: val.accessiblePMR,
      parkingDisponible: val.parkingDisponible
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
      type: 'ENTREE_SECONDAIRE', accessiblePMR: false, parkingDisponible: false
    });
  }

  editPointAccesItem(id: string): void {
    const item = this.pointsAcces.find(p => p.id === id);
    if (item) {
      this.editingPointId = id;
      this.accessForm.patchValue({
        type: item.type,
        nom: item.nom,
        latitude: item.coordonnees?.latitude,
        longitude: item.coordonnees?.longitude,
        description: item.description,
        accessiblePMR: item.accessiblePMR,
        parkingDisponible: item.parkingDisponible
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

  copyJson(): void {
    navigator.clipboard.writeText(this.jsonPayload).then(() => {
      this.copiedSuccess = true;
      setTimeout(() => this.copiedSuccess = false, 2000);
    });
  }

  generateJson(): void {
    if (this.locForm.invalid) {
      this.notificationMessage = "Veuillez remplir les informations de géolocalisation correctement.";
      setTimeout(() => this.notificationMessage = null, 3000);
      return;
    }
    
    const locVal = this.locForm.value;
    const geoPayload = {
      patrimoineId: this.patrimoineId,
      zoneGeographiqueId: locVal.zoneGeographiqueId,
      zoneTouristiqueId: locVal.zoneTouristiqueId,
      source: locVal.source,
      pointsGeo: [
        {
          ordre: 1,
          latitude: locVal.latitude,
          longitude: locVal.longitude,
          altitude: null
        }
      ],
      pointsAcces: this.pointsAcces,
      donneesSpatiales: {
        methodeAcquisition: locVal.methodeAcquisition,
        niveauFiabilite: locVal.niveauFiabilite
      }
    };
    
    this.jsonPayload = JSON.stringify(geoPayload, null, 2);
    this.showJsonPayload = true;
    this.notificationMessage = "Localisation créée avec succès ! (JSON généré)";
    setTimeout(() => this.notificationMessage = null, 3000);
  }
}
