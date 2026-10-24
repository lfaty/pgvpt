import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./back-office/admin.routes').then(m => m.ADMIN_ROUTES)
  },
  {
    path: '',
    loadChildren: () => import('./front-office/front.routes').then(m => m.FRONT_ROUTES)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
