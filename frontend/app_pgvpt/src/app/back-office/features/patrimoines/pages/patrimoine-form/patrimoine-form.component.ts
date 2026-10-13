import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { PatrimoineApiService } from '../../../../../core/services/api/patrimoine-api.service';

@Component({
  selector: 'app-patrimoine-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './patrimoine-form.component.html',
  styleUrls: ['./patrimoine-form.component.css']
})
export class PatrimoineFormComponent implements OnInit {
  patrimoineForm!: FormGroup;
  isEditMode = false;
  patrimoineId: string | null = null;
  patrimoineStep: number = 1;
  patrimoineSteps = [
    { step: 1, label: '1. Identification', icon: 'fas fa-id-card' },
    { step: 2, label: '2. Caractéristiques', icon: 'fas fa-sliders-h' },
    { step: 3, label: '3. Histoire & culture', icon: 'fas fa-book-open' },
    { step: 4, label: '4. Protection', icon: 'fas fa-shield-alt' }
  ];

  showJsonPayload: boolean = false;
  jsonPayload: string = '';
  jsonTypeTitle: string = 'Données du patrimoine';
  notificationMessage: string | null = null;
  copiedSuccess: boolean = false;
  showSuccessModal: boolean = false;
  saveStatus: 'idle' | 'saving' | 'success' | 'error' = 'idle';
  errorMessage: string = '';

  categories = [
    'PATRIMOINE_NATUREL',
    'PATRIMOINE_HISTORIQUE',
    'PATRIMOINE_CULTUREL',
    'PATRIMOINE_ARCHITECTURAL',
    'PATRIMOINE_RELIGIEUX',
    'PATRIMOINE_COLONIAL',
    'PATRIMOINE_ARCHEOLOGIQUE',
    'PATRIMOINE_MUSEAL',
    'PATRIMOINE_MEMORIEL',
    'PATRIMOINE_MARITIME',
    'PATRIMOINE_RURAL',
    'AUTRE'
  ];

  statuts = ['BROUILLON', 'EN_VALIDATION', 'VALIDE', 'PUBLIE', 'ARCHIVE'];

  constructor(
    private fb: FormBuilder,
    private patrimoineApiService: PatrimoineApiService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.patrimoineId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.patrimoineId;
    this.initPatrimoineForm();
    if (this.isEditMode) {
      this.loadData();
    }
  }

