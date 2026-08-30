# Données de test pour Swagger UI — Patrimoines

Base URL locale : `http://localhost:3001/api/v1`.

Dans Swagger, exécutez d'abord **POST /patrimoines**, puis copiez l'`id` retourné dans toutes les routes contenant `{id}`. Après l'ajout de la photo, copiez aussi son `id` pour `{photoId}`.

## POST /patrimoines

```json
{
  "code": "MON-DAK-TEST-001",
  "nom": "Maison des Esclaves - test Swagger",
  "nomLocal": "Maison des Esclaves",
  "nomHistorique": "Maison des captifs",
  "type": "MONUMENT",
  "categorie": "PATRIMOINE_MEMORIEL",
  "sousCategorie": "Lieu de mémoire",
  "periode": "COLONIALE",
  "siecle": "XVIIIe siècle",
  "dateOuverture": "1967-01-01",
  "description": "Lieu historique de mémoire situé sur l'île de Gorée.",
  "descriptionCourte": "Monument mémoriel majeur de Gorée.",
  "historique": "Édifiée au XVIIIe siècle, la maison est devenue un lieu de transmission de la mémoire.",
  "importanceHistorique": "Témoignage majeur de l'histoire de la traite atlantique.",
  "importanceCulturelle": "Lieu de recueillement et d'éducation patrimoniale.",
  "importanceTouristique": "Site incontournable pour les visiteurs de Dakar.",
  "valeurPatrimoniale": "Exceptionnelle",
  "valeurSpirituelle": "Lieu de mémoire et de recueillement.",
  "traditionsAssociees": ["Commémorations", "Visites pédagogiques"],
  "langues": ["Français", "Wolof", "Anglais"],
  "etatConservation": "BON",
  "statut": "BROUILLON",
  "classePatrimoine": true,
  "referenceClassement": "ARRETE-1966-42",
  "dateClassement": "1966-11-01",
  "inscritUnesco": true,
  "nomSiteUnesco": "Île de Gorée",
  "dateInscriptionUnesco": "1978-09-01",
  "protectionJuridique": "Classé monument historique",
  "organismeGestionnaire": "Direction du Patrimoine culturel",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "Conservateur de la Maison des Esclaves",
  "contact": {
    "nom": "Aminata Ndiaye",
    "fonction": "Conservatrice",
    "telephone": "+221 33 822 49 26",
    "email": "contact.maison-esclaves@example.sn",
    "siteWeb": "https://example.sn/maison-des-esclaves"
  },
  "siteWeb": "https://example.sn/maison-des-esclaves",
  "email": "contact.maison-esclaves@example.sn",
  "telephone": "+221338224926",
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": false,
    "accesFauteuilRoulant": false,
    "accesTransportPublic": true,
    "parking": false,
    "guideDisponible": true,
    "guideAudio": true,
    "langueVisite": ["Français", "Anglais", "Wolof"],
    "accesEnfant": true,
    "conditionsAcces": "Accès par la navette maritime vers Gorée.",
    "restrictions": ["Animaux non admis", "Photos sans flash"]
  },
  "tarification": {
    "gratuit": false,
    "tarifNormal": 1500,
    "devise": "XOF",
    "tarifEnfant": 500,
    "tarifEtudiant": 1000,
    "tarifResident": 1000,
    "tarifGroupe": 1200,
    "tarifGuide": 5000,
    "conditionsTarifaires": "Tarif groupe à partir de 10 personnes."
  },
  "menaces": [{
    "type": "Humidité saline",
    "niveau": "MOYEN",
    "description": "L'air marin accélère l'usure des matériaux.",
    "mesuresPrevention": "Suivi annuel et traitement des façades."
  }],
  "recommandationsVisiteur": ["Prévoir la traversée maritime", "Respecter le caractère mémoriel du lieu"],
  "reglesVisite": ["Ne pas toucher les collections", "Photographie sans flash uniquement"],
  "activitesTouristiques": ["Visite guidée", "Visite pédagogique", "Photographie"],
  "meilleurePeriodeVisite": "Novembre à mai",
  "dureeVisiteEstimeeMinutes": 60,
  "styleArchitectural": "Architecture coloniale afro-portugaise",
  "anneeConstruction": 1776,
  "identiteArchitecte": "Inconnu",
  "natureMonument": "MONUMENT_HISTORIQUE"
}
```

## GET /patrimoines

Utilisez par exemple les paramètres :

