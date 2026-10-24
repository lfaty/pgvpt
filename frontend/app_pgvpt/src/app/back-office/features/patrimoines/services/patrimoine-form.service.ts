import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class PatrimoineFormService {

  readonly categories = [
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

  readonly statuts = ['BROUILLON', 'EN_VALIDATION', 'VALIDE', 'PUBLIE', 'ARCHIVE'];

  constructor(private fb: FormBuilder) {}

  /**
   * Initialise la structure complète du Reactive Form pour un patrimoine
   */
  createPatrimoineForm(): FormGroup {
    const form = this.fb.group({
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
      importanceTouristique: [''],
      valeurPatrimoniale: [''],
      valeurSpirituelle: [''],
      traditionsAssociees: [''],
      langues: [''],

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
        etat: [''],
        dateEvaluation: [null],
        descriptionEtat: [''],
        degradations: [''],
        causesDegradation: [''],
        travauxNecessaires: [''],
        dernierTravaux: [''],
        dateDerniersTravaux: [null],
        organismeConservation: [''],
        budgetEstime: [null],
        recommandations: [''],
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

    this.setupTypeValidation(form);
    return form;
  }

  /**
   * Configure les validateurs conditionnels selon le type sélectionné
   */
  private setupTypeValidation(form: FormGroup): void {
    form.get('type')?.valueChanges.subscribe(type => {
      const catCtrl = form.get('categorie');
      const collectionsCtrl = form.get('nombreCollections');
      const superficieCtrl = form.get('superficie');
      const natureSiteCtrl = form.get('natureSite');
      const ecosystemeCtrl = form.get('ecosysteme');
      const styleArchCtrl = form.get('styleArchitectural');
      const natureMonumentCtrl = form.get('natureMonument');

      collectionsCtrl?.clearValidators();
      superficieCtrl?.clearValidators();
      natureSiteCtrl?.clearValidators();
      ecosystemeCtrl?.clearValidators();
      styleArchCtrl?.clearValidators();
      natureMonumentCtrl?.clearValidators();

      if (type === 'SITE_NATUREL') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_NATUREL');
        superficieCtrl?.setValidators([Validators.required, Validators.min(0.01)]);
        natureSiteCtrl?.setValidators([Validators.required]);
        ecosystemeCtrl?.setValidators([Validators.required]);
      } else if (type === 'MUSEE') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_MUSEAL');
        collectionsCtrl?.setValidators([Validators.required, Validators.min(0)]);
      } else if (type === 'MONUMENT') {
        if (!catCtrl?.value) catCtrl?.setValue('PATRIMOINE_HISTORIQUE');
        styleArchCtrl?.setValidators([Validators.required]);
        natureMonumentCtrl?.setValidators([Validators.required]);
      }

      collectionsCtrl?.updateValueAndValidity();
      superficieCtrl?.updateValueAndValidity();
      natureSiteCtrl?.updateValueAndValidity();
      ecosystemeCtrl?.updateValueAndValidity();
      styleArchCtrl?.updateValueAndValidity();
      natureMonumentCtrl?.updateValueAndValidity();
    });
  }

  /**
   * Remplit le formulaire réactif avec les données issues du backend
   */
  populateForm(form: FormGroup, data: any): void {
    const horairesArray = form.get('horaires') as FormArray;
    const menacesArray = form.get('menaces') as FormArray;
    const expositionsArray = form.get('expositions') as FormArray;
    const collectionsArray = form.get('collections') as FormArray;
    const especesProtegeesArray = form.get('especesProtegees') as FormArray;

    // Réinitialiser les FormArrays
    horairesArray.clear();
    menacesArray.clear();
    expositionsArray.clear();
    collectionsArray.clear();
    especesProtegeesArray.clear();

    // 1. Horaires
    if (data.horaires && Array.isArray(data.horaires)) {
      data.horaires.forEach((h: any) => {
        horairesArray.push(this.fb.group({
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
        menacesArray.push(this.fb.group({
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
        expositionsArray.push(this.fb.group({
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
        collectionsArray.push(this.fb.group({
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
        especesProtegeesArray.push(this.fb.group({
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

    // 6. Tableaux simples en chaînes
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

    // 10. Patch global
    const patchData = { ...data };
    delete patchData.horaires;
    delete patchData.menaces;
    delete patchData.expositions;
    delete patchData.collections;
    delete patchData.especesProtegees;
    delete patchData.contact;
    delete patchData.conservation;

    form.patchValue(patchData);

    // 11. Patch Contact
    form.get('contact')?.patchValue({
      nom: data.contact?.nom || '',
      fonction: data.contact?.fonction || '',
      telephone: data.contact?.telephone || data.telephone || '',
      email: data.contact?.email || data.email || '',
      siteWeb: data.contact?.siteWeb || data.siteWeb || ''
    });

    // 12. Patch Conservation
    if (data.conservation) {
      const cons = { ...data.conservation };
      ['degradations', 'causesDegradation', 'travauxNecessaires', 'recommandations'].forEach(k => {
        if (Array.isArray(cons[k])) {
          cons[k] = cons[k].join('\n');
        }
      });
      if (cons.dateEvaluation) cons.dateEvaluation = String(cons.dateEvaluation).substring(0, 10);
      if (cons.dateDerniersTravaux) cons.dateDerniersTravaux = String(cons.dateDerniersTravaux).substring(0, 10);

      form.get('conservation')?.patchValue(cons);
      if (cons.contactConservateur) {
        form.get('conservation.contactConservateur')?.patchValue(cons.contactConservateur);
      }
    }

    // 13. Photo principale
    if (data.photoPrincipale) {
      form.patchValue({
        urlPhotoPrincipale: data.photoPrincipale.urlPhotoPrincipale,
        nomPhotoPrincipale: data.photoPrincipale.nomPhotoPrincipale
      });
    } else if (data.photoPrincipaleUrl) {
      form.patchValue({
        urlPhotoPrincipale: data.photoPrincipaleUrl,
        nomPhotoPrincipale: data.nom
      });
    }
  }

  /**
   * Construit le payload API propre à envoyer au backend
   */
  buildPayload(raw: any): any {
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

    if (raw.contact) {
      if (raw.contact.telephone) cleanData['telephone'] = raw.contact.telephone;
      if (raw.contact.email) cleanData['email'] = raw.contact.email;
      if (raw.contact.siteWeb) cleanData['siteWeb'] = raw.contact.siteWeb;
    }

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

    return cleanData;
  }
}
