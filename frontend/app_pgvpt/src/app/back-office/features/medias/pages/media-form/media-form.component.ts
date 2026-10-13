import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MediaApiService } from '../../../../../core/services/api/media-api.service';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';

@Component({
  selector: 'app-media-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  templateUrl: './media-form.component.html',
  styleUrls: ['./media-form.component.css']
})
export class MediaFormComponent implements OnInit {
  patrimoineId: string | null = null;
  nomPatrimoine: string = '';
  patrimoinesList: any[] = [];
  mediaForm!: FormGroup;

  mediaList: any[] = [];
  editingMediaId: string | null = null;
  
  notificationMessage: string | null = null;
  isSaving: boolean = false;
  isDragging = false;

  saveStatus: 'idle' | 'saving' | 'success' | 'error' = 'idle';
  errorMessage: string = '';

  mediaTypes = ['PHOTO', 'VIDEO', 'AUDIO', 'DOCUMENT', 'PANORAMA_360', 'MODELE_3D', 'CARTE', 'ARCHIVE_NUMERISEE'];
  statuts = ['BROUILLON', 'VALIDE', 'PUBLIE', 'ARCHIVE'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private mediaApi: MediaApiService,
    private patrimoineApi: PatrimoineApiService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.patrimoineId = this.route.snapshot.paramMap.get('id');
    this.initMediaForm();
    if (this.patrimoineId) {
      this.loadExistingMedias();
      this.loadPatrimoine();
    } else {
      // Accès direct sans id dans l'URL (ex: depuis le menu déroulant navbar /admin/medias)
      this.patrimoineApi.getAll().subscribe({
        next: (list: any[]) => {
          this.patrimoinesList = list || [];
          if (this.patrimoinesList.length > 0) {
            this.selectPatrimoine(this.patrimoinesList[0].id);
          }
        },
        error: (err: any) => console.error("Erreur chargement liste patrimoines", err)
      });
    }
  }

  selectPatrimoine(id: string): void {
    this.patrimoineId = id;
    if (this.mediaForm) {
      this.mediaForm.patchValue({ patrimoineId: id });
    }
    this.loadExistingMedias();
    this.loadPatrimoine();
  }

  onSelectPatrimoineChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    if (select?.value) {
      this.selectPatrimoine(select.value);
    }
  }

  loadPatrimoine(): void {
    if (!this.patrimoineId) return;
    this.patrimoineApi.getById(this.patrimoineId).subscribe({
      next: (p: any) => {
         this.nomPatrimoine = p?.nom || '';
      },
      error: (e: any) => console.error(e)
    });
  }

  loadExistingMedias(): void {
    if (!this.patrimoineId) return;
    this.mediaApi.getByPatrimoineId(this.patrimoineId).subscribe({
      next: (medias: any[]) => {
        // L'API nous renvoie actuellement que les photos (dû à la limitation générée du backend sur TypeMedia)
        // Mais nous stockons ce qui revient dans la liste
        this.mediaList = medias || [];
      },
      error: (err: any) => {
        console.error("Erreur lors du chargement des médias", err);
      }
    });
  }

  initMediaForm(): void {
    this.mediaForm = this.fb.group({
      patrimoineId: [this.patrimoineId, Validators.required],
      type: ['PHOTO', Validators.required],
      nom: ['', [Validators.required, Validators.minLength(2)]],
      description: ['', [Validators.maxLength(255)]],
      url: ['', [Validators.required]],
      mimeType: [''],
      tailleOctets: [0],
      langue: ['fr'],
      auteur: [''],
      credit: [''],
      droitsUtilisation: [''],
      latitude: [null],
      longitude: [null],
      statut: ['BROUILLON'],
      checksumSha256: ['']
    });
  }

  addMediaItem(): void {
    if (this.mediaForm.invalid) {
      this.mediaForm.markAllAsTouched();
      return;
    }

    const val = this.mediaForm.value;
    const newItem = {
      id: this.editingMediaId || Date.now().toString(),
      ...val
    };

    if (this.editingMediaId) {
      const idx = this.mediaList.findIndex(m => m.id === this.editingMediaId);
      if (idx > -1) {
        this.mediaList[idx] = newItem;
      }
      this.editingMediaId = null;
    } else {
      this.mediaList.push(newItem);
    }
    
    this.mediaForm.reset({
      patrimoineId: this.patrimoineId,
      type: 'PHOTO',
      langue: 'fr',
      statut: 'BROUILLON'
    });
  }

  editMediaItem(id: string): void {
    const item = this.mediaList.find(m => m.id === id);
    if (item) {
      this.editingMediaId = id;
      this.mediaForm.patchValue(item);
    }
  }

  removeMediaItem(id: string): void {
    this.mediaList = this.mediaList.filter(m => m.id !== id);
    if (this.editingMediaId === id) {
      this.editingMediaId = null;
      this.mediaForm.reset({
        patrimoineId: this.patrimoineId,
        type: 'PHOTO',
        langue: 'fr',
        statut: 'BROUILLON'
      });
    }
  }

  closeStatusModal(): void {
    this.saveStatus = 'idle';
  }

  navigateToPatrimoines(): void {
    this.saveStatus = 'idle';
    this.router.navigate(['/admin/patrimoines']);
  }

  saveMedias(): void {
    if (this.mediaList.length === 0) {
      this.notificationMessage = "Veuillez ajouter au moins un média.";
      setTimeout(() => this.notificationMessage = null, 3000);
      return;
    }
    
    this.isSaving = true;
    this.saveStatus = 'saving';
    this.errorMessage = '';
    
    this.mediaApi.saveMediasBatch(this.mediaList).subscribe({
      next: (results: any) => {
        this.isSaving = false;
        this.saveStatus = 'success';
        this.notificationMessage = "Médias enregistrés avec succès !";
        setTimeout(() => {
          if (this.saveStatus === 'success') {
            this.saveStatus = 'idle';
          }
        }, 2500);
      },
      error: (err: any) => {
        this.isSaving = false;
        this.saveStatus = 'error';
        console.error("Erreur d'enregistrement", err);
        this.errorMessage = err?.error?.message || err?.message || "Une erreur est survenue lors de l'enregistrement des médias. Vérifiez l'API.";
        this.notificationMessage = "Une erreur est survenue lors de l'enregistrement.";
      }
    });
  }

  // --- Mass Upload Simulation ---
  triggerFileInput(): void {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.multiple = true;
    fileInput.accept = 'image/*,video/*,.pdf';
    fileInput.onchange = (e: any) => {
      const files = Array.from(e.target.files) as File[];
      this.handleFiles(files);
    };
    fileInput.click();
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
    if (event.dataTransfer?.files) {
      const files = Array.from(event.dataTransfer.files);
      this.handleFiles(files);
    }
  }

  private handleFiles(files: File[]): void {
    files.forEach(file => {
      let type = 'PHOTO';
      if (file.type.startsWith('video')) type = 'VIDEO';
      else if (file.type.includes('pdf')) type = 'DOCUMENT';
      else if (file.type.startsWith('audio')) type = 'AUDIO';

      const blobUrl = URL.createObjectURL(file); // Permet un vrai aperçu local

      const newItem = {
        id: Date.now().toString() + Math.random().toString().substring(2, 6),
        patrimoineId: this.patrimoineId,
        type: type,
        nom: file.name.split('.')[0],
        description: '',
        url: blobUrl, // On utilise l'URL locale pour l'aperçu
        auteur: '',
        droitsUtilisation: '',
        credit: '',
        langue: 'fr',
        statut: 'BROUILLON'
      };
      
      this.mediaList.unshift(newItem);
    });
  }


  // Permet à Angular d'autoriser les URLs externes et les blob: URLs dans les balises <video> et <object>
  getSafeUrl(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
