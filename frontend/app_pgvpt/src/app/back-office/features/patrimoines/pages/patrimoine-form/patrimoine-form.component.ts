import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { ToastService } from '../../../../../core/services/toast.service';
import { PatrimoineFormService } from '../../services/patrimoine-form.service';

import { StepIdentificationComponent } from './steps/step-identification/step-identification.component';
import { StepCaracteristiquesComponent } from './steps/step-caracteristiques/step-caracteristiques.component';
import { StepHistoireComponent } from './steps/step-histoire/step-histoire.component';
import { StepProtectionComponent } from './steps/step-protection/step-protection.component';
import { StatusModalComponent } from './steps/status-modal/status-modal.component';

@Component({
  selector: 'app-patrimoine-form',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    RouterModule,
    StepIdentificationComponent,
    StepCaracteristiquesComponent,
    StepHistoireComponent,
    StepProtectionComponent,
    StatusModalComponent
  ],
  templateUrl: './patrimoine-form.component.html',
  styleUrls: ['./patrimoine-form.component.css']
})
export class PatrimoineFormComponent implements OnInit, OnDestroy {
  patrimoineForm!: FormGroup;
  isEditMode = false;
  patrimoineId: string | null = null;
  patrimoineStep: number = 1;

  readonly patrimoineSteps = [
    { step: 1, label: '1. Identification', icon: 'fas fa-id-card' },
    { step: 2, label: '2. Caractéristiques', icon: 'fas fa-sliders-h' },
    { step: 3, label: '3. Histoire & culture', icon: 'fas fa-book-open' },
    { step: 4, label: '4. Protection', icon: 'fas fa-shield-alt' }
  ];

  saveStatus: 'idle' | 'saving' | 'success' | 'error' = 'idle';
  errorMessage: string = '';

  savedNom: string = '';
  savedType: string = '';
  savedCode: string = '';

  get categories(): string[] {
    return this.formService.categories;
  }

  get statuts(): string[] {
    return this.formService.statuts;
  }

  constructor(
    private formService: PatrimoineFormService,
    private patrimoineApiService: PatrimoineApiService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.patrimoineId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.patrimoineId;
    this.initPatrimoineForm();
    if (this.isEditMode) {
      this.loadData();
    }
  }

  initPatrimoineForm(): void {
    this.patrimoineForm = this.formService.createPatrimoineForm();
  }

  // --- Navigation Stepper ---
  setPatrimoineStep(step: number): void {
    this.patrimoineStep = step;
  }

  nextPatrimoineStep(): void {
    if (this.patrimoineStep < 4) this.patrimoineStep++;
  }

  prevPatrimoineStep(): void {
    if (this.patrimoineStep > 1) this.patrimoineStep--;
  }

  loadData(): void {
    if (!this.patrimoineId) return;
    this.patrimoineApiService.getById(this.patrimoineId).subscribe({
      next: (data: any) => {
        this.formService.populateForm(this.patrimoineForm, data);
      },
      error: (err) => {
        console.error('Erreur lors du chargement du patrimoine', err);
        this.showNotification('Erreur de chargement du patrimoine', 'error');
      }
    });
  }

  onSubmit(): void {
    if (this.patrimoineForm.invalid) {
      this.patrimoineForm.markAllAsTouched();
      this.toastService.warning("Veuillez renseigner tous les champs obligatoires (marqués d'une étoile rouge *).");
      return;
    }

    const raw = this.patrimoineForm.value;
    this.savedNom = raw.nom || '';
    this.savedType = raw.type || '';
    this.savedCode = raw.code || '';

    const cleanData = this.formService.buildPayload(raw);
    this.setSaveStatus('saving');
    this.errorMessage = '';

    if (this.isEditMode && this.patrimoineId) {
      this.patrimoineApiService.update(this.patrimoineId, cleanData).subscribe({
        next: (res) => {
          this.savedNom = res?.nom || this.patrimoineForm.get('nom')?.value;
          this.savedType = res?.type || this.patrimoineForm.get('type')?.value;
          this.savedCode = res?.code || this.patrimoineForm.get('code')?.value;
          this.setSaveStatus('success');
          this.showNotification('Patrimoine mis à jour avec succès.');
        },
        error: (err) => {
          this.setSaveStatus('error');
          console.error('Erreur lors de la mise à jour', err);
          this.errorMessage = err?.error?.message || err?.message || 'Erreur lors de la mise à jour du patrimoine.';
          this.showNotification('Erreur lors de la mise à jour du patrimoine.', 'error');
        }
      });
    } else {
      this.patrimoineApiService.create(cleanData).subscribe({
        next: (res) => {
          this.patrimoineId = res?.id || res?.patrimoineId || null;
          this.savedNom = res?.nom || this.patrimoineForm.get('nom')?.value;
          this.savedType = res?.type || this.patrimoineForm.get('type')?.value;
          this.savedCode = res?.code || this.patrimoineForm.get('code')?.value;
          this.setSaveStatus('success');
          this.showNotification('Patrimoine créé avec succès.');
        },
        error: (err) => {
          this.setSaveStatus('error');
          console.error('Erreur lors de la création', err);
          this.errorMessage = err?.error?.message || err?.message || 'Erreur lors de la création du patrimoine.';
          this.showNotification('Erreur lors de la création du patrimoine.', 'error');
        }
      });
    }
  }

  setSaveStatus(status: 'idle' | 'saving' | 'success' | 'error'): void {
    this.saveStatus = status;
    if (typeof document !== 'undefined') {
      if (status !== 'idle') {
        document.body.classList.add('overflow-hidden');
      } else {
        document.body.classList.remove('overflow-hidden');
      }
    }
  }

  ngOnDestroy(): void {
    if (typeof document !== 'undefined') {
      document.body.classList.remove('overflow-hidden');
    }
  }

  createAnother(): void {
    this.setSaveStatus('idle');
    this.isEditMode = false;
    this.patrimoineId = null;
    this.patrimoineStep = 1;
    this.initPatrimoineForm();
    this.router.navigate(['/admin/patrimoines/nouveau']);
  }

  closeStatusModal(): void {
    this.setSaveStatus('idle');
  }

  navigateToPatrimoines(): void {
    this.setSaveStatus('idle');
    this.router.navigate(['/admin/patrimoines']);
  }

  navigateTo(route: string): void {
    if (this.patrimoineId) {
      this.setSaveStatus('idle');
      this.router.navigate([`/admin/patrimoines/${this.patrimoineId}/${route}`]);
    }
  }

  showNotification(msg: string, type: 'success' | 'error' = 'success'): void {
    if (type === 'error') {
      this.toastService.error(msg);
    } else {
      this.toastService.success(msg);
    }
  }
}
