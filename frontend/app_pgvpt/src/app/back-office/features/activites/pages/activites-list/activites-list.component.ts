import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { 
  CircuitFacadeService, 
  Activite, 
  ActiviteCreateRequest, 
  ActiviteUpdateRequest 
} from '../../../../../core/services/circuit-facade.service';

@Component({
  selector: 'app-activites-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './activites-list.component.html',
  styleUrls: ['./activites-list.component.css']
})
export class ActivitesListComponent implements OnInit {
  activites: Activite[] = [];
  filteredActivites: Activite[] = [];
  isLoading: boolean = false;
  isSaving: boolean = false;

  // Filtres
  searchQuery: string = '';
  selectedStatut: 'TOUS' | 'ACTIF' | 'INACTIF' = 'TOUS';

  // Modal Création / Modification
  showModal: boolean = false;
  isEditMode: boolean = false;
  selectedActiviteId: string | null = null;
  activiteForm!: FormGroup;

  // Modal Confirmation Suppression
  activiteToDelete: Activite | null = null;

  // Notifications
  notificationMessage: string | null = null;
  notificationType: 'success' | 'error' = 'success';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private circuitFacade: CircuitFacadeService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadActivites();

    // Si on arrive depuis le menu "Nouvelle activité" (/admin/activites?action=nouveau ou /admin/activites/nouveau)
    this.route.queryParams.subscribe(params => {
      if (params['action'] === 'nouveau') {
        this.openCreateModal();
      }
    });

