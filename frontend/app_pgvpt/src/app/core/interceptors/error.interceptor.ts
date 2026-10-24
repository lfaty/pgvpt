import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ToastService } from '../services/toast.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastService = inject(ToastService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // 1. Ignorer silencieusement les erreurs 404 (ressources non encore créées, absentes ou optionnelles : géolocalisations, médias, contenus, etc.)
      if (error.status === 404) {
        return throwError(() => error);
      }

      let title = 'Erreur';
      let message = 'Une erreur est survenue lors de la communication avec le serveur.';

      const rawMsg = typeof error.error === 'string' ? error.error : (error.error?.message || error.message || '');

      if (error.status === 0) {
        title = 'Serveur Inaccessible';
        message = 'Impossible de joindre le serveur. Veuillez vérifier l\'état de la connexion réseau.';
      } else if (error.status === 400) {
        title = 'Données Invalides';
        message = error.error?.message || 'Les données soumises sont incomplètes ou invalides.';
        if (error.error?.errors && Array.isArray(error.error.errors)) {
          message = error.error.errors
            .map((e: any) => e.defaultMessage || e.message)
            .filter(Boolean)
            .join(', ') || message;
        }
      } else if (error.status === 401) {
        title = 'Session Expirée';
        message = 'Votre session a expiré. Veuillez vous reconnecter.';
      } else if (error.status === 403) {
        title = 'Accès Refusé';
        message = 'Vous n\'avez pas les permissions nécessaires pour effectuer cette action.';
      } else if (error.status === 409) {
        const conflictMsg = error.error?.message || '';
        const lower = conflictMsg.toLowerCase();
        if (lower.includes('supprim') || lower.includes('rattach') || lower.includes('données liées') || lower.includes('donnees liees')) {
          title = 'Suppression Impossible (Données Liées)';
          message = conflictMsg;
        } else {
          title = 'Conflit de Données';
          message = conflictMsg || 'Cette opération entre en conflit avec des données existantes (doublon ou contrainte).';
        }
      } else if (error.status >= 500) {
        const fullText = (rawMsg + ' ' + (typeof error.error === 'string' ? error.error : JSON.stringify(error.error || {}))).toLowerCase();

        // Détection intelligente des contraintes de clés étrangères (Foreign Key)
        if (
          fullText.includes('foreign key') ||
          fullText.includes('violates foreign key constraint') ||
          fullText.includes('dataintegrityviolationexception') ||
          fullText.includes('is still referenced') ||
          fullText.includes('contrainte') ||
          fullText.includes('cle etrangere')
        ) {
          title = 'Suppression Impossible (Données Liées)';
          if (req.url.includes('/activites')) {
            message = 'Cette activité est encore rattachée à des étapes de circuits existants. Même inactive, une activité liée ne peut pas être supprimée définitivement en base pour préserver ces circuits. Remplacez-la dans ces étapes ou laissez-la simplement désactivée.';
          } else {
            message = 'Cet élément est rattaché à d\'autres enregistrements du système (étapes de circuits, sous-entités, géolocalisations ou contenus). Veuillez d\'abord détacher ou supprimer ces éléments dépendants.';
          }
        } else {
          title = 'Erreur Serveur';
          message = error.error?.message || 'Une erreur inattendue est survenue sur le serveur.';
        }
      } else {
        title = 'Erreur';
        message = error.error?.message || 'Une anomalie a été rencontrée lors du traitement.';
      }

      // Alerte utilisateur claire, sans jargon technique ni stack traces
      toastService.error(message, title);

      return throwError(() => error);
    })
  );
};
