# Données de test - MS Patrimoine (CRUD)

Requêtes prêtes à l'emploi, alignées sur `src/main/resources/openapi/api.yml` (v2.0.0).

- **Via Gateway** : `http://localhost:8080/api/v1`
- **En direct** : `http://localhost:3001/api/v1`

*Note : remplacez `{{monumentId}}`, `{{museeId}}`, `{{siteId}}`… par les UUID renvoyés lors de la création.*

> ⚠️ Les champs texte non annotés (`description`, `historique`, …) sont en `varchar(255)` : les textes ci-dessous restent volontairement sous 255 caractères.
> ⚠️ N'envoyez **pas** `conservation` dans le POST : utilisez l'endpoint dédié `PUT /patrimoines/{id}/conservation` (étape 6).

---

## 1. CREATE (POST)

### 1.1 Monument - Maison des Esclaves
**Endpoint** : `POST http://localhost:8080/api/v1/patrimoines`
```json
{
  "type": "MONUMENT",
  "code": "SN-DKR-00125",
  "nom": "Maison des Esclaves",
  "nomLocal": "Kër Jaam yi",
  "nomHistorique": "Maison des Esclaves de Gorée",
  "categorie": "PATRIMOINE_MEMORIEL",
  "sousCategorie": "Lieu de mémoire de la traite",
  "periode": "COLONIALE",
  "siecle": "XVIIIe siècle",
  "dateOuverture": "1962-01-01",
  "description": "Maison historique de l'île de Gorée, symbole mondial de la mémoire de la traite négrière atlantique.",
  "descriptionCourte": "Lieu de mémoire de la traite négrière sur l'île de Gorée.",
  "historique": "Construite vers 1776 par les Hollandais, restaurée en 1990. Célèbre pour sa porte du voyage sans retour.",
  "importanceHistorique": "Témoin majeur de la traite atlantique",
  "importanceCulturelle": "Lieu de recueillement pour la diaspora africaine",
  "importanceTouristique": "Site le plus visité de Gorée",
  "valeurPatrimoniale": "Valeur universelle exceptionnelle",
  "valeurSpirituelle": "Lieu de mémoire et de réconciliation",
  "traditionsAssociees": ["Cérémonies commémoratives", "Visites de la diaspora"],
  "langues": ["Français", "Wolof", "Anglais"],
  "statut": "BROUILLON",
  "classePatrimoine": true,
  "referenceClassement": "Arrêté n°001/MCULT/1975",
  "dateClassement": "1975-06-15",
  "inscritUnesco": true,
  "nomSiteUnesco": "Île de Gorée",
  "dateInscriptionUnesco": "1978-09-08",
  "protectionJuridique": "Monument historique classé",
  "organismeGestionnaire": "Ministère de la Culture",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "Direction du Patrimoine Culturel",
  "siteWeb": "https://www.maisondesesclaves.sn",
  "email": "contact@maisondesesclaves.sn",
  "telephone": "+221338221528",
  "photoPrincipale": {
    "nomPhotoPrincipale": "Façade Maison des Esclaves",
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/maison-esclaves-facade.jpg"
  },
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
    "conditionsAcces": "Accès par chaloupe depuis le port de Dakar",
    "restrictions": ["Photos au flash interdites"]
  },
  "horaires": [
    { "jour": "LUNDI", "ouvert": false, "commentaire": "Fermé le lundi" },
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "12:30:00", "pauseFin": "14:30:00", "surReservation": false }
  ],
  "menaces": [
    { "type": "Erosion côtière", "niveau": "MOYEN", "description": "Avancée de la mer", "mesuresPrevention": "Digue de protection" }
  ],
  "styleArchitectural": "Architecture coloniale",
  "anneeConstruction": 1776,
  "identiteArchitecte": "Inconnu",
  "natureMonument": "MONUMENT_HISTORIQUE",
  "materiauxConstruction": ["Pierre basaltique", "Chaux", "Bois"],
  "dimensions": "20m x 15m, 2 niveaux",
  "commanditaire": "Compagnie hollandaise des Indes occidentales",
  "contexteHistorique": "Période de la traite atlantique",
  "personnagesAssocies": ["Boubacar Joseph Ndiaye"]
}
```

