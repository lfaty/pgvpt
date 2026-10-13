import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-coming-soon',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="flex flex-col items-center justify-center h-full min-h-[80vh] bg-slate-50/50 p-8 fade-in">
      <div class="bg-white p-10 sm:p-14 rounded-3xl shadow-lg text-center max-w-xl border border-slate-100 relative overflow-hidden group">
        
        <!-- Decoration background -->
        <div class="absolute -top-24 -right-24 w-48 h-48 bg-emerald-50 rounded-full blur-3xl opacity-50 group-hover:bg-emerald-100 transition-colors duration-1000"></div>
        <div class="absolute -bottom-24 -left-24 w-48 h-48 bg-indigo-50 rounded-full blur-3xl opacity-50 group-hover:bg-indigo-100 transition-colors duration-1000"></div>

        <div class="relative z-10">
          <div class="w-24 h-24 bg-gradient-to-br from-emerald-50 to-emerald-100 rounded-2xl flex items-center justify-center mx-auto mb-8 shadow-sm rotate-3 group-hover:rotate-0 transition-transform duration-300">
            <i [class]="'fas ' + icon + ' text-4xl text-emerald-600'"></i>
          </div>
          
          <h1 class="text-3xl font-extrabold text-slate-800 mb-4">{{ title }}</h1>
          
          <p class="text-slate-500 mb-8 text-lg leading-relaxed">
            L'espace dédié aux <strong class="text-slate-700 font-bold">{{ title | lowercase }}</strong> est actuellement en cours de conception. 
            Il sera disponible très prochainement pour enrichir votre expérience.
          </p>
          
          <div class="inline-flex items-center gap-3 px-5 py-2.5 bg-slate-100 text-slate-700 rounded-xl text-sm font-bold shadow-sm border border-slate-200">
            <i class="fas fa-hammer text-amber-500"></i> En construction
          </div>
          
          <div class="mt-8 pt-6 border-t border-slate-100">
             <a routerLink="/admin/dashboard" class="text-emerald-600 hover:text-emerald-700 text-sm font-bold flex items-center justify-center gap-2 transition-colors">
               <i class="fas fa-arrow-left"></i> Retour au tableau de bord
             </a>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .fade-in { animation: fadeIn 0.5s ease-out forwards; }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }
  `]
})
export class ComingSoonComponent implements OnInit {
  title = 'Module';
  icon = 'fa-tools';

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(data => {
      if (data['title']) this.title = data['title'];
      if (data['icon']) this.icon = data['icon'];
    });
  }
}
