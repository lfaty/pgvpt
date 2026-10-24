import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GeolocalisationApiService } from '../../../../../core/services/api/geolocalisation-api.service';
import { ToastService } from '../../../../../core/services/toast.service';

export interface ZoneGeographiqueItem {
  id: string;
  code: string;
  nom: string;
  description?: string;
  regions?: any[];
  superficieKm2?: number;
  latitudeCentre?: number;
  longitudeCentre?: number;
  actif?: boolean;
}

import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-zones-geographiques',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, ConfirmModalComponent],
  templateUrl: './zones-geographiques.component.html',
  styleUrls: ['./zones-geographiques.component.css']
})
export class ZonesGeographiquesComponent implements OnInit {
  zones: ZoneGeographiqueItem[] = [];
  filteredZones: ZoneGeographiqueItem[] = [];
  availableRegions: any[] = [];
  isLoading = false;
  isSubmitting = false;
  showCreateModal = false;
  showViewModal = false;
  selectedItem: ZoneGeographiqueItem | null = null;
  isEditMode = false;
  editingZone: ZoneGeographiqueItem | null = null;
  zoneToDelete: ZoneGeographiqueItem | null = null;
  isDeleting = false;
  currentStep = 1;
  readonly totalSteps = 3;
  zoneForm!: FormGroup;
  searchQuery = '';

  constructor(
    private fb: FormBuilder,
    private geoService: GeolocalisationApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadZones();
    this.loadRegions();
  }

