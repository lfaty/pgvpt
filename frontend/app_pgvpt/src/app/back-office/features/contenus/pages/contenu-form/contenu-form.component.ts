import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';
import { ContenuApiService } from '../../../../../core/services/api/contenu-api.service';
import { MediaApiService } from '../../../../../core/services/api/media-api.service';

@Component({
  selector: 'app-contenu-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './contenu-form.component.html',
  styleUrls: ['./contenu-form.component.css']
})
export class ContenuFormComponent implements OnInit {
  contenuForm!: FormGroup;
  isEditMode = false;
  contenuId: string | null = null;
  patrimoineId: string | null = null;
  nomPatrimoine: string = '';

  patrimoinesList: any[] = [];
  availableMedias: any[] = [];
  selectedMediaIds: string[] = [];

  // État du modal d'enregistrement
  saveStatus: 'idle' | 'saving' | 'success' | 'error' = 'idle';
  errorMessage: string = '';

  types = [
    'ARTICLE', 'DESCRIPTION', 'HISTORIQUE', 'RECIT', 'TEMOIGNAGE', 'GUIDE_VISITE', 'ACTUALITE'
  ];
  statuts = [
    'BROUILLON', 'EN_REVISION', 'VALIDE', 'PUBLIE', 'ARCHIVE'
  ];
  langues = [
    { code: 'fr', label: 'Français' },
    { code: 'en', label: 'Anglais' },
    { code: 'wo', label: 'Wolof' }
  ];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private patrimoineApi: PatrimoineApiService,
    private contenuApi: ContenuApiService,
    private mediaApi: MediaApiService
  ) { }

  ngOnInit(): void {
    const routeId = this.route.snapshot.paramMap.get('id');
    const isFromPatrimoine = this.router.url.includes('/patrimoines/');

    if (isFromPatrimoine) {
      this.patrimoineId = routeId;
      this.loadPatrimoine();
    } else if (routeId) {
      this.contenuId = routeId;
      this.isEditMode = true;
    } else {
      // Accès direct /admin/contenus/nouveau : on charge les patrimoines pour la sélection
      this.loadAllPatrimoines();
    }

    this.initForm();

    if (this.isEditMode) {
      this.loadContenu();
    }
  }

  loadAllPatrimoines(): void {
    this.patrimoineApi.getAll().subscribe({
      next: (list: any[]) => {
        this.patrimoinesList = list || [];
      },
      error: (err: any) => console.error('Erreur chargement liste patrimoines', err)
    });
  }

  onPatrimoineChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const selectedId = select?.value;
    if (selectedId) {
      this.patrimoineId = selectedId;
      this.loadPatrimoine();
    }
  }

  loadPatrimoine(): void {
    if (!this.patrimoineId) return;
    this.patrimoineApi.getById(this.patrimoineId).subscribe({
      next: (p: any) => {
        this.nomPatrimoine = p.nom;
        this.loadMedias();
      },
      error: (err: any) => console.error('Erreur chargement patrimoine', err)
    });
  }

  loadMedias(): void {
    if (!this.patrimoineId) {
      this.availableMedias = [];
      return;
    }
    this.mediaApi.getByPatrimoineId(this.patrimoineId).subscribe({
      next: (medias: any[]) => {
        this.availableMedias = medias || [];
      },
      error: (err: any) => console.error('Erreur chargement medias', err)
    });
  }

  toggleMedia(id: string): void {
    if (this.selectedMediaIds.includes(id)) {
      this.selectedMediaIds = this.selectedMediaIds.filter(mId => mId !== id);
    } else {
      this.selectedMediaIds.push(id);
    }
  }

  isMediaSelected(id: string): boolean {
    return this.selectedMediaIds.includes(id);
  }

  loadContenu(): void {
    if (!this.contenuId) return;
    this.contenuApi.getById(this.contenuId).subscribe({
      next: (data: any) => {
        this.patrimoineId = data.patrimoineId;
        this.selectedMediaIds = data.mediaIds || [];
        this.loadPatrimoine();
        this.contenuForm.patchValue({
          patrimoineId: data.patrimoineId,
          titre: data.titre,
          type: data.type,
          langue: data.langue,
          statut: data.statut || 'BROUILLON',
          resume: data.resume,
          corps: data.corps,
          motsCles: Array.isArray(data.motsCles) ? data.motsCles.join(', ') : (data.motsCles || '')
        });
      },
      error: (err) => console.error('Erreur chargement contenu', err)
    });
  }

  initForm(): void {
    this.contenuForm = this.fb.group({
      patrimoineId: [this.patrimoineId, Validators.required],
      titre: ['', Validators.required],
      type: ['ARTICLE', Validators.required],
      langue: ['fr', Validators.required],
      statut: ['BROUILLON', Validators.required],
      resume: [''],
      corps: ['', Validators.required],
      motsCles: ['']
    });
  }

  closeStatusModal(): void {
    this.saveStatus = 'idle';
  }

  navigateToContenus(): void {
    this.saveStatus = 'idle';
    this.router.navigate(['/admin/contenus']);
  }

  onSubmit(): void {
    if (this.contenuForm.valid) {
      this.saveStatus = 'saving';
      this.errorMessage = '';

      const raw = this.contenuForm.value;

      // 📦 Payload 100% conforme au contrat OpenAPI v2.0
      const payload = {
        patrimoineId: this.patrimoineId || raw.patrimoineId,
        type: raw.type,
        langue: raw.langue,
        titre: raw.titre,
        resume: raw.resume || null,
        corps: raw.corps,
        motsCles: raw.motsCles
          ? raw.motsCles.split(',').map((k: string) => k.trim()).filter((k: string) => k.length > 0)
          : [],
        mediaIds: this.selectedMediaIds // 🎯 Médias sélectionnés par l'utilisateur
      };

      const request$ = (this.isEditMode && this.contenuId)
        ? this.contenuApi.update(this.contenuId, payload)
        : this.contenuApi.create(payload);

      request$.subscribe({
        next: () => {
          this.saveStatus = 'success';
          setTimeout(() => {
            if (this.saveStatus === 'success') {
              this.router.navigate(['/admin/contenus']);
            }
          }, 1800);
        },
        error: (err: any) => {
          this.saveStatus = 'error';
          console.error("Erreur lors de l'enregistrement du contenu", err);
          this.errorMessage = err?.error?.message 
            || err?.error?.detail 
            || (typeof err?.error === 'string' ? err.error : null)
            || "Une erreur est survenue lors de la communication avec le serveur.";
        }
      });
    } else {
      this.contenuForm.markAllAsTouched();
    }
  }
}
