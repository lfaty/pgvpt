import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GeolocalisationApiService } from '../../../../../core/services/api/geolocalisation-api.service';
import { ToastService } from '../../../../../core/services/toast.service';

import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

export interface ZoneTouristiqueItem {
  id: string;
  code: string;
  nom: string;
  description?: string;
  zoneGeographiqueId?: string;
  zoneGeographiqueNom?: string;
  typeZone?: string;
  superficieKm2?: number;
  latitudeCentre?: number;
  longitudeCentre?: number;
  actif?: boolean;
}

@Component({
  selector: 'app-zones-touristiques',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, ConfirmModalComponent],
  templateUrl: './zones-touristiques.component.html',
  styleUrls: ['./zones-touristiques.component.css']
})
export class ZonesTouristiquesComponent implements OnInit {
  zones: ZoneTouristiqueItem[] = [];
  filteredZones: ZoneTouristiqueItem[] = [];
  zonesGeographiques: any[] = [];
  isLoading = false;
  isSubmitting = false;
  showCreateModal = false;
  showViewModal = false;
  selectedItem: ZoneTouristiqueItem | null = null;
  isEditMode = false;
  editingZone: ZoneTouristiqueItem | null = null;
  zoneToDelete: ZoneTouristiqueItem | null = null;
  isDeleting = false;
  currentStep = 1;
  readonly totalSteps = 3;
  viewMode: 'grid' | 'table' = 'grid';
  selectedTypeFilter: string = '';
  zoneForm!: FormGroup;
  searchQuery = '';

  typesZone = [
    { value: 'ZONE_BALNEAIRE', label: 'Zone Balnéaire' },
    { value: 'ZONE_URBAINE_ET_CULTURELLE', label: 'Zone Urbaine & Culturelle' },
    { value: 'ZONE_RURALE_ET_TERROIR', label: 'Zone Rurale & Terroir' },
    { value: 'ZONE_NATURELLE_PROTEGEE', label: 'Zone Naturelle Protégée' },
    { value: 'PARC_NATIONAL', label: 'Parc National' },
    { value: 'RESERVE_NATURELLE', label: 'Réserve Naturelle' },
    { value: 'ZONE_RELIGIEUSE_ET_PELERINAGE', label: 'Zone Religieuse & Pèlerinage' },
    { value: 'ZONE_TOURISTIQUE', label: 'Zone Touristique Standard' }
  ];

  constructor(
    private fb: FormBuilder,
    private geoService: GeolocalisationApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadZones();
    this.loadZonesGeographiques();
  }

