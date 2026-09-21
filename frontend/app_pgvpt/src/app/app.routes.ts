import { Routes } from '@angular/router';

import { HomeComponent } from './features/home/home.component';

import { PatrimoinesListComponent } from './features/patrimoines/pages/patrimoines-list/patrimoines-list.component';
import { PatrimoineDetailComponent } from './features/patrimoines/pages/patrimoine-detail/patrimoine-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'patrimoines', component: PatrimoinesListComponent },
  { path: 'patrimoines/:id', component: PatrimoineDetailComponent },
  { path: '**', redirectTo: '' }
];
