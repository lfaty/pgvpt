import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { ToastMessage, ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-toast-container',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast-container.component.html',
  styleUrls: ['./toast-container.component.css']
})
export class ToastContainerComponent implements OnInit, OnDestroy {
  toasts: ToastMessage[] = [];
  private sub!: Subscription;

  constructor(public toastService: ToastService) {}

  ngOnInit(): void {
    this.sub = this.toastService.toasts$.subscribe(list => {
      this.toasts = list;
    });
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  dismiss(id: string): void {
    this.toastService.dismiss(id);
  }

  getBorderColor(type: string): string {
    switch (type) {
      case 'error': return 'border-red-500/30 shadow-red-500/10';
      case 'warning': return 'border-amber-500/30 shadow-amber-500/10';
      case 'success': return 'border-emerald-500/30 shadow-emerald-500/10';
      default: return 'border-blue-500/30 shadow-blue-500/10';
    }
  }

  getIconColor(type: string): string {
    switch (type) {
      case 'error': return 'bg-red-100 text-red-600';
      case 'warning': return 'bg-amber-100 text-amber-600';
      case 'success': return 'bg-emerald-100 text-emerald-600';
      default: return 'bg-blue-100 text-blue-600';
    }
  }

  getIconClass(type: string): string {
    switch (type) {
      case 'error': return 'fas fa-circle-exclamation';
      case 'warning': return 'fas fa-triangle-exclamation';
      case 'success': return 'fas fa-circle-check';
      default: return 'fas fa-circle-info';
    }
  }
}
