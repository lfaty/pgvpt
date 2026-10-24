import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private activePostCount = signal<number>(0);

  /**
   * Signal réactif indiquant si au moins une requête de création/mutation (POST, etc.) est en cours
   */
  readonly isPosting = signal<boolean>(false);

  startPost(): void {
    const current = this.activePostCount() + 1;
    this.activePostCount.set(current);
    this.isPosting.set(true);
  }

  endPost(): void {
    const current = Math.max(0, this.activePostCount() - 1);
    this.activePostCount.set(current);
    this.isPosting.set(current > 0);
  }
}
