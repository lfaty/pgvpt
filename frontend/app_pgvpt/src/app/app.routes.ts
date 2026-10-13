import { Routes } from '@angular/router';

import { HomeComponent } from './front-office/features/home/home.component';
import { PatrimoinesListComponent as FrontPatrimoinesList } from './front-office/features/patrimoines/pages/patrimoines-list/patrimoines-list.component';
import { PatrimoineDetailComponent } from './front-office/features/patrimoines/pages/patrimoine-detail/patrimoine-detail.component';
import { PatrimoineMediasComponent } from './front-office/features/patrimoines/pages/patrimoine-medias/patrimoine-medias.component';

import { PublicLayoutComponent } from './front-office/layouts/public-layout/public-layout.component';
import { AdminLayoutComponent } from './back-office/layouts/admin-layout/admin-layout.component';
import { DashboardComponent } from './back-office/features/dashboard/dashboard.component';
import { PatrimoineFormComponent } from './back-office/features/patrimoines/pages/patrimoine-form/patrimoine-form.component';
import { PatrimoinesListComponent as AdminPatrimoinesList } from './back-office/features/patrimoines/pages/patrimoines-list/patrimoines-list.component';
import { ContenusListComponent } from './back-office/features/contenus/pages/contenus-list/contenus-list.component';
import { ContenuFormComponent } from './back-office/features/contenus/pages/contenu-form/contenu-form.component';


import { MediaDetailComponent } from './front-office/features/patrimoines/pages/media-detail/media-detail.component';
import { ContenuDetailComponent } from './front-office/features/patrimoines/pages/contenu-detail/contenu-detail.component';

export const routes: Routes = [
  // ── ESPACE PUBLIC (Touristes) ──
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'patrimoines', component: FrontPatrimoinesList },
      { path: 'patrimoines/:id', component: PatrimoineDetailComponent },
      { path: 'patrimoines/:id/medias', redirectTo: 'patrimoines/:id', pathMatch: 'full' },
      { path: 'patrimoines/:patrimoineId/medias/:mediaId', component: MediaDetailComponent },
      { path: 'medias/:id', component: MediaDetailComponent },
      { path: 'patrimoines/:patrimoineId/contenus/:contenuId', component: ContenuDetailComponent },
      { path: 'contenus/:id', component: ContenuDetailComponent },
      { path: 'recits', loadComponent: () => import('./front-office/features/recits/pages/recits-list/recits-list.component').then(m => m.RecitsListComponent) },
      { path: 'contenus', redirectTo: 'recits', pathMatch: 'full' }
    ]
  },

  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      
      // Patrimoines
      { path: 'patrimoines', component: AdminPatrimoinesList },
      { path: 'patrimoines/nouveau', component: PatrimoineFormComponent },
      { path: 'patrimoines/modifier/:id', component: PatrimoineFormComponent },
      
      // Contenus
      { path: 'contenus', component: ContenusListComponent },
      { path: 'contenus/nouveau', component: ContenuFormComponent },
      { path: 'contenus/modifier/:id', component: ContenuFormComponent },
      { path: 'patrimoines/:id/contenu', component: ContenuFormComponent },
      
      // Medias
      { 
        path: 'medias', 
        loadComponent: () => import('./back-office/features/medias/pages/medias-list/medias-list.component').then(m => m.MediasListComponent) 
      },
      { 
        path: 'medias/nouveau', 
        loadComponent: () => import('./back-office/features/medias/pages/media-form/media-form.component').then(m => m.MediaFormComponent) 
      },
      { 
        path: 'patrimoines/:id/medias', 
        loadComponent: () => import('./back-office/features/medias/pages/media-form/media-form.component').then(m => m.MediaFormComponent) 
      },
      
      // Geolocalisation
      { 
        path: 'patrimoines/:id/localisation', 
        loadComponent: () => import('./back-office/features/geolocalisations/pages/geolocalisation-form/geolocalisation-form.component').then(m => m.GeolocalisationFormComponent) 
      },
      
      // Modules en construction
      { 
        path: 'reservations', 
        loadComponent: () => import('./back-office/shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Réservations', icon: 'fa-calendar-check' }
      },
      { 
        path: 'circuits', 
        loadComponent: () => import('./back-office/shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Circuits', icon: 'fa-route' }
      },
      { 
        path: 'activites', 
        loadComponent: () => import('./back-office/shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Activités', icon: 'fa-hiking' }
      },
      { 
        path: 'parametres', 
        loadComponent: () => import('./back-office/shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Paramètres', icon: 'fa-cog' }
      },

      // Redirection pour les routes admin non trouvées
      { path: '**', redirectTo: 'dashboard' }
    ]
  },

  // ── REDIRECTION ──
  { path: '**', redirectTo: '' }
];
