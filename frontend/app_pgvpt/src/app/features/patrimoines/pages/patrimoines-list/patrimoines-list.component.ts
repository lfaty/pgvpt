import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MockService } from '../../../../core/services/mock.service';
import { PatrimoineCardComponent } from '../../components/patrimoine-card/patrimoine-card.component';
import { PatrimoineUI } from '../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-patrimoines-list',
  standalone: true,
  imports: [CommonModule, FormsModule, PatrimoineCardComponent],
  templateUrl: './patrimoines-list.component.html',
  styleUrl: './patrimoines-list.component.css'
})
export class PatrimoinesListComponent implements OnInit {
  patrimoines: PatrimoineUI[] = [];
  filteredPatrimoines: PatrimoineUI[] = [];

  searchTerm: string = '';
  selectedCategory: string = 'TOUTES';
  selectedRegion: string = 'TOUTES';

  categories = ['TOUTES', 'HISTORIQUE', 'CULTUREL', 'NATUREL'];
  // Extraction des régions uniques ou liste statique
  regions = ['TOUTES', 'Dakar', 'Saint-Louis', 'Tambacounda', 'Fatick', 'Diourbel', 'Ziguinchor'];

  constructor(
    private mockService: MockService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.mockService.getMockData<PatrimoineUI[]>('patrimoines.json').subscribe(data => {
      this.patrimoines = data;
      this.filteredPatrimoines = data;

      // Gestion des liens de la Navbar (ex: /patrimoines?category=culturel)
      this.route.queryParams.subscribe(params => {
        if (params['category']) {
          let cat = params['category'].toUpperCase();
          if (cat === 'MUSEE') cat = 'CULTUREL'; // Map navbar link to actual type
          if (this.categories.includes(cat)) {
            this.selectedCategory = cat;
          }
        }
        this.applyFilters();
      });
    });
  }

  applyFilters(): void {
    this.filteredPatrimoines = this.patrimoines.filter(p => {
      const matchSearch = this.searchTerm.trim() === '' || 
        p.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
        p.description.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      const matchCategory = this.selectedCategory === 'TOUTES' || p.type === this.selectedCategory;
      const matchRegion = this.selectedRegion === 'TOUTES' || p.localisation.region === this.selectedRegion;
      
      return matchSearch && matchCategory && matchRegion;
    });
  }
}