  initForm(): void {
    this.zoneForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(3)]],
      nom: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      zoneGeographiqueId: ['', Validators.required],
      typeZone: ['ZONE_TOURISTIQUE', Validators.required],
      superficieKm2: [null, [Validators.min(0)]],
      latitudeCentre: [null, [Validators.min(-90), Validators.max(90)]],
      longitudeCentre: [null, [Validators.min(-180), Validators.max(180)]]
    });
  }

  loadZones(): void {
    this.isLoading = true;
    this.geoService.getZonesTouristiques().subscribe({
      next: (data) => {
        this.zones = Array.isArray(data) ? data : [];
        this.applyFilter();
        this.isLoading = false;
      },
      error: (err) => {
        console.warn('Erreur chargement zones touristiques :', err);
        this.zones = [];
        this.applyFilter();
        this.isLoading = false;
      }
    });
  }

  loadZonesGeographiques(): void {
    this.geoService.getZonesGeographiques().subscribe({
      next: (data) => {
        this.zonesGeographiques = Array.isArray(data) ? data : [];
      },
      error: () => {
        this.zonesGeographiques = [];
      }
    });
  }

  applyFilter(): void {
    const q = this.searchQuery.toLowerCase().trim();
    this.filteredZones = this.zones.filter(z => {
      const zgNom = this.getZoneGeographiqueNom(z).toLowerCase();
      const matchesSearch = !q ||
        z.nom?.toLowerCase().includes(q) ||
        z.code?.toLowerCase().includes(q) ||
        (z.description && z.description.toLowerCase().includes(q)) ||
        zgNom.includes(q);

      const matchesType = !this.selectedTypeFilter || z.typeZone === this.selectedTypeFilter;

      return matchesSearch && matchesType;
    });
  }

  setTypeFilter(type: string): void {
    this.selectedTypeFilter = this.selectedTypeFilter === type ? '' : type;
    this.applyFilter();
  }

  getZoneGeographiqueNom(zone: any): string {
    if (!zone) return 'Non rattaché';
    if (zone.zoneGeographiqueNom) return zone.zoneGeographiqueNom;
    if (zone.zoneGeographique?.nom) return zone.zoneGeographique.nom;
    const zgId = zone.zoneGeographiqueId || zone.zoneGeographique?.id;
    if (zgId) {
      const found = this.zonesGeographiques.find(zg => zg.id === zgId);
      if (found) return found.nom;
    }
    return 'Non rattaché';
  }

  getTypeZoneInfo(type?: string): { label: string; icon: string; badgeClass: string; iconBgClass: string } {
    switch (type) {
      case 'ZONE_BALNEAIRE':
        return {
          label: 'Zone Balnéaire',
          icon: 'fas fa-umbrella-beach',
          badgeClass: 'bg-cyan-50 text-cyan-800 border border-cyan-200/80',
          iconBgClass: 'bg-cyan-100 text-cyan-700'
        };
      case 'ZONE_URBAINE_ET_CULTURELLE':
        return {
          label: 'Urbaine & Culturelle',
          icon: 'fas fa-landmark',
          badgeClass: 'bg-indigo-50 text-indigo-800 border border-indigo-200/80',
          iconBgClass: 'bg-indigo-100 text-indigo-700'
        };
      case 'ZONE_RURALE_ET_TERROIR':
        return {
          label: 'Rurale & Terroir',
          icon: 'fas fa-wheat-awn',
          badgeClass: 'bg-amber-50 text-amber-800 border border-amber-200/80',
          iconBgClass: 'bg-amber-100 text-amber-700'
        };
      case 'ZONE_NATURELLE_PROTEGEE':
        return {
          label: 'Naturelle Protégée',
          icon: 'fas fa-leaf',
          badgeClass: 'bg-emerald-50 text-emerald-800 border border-emerald-200/80',
          iconBgClass: 'bg-emerald-100 text-emerald-700'
        };
      case 'PARC_NATIONAL':
        return {
          label: 'Parc National',
          icon: 'fas fa-mountain-sun',
          badgeClass: 'bg-emerald-100 text-emerald-900 border border-emerald-300',
          iconBgClass: 'bg-emerald-200 text-emerald-800'
        };
      case 'RESERVE_NATURELLE':
        return {
          label: 'Réserve Naturelle',
          icon: 'fas fa-tree',
          badgeClass: 'bg-teal-50 text-teal-800 border border-teal-200/80',
          iconBgClass: 'bg-teal-100 text-teal-700'
        };
      case 'ZONE_RELIGIEUSE_ET_PELERINAGE':
        return {
          label: 'Religieuse & Pèlerinage',
          icon: 'fas fa-place-of-worship',
          badgeClass: 'bg-purple-50 text-purple-800 border border-purple-200/80',
          iconBgClass: 'bg-purple-100 text-purple-700'
        };
      default:
        return {
          label: 'Touristique Standard',
          icon: 'fas fa-map-marked-alt',
          badgeClass: 'bg-teal-50 text-teal-800 border border-teal-200/80',
          iconBgClass: 'bg-teal-100 text-teal-700'
        };
    }
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.editingZone = null;
    this.currentStep = 1;
    this.isSubmitting = false;
    this.zoneForm.reset({
      typeZone: 'ZONE_TOURISTIQUE'
    });
    this.showCreateModal = true;
  }

  openEditModal(zone: ZoneTouristiqueItem): void {
    this.isEditMode = true;
    this.editingZone = zone;
    this.currentStep = 1;
    this.isSubmitting = false;

    this.zoneForm.reset({
      code: zone.code || '',
      nom: zone.nom || '',
      description: zone.description || '',
      typeZone: zone.typeZone || 'ZONE_TOURISTIQUE',
      zoneGeographiqueId: zone.zoneGeographiqueId || (zone as any).zoneGeographique?.id || '',
      superficieKm2: zone.superficieKm2 != null ? zone.superficieKm2 : null,
      latitudeCentre: zone.latitudeCentre != null ? zone.latitudeCentre : null,
      longitudeCentre: zone.longitudeCentre != null ? zone.longitudeCentre : null
    });

    this.showCreateModal = true;
  }

  closeCreateModal(): void {
    this.showCreateModal = false;
    this.isEditMode = false;
    this.editingZone = null;
    this.currentStep = 1;
    this.isSubmitting = false;
  }

  openViewModal(zone: ZoneTouristiqueItem): void {
    this.selectedItem = zone;
    this.showViewModal = true;
  }

  closeViewModal(): void {
    this.showViewModal = false;
    this.selectedItem = null;
  }

  isStep1Valid(): boolean {
    const code = this.zoneForm.get('code');
    const nom = this.zoneForm.get('nom');
    const typeZone = this.zoneForm.get('typeZone');
    return !!(code?.valid && nom?.valid && typeZone?.valid);
  }

  isStep2Valid(): boolean {
    const zgId = this.zoneForm.get('zoneGeographiqueId');
    return !!(zgId?.valid && zgId.value);
  }

  isStep3Valid(): boolean {
    const sup = this.zoneForm.get('superficieKm2');
    const lat = this.zoneForm.get('latitudeCentre');
    const lng = this.zoneForm.get('longitudeCentre');
    return (!sup || sup.valid) && (!lat || lat.valid) && (!lng || lng.valid);
  }

  canProceed(): boolean {
    if (this.currentStep === 1) return this.isStep1Valid();
    if (this.currentStep === 2) return this.isStep2Valid();
    if (this.currentStep === 3) return this.isStep3Valid() && this.zoneForm.valid;
    return false;
  }

  nextStep(): void {
    if (this.currentStep === 1) {
      if (!this.isStep1Valid()) {
        this.zoneForm.get('code')?.markAsTouched();
        this.zoneForm.get('nom')?.markAsTouched();
        this.zoneForm.get('typeZone')?.markAsTouched();
        return;
      }
    } else if (this.currentStep === 2) {
      if (!this.isStep2Valid()) {
        this.zoneForm.get('zoneGeographiqueId')?.markAsTouched();
        this.notify('Veuillez sélectionner la zone géographique de rattachement.', 'warning');
        return;
      }
    }
    if (this.currentStep < this.totalSteps) {
      this.currentStep++;
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  goToStep(step: number): void {
    if (step === 1) {
      this.currentStep = 1;
    } else if (step === 2 && this.isStep1Valid()) {
      this.currentStep = 2;
    } else if (step === 3 && this.isStep1Valid() && this.isStep2Valid()) {
      this.currentStep = 3;
    }
  }

  getSelectedZoneGeo(): any {
    const zgId = this.zoneForm.get('zoneGeographiqueId')?.value;
    return this.zonesGeographiques.find(z => z.id === zgId);
  }

  getSelectedTypeZoneLabel(): string {
    const val = this.zoneForm.get('typeZone')?.value;
    const found = this.typesZone.find(t => t.value === val);
    return found ? found.label : (val || 'Standard');
  }

  submitCreate(): void {
    if (this.zoneForm.invalid) {
      this.zoneForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formVal = this.zoneForm.value;
    const payload = {
      ...formVal,
      code: formVal.code ? formVal.code.toUpperCase() : ''
    };

    if (this.isEditMode && this.editingZone) {
      this.geoService.updateZoneTouristique(this.editingZone.id, payload).subscribe({
        next: (updated) => {
          this.isSubmitting = false;
          const idx = this.zones.findIndex(z => z.id === this.editingZone!.id);
          if (idx !== -1) {
            this.zones[idx] = { ...this.zones[idx], ...updated };
          }
          this.applyFilter();
          this.closeCreateModal();
          this.notify('Zone touristique modifiée avec succès !');
        },
        error: (err) => {
          this.isSubmitting = false;
          console.error('Erreur API modification zone touristique', err);
          const errMsg = err?.error?.message || err?.message || 'Erreur lors de la modification de la zone touristique.';
          this.notify('Erreur API : ' + errMsg, 'warning');
        }
      });
    } else {
      this.geoService.createZoneTouristique(payload).subscribe({
        next: (created) => {
          this.isSubmitting = false;
          this.zones.unshift(created);
          this.applyFilter();
          this.closeCreateModal();
          this.notify('Zone touristique créée avec succès !');
        },
        error: (err) => {
          this.isSubmitting = false;
          console.error('Erreur API création zone touristique', err);
          const errMsg = err?.error?.message || err?.message || 'Erreur lors de la création de la zone touristique.';
          this.notify('Erreur API : ' + errMsg);
        }
      });
    }
  }

  deleteZone(zone: ZoneTouristiqueItem): void {
    this.zoneToDelete = zone;
  }

  cancelDeleteZone(): void {
    this.zoneToDelete = null;
    this.isDeleting = false;
  }

  confirmDeleteZone(): void {
    if (!this.zoneToDelete) return;
    const zone = this.zoneToDelete;
    this.isDeleting = true;

    this.geoService.deleteZoneTouristique(zone.id).subscribe({
      next: () => {
        this.zones = this.zones.filter(z => z.id !== zone.id);
        this.applyFilter();
        this.notify('Zone touristique supprimée.');
        this.zoneToDelete = null;
        this.isDeleting = false;
      },
      error: (err) => {
        console.error('Erreur suppression zone touristique', err);
        this.notify('Erreur lors de la suppression.', 'warning');
        this.isDeleting = false;
      }
    });
  }

  copyUuid(id: string): void {
    navigator.clipboard.writeText(id).then(() => {
      this.notify('Identifiant UUID copié dans le presse-papier !');
    });
  }

  notify(msg: string, type: 'success' | 'info' | 'warning' = 'success'): void {
    if (type === 'success') {
      this.toastService.success(msg);
    } else if (type === 'warning') {
      this.toastService.warning(msg);
    } else {
      this.toastService.info(msg);
    }
  }
}
