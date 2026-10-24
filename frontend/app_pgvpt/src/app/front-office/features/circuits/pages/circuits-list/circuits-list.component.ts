import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { CircuitFacadeService, CircuitUI, NiveauDifficulte, TypeCircuit } from '../../../../../core/services/circuit-facade.service';
import { CircuitCardComponent } from '../../components/circuit-card/circuit-card.component';

@Component({
  selector: 'app-circuits-list',
  standalone: true,
  imports: [CommonModule, FormsModule, CircuitCardComponent],
  templateUrl: './circuits-list.component.html',
  styleUrl: './circuits-list.component.css'
})
export class CircuitsListComponent implements OnInit, OnDestroy {
  circuits: CircuitUI[] = [];
  filteredCircuits: CircuitUI[] = [];

  isLoading: boolean = true;
  hasError: boolean = false;
  errorMessage: string = '';

  searchTerm: string = '';
  selectedType: string = 'TOUS';
  selectedDifficulte: string = 'TOUTES';
  selectedDureeMax: string = 'TOUTES';

  private sub?: Subscription;

  types = [
    { value: 'TOUS', label: 'Tous les types' },
    { value: TypeCircuit.Culturel, label: 'Culturel' },
    { value: TypeCircuit.Historique, label: 'Historique' },
    { value: TypeCircuit.Nature, label: 'Nature' },
    { value: TypeCircuit.Religieux, label: 'Religieux' },
    { value: TypeCircuit.Gastronomique, label: 'Gastronomique' },
    { value: TypeCircuit.Artisanal, label: 'Artisanal' },
    { value: TypeCircuit.Mixte, label: 'Mixte' }
  ];

  difficultes = [
    { value: 'TOUTES', label: 'Toutes les difficultés' },
    { value: NiveauDifficulte.Facile, label: 'Facile' },
    { value: NiveauDifficulte.Moyen, label: 'Moyen' },
    { value: NiveauDifficulte.Difficile, label: 'Difficile' }
  ];

  durees = [
    { value: 'TOUTES', label: 'Toutes les durées' },
    { value: 'COURT', label: 'Moins de 2h' },
    { value: 'MOYEN', label: 'Demi-journée (2h - 5h)' },
    { value: 'LONG', label: 'Journée entière (+ de 5h)' }
  ];

  constructor(
    private circuitFacade: CircuitFacadeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.isLoading = true;
    this.hasError = false;

    this.sub?.unsubscribe();
    this.sub = combineLatest([
      this.circuitFacade.getCircuits(),
      this.route.queryParamMap
    ]).subscribe({
      next: ([data, params]) => {
        this.circuits = data;
        this.isLoading = false;

        // Type (?type=CULTUREL, etc.)
        const typeParam = (params.get('type') ?? '').toUpperCase();
        this.selectedType = this.types.some(t => t.value === typeParam) ? typeParam : 'TOUS';

        // Difficulté (?difficulte=FACILE, etc.)
        const diffParam = (params.get('difficulte') ?? '').toUpperCase();
        this.selectedDifficulte = this.difficultes.some(d => d.value === diffParam) ? diffParam : 'TOUTES';

        // Recherche textuelle (?q=...)
        const q = params.get('q');
        if (q) {
          this.searchTerm = q;
        }

        this.applyFilters();
      },
      error: (err) => {
        console.error('Erreur chargement des circuits:', err);
        this.isLoading = false;
        this.hasError = true;
        this.errorMessage = 'Impossible de charger la liste des circuits pour le moment.';
      }
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  get selectedTypeLabel(): string {
    return this.types.find(t => t.value === this.selectedType)?.label ?? this.selectedType;
  }

  get selectedDifficulteLabel(): string {
    return this.difficultes.find(d => d.value === this.selectedDifficulte)?.label ?? this.selectedDifficulte;
  }

  clearType(): void {
    this.selectedType = 'TOUS';
    this.updateQueryParams({ type: null });
    this.applyFilters();
  }

  clearDifficulte(): void {
    this.selectedDifficulte = 'TOUTES';
    this.updateQueryParams({ difficulte: null });
    this.applyFilters();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.updateQueryParams({ q: null });
    this.applyFilters();
  }

  resetAllFilters(): void {
    this.searchTerm = '';
    this.selectedType = 'TOUS';
    this.selectedDifficulte = 'TOUTES';
    this.selectedDureeMax = 'TOUTES';
    this.updateQueryParams({ type: null, difficulte: null, q: null });
    this.applyFilters();
  }

  private updateQueryParams(params: Record<string, any>): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: params,
      queryParamsHandling: 'merge'
    });
  }

  applyFilters(): void {
    this.filteredCircuits = this.circuits.filter(c => {
      // 1. Recherche par nom ou description
      const search = this.searchTerm.trim().toLowerCase();
      const matchSearch = !search ||
        (c.nom && c.nom.toLowerCase().includes(search)) ||
        (c.description && c.description.toLowerCase().includes(search));

      // 2. Type de circuit
      const matchType = this.selectedType === 'TOUS' || c.type === this.selectedType;

      // 3. Difficulté
      const matchDifficulte = this.selectedDifficulte === 'TOUTES' || c.niveauDifficulte === this.selectedDifficulte;

      // 4. Durée
      let matchDuree = true;
      const minutes = c.dureeMinutes || 0;
      if (this.selectedDureeMax === 'COURT') {
        matchDuree = minutes > 0 && minutes <= 120;
      } else if (this.selectedDureeMax === 'MOYEN') {
        matchDuree = minutes > 120 && minutes <= 300;
      } else if (this.selectedDureeMax === 'LONG') {
        matchDuree = minutes > 300;
      }

      return matchSearch && matchType && matchDifficulte && matchDuree;
    });
  }
}