  initForm(): void {
    this.zoneForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(3)]],
      nom: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      regionIds: [[], [Validators.required]],
      superficieKm2: [null, [Validators.min(0)]],
      latitudeCentre: [null, [Validators.min(-90), Validators.max(90)]],
      longitudeCentre: [null, [Validators.min(-180), Validators.max(180)]]
    });
  }

  loadZones(): void {
    this.isLoading = true;
    this.geoService.getZonesGeographiques().subscribe({
      next: (data) => {
        this.zones = Array.isArray(data) ? data : [];
        this.applyFilter();
        this.isLoading = false;
      },
      error: (err) => {
        console.warn('Erreur chargement zones géographiques :', err);
        this.zones = [];
        this.applyFilter();
        this.isLoading = false;
      }
    });
  }

  loadRegions(): void {
    this.geoService.getRegions().subscribe({
      next: (data) => {
        this.availableRegions = Array.isArray(data) ? data : [];
      },
      error: () => {
        this.availableRegions = [];
      }
    });
  }

  applyFilter(): void {
    const q = this.searchQuery.toLowerCase().trim();
    if (!q) {
      this.filteredZones = [...this.zones];
    } else {
      this.filteredZones = this.zones.filter(z =>
        z.nom?.toLowerCase().includes(q) ||
        z.code?.toLowerCase().includes(q) ||
        (z.description && z.description.toLowerCase().includes(q))
      );
    }
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.editingZone = null;
    this.currentStep = 1;
    this.isSubmitting = false;
    this.zoneForm.reset({
      regionIds: []
    });
    this.showCreateModal = true;
  }

  openEditModal(zg: ZoneGeographiqueItem): void {
    this.isEditMode = true;
    this.editingZone = zg;
    this.currentStep = 1;
    this.isSubmitting = false;

    let rIds: string[] = [];
    if (zg.regions && Array.isArray(zg.regions)) {
      rIds = zg.regions.map(r => {
        if (typeof r === 'string') return r;
        return r.regionId || r.id;
      }).filter(Boolean);
    }

    this.zoneForm.reset({
      code: zg.code || '',
      nom: zg.nom || '',
      description: zg.description || '',
      regionIds: rIds,
      superficieKm2: zg.superficieKm2 != null ? zg.superficieKm2 : null,
      latitudeCentre: zg.latitudeCentre != null ? zg.latitudeCentre : null,
      longitudeCentre: zg.longitudeCentre != null ? zg.longitudeCentre : null
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

  openViewModal(zg: ZoneGeographiqueItem): void {
    this.selectedItem = zg;
    this.showViewModal = true;
  }

  closeViewModal(): void {
    this.showViewModal = false;
    this.selectedItem = null;
  }

  isStep1Valid(): boolean {
    const code = this.zoneForm.get('code');
    const nom = this.zoneForm.get('nom');
    return !!(code?.valid && nom?.valid);
  }

  isStep2Valid(): boolean {
    const rIds = this.zoneForm.get('regionIds')?.value || [];
    return rIds.length > 0;
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
        return;
      }
    } else if (this.currentStep === 2) {
      if (!this.isStep2Valid()) {
        this.notify('Veuillez sélectionner au moins une région.', 'warning');
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

  selectAllRegions(): void {
    const allIds = this.availableRegions.map(r => r.id);
    this.zoneForm.patchValue({ regionIds: allIds });
    this.zoneForm.get('regionIds')?.markAsTouched();
  }

  deselectAllRegions(): void {
    this.zoneForm.patchValue({ regionIds: [] });
    this.zoneForm.get('regionIds')?.markAsTouched();
  }

  getSelectedRegions(): any[] {
    const ids = (this.zoneForm.get('regionIds')?.value || []) as string[];
    return this.availableRegions.filter(r => ids.includes(r.id));
  }

  onRegionToggle(regionId: string, event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const current = (this.zoneForm.get('regionIds')?.value || []) as string[];
    if (checkbox.checked) {
      this.zoneForm.patchValue({ regionIds: [...current, regionId] });
    } else {
      this.zoneForm.patchValue({ regionIds: current.filter(id => id !== regionId) });
    }
    this.zoneForm.get('regionIds')?.markAsTouched();
  }

  isRegionSelected(regionId: string): boolean {
    const current = (this.zoneForm.get('regionIds')?.value || []) as string[];
    return current.includes(regionId);
  }

  getRegionName(reg: any): string {
    if (!reg) return '';
    if (typeof reg === 'string') {
      const match = this.availableRegions.find(r => r.id === reg || r.nom === reg || r.code === reg);
      return match ? match.nom : reg;
    }
    if (reg.nom) return reg.nom;
    if (reg.regionNom) return reg.regionNom;
    if (reg.region?.nom) return reg.region.nom;
    if (reg.regionId) {
      const match = this.availableRegions.find(r => r.id === reg.regionId);
      if (match) return match.nom;
    }
    if (reg.id) {
      const match = this.availableRegions.find(r => r.id === reg.id);
      if (match) return match.nom;
    }
    return reg.code || 'Région';
  }

  submitCreate(): void {
    const rIds = this.zoneForm.get('regionIds')?.value || [];
    if (rIds.length === 0) {
      this.notify('Veuillez sélectionner au moins une région pour cette zone géographique.');
      return;
    }

    if (this.zoneForm.invalid) {
      this.zoneForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formVal = this.zoneForm.value;
    const payload = {
      ...formVal,
      code: formVal.code.toUpperCase(),
      regionIds: formVal.regionIds
    };

    if (this.isEditMode && this.editingZone) {
      this.geoService.updateZoneGeographique(this.editingZone.id, payload).subscribe({
        next: (updated) => {
          this.isSubmitting = false;
          const idx = this.zones.findIndex(z => z.id === this.editingZone!.id);
          if (idx !== -1) {
            this.zones[idx] = { ...this.zones[idx], ...updated };
          }
          this.applyFilter();
          this.closeCreateModal();
          this.notify('Zone géographique modifiée avec succès !');
        },
        error: (err) => {
          this.isSubmitting = false;
          console.error('Erreur modification zone géographique :', err);
          const errMsg = err?.error?.message || err?.message || 'Erreur lors de la modification de la zone géographique.';
          this.notify('Erreur API : ' + errMsg, 'warning');
        }
      });
    } else {
      this.geoService.createZoneGeographique(payload).subscribe({
        next: (created) => {
          this.isSubmitting = false;
          this.zones.unshift(created);
          this.applyFilter();
          this.closeCreateModal();
          this.notify('Zone géographique créée avec succès !');
        },
        error: (err) => {
          this.isSubmitting = false;
          console.error('Erreur création zone géographique :', err);
          const errMsg = err?.error?.message || err?.message || 'Erreur lors de la création de la zone géographique.';
          this.notify('Erreur API : ' + errMsg, 'warning');
        }
      });
    }
  }

  deleteZone(zone: ZoneGeographiqueItem): void {
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

    this.geoService.deleteZoneGeographique(zone.id).subscribe({
      next: () => {
        this.zones = this.zones.filter(z => z.id !== zone.id);
        this.applyFilter();
        this.notify('Zone géographique supprimée.');
        this.zoneToDelete = null;
        this.isDeleting = false;
      },
      error: () => {
        this.zones = this.zones.filter(z => z.id !== zone.id);
        this.applyFilter();
        this.notify('Zone géographique supprimée (Mode local).');
        this.zoneToDelete = null;
        this.isDeleting = false;
      }
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
