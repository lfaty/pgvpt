import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { LoadingService } from '../services/loading.service';

/**
 * Intercepteur HTTP détectant les requêtes POST (et mutations)
 * pour activer automatiquement l'indicateur de chargement (cercle qui tourne).
 */
export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  // Détection des requêtes POST (création) ainsi que PUT/PATCH
  const isMutatingPost = req.method === 'POST' || req.method === 'PUT' || req.method === 'PATCH';

  if (isMutatingPost) {
    loadingService.startPost();
    return next(req).pipe(
      finalize(() => {
        loadingService.endPost();
      })
    );
  }

  return next(req);
};
