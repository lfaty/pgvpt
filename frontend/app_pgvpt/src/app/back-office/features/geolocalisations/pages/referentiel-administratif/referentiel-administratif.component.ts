import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { GeolocalisationApiService } from '../../../../../core/services/api/geolocalisation-api.service';
import { ToastService } from '../../../../../core/services/toast.service';

import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-referentiel-administratif',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, ConfirmModalComponent],
  templateUrl: './referentiel-administratif.component.html',
  styleUrls: ['./referentiel-administratif.component.css']
})
export class ReferentielAdministratifComponent implements OnInit {
  activeTab: 'pays' | 'regions' | 'departements' | 'communes' | 'villages' | 'quartiers' = 'regions';
  searchQuery = '';

  // Données enregistrées dans notre base de données locale
  pays: any[] = [];
  regions: any[] = [];
  departements: any[] = [];
  communes: any[] = [];
  villages: any[] = [];
  quartiers: any[] = [];

  // Modale de Création / Modification
  showCreateModal = false;
  isSubmitting = false;
  isEditMode = false;
  editingItem: any = null;
  createForm!: FormGroup;

  // Modale de Consultation (Voir)
  showViewModal = false;
  selectedItem: any = null;

  // Modale de Suppression
  itemToDelete: any = null;
  isDeleting = false;

  constructor(
    private fb: FormBuilder,
    private geoService: GeolocalisationApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initCreateForm();
    this.loadAllData();
  }

