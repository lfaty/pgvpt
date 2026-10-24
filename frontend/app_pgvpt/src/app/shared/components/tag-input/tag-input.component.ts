import { Component, ElementRef, forwardRef, Input, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-tag-input',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TagInputComponent),
      multi: true
    }
  ],
  template: `
    <div class="space-y-1">
      <div
        class="w-full min-h-[42px] px-3 py-1.5 bg-gray-50 border border-gray-200 rounded-xl focus-within:bg-white focus-within:border-[#0b5d5e] focus-within:ring-1 focus-within:ring-[#0b5d5e] transition-all flex flex-wrap items-center gap-1.5 cursor-text"
        [class.opacity-60]="disabled"
        (click)="focusInput()">
        
        <!-- Liste des Chips / Tags -->
        <span
          *ngFor="let tag of tags; let i = index"
          class="inline-flex items-center gap-1 px-2.5 py-1 rounded-lg text-xs font-semibold border transition-all animate-fade-in"
          [ngClass]="getBadgeClasses()">
          <span class="max-w-[220px] truncate">{{ tag }}</span>
          <button
            type="button"
            *ngIf="!disabled"
            (click)="removeTag(i, $event)"
            class="w-4 h-4 rounded-full flex items-center justify-center text-[10px] opacity-70 hover:opacity-100 hover:bg-black/10 transition-all ml-0.5"
            title="Supprimer">
            <i class="fas fa-times"></i>
          </button>
        </span>

        <!-- Input de saisie dynamique -->
        <div class="flex-1 min-w-[150px] flex items-center gap-1">
          <input
            #inputRef
            type="text"
            [(ngModel)]="inputValue"
            [disabled]="disabled"
            (keydown)="onKeyDown($event)"
            (paste)="onPaste($event)"
            (blur)="onBlur()"
            [placeholder]="tags.length === 0 ? placeholder : 'Ajouter un autre...'"
            class="w-full bg-transparent border-0 outline-none text-xs text-gray-800 placeholder-gray-400 p-0 focus:ring-0">

          <button
            *ngIf="inputValue.trim() && !disabled"
            type="button"
            (click)="addFromInput($event)"
            class="px-2 py-0.5 rounded-md bg-[#0b5d5e] text-white text-[10px] font-bold hover:bg-[#084b4c] transition-colors flex items-center gap-1 shrink-0">
            <i class="fas fa-plus text-[9px]"></i>
            <span>Ajouter</span>
          </button>
        </div>

      </div>

      <!-- Astuce clavier ergonomique -->
      <p class="text-[10px] text-gray-400 flex items-center gap-1 pl-1">
        <i class="fas fa-info-circle text-[9px] text-gray-400"></i>
        <span>Validez avec <kbd class="px-1 py-0.5 bg-gray-100 border border-gray-200 rounded text-[9px] font-mono text-gray-600">Entrée</kbd> ou <kbd class="px-1 py-0.5 bg-gray-100 border border-gray-200 rounded text-[9px] font-mono text-gray-600">,</kbd></span>
      </p>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }
  `]
})
export class TagInputComponent implements ControlValueAccessor {
  @Input() placeholder: string = 'Saisir une valeur...';
  @Input() badgeColor: 'teal' | 'amber' | 'rose' | 'purple' | 'blue' = 'teal';

  @ViewChild('inputRef') inputRef!: ElementRef<HTMLInputElement>;

  tags: string[] = [];
  inputValue: string = '';
  disabled: boolean = false;

  onChange: (val: string[]) => void = () => {};
  onTouched: () => void = () => {};

  writeValue(value: any): void {
    if (Array.isArray(value)) {
      this.tags = value.filter(v => v !== null && v !== undefined && String(v).trim() !== '').map(v => String(v).trim());
    } else if (typeof value === 'string' && value.trim() !== '') {
      this.tags = value
        .split(/[\n,]/)
        .map(s => s.trim())
        .filter(s => s.length > 0);
    } else {
      this.tags = [];
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  focusInput(): void {
    if (!this.disabled && this.inputRef) {
      this.inputRef.nativeElement.focus();
    }
  }

  addTag(raw: string): void {
    if (!raw) return;
    const items = raw
      .split(/[\n,]/)
      .map(s => s.trim())
      .filter(s => s.length > 0);

    let changed = false;
    items.forEach(item => {
      if (!this.tags.includes(item)) {
        this.tags.push(item);
        changed = true;
      }
    });

    if (changed) {
      this.inputValue = '';
      this.onChange(this.tags);
      this.onTouched();
    }
  }

  addFromInput(event?: Event): void {
    if (event) event.stopPropagation();
    if (this.inputValue.trim()) {
      this.addTag(this.inputValue);
      this.inputValue = '';
    }
  }

  removeTag(index: number, event?: Event): void {
    if (event) event.stopPropagation();
    if (this.disabled) return;
    this.tags.splice(index, 1);
    this.onChange(this.tags);
    this.onTouched();
  }

  onKeyDown(event: KeyboardEvent): void {
    if (this.disabled) return;
    if (event.key === 'Enter' || event.key === ',') {
      event.preventDefault();
      event.stopPropagation();
      this.addFromInput();
    } else if (event.key === 'Backspace' && !this.inputValue && this.tags.length > 0) {
      this.removeTag(this.tags.length - 1);
    }
  }

  onPaste(event: ClipboardEvent): void {
    const text = event.clipboardData?.getData('text');
    if (text && (text.includes('\n') || text.includes(','))) {
      event.preventDefault();
      this.addTag(text);
    }
  }

  onBlur(): void {
    if (this.inputValue.trim()) {
      this.addFromInput();
    }
    this.onTouched();
  }

  getBadgeClasses(): string {
    switch (this.badgeColor) {
      case 'amber':
        return 'bg-amber-500/10 text-amber-800 border-amber-500/20';
      case 'rose':
        return 'bg-rose-500/10 text-rose-800 border-rose-500/20';
      case 'purple':
        return 'bg-purple-500/10 text-purple-800 border-purple-500/20';
      case 'blue':
        return 'bg-blue-500/10 text-blue-800 border-blue-500/20';
      case 'teal':
      default:
        return 'bg-[#0b5d5e]/10 text-[#0b5d5e] border-[#0b5d5e]/20';
    }
  }
}
