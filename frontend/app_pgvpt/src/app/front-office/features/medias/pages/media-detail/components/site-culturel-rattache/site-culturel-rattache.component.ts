import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-site-culturel-rattache',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './site-culturel-rattache.component.html'
})
export class SiteCulturelRattacheComponent {
  @Input() patrimoine: any = null;
}
