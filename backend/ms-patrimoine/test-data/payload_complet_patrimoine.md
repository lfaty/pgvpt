# Documentation du Payload (POST) - Création d'un Patrimoine (Musée)

Ce document décrit avec précision **tous les champs** pris en charge par l'Étape 1 (Formulaire Patrimoine) pour un objet de type `MUSEE`. Il sert de référence absolue pour tester votre Backend.

Vous pouvez copier ce JSON et l'importer dans Postman, ou utiliser ces valeurs comme référence pour remplir manuellement votre interface Angular.

## Payload JSON Complet et Documenté

```json
{
  // ==========================================
  // ÉTAPE 1 : IDENTIFICATION
  // ==========================================
  "code": "SN-DKR-001",                               // (Optionnel) Identifiant administratif
  "nom": "La Maison des Esclaves",                      // (Obligatoire) Nom principal du site
  "nomLocal": "Keur Ndongo",                            // (Optionnel) Nom en langue locale
  "nomHistorique": "Esclaverie de Gorée",               // (Optionnel) Ancien nom historique
  "type": "MUSEE",                                      // (Obligatoire) MONUMENT, MUSEE ou SITE_NATUREL
  "categorie": "PATRIMOINE_MUSEAL",                     // (Obligatoire) Sous-catégorie associée au type
  "periode": "EPOQUE_COLONIALE",                        // (Optionnel) Période historique
  "siecle": "XVIIIe siècle",                            // (Optionnel) Siècle de création
  "description": "Bâtiment historique situé sur l'île de Gorée, devenu un symbole universel de la traite négrière.", // (Obligatoire)
  "descriptionCourte": "Symbole de la mémoire de l'esclavage à Gorée", // (Optionnel) Max 1000 chars
  
  // -- Photo Principale --
  "photoPrincipaleUrl": "https://images.unsplash.com/photo-1572953109213-3be62398eb95", // Url directe
  "photoPrincipale": {
    "nomPhotoPrincipale": "Façade extérieure et porche de la Maison", // Nom de la photo
    "urlPhotoPrincipale": "https://images.unsplash.com/photo-1572953109213-3be62398eb95"  // Répétition pour l'objet imbriqué
  },

  // ==========================================
  // ÉTAPE 2 : CARACTÉRISTIQUES (Spécifique MUSEE)
  // ==========================================
  "nombreCollections": 5,                               // (Obligatoire pour Musée) 
  "nombreOeuvres": 250,                                 // (Optionnel) Quantité estimée d'œuvres
  "capaciteAccueil": 200,                               // (Optionnel) Nombre de visiteurs max simultanés
  "typesCollections": "Objets historiques, Fers, Documents d'archives", // (Optionnel)
  "museographie": "Parcours chronologique sur 2 étages",// (Optionnel)

  // ==========================================
  // ÉTAPE 3 : HISTOIRE ET CULTURE
  // ==========================================
  "historique": "Construite en 1776, cette maison a été transformée en musée en 1962 grâce à Joseph Ndiaye.", // (Optionnel)
  "importanceHistorique": "Point de départ symbolique des esclaves vers les Amériques", // (Optionnel)
  "importanceCulturelle": "Lieu de recueillement et de pèlerinage pour la diaspora africaine", // (Optionnel)

  // ==========================================
  // ÉTAPE 4 : PROTECTION ET GESTION
  // ==========================================
  "statut": "PUBLIE",                                   // BROUILLON, PUBLIE, ou ARCHIVE
  "classePatrimoine": "Patrimoine Mondial de l'Humanité",// (Optionnel) Intitulé de la classe
  "referenceClassement": "UNESCO Ref 26",               // (Optionnel) Numéro de décret/référence
  "inscritUnesco": true,                                // (Optionnel) true/false
  "protectionJuridique": "Classé sur la liste du patrimoine historique sénégalais en 1975.", // (Optionnel)

  // -- Les nouveaux FormArrays ajoutés --
  "horaires": [
    {
      "jour": "MARDI",
      "ouvert": true,
      "heureOuverture": "10:30:00",
      "heureFermeture": "17:00:00",
      "commentaire": "Ouverture continue sans pause midi"
    },
    {
      "jour": "LUNDI",
      "ouvert": false,
      "heureOuverture": "",
      "heureFermeture": "",
      "commentaire": "Fermeture hebdomadaire pour entretien"
    }
  ],
  
  "expositions": [
    {
      "titre": "L'esclavage à travers les âges",
      "permanente": true,
      "description": "Exposition principale relatant la traite transatlantique."
    }
  ]
}
```

## Consignes pour le Test
1. Allez sur votre formulaire Angular.
2. Entrez scrupuleusement ces valeurs dans chaque champ.
3. Arrivé à l'étape 4, cliquez deux fois sur **"Ajouter un horaire"** et remplissez Mardi (Ouvert) et Lundi (Fermé).
4. Cliquez sur **"Ajouter une exposition"** et remplissez-la.
5. Cliquez sur **Enregistrer** !
6. Vous pourrez alors comparer le JSON généré par Angular (via le bouton Générer JSON) avec ce modèle de référence.
