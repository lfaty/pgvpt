import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { PatrimoineFacadeService } from '../../../../../core/services/patrimoine-facade.service';

@Component({
  selector: 'app-patrimoine-medias',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './patrimoine-medias.component.html',
  styleUrl: './patrimoine-medias.component.css'
})
export class PatrimoineMediasComponent implements OnInit {
  patrimoineId: string = '';
  patrimoine: any;
  allMedias: any[] = [];
  displayedMedias: any[] = [];
  
  loading = true;

  // Filters
  activeTypeFilter: string = 'all'; // 'all', 'photos', 'videos', 'audios', 'documents', 'immersions'
  sortOrder: string = 'recent'; // 'recent', 'name'

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private facadeService: PatrimoineFacadeService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.patrimoineId = params['id'];
      this.loadData();
    });

    this.route.queryParams.subscribe(params => {
      if (params['type']) {
        this.activeTypeFilter = params['type'];
      }
      this.applyFilters();
    });
  }

  loadData(): void {
    this.facadeService.getPatrimoineRaw(this.patrimoineId).subscribe(p => {
      this.patrimoine = p;
    });

    this.facadeService.getMedias(this.patrimoineId).subscribe(medias => {
      this.allMedias = medias.map((m: any) => ({ ...m, url: m.url.startsWith('/') ? m.url : '/' + m.url }));
      this.loading = false;
      this.applyFilters();
    });
  }

  setFilter(type: string): void {
    this.activeTypeFilter = type;
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: type === 'all' ? null : type },
      queryParamsHandling: 'merge'
    });
    this.applyFilters();
  }

  setSort(order: string): void {
    this.sortOrder = order;
    this.applyFilters();
  }

  applyFilters(): void {
    if (!this.allMedias || this.allMedias.length === 0) return;

    let filtered = [...this.allMedias];

    // Filter by type
    switch (this.activeTypeFilter) {
      case 'photos':
        filtered = filtered.filter(m => ['PHOTO', 'CARTE', 'ARCHIVE_NUMERISEE'].includes(m.type));
        break;
      case 'videos':
        filtered = filtered.filter(m => m.type === 'VIDEO');
        break;
      case 'audios':
        filtered = filtered.filter(m => m.type === 'AUDIO');
        break;
      case 'documents':
        filtered = filtered.filter(m => m.type === 'DOCUMENT');
        break;
      case 'immersions':
        filtered = filtered.filter(m => ['PANORAMA_360', 'MODELE_3D'].includes(m.type));
        break;
    }

    // Sort
    if (this.sortOrder === 'name') {
      filtered.sort((a, b) => (a.titre || '').localeCompare(b.titre || ''));
    } else {
      // 'recent' -> By default we mock it by reversing the array so it looks like sorting
      filtered = filtered.reverse();
    }

    this.displayedMedias = filtered;
  }
}
