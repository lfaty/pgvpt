import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MockService } from '../../../../core/services/mock.service';
import { PatrimoineCardComponent } from '../../components/patrimoine-card/patrimoine-card.component';
import { PatrimoineUI } from '../../../../core/models/patrimoine.ui';

@Component({
  selector: 'app-patrimoine-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, PatrimoineCardComponent],
  templateUrl: './patrimoine-detail.component.html',
  styleUrl: './patrimoine-detail.component.css'
})
export class PatrimoineDetailComponent implements OnInit {
  patrimoine: PatrimoineUI | undefined;
  similaires: PatrimoineUI[] = [];
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private mockService: MockService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.loadPatrimoine(id);
    });
  }

  loadPatrimoine(id: string): void {
    this.loading = true;
    this.mockService.getMockData<PatrimoineUI[]>('patrimoines.json').subscribe(data => {
      this.patrimoine = data.find((p: PatrimoineUI) => p.id === id);
      
      // Load similaires (same type, excluding current)
      if (this.patrimoine) {
        const currentType = this.patrimoine.type;
        this.similaires = data
          .filter(p => p.type === currentType && p.id !== id)
          .slice(0, 3);
      }
      
      this.loading = false;
      // Scroll to top
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  }
}
