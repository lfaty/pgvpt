import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';
import { PatrimoineCardComponent } from '../../components/patrimoine-card/patrimoine-card.component';
import { PatrimoineUI } from '../../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-patrimoines-list',
  standalone: true,
  imports: [CommonModule, FormsModule, PatrimoineCardComponent],
  templateUrl: './patrimoines-list.component.html',
  styleUrl: './patrimoines-list.component.css'
})
export class PatrimoinesListComponent implements OnInit, OnDestroy {
  patrimoines: PatrimoineUI[] = [];
  filteredPatrimoines: PatrimoineUI[] = [];

  searchTerm: string = '';
  selectedCategory: string = 'TOUTES';
  selectedRegion: string = 'TOUTES';
  selectedType: string = 'TOUS';
  backendError: boolean = false;

  private sub?: Subscription;

  types = [
    { value: 'MONUMENT', label: 'Monuments historiques' },
    { value: 'MUSEE', label: 'Musées' },
    { value: 'SITE_NATUREL', label: 'Sites naturels' }
  ];

  categories = [
    { value: 'TOUTES', label: 'Toutes les catégories' },
    { value: 'PATRIMOINE_NATUREL', label: 'Naturel' },
    { value: 'PATRIMOINE_CULTUREL', label: 'Culturel' },
    { value: 'PATRIMOINE_MEMORIEL', label: 'Mémoriel' },
    { value: 'PATRIMOINE_COLONIAL', label: 'Colonial' },
    { value: 'PATRIMOINE_RELIGIEUX', label: 'Religieux' }
  ];
  
  regions: string[] = [
    'TOUTES',
    'Dakar',
    'Diourbel',
    'Fatick',
    'Kaffrine',
    'Kaolack',
    'Kédougou',
    'Kolda',
    'Louga',
    'Matam',
    'Saint-Louis',
    'Sédhiou',
    'Tambacounda',
    'Thiès',
    'Ziguinchor'
  ];

  constructor(
    private patrimoineService: PatrimoineFacadeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Ré-applique les filtres à chaque changement de queryParams (clic dans le header)
    this.sub = combineLatest([
      this.patrimoineService.getPatrimoines(),
      this.route.queryParamMap
    ]).subscribe({
      next: ([data, params]) => {
        this.backendError = false;
        this.patrimoines = data;

        // Catégorie (?categorie=... ou ancien ?category=...)
        let cat = (params.get('categorie') ?? params.get('category') ?? '').toUpperCase();
        if (cat === 'CULTUREL') cat = 'PATRIMOINE_CULTUREL';
        if (cat === 'NATUREL') cat = 'PATRIMOINE_NATUREL';
        if (cat === 'HISTORIQUE') cat = 'PATRIMOINE_COLONIAL';
        this.selectedCategory = this.categories.some(c => c.value === cat) ? cat : 'TOUTES';

        // Type (?type=MONUMENT | MUSEE | SITE_NATUREL)
        const type = (params.get('type') ?? '').toUpperCase();
        this.selectedType = this.types.some(t => t.value === type) ? type : 'TOUS';

        // Search term (?q=...)
        const q = params.get('q');
        if (q) {
          this.searchTerm = q;
        }

        this.applyFilters();
      },
      error: (err) => {
        console.warn('Erreur chargement catalogue patrimoines', err);
        this.backendError = true;
        this.patrimoines = [];
        this.filteredPatrimoines = [];
      }
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  get selectedTypeLabel(): string {
    return this.types.find(t => t.value === this.selectedType)?.label ?? this.selectedType;
  }

  clearType(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: null },
      queryParamsHandling: 'merge'
    });
  }

  resetUrlFilters(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: null, categorie: null, category: null },
      queryParamsHandling: 'merge'
    });
  }

  applyFilters(): void {
    this.filteredPatrimoines = this.patrimoines.filter(p => {
      const matchSearch = this.searchTerm.trim() === '' || 
        p.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
        (p.description && p.description.toLowerCase().includes(this.searchTerm.toLowerCase()));
      
      const matchCategory = this.selectedCategory === 'TOUTES' || p.categorie === this.selectedCategory;
      const matchRegion = this.selectedRegion === 'TOUTES' || (!!p.region && p.region.toLowerCase().includes(this.selectedRegion.toLowerCase()));
      
      const matchType = this.selectedType === 'TOUS' || p.type === this.selectedType;
      
      return matchSearch && matchCategory && matchRegion && matchType;
    });
  }
}