  initPatrimoineForm(): void {
    this.patrimoineForm = this.fb.group({
      // 1. Identification
      code: [''],
      nom: ['', [Validators.required, Validators.minLength(2)]],
      nomLocal: [''],
      nomHistorique: [''],
      type: ['', Validators.required],
      categorie: ['', Validators.required],
      periode: [''],
      siecle: [''],
      description: ['', Validators.required],
      descriptionCourte: [''],
      nomPhotoPrincipale: [''],
      urlPhotoPrincipale: [''],
      sousCategorie: [''],
      dateOuverture: [null],
      organismeGestionnaire: [''],
      proprietaire: [''],
      gestionnaire: [''],
      contact: this.fb.group({
        nom: [''],
        fonction: [''],
        telephone: [''],
        email: [''],
        siteWeb: ['']
      }),
      accessibilite: this.fb.group({
        accessiblePublic: [false],
        accessibilitePMR: [false],
        accesFauteuilRoulant: [false],
        accesTransportPublic: [false],
        parking: [false],
        guideDisponible: [false],
        guideAudio: [false],
        accesEnfant: [false],
        langueVisite: [''],
        restrictions: [''],
        conditionsAcces: ['']
      }),


      // 2. Caractéristiques
      styleArchitectural: [''],
      anneeConstruction: [null],
      anneeRenovation: [null],
      architecte: [''],
      identiteArchitecte: [''],
      natureMonument: [''],
      materiauxConstruction: [''],
      dimensions: [''],
      commanditaire: [''],
      contexteHistorique: [''],
      personnagesAssocies: [''],

      nombreCollections: [null],
      nombreOeuvres: [null],
      capaciteAccueil: [null],
      museographie: [''],
      superficie: [null],
      natureSite: [''],
      ecosysteme: [''],
      biodiversite: [''],
      especesProtegees: this.fb.array([]),
      zoneProtegee: [false],
      categorieProtection: [''],
      risquesEnvironnementaux: [''],

      // 3. Histoire & culture
      historique: [''],
      importanceHistorique: [''],
      importanceCulturelle: [''],
      traditionsAssociees: [''],
      langues: [''],
      importanceTouristique: [''],
      valeurPatrimoniale: [''],
      valeurSpirituelle: [''],

      // 4. Protection
      statut: ['BROUILLON'],
      classePatrimoine: [''],
      referenceClassement: [''],
      dateClassement: [null],
      inscritUnesco: ['false'],
      nomSiteUnesco: [''],
      dateInscriptionUnesco: [null],
      protectionJuridique: [''],
      menaces: this.fb.array([]),
      conservation: this.fb.group({
        id: [null],
        etat: [''], // ex: EXCELLENT, BON, MOYEN, MAUVAIS, CRITIQUE, RUINE
        dateEvaluation: [null],
        descriptionEtat: [''],
        degradations: [''], // Comma-separated string mapped to List
        causesDegradation: [''], // Comma-separated string mapped to List
        travauxNecessaires: [''], // Comma-separated string mapped to List
        dernierTravaux: [''],
        dateDerniersTravaux: [null],
        organismeConservation: [''],
        budgetEstime: [null],
        recommandations: [''], // Comma-separated string mapped to List
        contactConservateur: this.fb.group({
          id: [null],
          nom: [''],
          fonction: [''],
          telephone: [''],
          email: [''],
          siteWeb: [''],
          organisme: ['']
        })
      }),

      // 5. Horaires et Expositions (FormArrays)
      horaires: this.fb.array([]),
      expositions: this.fb.array([]),
      collections: this.fb.array([])
    });

    this.patrimoineForm.get('type')?.valueChanges.subscribe(type => {
      const catCtrl = this.patrimoineForm.get('categorie');
      const collectionsCtrl = this.patrimoineForm.get('nombreCollections');

      if (type === 'SITE_NATUREL') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_NATUREL');
        collectionsCtrl?.clearValidators();
      } else if (type === 'MUSEE') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_MUSEAL');
        collectionsCtrl?.setValidators([Validators.required, Validators.min(0)]);
      } else if (type === 'MONUMENT') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_HISTORIQUE');
        collectionsCtrl?.clearValidators();
      } else {
        collectionsCtrl?.clearValidators();
      }
      collectionsCtrl?.updateValueAndValidity();
    });
  }



  get horairesArray(): FormArray {
    return this.patrimoineForm.get('horaires') as FormArray;
  }
  
  get menacesArray(): FormArray {
    return this.patrimoineForm.get('menaces') as FormArray;
  }

  addHoraire(): void {
    const h = this.fb.group({
      jour: ['LUNDI'],
      ouvert: [true],
      heureOuverture: [''],
      heureFermeture: [''],
      pauseDebut: [''],
      pauseFin: [''],
      surReservation: [false],
      commentaire: ['']
    });
    this.horairesArray.push(h);
  }

  removeHoraire(index: number): void {
    this.horairesArray.removeAt(index);
  }

  addMenace(): void {
    const m = this.fb.group({
      type: ['', Validators.required],
      niveau: ['MOYEN', Validators.required],
      description: [''],
      mesuresPrevention: ['']
    });
    this.menacesArray.push(m);
  }

  removeMenace(index: number): void {
    this.menacesArray.removeAt(index);
  }

  
  get expositionsArray(): FormArray {
    return this.patrimoineForm.get('expositions') as FormArray;
  }
  addExposition(): void {
    const e = this.fb.group({
      titre: [''],
      description: [''],
      dateDebut: [''],
      dateFin: [''],
      commissaire: [''],
      lieu: [''],
      expositionPermanente: [false],
      imageMediaId: [null]
    });
    this.expositionsArray.push(e);
  }
  removeExposition(index: number): void {
    this.expositionsArray.removeAt(index);
  }

  get collectionsArray(): FormArray {
    return this.patrimoineForm.get('collections') as FormArray;
  }
  addCollection(): void {
    const c = this.fb.group({
      nom: [''],
      description: [''],
      nombreOeuvres: [null],
      periode: [''],
      origine: [''],
      themes: [''] // Comma separated string for simplicity, mapped to List<String> in backend
    });
    this.collectionsArray.push(c);
  }
  removeCollection(index: number): void {
    this.collectionsArray.removeAt(index);
  }

  get especesProtegeesArray(): FormArray {
    return this.patrimoineForm.get('especesProtegees') as FormArray;
  }
  addEspeceProtegee(): void {
    const ep = this.fb.group({
      nomCommun: [''],
      nomScientifique: [''],
      nomLocal: [''],
      type: ['FAUNE'],
      statutConservation: [''],
      niveauProtection: [''],
      description: [''],
      photoMediaId: [null]
    });
    this.especesProtegeesArray.push(ep);
  }
  removeEspeceProtegee(index: number): void {
    this.especesProtegeesArray.removeAt(index);
  }

  setPatrimoineStep(step: number): void {
    this.patrimoineStep = step;
  }

  nextPatrimoineStep(): void {
    if (this.patrimoineStep < 4) this.patrimoineStep++;
  }

  prevPatrimoineStep(): void {
    if (this.patrimoineStep > 1) this.patrimoineStep--;
  }

  loadData(): void {
    if (!this.patrimoineId) return;
    this.patrimoineApiService.getById(this.patrimoineId).subscribe({
      next: (data: any) => {
        console.log('--- API DATA ---', data);
        console.log('Expositions from API:', data.expositions);
        console.log('Contact from API:', data.contact);
        console.log('Root telephone:', data.telephone);
        // Vider les FormArrays existants pour éviter les doublons
        this.horairesArray.clear();
        this.menacesArray.clear();
        this.expositionsArray.clear();
        this.collectionsArray.clear();
        this.especesProtegeesArray.clear();

        // 1. Horaires (tronquer HH:mm:ss en HH:mm pour les inputs HTML time)
        if (data.horaires && Array.isArray(data.horaires)) {
          data.horaires.forEach((h: any) => {
            this.horairesArray.push(this.fb.group({
              jour: [h.jour || 'LUNDI'],
              ouvert: [h.ouvert ?? true],
              heureOuverture: [h.heureOuverture ? String(h.heureOuverture).substring(0, 5) : ''],
              heureFermeture: [h.heureFermeture ? String(h.heureFermeture).substring(0, 5) : ''],
              pauseDebut: [h.pauseDebut ? String(h.pauseDebut).substring(0, 5) : ''],
              pauseFin: [h.pauseFin ? String(h.pauseFin).substring(0, 5) : ''],
              surReservation: [h.surReservation ?? false],
              commentaire: [h.commentaire || '']
            }));
          });
        }

        // 2. Menaces
        if (data.menaces && Array.isArray(data.menaces)) {
          data.menaces.forEach((m: any) => {
            this.menacesArray.push(this.fb.group({
              type: [m.type || '', Validators.required],
              niveau: [m.niveau || 'MOYEN', Validators.required],
              description: [m.description || ''],
              mesuresPrevention: [m.mesuresPrevention || '']
            }));
          });
        }

        // 3. Expositions
        if (data.expositions && Array.isArray(data.expositions)) {
          data.expositions.forEach((e: any) => {
            this.expositionsArray.push(this.fb.group({
              titre: [e.titre || ''],
              description: [e.description || ''],
              dateDebut: [e.dateDebut ? String(e.dateDebut).substring(0, 10) : ''],
              dateFin: [e.dateFin ? String(e.dateFin).substring(0, 10) : ''],
              commissaire: [e.commissaire || ''],
              lieu: [e.lieu || ''],
              expositionPermanente: [e.expositionPermanente ?? false],
              imageMediaId: [e.imageMediaId || null]
            }));
          });
        }

        // 4. Collections
        if (data.collections && Array.isArray(data.collections)) {
          data.collections.forEach((c: any) => {
            let themesStr = c.themes;
            if (Array.isArray(themesStr)) {
              themesStr = themesStr.join('\n');
            }
            this.collectionsArray.push(this.fb.group({
              nom: [c.nom || ''],
              description: [c.description || ''],
              nombreOeuvres: [c.nombreOeuvres ?? null],
              periode: [c.periode || ''],
              origine: [c.origine || ''],
              themes: [themesStr || '']
            }));
          });
        }

        // 5. Espèces protégées
        if (data.especesProtegees && Array.isArray(data.especesProtegees)) {
          data.especesProtegees.forEach((ep: any) => {
            this.especesProtegeesArray.push(this.fb.group({
              nomCommun: [ep.nomCommun || ''],
              nomScientifique: [ep.nomScientifique || ''],
              nomLocal: [ep.nomLocal || ''],
              type: [ep.type || 'FAUNE'],
              statutConservation: [ep.statutConservation || ''],
              niveauProtection: [ep.niveauProtection || ''],
              description: [ep.description || ''],
              photoMediaId: [ep.photoMediaId || null]
            }));
          });
        }

        // 6. Convertir les tableaux simples en chaînes pour les textarea
        ['traditionsAssociees', 'langues', 'risquesEnvironnementaux', 'materiauxConstruction', 'personnagesAssocies'].forEach(key => {
          if (data[key] && Array.isArray(data[key])) {
            data[key] = data[key].join('\n');
          }
        });

        // 7. Accessibilité
        if (data.accessibilite) {
          if (Array.isArray(data.accessibilite.langueVisite)) data.accessibilite.langueVisite = data.accessibilite.langueVisite.join('\n');
          if (Array.isArray(data.accessibilite.restrictions)) data.accessibilite.restrictions = data.accessibilite.restrictions.join('\n');
        }

        // 8. Formatage des dates racines
        if (data.dateOuverture) data.dateOuverture = String(data.dateOuverture).substring(0, 10);
        if (data.dateClassement) data.dateClassement = String(data.dateClassement).substring(0, 10);
        if (data.dateInscriptionUnesco) data.dateInscriptionUnesco = String(data.dateInscriptionUnesco).substring(0, 10);

        // 9. Booléens sous forme de string pour les <select>
        if (data.inscritUnesco !== undefined && data.inscritUnesco !== null) data.inscritUnesco = String(data.inscritUnesco);
        if (data.zoneProtegee !== undefined && data.zoneProtegee !== null) data.zoneProtegee = String(data.zoneProtegee);
        if (data.classePatrimoine !== undefined && data.classePatrimoine !== null) data.classePatrimoine = String(data.classePatrimoine);

        // 10. Patch global sans écraser les FormArrays ni les groupes complexes gérés manuellement
        const patchData = { ...data };
        delete patchData.horaires;
        delete patchData.menaces;
        delete patchData.expositions;
        delete patchData.collections;
        delete patchData.especesProtegees;
        delete patchData.contact;
        delete patchData.conservation;
        this.patrimoineForm.patchValue(patchData);

        // 11. Patch explicite du Contact (gère les propriétés plates retournées par l'API telephone, email, siteWeb)
        this.patrimoineForm.get('contact')?.patchValue({
          nom: data.contact?.nom || '',
          fonction: data.contact?.fonction || '',
          telephone: data.contact?.telephone || data.telephone || '',
          email: data.contact?.email || data.email || '',
          siteWeb: data.contact?.siteWeb || data.siteWeb || ''
        });

        // 12. Patch explicite et complet de Conservation
        if (data.conservation) {
          const cons = { ...data.conservation };
          ['degradations', 'causesDegradation', 'travauxNecessaires', 'recommandations'].forEach(k => {
            if (Array.isArray(cons[k])) {
              cons[k] = cons[k].join('\n');
            }
          });
          if (cons.dateEvaluation) cons.dateEvaluation = String(cons.dateEvaluation).substring(0, 10);
          if (cons.dateDerniersTravaux) cons.dateDerniersTravaux = String(cons.dateDerniersTravaux).substring(0, 10);

          this.patrimoineForm.get('conservation')?.patchValue(cons);
          if (cons.contactConservateur) {
            this.patrimoineForm.get('conservation.contactConservateur')?.patchValue(cons.contactConservateur);
          }
        }

        // 13. Photo principale
        if (data.photoPrincipale) {
          this.patrimoineForm.patchValue({
            urlPhotoPrincipale: data.photoPrincipale.urlPhotoPrincipale,
            nomPhotoPrincipale: data.photoPrincipale.nomPhotoPrincipale
          });
        } else if (data.photoPrincipaleUrl) {
          this.patrimoineForm.patchValue({
            urlPhotoPrincipale: data.photoPrincipaleUrl,
            nomPhotoPrincipale: data.nom
          });
        }
      },
      error: (err) => {
        console.error('Erreur lors du chargement du patrimoine', err);
        this.showNotification('Erreur de chargement du patrimoine', 'error');
      }
    });
  }

  onSubmit(): void {
    if (this.patrimoineForm.invalid) {
      this.notificationMessage = "Veuillez remplir tous les champs obligatoires du patrimoine.";
      setTimeout(() => this.notificationMessage = null, 3000);
      return;
    }
    this.generateJson();
  }

  copyJson(): void {
    navigator.clipboard.writeText(this.jsonPayload).then(() => {
      this.copiedSuccess = true;
      setTimeout(() => this.copiedSuccess = false, 2000);
    });
  }

  generateJson(): void {
    const raw = this.patrimoineForm.value;
    const type = raw.type;
    const baseFields = [
      'code', 'nom', 'nomLocal', 'nomHistorique', 'type', 'categorie', 'sousCategorie',
      'periode', 'siecle', 'dateOuverture', 'description', 'descriptionCourte',
      'nomPhotoPrincipale', 'urlPhotoPrincipale', 'organismeGestionnaire', 'proprietaire', 'gestionnaire',
      'contact', 'accessibilite', 'conservation'
    ];
    const cultureFields = [
      'historique', 'importanceHistorique', 'importanceCulturelle', 'importanceTouristique',
      'valeurPatrimoniale', 'valeurSpirituelle', 'traditionsAssociees', 'langues'
    ];
    const protectionFields = [
      'statut', 'classePatrimoine', 'referenceClassement', 'dateClassement',
      'inscritUnesco', 'nomSiteUnesco', 'dateInscriptionUnesco', 'protectionJuridique',
      'horaires', 'expositions', 'menaces'
    ];
    let specificFields: string[] = [];
    if (type === 'MONUMENT') {
      specificFields = [
        'styleArchitectural', 'anneeConstruction', 'anneeRenovation',
        'architecte', 'identiteArchitecte', 'natureMonument', 'materiauxConstruction',
        'dimensions', 'commanditaire', 'contexteHistorique', 'personnagesAssocies'
      ];
    } else if (type === 'MUSEE') {
      specificFields = [
        'nombreCollections', 'nombreOeuvres', 'capaciteAccueil',
        'collections', 'museographie'
      ];
    } else if (type === 'SITE_NATUREL') {
      specificFields = [
        'superficie', 'natureSite', 'ecosysteme', 'biodiversite', 'categorieProtection',
        'especesProtegees', 'zoneProtegee', 'risquesEnvironnementaux'
      ];
    }
    const allowedKeys = new Set([...baseFields, ...specificFields, ...cultureFields, ...protectionFields]);
    const cleanData: Record<string, any> = {};
    Object.keys(raw).forEach(key => {
      if (!allowedKeys.has(key)) return;
      const val = raw[key];
      if (val !== null && val !== undefined && val !== '') {
        if (key === 'inscritUnesco' || key === 'zoneProtegee' || key === 'classePatrimoine') {
          cleanData[key] = (val === 'true' || val === true);
        } else if (typeof val === 'string' && !isNaN(Number(val)) && ['anneeConstruction', 'anneeRenovation', 'nombreCollections', 'nombreOeuvres', 'capaciteAccueil', 'superficie'].includes(key)) {
          cleanData[key] = Number(val);
        } else if (['traditionsAssociees', 'langues', 'risquesEnvironnementaux', 'materiauxConstruction', 'personnagesAssocies'].includes(key)) {
          cleanData[key] = typeof val === 'string' ? val.split('\n').map((s: string) => s.trim()).filter((s: string) => s !== '') : val;
        } else if (key === 'collections' && Array.isArray(val)) {
          cleanData[key] = val.map(c => {
            const cleanC = { ...c };
            if (typeof cleanC.themes === 'string') {
              cleanC.themes = cleanC.themes.split('\n').map((s: string) => s.trim()).filter((s: string) => s !== '');
            }
            if (cleanC.nombreOeuvres !== null && cleanC.nombreOeuvres !== undefined && cleanC.nombreOeuvres !== '') {
              cleanC.nombreOeuvres = Number(cleanC.nombreOeuvres);
            } else {
              delete cleanC.nombreOeuvres;
            }
            return cleanC;
          });
        } else if (key === 'horaires' && Array.isArray(val)) {
          cleanData[key] = val.map(h => {
             const cleanH = { ...h };
             ['heureOuverture', 'heureFermeture', 'pauseDebut', 'pauseFin'].forEach(t => {
                if (!cleanH[t]) {
                   delete cleanH[t];
                } else if (typeof cleanH[t] === 'string' && cleanH[t].length === 5) {
                   cleanH[t] = cleanH[t] + ':00';
                }
             });
             return cleanH;
          });
        } else if (key === 'expositions' && Array.isArray(val)) {
          cleanData[key] = val.map(e => {
             const cleanE = { ...e };
             ['dateDebut', 'dateFin'].forEach(d => {
                if (!cleanE[d]) delete cleanE[d];
             });
             return cleanE;
          });
        } else if (key === 'accessibilite' && val) {
          const acc = { ...val };
          if (typeof acc.langueVisite === 'string') {
            acc.langueVisite = acc.langueVisite.split('\n').map((s: string) => s.trim()).filter((s: string) => s !== '');
          }
          if (typeof acc.restrictions === 'string') {
            acc.restrictions = acc.restrictions.split('\n').map((s: string) => s.trim()).filter((s: string) => s !== '');
          }
          cleanData[key] = acc;
        } else if (key === 'conservation' && val) {
          const cons = { ...val };
          ['degradations', 'causesDegradation', 'travauxNecessaires', 'recommandations'].forEach(k => {
            if (typeof cons[k] === 'string') {
              cons[k] = cons[k].split('\n').map((s: string) => s.trim()).filter((s: string) => s !== '');
            } else if (!Array.isArray(cons[k])) {
              cons[k] = [];
            }
          });
          if (!cons.dateEvaluation) delete cons.dateEvaluation;
          if (!cons.dateDerniersTravaux) delete cons.dateDerniersTravaux;
          if (cons.budgetEstime === '' || cons.budgetEstime === null || isNaN(Number(cons.budgetEstime))) {
            delete cons.budgetEstime;
          } else {
            cons.budgetEstime = Number(cons.budgetEstime);
          }
          if (cons.contactConservateur) {
            const cc = { ...cons.contactConservateur };
            if (!cc.siteWeb) delete cc.siteWeb;
            if (!cc.email) delete cc.email;
            if (!cc.telephone) delete cc.telephone;
            if (!cc.fonction) delete cc.fonction;
            if (!cc.organisme) delete cc.organisme;
            if (!cc.nom || cc.nom.trim() === '') {
              delete cons.contactConservateur;
            } else {
              cons.contactConservateur = cc;
            }
          }
          if (cons.etat) {
            cleanData[key] = cons;
          }
        } else if (key !== 'contact') {
          cleanData[key] = val;
        }
      }
    });

    // Mettre les champs contact au niveau racine requis par le contrat OpenAPI
    if (raw.contact) {
      if (raw.contact.telephone) cleanData['telephone'] = raw.contact.telephone;
      if (raw.contact.email) cleanData['email'] = raw.contact.email;
      if (raw.contact.siteWeb) cleanData['siteWeb'] = raw.contact.siteWeb;
    }

    // Nettoyer les dates racines vides pour éviter les erreurs Jackson 400
    ['dateOuverture', 'dateClassement', 'dateInscriptionUnesco'].forEach(d => {
      if (!cleanData[d]) delete cleanData[d];
    });

    if (raw.urlPhotoPrincipale) {
      cleanData['photoPrincipaleUrl'] = raw.urlPhotoPrincipale;
      cleanData['photoPrincipale'] = {
        nomPhotoPrincipale: raw.nomPhotoPrincipale || raw.nom,
        urlPhotoPrincipale: raw.urlPhotoPrincipale
      };
    }
    this.jsonPayload = JSON.stringify(cleanData, null, 2);
    this.showJsonPayload = true;
    this.saveStatus = 'saving';
    this.errorMessage = '';
    
    // Appel réel à l'API
    if (this.isEditMode && this.patrimoineId) {
      this.patrimoineApiService.update(this.patrimoineId, cleanData).subscribe({
        next: (res) => {
          this.saveStatus = 'success';
          this.showNotification('Patrimoine mis à jour avec succès.');
          setTimeout(() => {
            if (this.saveStatus === 'success') {
              this.router.navigate(['/admin/patrimoines']);
            }
          }, 2000);
        },
        error: (err) => {
          this.saveStatus = 'error';
          console.error('Erreur lors de la mise à jour', err);
          this.errorMessage = err?.error?.message || err?.message || 'Erreur lors de la mise à jour du patrimoine.';
          this.showNotification('Erreur lors de la mise à jour du patrimoine.', 'error');
        }
      });
    } else {
      this.patrimoineApiService.create(cleanData).subscribe({
        next: (res) => {
          this.patrimoineId = res.id;
          this.saveStatus = 'success';
          this.showNotification('Patrimoine créé avec succès.');
          setTimeout(() => {
            if (this.saveStatus === 'success') {
              this.router.navigate(['/admin/patrimoines']);
            }
          }, 2000);
        },
        error: (err) => {
          this.saveStatus = 'error';
          console.error('Erreur lors de la création', err);
          this.errorMessage = err?.error?.message || err?.message || 'Erreur lors de la création du patrimoine.';
          this.showNotification('Erreur lors de la création du patrimoine.', 'error');
        }
      });
    }
  }

  closeStatusModal(): void {
    this.saveStatus = 'idle';
  }

  navigateToPatrimoines(): void {
    this.saveStatus = 'idle';
    this.router.navigate(['/admin/patrimoines']);
  }

  navigateTo(route: string): void {
    if (this.patrimoineId) {
      this.router.navigate([`/admin/patrimoines/${this.patrimoineId}/${route}`]);
    }
  }

  showNotification(msg: string, type: 'success' | 'error' = 'success'): void {
    this.notificationMessage = msg;
    // On peut aussi adapter le style de notification selon `type` si besoin dans le template HTML
    setTimeout(() => this.notificationMessage = null, 4000);
  }

  closeModal(): void {
    this.showSuccessModal = false;
  }
}