### 1.2 Musée - Musée des Civilisations Noires
**Endpoint** : `POST http://localhost:8080/api/v1/patrimoines`
```json
{
  "type": "MUSEE",
  "code": "SN-DKR-00310",
  "nom": "Musée des Civilisations Noires",
  "nomLocal": "MCN",
  "categorie": "PATRIMOINE_MUSEAL",
  "sousCategorie": "Musée national",
  "periode": "CONTEMPORAINE",
  "siecle": "XXIe siècle",
  "dateOuverture": "2018-12-06",
  "description": "Musée consacré aux civilisations noires d'Afrique et de la diaspora, situé sur le boulevard du Général de Gaulle à Dakar.",
  "descriptionCourte": "Grand musée panafricain de Dakar.",
  "historique": "Imaginé par Léopold Sédar Senghor en 1966, inauguré en 2018.",
  "importanceCulturelle": "Vitrine des cultures africaines",
  "importanceTouristique": "Incontournable culturel de Dakar",
  "langues": ["Français", "Anglais"],
  "statut": "BROUILLON",
  "classePatrimoine": false,
  "inscritUnesco": false,
  "organismeGestionnaire": "Ministère de la Culture",
  "proprietaire": "État du Sénégal",
  "siteWeb": "https://mcn.sn",
  "email": "info@mcn.sn",
  "telephone": "+221338895050",
  "photoPrincipale": {
    "nomPhotoPrincipale": "Façade du Musée des Civilisations Noires",
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/mcn-facade.jpg"
  },
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": true,
    "accesFauteuilRoulant": true,
    "accesTransportPublic": true,
    "parking": true,
    "guideDisponible": true,
    "guideAudio": false,
    "langueVisite": ["Français", "Anglais"],
    "accesEnfant": true,
    "conditionsAcces": "Billet d'entrée obligatoire"
  },
  "horaires": [
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "19:00:00" },
    { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "17:00:00", "surReservation": true }
  ],
  "nombreCollections": 2,
  "collections": [
    { "nom": "Masques et statuaires", "description": "Art sacré d'Afrique de l'Ouest", "nombreOeuvres": 450, "periode": "XIXe-XXe siècle", "origine": "Afrique de l'Ouest", "themes": ["Rituel", "Sculpture"] },
    { "nom": "Textiles africains", "description": "Pagnes tissés et teintures", "nombreOeuvres": 200, "periode": "XXe siècle", "origine": "Sénégal, Mali", "themes": ["Artisanat"] }
  ],
  "expositions": [
    { "titre": "Civilisations africaines", "description": "Exposition inaugurale", "dateDebut": "2018-12-06", "commissaire": "Hamady Bocoum", "lieu": "Salle principale", "expositionPermanente": true }
  ]
}
```

### 1.3 Site naturel - Parc national des oiseaux du Djoudj
**Endpoint** : `POST http://localhost:8080/api/v1/patrimoines`
```json
{
  "type": "SITE_NATUREL",
  "code": "SN-STL-00042",
  "nom": "Parc national des oiseaux du Djoudj",
  "categorie": "PATRIMOINE_NATUREL",
  "sousCategorie": "Zone humide",
  "periode": "CONTEMPORAINE",
  "dateOuverture": "1971-04-14",
  "description": "Zone humide du delta du fleuve Sénégal accueillant près de 3 millions d'oiseaux migrateurs chaque année.",
  "descriptionCourte": "Troisième réserve ornithologique mondiale.",
  "importanceTouristique": "Observation des pélicans et flamants roses",
  "langues": ["Français", "Wolof"],
  "statut": "BROUILLON",
  "classePatrimoine": true,
  "inscritUnesco": true,
  "nomSiteUnesco": "Parc national des oiseaux du Djoudj",
  "dateInscriptionUnesco": "1981-10-26",
  "protectionJuridique": "Parc national",
  "organismeGestionnaire": "Direction des Parcs Nationaux",
  "photoPrincipale": {
    "nomPhotoPrincipale": "Pélicans au Djoudj",
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/djoudj-pelicans.jpg"
  },
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": false,
    "parking": true,
    "guideDisponible": true,
    "langueVisite": ["Français", "Anglais"],
    "accesEnfant": true,
    "conditionsAcces": "Ouvert de novembre à avril"
  },
  "horaires": [
    { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00" }
  ],
  "menaces": [
    { "type": "Plantes invasives", "niveau": "ELEVE", "description": "Prolifération du typha", "mesuresPrevention": "Arrachage mécanique" }
  ],
  "superficie": 16000,
  "natureSite": "ZONE_HUMIDE",
  "ecosysteme": "Delta et marécages",
  "especesProtegees": [
    { "nomCommun": "Pélican blanc", "nomScientifique": "Pelecanus onocrotalus", "nomLocal": "Ngaab", "type": "FAUNE", "statutConservation": "Préoccupation mineure", "niveauProtection": "Intégrale", "description": "Colonie nicheuse majeure" },
    { "nomCommun": "Flamant rose", "nomScientifique": "Phoenicopterus roseus", "type": "FAUNE", "statutConservation": "Préoccupation mineure" }
  ]
}
```

