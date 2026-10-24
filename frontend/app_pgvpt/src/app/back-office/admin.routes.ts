import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },

      // Patrimoines
      {
        path: 'patrimoines',
        loadComponent: () => import('./features/patrimoines/pages/patrimoines-list/patrimoines-list.component').then(m => m.PatrimoinesListComponent)
      },
      {
        path: 'patrimoines/nouveau',
        loadComponent: () => import('./features/patrimoines/pages/patrimoine-form/patrimoine-form.component').then(m => m.PatrimoineFormComponent)
      },
      {
        path: 'patrimoines/modifier/:id',
        loadComponent: () => import('./features/patrimoines/pages/patrimoine-form/patrimoine-form.component').then(m => m.PatrimoineFormComponent)
      },

      // Contenus
      {
        path: 'contenus',
        loadComponent: () => import('./features/contenus/pages/contenus-list/contenus-list.component').then(m => m.ContenusListComponent)
      },
      {
        path: 'contenus/nouveau',
        loadComponent: () => import('./features/contenus/pages/contenu-form/contenu-form.component').then(m => m.ContenuFormComponent)
      },
      {
        path: 'contenus/modifier/:id',
        loadComponent: () => import('./features/contenus/pages/contenu-form/contenu-form.component').then(m => m.ContenuFormComponent)
      },
      {
        path: 'patrimoines/:id/contenu',
        loadComponent: () => import('./features/contenus/pages/contenu-form/contenu-form.component').then(m => m.ContenuFormComponent)
      },

      // Medias
      {
        path: 'medias',
        loadComponent: () => import('./features/medias/pages/medias-list/medias-list.component').then(m => m.MediasListComponent)
      },
      {
        path: 'medias/nouveau',
        loadComponent: () => import('./features/medias/pages/media-form/media-form.component').then(m => m.MediaFormComponent)
      },
      {
        path: 'patrimoines/:id/medias',
        loadComponent: () => import('./features/medias/pages/media-form/media-form.component').then(m => m.MediaFormComponent)
      },

      // Geolocalisation
      {
        path: 'patrimoines/:id/localisation',
        loadComponent: () => import('./features/geolocalisations/pages/geolocalisation-form/geolocalisation-form.component').then(m => m.GeolocalisationFormComponent)
      },
      {
        path: 'geolocalisations/zones-touristiques',
        loadComponent: () => import('./features/geolocalisations/pages/zones-touristiques/zones-touristiques.component').then(m => m.ZonesTouristiquesComponent)
      },
      {
        path: 'geolocalisations/zones-geographiques',
        loadComponent: () => import('./features/geolocalisations/pages/zones-geographiques/zones-geographiques.component').then(m => m.ZonesGeographiquesComponent)
      },
      {
        path: 'geolocalisations/referentiel',
        loadComponent: () => import('./features/geolocalisations/pages/referentiel-administratif/referentiel-administratif.component').then(m => m.ReferentielAdministratifComponent)
      },

      // Modules transverses (Coming Soon)
      {
        path: 'reservations',
        loadComponent: () => import('../shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Réservations', icon: 'fa-calendar-check' }
      },
      // Circuits
      {
        path: 'circuits',
        loadComponent: () => import('./features/circuits/pages/circuits-list/circuits-list.component').then(m => m.CircuitsListComponent)
      },
      {
        path: 'circuits/nouveau',
        loadComponent: () => import('./features/circuits/pages/circuit-form/circuit-form.component').then(m => m.CircuitFormComponent)
      },
      {
        path: 'circuits/modifier/:id',
        loadComponent: () => import('./features/circuits/pages/circuit-form/circuit-form.component').then(m => m.CircuitFormComponent)
      },
      // Activités
      {
        path: 'activites',
        loadComponent: () => import('./features/activites/pages/activites-list/activites-list.component').then(m => m.ActivitesListComponent)
      },
      {
        path: 'activites/nouveau',
        loadComponent: () => import('./features/activites/pages/activites-list/activites-list.component').then(m => m.ActivitesListComponent)
      },
      {
        path: 'circuits/activites',
        redirectTo: 'activites',
        pathMatch: 'full'
      },
      {
        path: 'parametres',
        loadComponent: () => import('../shared/components/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent),
        data: { title: 'Paramètres', icon: 'fa-cog' }
      },

      { path: '**', redirectTo: 'dashboard' }
    ]
  }
];
