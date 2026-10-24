import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-galerie-medias',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './galerie-medias.component.html'
})
export class GalerieMediasComponent {
  @Input() patrimoine: any;
  @Input() galerieMedias: any[] = [];
  @Input() mediaTabs: { type: string; label: string; count: number }[] = [];
  @Input() activeMediaFilter: string = 'ALL';
  @Input() mediaSearchQuery: string = '';
  @Input() mediaSortOrder: 'NEWEST' | 'OLDEST' = 'NEWEST';
  @Input() filteredPhotos: any[] = [];
  @Input() filteredVideos: any[] = [];
  @Input() filteredAudios: any[] = [];
  @Input() filteredDocuments: any[] = [];
  @Input() filteredImmersions: any[] = [];

  @Output() filterChange = new EventEmitter<string>();
  @Output() searchChange = new EventEmitter<string>();
  @Output() sortChange = new EventEmitter<'NEWEST' | 'OLDEST'>();

  setMediaFilter(type: string): void {
    this.activeMediaFilter = type;
    this.filterChange.emit(type);
  }

  onSearchChange(): void {
    this.searchChange.emit(this.mediaSearchQuery);
  }

  onSortChange(): void {
    this.sortChange.emit(this.mediaSortOrder);
  }
}
