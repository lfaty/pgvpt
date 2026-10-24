import { Component, OnInit, OnChanges, Input, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { 
  CircuitFacadeService, 
  EtapeCircuit, 
  EtapeCircuitCreateRequest, 
  OrdreEtape, 
  Activite 
} from '../../../../../core/services/circuit-facade.service';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';
import { ToastService } from '../../../../../core/services/toast.service';

@Component({
  selector: 'app-circuit-etapes-manager',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './circuit-etapes-manager.component.html',
  styleUrls: ['./circuit-etapes-manager.component.css']
})
export class CircuitEtapesManagerComponent implements OnInit, OnChanges {
  @Input({ required: true }) circuitId!: string;
  @Input() patrimoinesList: PatrimoineUI[] = [];
  @Input() activitesList: Activite[] = [];
  @Input() initialDuree: number | null = null;
  @Input() initialDistance: number | null = null;
  @Input() initialPrix: number | null = null;

  etapes: EtapeCircuit[] = [];
  etapeForm!: FormGroup;

  showAddEtapeForm: boolean = false;
  isSavingEtape: boolean = false;
  isLoadingEtapes: boolean = false;

  calculatedDuree: number | null = null;
  calculatedDistance: number | null = null;
  calculatedPrix: number | null = null;

  constructor(
    private fb: FormBuilder,
    private circuitFacade: CircuitFacadeService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.initEtapeForm();
    if (this.circuitId) {
      this.loadEtapes();
    }
    this.calculatedDuree = this.initialDuree;
    this.calculatedDistance = this.initialDistance;
    this.calculatedPrix = this.initialPrix;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['circuitId'] && this.circuitId && !changes['circuitId'].isFirstChange()) {
      this.loadEtapes();
    }
    if (changes['patrimoinesList'] && this.etapeForm && !this.etapeForm.get('patrimoineId')?.value && this.patrimoinesList.length > 0) {
      this.etapeForm.patchValue({ patrimoineId: this.patrimoinesList[0].id });
    }
    if (changes['activitesList'] && this.etapeForm && !this.etapeForm.get('activiteId')?.value && this.activeActivitesList.length > 0) {
      this.etapeForm.patchValue({ activiteId: this.activeActivitesList[0].id });
    }
    if (changes['initialDuree'] && this.initialDuree !== undefined) {
      this.calculatedDuree = this.initialDuree;
    }
    if (changes['initialDistance'] && this.initialDistance !== undefined) {
      this.calculatedDistance = this.initialDistance;
    }
    if (changes['initialPrix'] && this.initialPrix !== undefined) {
      this.calculatedPrix = this.initialPrix;
    }
  }

  get activeActivitesList(): Activite[] {
    return this.activitesList.filter(a => a.actif !== false);
  }

  private initEtapeForm(): void {
    this.etapeForm = this.fb.group({
      patrimoineId: [this.patrimoinesList[0]?.id || '', Validators.required],
      activiteId: [this.activeActivitesList[0]?.id || '', Validators.required],
      dureeMinutes: [60, [Validators.required, Validators.min(1)]],
      distanceDepuisPrecedenteKm: [10, [Validators.min(0)]],
      prix: [0, [Validators.required, Validators.min(0)]],
      description: [''],
      obligatoire: [true]
    });
  }

  loadEtapes(): void {
    if (!this.circuitId) return;
    this.isLoadingEtapes = true;
    this.circuitFacade.getEtapesByCircuitId(this.circuitId).subscribe({
      next: (data) => {
        this.etapes = data;
        this.isLoadingEtapes = false;
      },
      error: (err) => {
        console.warn('Erreur chargement étapes:', err);
        this.isLoadingEtapes = false;
      }
    });
  }

  onAjouterEtape(): void {
    if (this.etapeForm.invalid || !this.circuitId) {
      this.etapeForm.markAllAsTouched();
      return;
    }

    this.isSavingEtape = true;
    const v = this.etapeForm.value;
    const nextOrdre = this.etapes.length + 1;

    const req: EtapeCircuitCreateRequest = {
      patrimoineId: v.patrimoineId,
      activiteId: v.activiteId,
      ordre: nextOrdre,
      dureeMinutes: v.dureeMinutes,
      distanceDepuisPrecedenteKm: v.distanceDepuisPrecedenteKm,
      prix: v.prix,
      devise: 'XOF',
      description: v.description,
      obligatoire: v.obligatoire !== false,
      actif: true
    };

    this.circuitFacade.createEtape(this.circuitId, req).subscribe({
      next: (nouvelleEtape) => {
        this.etapes.push(nouvelleEtape);
        this.isSavingEtape = false;
        this.showAddEtapeForm = false;
        this.toastService.success(`L'étape ${nextOrdre} a été ajoutée au parcours !`);
        this.recalculerMetriques();
      },
      error: (err) => {
        console.error('Erreur ajout étape:', err);
        this.toastService.error('Impossible d\'ajouter l\'étape.');
        this.isSavingEtape = false;
      }
    });
  }

  supprimerEtape(etape: EtapeCircuit): void {
    if (!etape.id || !this.circuitId) return;

    this.circuitFacade.deleteEtape(etape.id).subscribe({
      next: () => {
        this.etapes = this.etapes.filter(e => e.id !== etape.id);
        this.synchroniserOrdres();
        this.toastService.success(`Étape ${etape.ordre} retirée.`);
        this.recalculerMetriques();
      },
      error: (err) => {
        console.error('Erreur suppression étape:', err);
        this.toastService.error('Erreur lors de la suppression de l\'étape.');
      }
    });
  }

  monterEtape(index: number): void {
    if (index <= 0 || !this.circuitId) return;
    const temp = this.etapes[index];
    this.etapes[index] = this.etapes[index - 1];
    this.etapes[index - 1] = temp;
    this.synchroniserOrdres();
  }

  descendreEtape(index: number): void {
    if (index >= this.etapes.length - 1 || !this.circuitId) return;
    const temp = this.etapes[index];
    this.etapes[index] = this.etapes[index + 1];
    this.etapes[index + 1] = temp;
    this.synchroniserOrdres();
  }

  private synchroniserOrdres(): void {
    if (!this.circuitId) return;

    const ordres: OrdreEtape[] = this.etapes.map((e, idx) => {
      (e as any).ordre = idx + 1;
      return {
        etapeId: e.id!,
        ordre: idx + 1
      };
    });

    this.circuitFacade.reordonnerEtapes(this.circuitId, ordres).subscribe({
      next: () => {
        this.toastService.success('Ordre des étapes mis à jour.');
      },
      error: (err) => console.warn('Erreur synchro ordres:', err)
    });
  }

  recalculerMetriques(): void {
    if (!this.circuitId) return;

    this.circuitFacade.calculerDuree(this.circuitId).subscribe({
      next: (res) => this.calculatedDuree = res.dureeTotaleMinutes || null
    });
    this.circuitFacade.calculerDistance(this.circuitId).subscribe({
      next: (res) => this.calculatedDistance = res.distanceTotaleKm || null
    });
    this.circuitFacade.calculerPrix(this.circuitId).subscribe({
      next: (res) => this.calculatedPrix = res.prixTotal || null
    });
  }

  getPatrimoineNom(patrimoineId: string): string {
    const found = this.patrimoinesList.find(p => p.id === patrimoineId);
    return found ? found.nom : `Patrimoine (${patrimoineId.substring(0, 8)}...)`;
  }

  getActiviteNom(activiteId?: string): string {
    if (!activiteId) return 'Visite générale';
    const found = this.activitesList.find(a => a.id === activiteId);
    return found ? found.libelle : 'Activité';
  }
}