### 1.4 Monument jetable (pour tester DELETE)
**Endpoint** : `POST http://localhost:8080/api/v1/patrimoines`
```json
{
  "type": "MONUMENT",
  "code": "TEST-DEL-001",
  "nom": "Monument de test à supprimer",
  "categorie": "AUTRE",
  "description": "Patrimoine temporaire pour tester la suppression.",
  "classePatrimoine": false,
  "inscritUnesco": false,
  "styleArchitectural": "Moderne",
  "natureMonument": "STATUE",
  "photoPrincipale": {
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/test-delete.jpg"
  }
}
```

### 1.5 Cas d'erreur (attendu : `400 Bad Request`)
```json
{
  "type": "MONUMENT",
  "nom": "Sans catégorie ni description",
  "styleArchitectural": "Moderne"
}
```
```json
{
  "type": "INCONNU",
  "nom": "Type invalide",
  "categorie": "AUTRE",
  "description": "Le discriminant 'type' n'existe pas"
}
```

---

## 2. READ (GET)

| Action | Requête |
|---|---|
| Tout lister | `GET /api/v1/patrimoines/all?page=0&size=20` |
| Lister + trier | `GET /api/v1/patrimoines?page=0&size=10&sort=nom,asc` |
| Filtre type | `GET /api/v1/patrimoines?type=MONUMENT` |
| Filtre catégorie | `GET /api/v1/patrimoines?categorie=PATRIMOINE_NATUREL` |
| Filtre statut | `GET /api/v1/patrimoines?statut=PUBLIE` |
| Filtre UNESCO | `GET /api/v1/patrimoines?inscritUnesco=true&classePatrimoine=true` |
| Accessibles | `GET /api/v1/patrimoines?accessiblePublic=true` |
| Recherche texte | `GET /api/v1/patrimoines?q=Gorée` |
| Par ID | `GET /api/v1/patrimoines/{{monumentId}}` |
| ID inexistant (404) | `GET /api/v1/patrimoines/00000000-0000-0000-0000-000000000000` |

---

## 3. UPDATE complet (PUT)

> `type` doit correspondre au type existant, sinon erreur « Incompatibilité de type ». `nom`, `type`, `categorie`, `description` sont obligatoires.

**Endpoint** : `PUT http://localhost:8080/api/v1/patrimoines/{{monumentId}}`
```json
{
  "type": "MONUMENT",
  "nom": "Maison des Esclaves de Gorée",
  "categorie": "PATRIMOINE_MEMORIEL",
  "description": "Description mise à jour : musée-mémorial de la traite négrière sur l'île de Gorée.",
  "descriptionCourte": "Mémorial de la traite négrière.",
  "classePatrimoine": true,
  "inscritUnesco": true,
  "styleArchitectural": "Architecture coloniale afro-portugaise",
  "anneeConstruction": 1780,
  "natureMonument": "MONUMENT_COMMEMORATIF",
  "materiauxConstruction": ["Pierre basaltique", "Chaux"],
  "photoPrincipale": {
    "nomPhotoPrincipale": "Porte du voyage sans retour",
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/maison-esclaves-porte.jpg"
  }
}
```

**Endpoint** : `PUT http://localhost:8080/api/v1/patrimoines/{{museeId}}`
```json
{
  "type": "MUSEE",
  "nom": "Musée des Civilisations Noires",
  "categorie": "PATRIMOINE_MUSEAL",
  "description": "Musée panafricain de 14 000 m² dédié aux civilisations noires.",
  "nombreCollections": 3
}
```

---

## 4. UPDATE partiel (PATCH)

**Endpoint** : `PATCH http://localhost:8080/api/v1/patrimoines/{{siteId}}`
```json
{
  "descriptionCourte": "Paradis des oiseaux migrateurs.",
  "superficie": 16500,
  "capaciteAccueil": 300,
  "zoneProtegee": true,
  "categorieProtection": "Parc national - Ramsar"
}
```

**Endpoint** : `PATCH http://localhost:8080/api/v1/patrimoines/{{museeId}}`
```json
{
  "nombreOeuvres": 18000,
  "museographie": "Parcours thématique circulaire",
  "servicesMusee": ["Visite guidée", "Boutique", "Cafétéria"]
}
```

