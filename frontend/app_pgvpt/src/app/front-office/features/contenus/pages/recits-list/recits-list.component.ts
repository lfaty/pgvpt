import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ContenuApiService } from '../../../../../core/services/api/contenu-api.service';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';

@Component({
  selector: 'app-recits-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './recits-list.component.html',
  styleUrls: ['./recits-list.component.css']
})
export class RecitsListComponent implements OnInit {
  private contenuApi = inject(ContenuApiService);
  private facadeService = inject(PatrimoineFacadeService);

  contenus: any[] = [];
  filteredContenus: any[] = [];
  patrimoinesMap: Map<string, any> = new Map();

  loading: boolean = true;
  searchTerm: string = '';
  selectedType: string = 'TOUS';

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;

    forkJoin({
      contenus: this.contenuApi.getAll().pipe(catchError(() => of([]))),
      patrimoines: this.facadeService.getPatrimoines().pipe(catchError(() => of([])))
    }).subscribe({
      next: (data) => {
        // Stocker les patrimoines dans une map pour lookup rapide O(1)
        (data.patrimoines || []).forEach(p => {
          if (p.id) {
            this.patrimoinesMap.set(p.id, p);
          }
        });

        // Filtrer uniquement les contenus publiés ou non archivés
        this.contenus = (data.contenus || []).filter(c => c.statut !== 'ARCHIVE');
        this.applyFilters();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  setTypeFilter(type: string): void {
    this.selectedType = type;
    this.applyFilters();
  }

  applyFilters(): void {
    let result = [...this.contenus];

    if (this.selectedType !== 'TOUS') {
      result = result.filter(c => c.type === this.selectedType);
    }

    if (this.searchTerm && this.searchTerm.trim() !== '') {
      const q = this.searchTerm.toLowerCase().trim();
      result = result.filter(c =>
        (c.titre && c.titre.toLowerCase().includes(q)) ||
        (c.resume && c.resume.toLowerCase().includes(q)) ||
        (c.corps && c.corps.toLowerCase().includes(q)) ||
        (c.motsCles && c.motsCles.some((k: string) => k.toLowerCase().includes(q))) ||
        (this.getPatrimoineNom(c.patrimoineId).toLowerCase().includes(q))
      );
    }

    this.filteredContenus = result;
  }

  getPatrimoine(id: string): any | undefined {
    return this.patrimoinesMap.get(id);
  }

  getPatrimoineNom(id: string): string {
    const p = this.patrimoinesMap.get(id);
    return p ? p.nom : 'Patrimoine du Sénégal';
  }

  getReadingTime(text: string): number {
    if (!text) return 2;
    const words = text.trim().split(/\s+/).length;
    return Math.max(1, Math.ceil(words / 200));
  }

  getContenuTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      'ARTICLE': 'Article Historique',
      'GUIDE_VISITE': 'Guide de Visite',
      'RECIT': 'Récit Vivant',
      'CHRONIQUE': 'Chronique',
      'ANECDOTE': 'Anecdote Culturelle',
      'LEGENDE': 'Légende & Mythe',
      'TEMOIGNAGE': 'Témoignage'
    };
    return labels[type] || type || 'Récit';
  }

  getContenuTypeIcon(type: string): string {
    const icons: Record<string, string> = {
      'ARTICLE': 'fa-feather-pointed',
      'GUIDE_VISITE': 'fa-compass',
      'RECIT': 'fa-book-open',
      'CHRONIQUE': 'fa-clock-rotate-left',
      'ANECDOTE': 'fa-lightbulb',
      'LEGENDE': 'fa-dragon',
      'TEMOIGNAGE': 'fa-comments'
    };
    return icons[type] || 'fa-newspaper';
  }

  getArticleImage(c: any): string {
    const p = this.patrimoinesMap.get(c.patrimoineId);
    if (p && p.imageUrl) return p.imageUrl;
    return 'assets/images/activite_saloum.jpg';
  }
}
