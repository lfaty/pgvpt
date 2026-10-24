import { Routes } from '@angular/router';
import { PublicLayoutComponent } from './layouts/public-layout/public-layout.component';

export const FRONT_ROUTES: Routes = [
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      {
        path: '',
        loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent)
      },
      {
        path: 'patrimoines',
        loadComponent: () => import('./features/patrimoines/pages/patrimoines-list/patrimoines-list.component').then(m => m.PatrimoinesListComponent)
      },
      {
        path: 'patrimoines/:id',
        loadComponent: () => import('./features/patrimoines/pages/patrimoine-detail/patrimoine-detail.component').then(m => m.PatrimoineDetailComponent)
      },
      {
        path: 'patrimoines/:id/medias',
        redirectTo: 'patrimoines/:id',
        pathMatch: 'full'
      },
      {
        path: 'patrimoines/:patrimoineId/medias/:mediaId',
        loadComponent: () => import('./features/medias/pages/media-detail/media-detail.component').then(m => m.MediaDetailComponent)
      },
      {
        path: 'medias/:id',
        loadComponent: () => import('./features/medias/pages/media-detail/media-detail.component').then(m => m.MediaDetailComponent)
      },
      {
        path: 'patrimoines/:patrimoineId/contenus/:contenuId',
        loadComponent: () => import('./features/contenus/pages/contenu-detail/contenu-detail.component').then(m => m.ContenuDetailComponent)
      },
      {
        path: 'contenus/:id',
        loadComponent: () => import('./features/contenus/pages/contenu-detail/contenu-detail.component').then(m => m.ContenuDetailComponent)
      },
      {
        path: 'recits',
        loadComponent: () => import('./features/contenus/pages/recits-list/recits-list.component').then(m => m.RecitsListComponent)
      },
      {
        path: 'contenus',
        redirectTo: 'recits',
        pathMatch: 'full'
      },
      {
        path: 'carte',
        loadComponent: () => import('./features/geolocalisations/pages/carte-patrimoines/carte-patrimoines.component').then(m => m.CartePatrimoinesComponent)
      },
      {
        path: 'geolocalisations',
        redirectTo: 'carte',
        pathMatch: 'full'
      },
      {
        path: 'circuits',
        loadComponent: () => import('./features/circuits/pages/circuits-list/circuits-list.component').then(m => m.CircuitsListComponent)
      },
      {
        path: 'circuits/:id',
        loadComponent: () => import('./features/circuits/pages/circuit-detail/circuit-detail.component').then(m => m.CircuitDetailComponent)
      }
    ]
  }
];