  initCreateForm(): void {
    this.createForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(2)]],
      nom: ['', [Validators.required, Validators.minLength(2)]],
      parentId: [''],
      devise: ['FCFA'],
      description: ['']
    });
  }

  loadAllData(): void {
    this.geoService.getPays().subscribe({
      next: (data) => this.pays = Array.isArray(data) ? data : [],
      error: () => this.pays = []
    });

    this.geoService.getRegions().subscribe({
      next: (data) => this.regions = Array.isArray(data) ? data : [],
      error: () => this.regions = []
    });

    this.geoService.getDepartements().subscribe({
      next: (data) => this.departements = Array.isArray(data) ? data : [],
      error: () => this.departements = []
    });

    this.geoService.getCommunes().subscribe({
      next: (data) => this.communes = Array.isArray(data) ? data : [],
      error: () => this.communes = []
    });

    this.geoService.getVillages().subscribe({
      next: (data) => this.villages = Array.isArray(data) ? data : [],
      error: () => this.villages = []
    });

    this.geoService.getQuartiers().subscribe({
      next: (data) => this.quartiers = Array.isArray(data) ? data : [],
      error: () => this.quartiers = []
    });
  }

  get currentItems(): any[] {
    let list: any[] = [];
    if (this.activeTab === 'pays') list = this.pays;
    else if (this.activeTab === 'regions') list = this.regions;
    else if (this.activeTab === 'departements') list = this.departements;
    else if (this.activeTab === 'communes') list = this.communes;
    else if (this.activeTab === 'villages') list = this.villages;
    else list = this.quartiers;

    const q = this.searchQuery.toLowerCase().trim();
    if (!q) return list;
    return list.filter(item =>
      item.nom?.toLowerCase().includes(q) ||
      item.code?.toLowerCase().includes(q) ||
      item.regionNom?.toLowerCase().includes(q) ||
      item.departementNom?.toLowerCase().includes(q) ||
      item.communeNom?.toLowerCase().includes(q) ||
      item.villageNom?.toLowerCase().includes(q)
    );
  }

  setTab(tab: 'pays' | 'regions' | 'departements' | 'communes' | 'villages' | 'quartiers'): void {
    this.activeTab = tab;
    this.searchQuery = '';
  }

  // --- Consultation (Voir) ---

  openViewModal(item: any): void {
    this.selectedItem = item;
    this.showViewModal = true;
  }

  closeViewModal(): void {
    this.showViewModal = false;
    this.selectedItem = null;
  }

  editFromView(): void {
    const item = this.selectedItem;
    this.closeViewModal();
    if (item) {
      this.openEditModal(item);
    }
  }

  // --- Création & Modification ---

  openCreateModal(): void {
    this.isSubmitting = false;
    this.isEditMode = false;
    this.editingItem = null;

    const defaultParentId =
      this.activeTab === 'regions' && this.pays.length > 0 ? this.pays[0].id :
      this.activeTab === 'departements' && this.regions.length > 0 ? this.regions[0].id :
      this.activeTab === 'communes' && this.departements.length > 0 ? this.departements[0].id :
      this.activeTab === 'villages' && this.communes.length > 0 ? this.communes[0].id :
      this.activeTab === 'quartiers' && this.villages.length > 0 ? this.villages[0].id : '';

    this.createForm.reset({
      code: '',
      nom: '',
      parentId: defaultParentId,
      devise: 'FCFA',
      description: ''
    });

    this.showCreateModal = true;
  }

  openEditModal(item: any): void {
    this.isSubmitting = false;
    this.isEditMode = true;
    this.editingItem = item;

    let parentId = '';
    if (this.activeTab === 'regions') {
      parentId = item.paysId || item.pays?.id || (this.pays.length > 0 ? this.pays[0].id : '');
    } else if (this.activeTab === 'departements') {
      parentId = item.regionId || item.region?.id || '';
    } else if (this.activeTab === 'communes') {
      parentId = item.departementId || item.departement?.id || '';
    } else if (this.activeTab === 'villages') {
      parentId = item.communeId || item.commune?.id || '';
    } else if (this.activeTab === 'quartiers') {
      parentId = item.villageId || item.village?.id || '';
    }

    this.createForm.reset({
      code: item.code || item.codeIso || '',
      nom: item.nom || item.libelle || '',
      parentId: parentId,
      devise: item.devise || 'FCFA',
      description: item.description || ''
    });

    this.showCreateModal = true;
  }

  closeCreateModal(): void {
    this.showCreateModal = false;
    this.isSubmitting = false;
    this.isEditMode = false;
    this.editingItem = null;
  }

  submitSave(): void {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const val = this.createForm.value;

    if (this.isEditMode && this.editingItem) {
      this.executeEdit(val);
    } else {
      this.executeCreate(val);
    }
  }

  private executeCreate(val: any): void {
    if (this.activeTab === 'pays') {
      const payload = {
        nom: val.nom,
        code: val.code.toUpperCase(),
        devise: val.devise || 'FCFA',
        codeIso2: 'SN',
        codeIso3: 'SEN',
        codeDevise: 'XOF',
        indicatifTelephonique: '+221',
        continent: 'Afrique',
        langueOfficielle: 'Français',
        actif: true
      };
      this.geoService.createPays(payload).subscribe({
        next: (created) => {
          this.pays.unshift(created);
          this.closeCreateModal();
          this.notify('Pays enregistré en base avec succès !');
        },
        error: (err) => this.handleError('création pays', err)
      });
    } else if (this.activeTab === 'regions') {
      const selectedPaysId = val.parentId || (this.pays.length > 0 ? this.pays[0].id : null);
      const payload = {
        code: val.code.toUpperCase(),
        nom: val.nom,
        paysId: selectedPaysId,
        description: val.description
      };
      this.geoService.createRegion(payload).subscribe({
        next: (created) => {
          this.regions.unshift(created);
          this.closeCreateModal();
          this.notify(`Région "${created.nom}" enregistrée en base avec succès !`);
        },
        error: (err) => this.handleError('création région', err)
      });
    } else if (this.activeTab === 'departements') {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner la région de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, regionId: val.parentId, description: val.description };
      this.geoService.createDepartement(payload).subscribe({
        next: (created) => {
          const reg = this.regions.find(r => r.id === val.parentId);
          this.departements.unshift({ ...created, regionNom: reg ? reg.nom : '' });
          this.closeCreateModal();
          this.notify(`Département "${created.nom}" enregistré avec succès !`);
        },
        error: (err) => this.handleError('création département', err)
      });
    } else if (this.activeTab === 'communes') {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner le département de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, departementId: val.parentId, description: val.description };
      this.geoService.createCommune(payload).subscribe({
        next: (created) => {
          const dept = this.departements.find(d => d.id === val.parentId);
          this.communes.unshift({ ...created, departementNom: dept ? dept.nom : '' });
          this.closeCreateModal();
          this.notify(`Commune "${created.nom}" enregistrée avec succès !`);
        },
        error: (err) => this.handleError('création commune', err)
      });
    } else if (this.activeTab === 'villages') {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner la commune de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, communeId: val.parentId, description: val.description };
      this.geoService.createVillage(payload).subscribe({
        next: (created) => {
          const com = this.communes.find(c => c.id === val.parentId);
          this.villages.unshift({ ...created, communeNom: com ? com.nom : '' });
          this.closeCreateModal();
          this.notify(`Village "${created.nom}" enregistré avec succès !`);
        },
        error: (err) => this.handleError('création village', err)
      });
    } else {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner le village de rattachement.', 'warning');
        return;
      }
      const selectedVillage = this.villages.find(v => v.id === val.parentId);
      const communeId = selectedVillage?.communeId || selectedVillage?.commune?.id || (this.communes.length > 0 ? this.communes[0].id : null);
      const payload = {
        code: val.code.toUpperCase(),
        nom: val.nom,
        villageId: val.parentId,
        communeId: communeId,
        description: val.description
      };
      this.geoService.createQuartier(payload).subscribe({
        next: (created) => {
          this.quartiers.unshift({ ...created, villageNom: selectedVillage ? selectedVillage.nom : '' });
          this.closeCreateModal();
          this.notify(`Quartier "${created.nom}" enregistré avec succès !`);
        },
        error: (err) => this.handleError('création quartier', err)
      });
    }
  }

  private executeEdit(val: any): void {
    const id = this.editingItem.id;

    if (this.activeTab === 'pays') {
      const payload = {
        nom: val.nom,
        code: val.code.toUpperCase(),
        devise: val.devise || 'FCFA',
        description: val.description,
        actif: true
      };
      this.geoService.updatePays(id, payload).subscribe({
        next: (updated) => {
          const idx = this.pays.findIndex(p => p.id === id);
          if (idx !== -1) this.pays[idx] = { ...this.pays[idx], ...updated };
          this.closeCreateModal();
          this.notify(`Pays "${updated.nom}" mis à jour avec succès !`);
        },
        error: (err) => this.handleError('modification pays', err)
      });
    } else if (this.activeTab === 'regions') {
      const selectedPaysId = val.parentId || (this.pays.length > 0 ? this.pays[0].id : null);
      const payload = {
        code: val.code.toUpperCase(),
        nom: val.nom,
        paysId: selectedPaysId,
        description: val.description
      };
      this.geoService.updateRegion(id, payload).subscribe({
        next: (updated) => {
          const idx = this.regions.findIndex(r => r.id === id);
          if (idx !== -1) this.regions[idx] = { ...this.regions[idx], ...updated };
          this.closeCreateModal();
          this.notify(`Région "${updated.nom}" mise à jour avec succès !`);
        },
        error: (err) => this.handleError('modification région', err)
      });
    } else if (this.activeTab === 'departements') {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner la région de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, regionId: val.parentId, description: val.description };
      this.geoService.updateDepartement(id, payload).subscribe({
        next: (updated) => {
          const reg = this.regions.find(r => r.id === val.parentId);
          const idx = this.departements.findIndex(d => d.id === id);
          if (idx !== -1) {
            this.departements[idx] = { ...this.departements[idx], ...updated, regionNom: reg ? reg.nom : '' };
          }
          this.closeCreateModal();
          this.notify(`Département "${updated.nom}" mis à jour avec succès !`);
        },
        error: (err) => this.handleError('modification département', err)
      });
    } else if (this.activeTab === 'communes') {
      if (!val.parentId) {
        this.isSubmitting = false;
        this.notify('Veuillez sélectionner le département de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, departementId: val.parentId, description: val.description };
      this.geoService.updateCommune(id, payload).subscribe({
        next: (updated) => {
          const dept = this.departements.find(d => d.id === val.parentId);
          const idx = this.communes.findIndex(c => c.id === id);
          if (idx !== -1) {
            this.communes[idx] = { ...this.communes[idx], ...updated, departementNom: dept ? dept.nom : '' };
          }
          this.closeCreateModal();
          this.notify(`Commune "${updated.nom}" mise à jour avec succès !`);
        },
        error: (err) => this.handleError('modification commune', err)
      });
    } else if (this.activeTab === 'villages') {
      if (!val.parentId) {
        this.notify('Veuillez sélectionner la commune de rattachement.', 'warning');
        return;
      }
      const payload = { code: val.code.toUpperCase(), nom: val.nom, communeId: val.parentId, description: val.description };
      this.geoService.updateVillage(id, payload).subscribe({
        next: (updated) => {
          const com = this.communes.find(c => c.id === val.parentId);
          const idx = this.villages.findIndex(v => v.id === id);
          if (idx !== -1) {
            this.villages[idx] = { ...this.villages[idx], ...updated, communeNom: com ? com.nom : '' };
          }
          this.closeCreateModal();
          this.notify(`Village "${updated.nom}" mis à jour avec succès !`);
        },
        error: (err) => this.handleError('modification village', err)
      });
    } else {
      const payload = { code: val.code.toUpperCase(), nom: val.nom, description: val.description };
      this.geoService.updateQuartier(id, payload).subscribe({
        next: (updated) => {
          const idx = this.quartiers.findIndex(q => q.id === id);
          if (idx !== -1) {
            this.quartiers[idx] = { ...this.quartiers[idx], ...updated };
          }
          this.closeCreateModal();
          this.notify(`Quartier "${updated.nom}" mis à jour avec succès !`);
        },
        error: (err) => this.handleError('modification quartier', err)
      });
    }
  }

  deleteItem(item: any): void {
    this.itemToDelete = item;
  }

  cancelDeleteItem(): void {
    this.itemToDelete = null;
    this.isDeleting = false;
  }

  confirmDeleteItem(): void {
    if (!this.itemToDelete) return;
    const item = this.itemToDelete;
    this.isDeleting = true;

    const onComplete = (msg: string) => {
      this.isDeleting = false;
      this.itemToDelete = null;
      this.notify(msg);
      this.loadAllData();
    };

    const onError = (action: string, err: any) => {
      this.isDeleting = false;
      this.handleError(action, err);
    };

    if (this.activeTab === 'pays') {
      this.geoService.deletePays(item.id).subscribe({
        next: () => onComplete('Pays et ses dépendances supprimés en cascade.'),
        error: (err) => onError('suppression pays', err)
      });
    } else if (this.activeTab === 'regions') {
      this.geoService.deleteRegion(item.id).subscribe({
        next: () => onComplete('Région et ses dépendances supprimées en cascade.'),
        error: (err) => onError('suppression région', err)
      });
    } else if (this.activeTab === 'departements') {
      this.geoService.deleteDepartement(item.id).subscribe({
        next: () => onComplete('Département et ses dépendances supprimés en cascade.'),
        error: (err) => onError('suppression département', err)
      });
    } else if (this.activeTab === 'communes') {
      this.geoService.deleteCommune(item.id).subscribe({
        next: () => onComplete('Commune et ses dépendances supprimées en cascade.'),
        error: (err) => onError('suppression commune', err)
      });
    } else if (this.activeTab === 'villages') {
      this.geoService.deleteVillage(item.id).subscribe({
        next: () => onComplete('Village et ses quartiers supprimés en cascade.'),
        error: (err) => onError('suppression village', err)
      });
    } else {
      this.geoService.deleteQuartier(item.id).subscribe({
        next: () => onComplete('Quartier supprimé.'),
        error: (err) => onError('suppression quartier', err)
      });
    }
  }

  getParentName(item: any): string {
    if (!item) return '-';
    if (this.activeTab === 'regions') {
      return item.paysNom || item.pays?.nom || 'Sénégal';
    } else if (this.activeTab === 'departements') {
      if (item.regionNom) return item.regionNom;
      const reg = this.regions.find(r => r.id === (item.regionId || item.region?.id));
      return reg ? reg.nom : (item.regionId || '-');
    } else if (this.activeTab === 'communes') {
      if (item.departementNom) return item.departementNom;
      const dept = this.departements.find(d => d.id === (item.departementId || item.departement?.id));
      return dept ? dept.nom : (item.departementId || '-');
    } else if (this.activeTab === 'villages') {
      if (item.communeNom) return item.communeNom;
      const com = this.communes.find(c => c.id === (item.communeId || item.commune?.id));
      return com ? com.nom : (item.communeId || '-');
    } else if (this.activeTab === 'quartiers') {
      if (item.villageNom) return item.villageNom;
      if (item.village?.nom) return item.village.nom;
      const vil = this.villages.find(v => v.id === (item.villageId || item.village?.id));
      return vil ? vil.nom : (item.commune?.nom || '-');
    } else {
      return item.continent || 'Afrique';
    }
  }

  private handleError(action: string, err: any): void {
    this.isSubmitting = false;
    console.error(`Erreur ${action} :`, err);
    const msg = err?.error?.message || err?.message || 'Une erreur est survenue';
    this.notify(`Erreur lors de la ${action} : ${msg}`, 'warning');
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
