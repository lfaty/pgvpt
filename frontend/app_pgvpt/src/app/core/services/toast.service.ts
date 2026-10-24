import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export type ToastType = 'error' | 'warning' | 'success' | 'info';

export interface ToastMessage {
  id: string;
  type: ToastType;
  title: string;
  message: string;
  details?: string;
  duration?: number; // Duration in ms (0 for persistent)
  createdAt: number;
}

export interface ToastOptions {
  type?: ToastType;
  title?: string;
  message: string;
  details?: string;
  duration?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private toastsSubject = new BehaviorSubject<ToastMessage[]>([]);
  public toasts$: Observable<ToastMessage[]> = this.toastsSubject.asObservable();

  show(options: ToastOptions): string {
    const id = 'toast_' + Date.now() + '_' + Math.random().toString(36).substring(2, 7);
    const type = options.type || 'info';
    const duration = options.duration !== undefined ? options.duration : (type === 'error' ? 7000 : 4500);

    const toast: ToastMessage = {
      id,
      type,
      title: options.title || this.getDefaultTitle(type),
      message: options.message,
      details: options.details,
      duration,
      createdAt: Date.now()
    };

    const current = this.toastsSubject.getValue();
    // Keep max 5 toasts at a time
    const updated = [toast, ...current].slice(0, 5);
    this.toastsSubject.next(updated);

    if (duration > 0) {
      setTimeout(() => {
        this.dismiss(id);
      }, duration);
    }

    return id;
  }

  error(message: string, title?: string, details?: string, duration?: number): string {
    return this.show({ type: 'error', message, title, details, duration });
  }

  warning(message: string, title?: string, details?: string, duration?: number): string {
    return this.show({ type: 'warning', message, title, details, duration });
  }

  success(message: string, title?: string, duration?: number): string {
    return this.show({ type: 'success', message, title, duration });
  }

  info(message: string, title?: string, duration?: number): string {
    return this.show({ type: 'info', message, title, duration });
  }

  dismiss(id: string): void {
    const current = this.toastsSubject.getValue();
    this.toastsSubject.next(current.filter(t => t.id !== id));
  }

  clear(): void {
    this.toastsSubject.next([]);
  }

  private getDefaultTitle(type: ToastType): string {
    switch (type) {
      case 'error': return 'Erreur';
      case 'warning': return 'Attention';
      case 'success': return 'Succès';
      case 'info': return 'Information';
    }
  }
}