    if (this.route.snapshot.url.some(segment => segment.path === 'nouveau')) {
      this.openCreateModal();
    }
  }

  private initForm(): void {
    this.activiteForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50), Validators.pattern(/^[A-Z0-9_-]+$/)]],
      libelle: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]],
      prixInitial: [0, [Validators.required, Validators.min(0)]],
      description: ['', [Validators.maxLength(1000)]],
      actif: [true]
    });
  }

  loadActivites(): void {
    this.isLoading = true;
    this.circuitFacade.getActivites().subscribe({
      next: (data) => {
        this.activites = data || [];
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement des activités:', err);
        this.activites = [];
        this.applyFilters();
        this.isLoading = false;
        this.showToast('Erreur lors du chargement des activités.', 'error');
      }
    });
  }

  // --- Filtres et KPIs ---

  get totalCount(): number {
    return this.activites.length;
  }

  get actifCount(): number {
    return this.activites.filter(a => a.actif !== false).length;
  }

  get inactifCount(): number {
    return this.activites.filter(a => a.actif === false).length;
  }

  applyFilters(): void {
    const q = this.searchQuery.trim().toLowerCase();
    this.filteredActivites = this.activites.filter(a => {
      const matchSearch = !q ||
        (a.code && a.code.toLowerCase().includes(q)) ||
        (a.libelle && a.libelle.toLowerCase().includes(q)) ||
        (a.description && a.description.toLowerCase().includes(q));

      const isActif = a.actif !== false;
      const matchStatut = this.selectedStatut === 'TOUS' ||
        (this.selectedStatut === 'ACTIF' && isActif) ||
        (this.selectedStatut === 'INACTIF' && !isActif);

      return matchSearch && matchStatut;
    });
  }

  setStatutFilter(statut: 'TOUS' | 'ACTIF' | 'INACTIF'): void {
    this.selectedStatut = statut;
    this.applyFilters();
  }

  onSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchQuery = input.value;
    this.applyFilters();
  }

  // --- Gestion du Formulaire (Modal) ---

  openCreateModal(): void {
    this.isEditMode = false;
    this.selectedActiviteId = null;
    this.activiteForm.reset({
      code: '',
      libelle: '',
      prixInitial: 0,
      description: '',
      actif: true
    });
    this.showModal = true;
  }

  openEditModal(activite: Activite): void {
    this.isEditMode = true;
    this.selectedActiviteId = activite.id || null;
    this.activiteForm.patchValue({
      code: activite.code,
      libelle: activite.libelle,
      prixInitial: activite.prixInitial || 0,
      description: activite.description || '',
      actif: activite.actif !== false
    });
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedActiviteId = null;
    this.activiteForm.reset();
  }

  // Forcer les majuscules sur le code activité
  onCodeInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    const upper = input.value.toUpperCase().replace(/[^A-Z0-9_-]/g, '');
    this.activiteForm.get('code')?.setValue(upper, { emitEvent: false });
  }

  submitForm(): void {
    if (this.activiteForm.invalid) {
      this.activiteForm.markAllAsTouched();
      return;
    }

    this.isSaving = true;
    const formVal = this.activiteForm.value;

    if (this.isEditMode && this.selectedActiviteId) {
      const updateReq: ActiviteUpdateRequest = {
        code: formVal.code.trim().toUpperCase(),
        libelle: formVal.libelle.trim(),
        prixInitial: Number(formVal.prixInitial) || 0,
        description: formVal.description ? formVal.description.trim() : '',
        actif: formVal.actif
      };

      this.circuitFacade.updateActivite(this.selectedActiviteId, updateReq).subscribe({
        next: (updated) => {
          this.isSaving = false;
          this.closeModal();
          this.showToast(`L'activité "${updated.libelle}" a été mise à jour avec succès.`, 'success');
          this.loadActivites();
        },
        error: (err) => {
          this.isSaving = false;
          console.error('Erreur modification activité:', err);
          this.showToast('Erreur lors de la modification de l\'activité.', 'error');
        }
      });
    } else {
      const createReq: ActiviteCreateRequest = {
        code: formVal.code.trim().toUpperCase(),
        libelle: formVal.libelle.trim(),
        prixInitial: Number(formVal.prixInitial) || 0,
        description: formVal.description ? formVal.description.trim() : '',
        actif: formVal.actif !== false
      };

      this.circuitFacade.createActivite(createReq).subscribe({
        next: (created) => {
          this.isSaving = false;
          this.closeModal();
          this.showToast(`L'activité "${created.libelle}" a été créée avec succès.`, 'success');
          this.loadActivites();
        },
        error: (err) => {
          this.isSaving = false;
          console.error('Erreur création activité:', err);
          this.showToast('Erreur lors de la création de l\'activité. Vérifiez l\'unicité du code.', 'error');
        }
      });
    }
  }

  // --- Activation / Désactivation ---

  toggleActif(activite: Activite): void {
    const id = activite.id;
    if (!id) return;
    const nouvelEtat = activite.actif === false;

    const req$ = nouvelEtat 
      ? this.circuitFacade.activerActivite(id) 
      : this.circuitFacade.desactiverActivite(id);

    req$.subscribe({
      next: () => {
        activite.actif = nouvelEtat;
        this.applyFilters();
        this.showToast(
          `Activité ${activite.libelle} ${nouvelEtat ? 'activée' : 'désactivée'}.`, 
          'success'
        );
      },
      error: () => {
        // Fallback avec update complet si le microservice ne supporte pas patch
        const fallbackReq: ActiviteUpdateRequest = {
          code: activite.code,
          libelle: activite.libelle,
          prixInitial: activite.prixInitial,
          description: activite.description,
          actif: nouvelEtat
        };
        this.circuitFacade.updateActivite(id, fallbackReq).subscribe({
          next: () => {
            activite.actif = nouvelEtat;
            this.applyFilters();
            this.showToast(`Activité ${activite.libelle} mise à jour.`, 'success');
          },
          error: (err) => {
            console.error('Erreur changement état activité:', err);
            this.showToast('Impossible de changer le statut de cette activité.', 'error');
          }
        });
      }
    });
  }

  // --- Suppression ---

  confirmDelete(activite: Activite): void {
    this.activiteToDelete = activite;
  }

  cancelDelete(): void {
    this.activiteToDelete = null;
  }

  executeDelete(): void {
    if (!this.activiteToDelete || !this.activiteToDelete.id) return;

    const id = this.activiteToDelete.id;
    const libelle = this.activiteToDelete.libelle;

    this.circuitFacade.deleteActivite(id).subscribe({
      next: () => {
        this.activiteToDelete = null;
        this.showToast(`L'activité "${libelle}" a été supprimée avec succès.`, 'success');
        this.loadActivites();
      },
      error: (err) => {
        console.error('Erreur suppression activité:', err);
        this.activiteToDelete = null;
        this.showToast('Impossible de supprimer cette activité (elle est peut-être liée à des étapes de circuits).', 'error');
      }
    });
  }

  private showToast(message: string, type: 'success' | 'error' = 'success'): void {
    this.notificationMessage = message;
    this.notificationType = type;
    setTimeout(() => {
      this.notificationMessage = null;
    }, 4500);
  }
}