**Endpoint** : `PATCH http://localhost:8080/api/v1/patrimoines/{{monumentId}}`
```json
{
  "photoPrincipale": {
    "nomPhotoPrincipale": "Nouvelle façade (Patch)",
    "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/nouvelle-facade-patch.jpg"
  }
}
```

---

## 5. Statut / Publication

**Endpoint** : `PATCH http://localhost:8080/api/v1/patrimoines/{{monumentId}}/statut`
```json
{ "statut": "EN_VALIDATION" }
```
Puis `{ "statut": "VALIDE" }`, puis `{ "statut": "PUBLIE" }` (renseigne `publishedAt`).

**Dépublier** : `PATCH http://localhost:8080/api/v1/patrimoines/{{monumentId}}/depublier` (sans body)
→ repasse en `VALIDE`. Si le patrimoine n'est pas `PUBLIE` → erreur métier attendue.

---

## 6. Conservation

Aucun endpoint ne crée de `ContactConservateur` : insérez-en un en base (`patrimoine_db`) :
```sql
INSERT INTO contact_conservateurs (id, created_at, updated_at, nom, fonction, telephone, email, site_web, organisme)
VALUES ('11111111-1111-1111-1111-111111111111', now(), now(),
        'Awa Diop', 'Conservatrice en chef', '+221771234567',
        'awa.diop@culture.gouv.sn', 'https://culture.gouv.sn', 'Direction du Patrimoine Culturel');
```

**Endpoint** : `PUT http://localhost:8080/api/v1/patrimoines/{{monumentId}}/conservation`
```json
{
  "patrimoineId": "{{monumentId}}",
  "contactConservateurId": "11111111-1111-1111-1111-111111111111",
  "etat": "BON",
  "dateEvaluation": "2026-09-15",
  "descriptionEtat": "Structure saine, enduits extérieurs dégradés par l'air marin.",
  "degradations": ["Fissures murales", "Écaillement des enduits"],
  "causesDegradation": ["Salinité", "Humidité"],
  "travauxNecessaires": ["Reprise des enduits", "Traitement anti-sel"],
  "dernierTravaux": "Restauration de la toiture",
  "dateDerniersTravaux": "2022-03-10",
  "organismeConservation": "Direction du Patrimoine Culturel",
  "budgetEstime": 45000000,
  "recommandations": ["Inspection annuelle", "Limiter les groupes à 30 personnes"]
}
```
**Lecture** : `GET http://localhost:8080/api/v1/patrimoines/{{monumentId}}/conservation`

---

## 7. Horaires

**Endpoint** : `PUT http://localhost:8080/api/v1/patrimoines/{{monumentId}}/horaires` (remplace toute la liste)
```json
[
  { "jour": "LUNDI", "ouvert": false, "commentaire": "Fermeture hebdomadaire" },
  { "jour": "MARDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "12:30:00", "pauseFin": "14:30:00" },
  { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "12:30:00", "pauseFin": "14:30:00" },
  { "jour": "JEUDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "12:30:00", "pauseFin": "14:30:00" },
  { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "15:00:00", "commentaire": "Pause prière" },
  { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00" },
  { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "16:00:00", "surReservation": true }
]
```
**Vider les horaires** : même endpoint avec `[]`.
**Lecture** : `GET http://localhost:8080/api/v1/patrimoines/{{monumentId}}/horaires`

---

## 8. Photo principale

**Endpoint** : `PUT http://localhost:8080/api/v1/patrimoines/{{monumentId}}/photo-principale`
```json
{
  "nomPhotoPrincipale": "Façade Maison des Esclaves",
  "urlPhotoPrincipale": "https://images.pgvpt.sn/patrimoines/maison-esclaves-facade.jpg"
}
```
- **Lecture** : `GET .../patrimoines/{{monumentId}}/photo-principale`
- **Suppression** : `DELETE .../patrimoines/{{monumentId}}/photo-principale` → `204`

---

## 9. DELETE

- `DELETE http://localhost:8080/api/v1/patrimoines/{{deleteId}}` → `204 No Content`
- Rejouer la même requête → `404 Not Found`

---

## Ordre de test conseillé

1. POST 1.1 → 1.4 (noter les IDs)
2. GET (liste, filtres, par ID)
3. PUT puis PATCH
4. Horaires, photo, conservation (après l'INSERT SQL)
5. Statut : `EN_VALIDATION` → `VALIDE` → `PUBLIE` → `depublier`
6. DELETE sur 1.4