```text
page=0&size=10&sort=nom,asc&type=MONUMENT&categorie=PATRIMOINE_MEMORIEL&statut=BROUILLON&etatConservation=BON&accessiblePublic=true&inscritUnesco=true&classePatrimoine=true&q=Esclaves
```

## GET /patrimoines/{id}

Dans Swagger, remplacez `{id}` par l'identifiant de la réponse de création.

## PUT /patrimoines/{id}

```json
{
  "code": "MON-DAK-TEST-001",
  "nom": "Maison des Esclaves - test Swagger mise à jour",
  "type": "MONUMENT",
  "categorie": "PATRIMOINE_MEMORIEL",
  "description": "Lieu historique de mémoire situé sur l'île de Gorée, avec médiation renforcée.",
  "descriptionCourte": "Monument mémoriel de Gorée, récemment documenté.",
  "etatConservation": "BON",
  "statut": "EN_VALIDATION",
  "langues": ["Français", "Wolof", "Anglais"],
  "styleArchitectural": "Architecture coloniale afro-portugaise",
  "anneeConstruction": 1776,
  "identiteArchitecte": "Inconnu",
  "natureMonument": "MONUMENT_HISTORIQUE"
}
```

## GET /patrimoines/recherche

Utilisez les paramètres :

```text
q=Gorée&type=MONUMENT&categorie=PATRIMOINE_MEMORIEL&region=Dakar&departement=Dakar&commune=Gorée
```

`region`, `departement` et `commune` sont présents dans le contrat Swagger, mais ils ne sont pas encore exploités par le service de recherche actuel.

## POST /patrimoines/{id}/photos

```json
{
  "url": "https://images.example.sn/patrimoines/maison-esclaves-facade.jpg",
  "miniatureUrl": "https://images.example.sn/patrimoines/maison-esclaves-facade-320.jpg",
  "titre": "Façade de la Maison des Esclaves",
  "description": "Vue de la cour principale.",
  "auteur": "PGVPT - équipe éditoriale",
  "datePrise": "2026-08-01",
  "droits": "Tous droits réservés",
  "licence": "CC BY-NC 4.0",
  "photoPrincipale": true,
  "ordre": 1
}
```

## GET /patrimoines/{id}/photos

Utilisez le même `{id}`.

## DELETE /patrimoines/{id}/photos/{photoId}

Utilisez `{photoId}` retourné par `POST /patrimoines/{id}/photos`.

## PUT /patrimoines/{id}/horaires

```json
[
  { "jour": "LUNDI", "ouvert": false, "surReservation": false, "commentaire": "Fermé hebdomadaire" },
  { "jour": "MARDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:00:00", "surReservation": false },
  { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:00:00", "surReservation": false },
  { "jour": "JEUDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:00:00", "surReservation": false },
  { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:30:00", "surReservation": false },
  { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:30:00", "surReservation": false },
  { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "17:00:00", "surReservation": true, "commentaire": "Réservation recommandée pour les groupes" }
]
```

## GET /patrimoines/{id}/horaires

Utilisez le même `{id}` après la mise à jour.

## PUT /patrimoines/{id}/conservation

```json
{
  "etat": "BON",
  "dateEvaluation": "2026-07-15",
  "evaluateur": "Mamadou Diallo, architecte du patrimoine",
  "descriptionEtat": "Structure stable ; altérations superficielles liées à l'humidité saline.",
  "degradations": ["Écaillage ponctuel des enduits", "Traces d'humidité sur les murs extérieurs"],
  "causesDegradation": ["Air marin salin", "Forte fréquentation touristique"],
  "travauxNecessaires": ["Réfection localisée des enduits", "Contrôle annuel de l'humidité"],
  "dernierTravaux": "Restauration des façades et traitement préventif",
  "dateDerniersTravaux": "2024-11-20",
  "organismeConservation": "Direction du Patrimoine culturel",
  "budgetEstime": 12500000,
  "recommandations": ["Installer des capteurs d'humidité", "Limiter les groupes à 25 visiteurs"]
}
```

## GET /patrimoines/{id}/conservation

Utilisez le même `{id}`. Dans l'état actuel du backend, GET et PUT conservation retournent `501 Not Implemented` : il manque l'implémentation de `ConservationController`.

## PATCH /patrimoines/{id}/publier et PATCH /patrimoines/{id}/depublier

Ces deux routes n'ont pas de corps JSON. Saisissez seulement `{id}` puis cliquez sur **Execute**.

## DELETE /patrimoines/{id}

Saisissez `{id}`. Exécutez cette requête en dernier : elle retourne `204 No Content`.
