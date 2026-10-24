import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { 
  CircuitFacadeService, 
  CircuitCreateRequest, 
  CircuitUpdateRequest, 
  Activite,
  NiveauDifficulte, 
  TypeCircuit 
} from '../../../../../core/services/circuit-facade.service';
import { GeoFacadeService } from '../../../../../core/services/geo-facade.service';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';
import { ToastService } from '../../../../../core/services/toast.service';
import { CircuitEtapesManagerComponent } from '../../components/circuit-etapes-manager/circuit-etapes-manager.component';

export interface ZoneTouristiqueOption {
  id: string;
  nom: string;
  code?: string;
}

@Component({
  selector: 'app-admin-circuit-form',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    RouterModule, 
    CircuitEtapesManagerComponent
  ],
  templateUrl: './circuit-form.component.html',
  styleUrls: ['./circuit-form.component.css']
})
export class CircuitFormComponent implements OnInit {
  circuitForm!: FormGroup;

  isEditMode: boolean = false;
  circuitId: string | null = null;

  isLoading: boolean = false;
  isSaving: boolean = false;
  errorMessage: string | null = null;

  patrimoinesList: PatrimoineUI[] = [];
  activitesList: Activite[] = [];
  zonesTouristiques: ZoneTouristiqueOption[] = [];

  // Métriques initiales venant du circuit
  initialDuree: number | null = null;
  initialDistance: number | null = null;
  initialPrix: number | null = null;

  types = [
    { value: TypeCircuit.Culturel, label: 'Culturel' },
    { value: TypeCircuit.Historique, label: 'Historique' },
    { value: TypeCircuit.Nature, label: 'Nature & Écotourisme' },
    { value: TypeCircuit.Religieux, label: 'Spirituel & Religieux' },
    { value: TypeCircuit.Gastronomique, label: 'Gastronomique' },
    { value: TypeCircuit.Artisanal, label: 'Artisanal & Coutumier' },
    { value: TypeCircuit.Mixte, label: 'Mixte (Découverte globale)' }
  ];

  difficultes = [
    { value: NiveauDifficulte.Facile, label: 'Facile — Tout public, marche légère' },
    { value: NiveauDifficulte.Moyen, label: 'Moyen — Bonne condition physique requise' },
    { value: NiveauDifficulte.Difficile, label: 'Difficile — Itinéraire engagé, longues distances' }
  ];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private circuitFacade: CircuitFacadeService,
    private geoFacade: GeoFacadeService,
    private patrimoineFacade: PatrimoineFacadeService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadZonesTouristiques();
    this.loadPatrimoines();
    this.loadActivites();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.circuitId = id;
      this.loadCircuit(id);
    }
  }

  private initForm(): void {
    this.circuitForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200)]],
      description: [''],
      type: [TypeCircuit.Culturel, Validators.required],
      zoneTouristiqueId: ['', Validators.required],
      niveauDifficulte: [NiveauDifficulte.Facile, Validators.required],
      dateDebut: [null],
      dateFin: [null],
      actif: [true]
    });
  }

  private loadZonesTouristiques(): void {
    this.geoFacade.getZonesTouristiques().subscribe({
      next: (items) => {
        if (items && items.length > 0) {
          this.zonesTouristiques = items.map((z: any) => ({
            id: z.id,
            nom: z.nom || z.code || 'Zone touristique',
            code: z.code
          }));
          if (!this.isEditMode && !this.circuitForm.get('zoneTouristiqueId')?.value) {
            this.circuitForm.patchValue({ zoneTouristiqueId: this.zonesTouristiques[0].id });
          }
        } else {
          this.zonesTouristiques = [];
        }
      },
      error: () => {
        this.zonesTouristiques = [];
      }
    });
  }

  private loadPatrimoines(): void {
    this.patrimoineFacade.getPatrimoines().subscribe({
      next: (data) => {
        this.patrimoinesList = data;
      },
      error: (err) => console.warn('Erreur chargement patrimoines:', err)
    });
  }

  private loadActivites(): void {
    this.circuitFacade.getActivites().subscribe({
      next: (data) => {
        this.activitesList = data;
      },
      error: (err) => console.warn('Erreur chargement activités:', err)
    });
  }

  private loadCircuit(id: string): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.circuitFacade.getCircuitById(id).subscribe({
      next: (circuit) => {
        if (!circuit) {
          this.errorMessage = 'Circuit introuvable.';
          this.isLoading = false;
          return;
        }

        const dateDebutStr = circuit.dateDebut ? new Date(circuit.dateDebut).toISOString().substring(0, 10) : null;
        const dateFinStr = circuit.dateFin ? new Date(circuit.dateFin).toISOString().substring(0, 10) : null;

        this.circuitForm.patchValue({
          nom: circuit.nom || '',
          description: circuit.description || '',
          type: circuit.type || TypeCircuit.Culturel,
          zoneTouristiqueId: circuit.zoneTouristiqueId || '',
          niveauDifficulte: circuit.niveauDifficulte || NiveauDifficulte.Facile,
          dateDebut: dateDebutStr,
          dateFin: dateFinStr,
          actif: circuit.actif !== false
        });

        this.initialDuree = circuit.dureeMinutes || null;
        this.initialDistance = circuit.distanceKm || null;
        this.initialPrix = circuit.coutTotal || null;

        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement circuit:', err);
        this.errorMessage = 'Impossible de charger les données du circuit.';
        this.isLoading = false;
      }
    });
  }

  // --- Sauvegarde Circuit Principal ---

  onSubmit(): void {
    if (this.circuitForm.invalid) {
      this.circuitForm.markAllAsTouched();
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires correctement.';
      return;
    }

    this.isSaving = true;
    this.errorMessage = null;
    const formValue = this.circuitForm.value;

    if (this.isEditMode && this.circuitId) {
      const updateReq: CircuitUpdateRequest = {
        nom: formValue.nom,
        description: formValue.description,
        type: formValue.type,
        zoneTouristiqueId: formValue.zoneTouristiqueId,
        niveauDifficulte: formValue.niveauDifficulte,
        dateDebut: formValue.dateDebut || null,
        dateFin: formValue.dateFin || null,
        actif: formValue.actif
      };

      this.circuitFacade.updateCircuit(this.circuitId, updateReq).subscribe({
        next: () => {
          this.isSaving = false;
          this.toastService.success('Circuit mis à jour avec succès.');
        },
        error: (err) => {
          console.error('Erreur mise à jour circuit:', err);
          this.errorMessage = 'Une erreur est survenue lors de la mise à jour.';
          this.isSaving = false;
        }
      });

    } else {
      const createReq: CircuitCreateRequest = {
        nom: formValue.nom,
        description: formValue.description,
        type: formValue.type,
        zoneTouristiqueId: formValue.zoneTouristiqueId,
        niveauDifficulte: formValue.niveauDifficulte,
        dateDebut: formValue.dateDebut || null,
        dateFin: formValue.dateFin || null,
        actif: formValue.actif
      };

      this.circuitFacade.createCircuit(createReq).subscribe({
        next: (created) => {
          this.isSaving = false;
          this.toastService.success('Circuit créé avec succès. Vous pouvez maintenant lui ajouter des étapes !');
          if (created.id) {
            this.router.navigate(['/admin/circuits/modifier', created.id]);
          } else {
            this.router.navigate(['/admin/circuits']);
          }
        },
        error: (err) => {
          console.error('Erreur création circuit:', err);
          this.errorMessage = 'Une erreur est survenue lors de la création.';
          this.isSaving = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/admin/circuits']);
  }
}
