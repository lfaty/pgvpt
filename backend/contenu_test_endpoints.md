# Endpoints de test - MS Contenu via Gateway

Voici les requêtes HTTP/cURL prêtes à l'emploi pour tester vos différentes données via l'API Gateway (`localhost:8080`) ou directement sur le microservice (`localhost:3006`).

Les routes de ce microservice sont préfixées par `/api/v1/contenus` conformément à votre configuration OpenAPI (v2.0.0) et au routage Gateway.

> **Important (Contraintes d'intégrité Feign)** :
> 1. `patrimoineId` est **obligatoire** et validé en temps réel auprès de **ms-patrimoine** via Eureka (`ms-patrimoine:3001`).
> 2. `mediaIds` est une liste optionnelle d'UUIDs validée auprès de **ms-media** via Eureka (`ms-media:3007`).
> 3. *UUIDs réels prêts à l'emploi (déjà présents dans vos bases actives)* :
>    - `patrimoineId` : `b191a620-b4bf-4f27-b1e6-85bfb8d0dad3` (Maison des Esclaves de Gorée)
>    - `mediaId` : `efde32be-4032-4f5e-a064-35c9a04d9062` (Photo de la Maison des Esclaves)
>    - `auteurActeurId` (optionnel) : un UUID d'acteur comme `550e8400-e29b-41d4-a716-446655440010`

---

## 1. CREATE (POST)

### 1.1 Créer un Article historique (Français) avec médias
**Endpoint** : `POST http://localhost:8080/api/v1/contenus`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "ARTICLE",
  "langue": "fr",
  "titre": "La Maison des Esclaves de Gorée : Mémoire et Histoire",
  "resume": "Présentation historique et mémorielle de la Maison des Esclaves de l'île de Gorée.",
  "corps": "Construite en 1776, la Maison des Esclaves est un haut lieu de mémoire situé sur l'île de Gorée, au large de Dakar. Symbole universel de la traite transatlantique, elle permet aux visiteurs du monde entier de se recueillir et de comprendre cette page essentielle de l'histoire humaine. Sa célèbre 'Porte du voyage sans retour' ouvre directement sur l'océan.",
  "auteurActeurId": "550e8400-e29b-41d4-a716-446655440010",
  "motsCles": [
    "Gorée",
    "Maison des Esclaves",
    "Mémoire",
    "Histoire",
    "UNESCO",
    "Sénégal"
  ],
  "mediaIds": [
    "efde32be-4032-4f5e-a064-35c9a04d9062"
  ]
}
```

---

### 1.2 Créer un Guide de Visite (Français) sans média
**Endpoint** : `POST http://localhost:8080/api/v1/contenus`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "GUIDE_VISITE",
  "langue": "fr",
  "titre": "Guide pratique : Visiter la Maison des Esclaves",
  "resume": "Conseils pratiques pour préparer votre visite à Gorée.",
  "corps": "Pour vous rendre à la Maison des Esclaves, empruntez la chaloupe au port de Dakar (embarcadère de Gorée). La traversée dure environ 20 minutes. Le site est ouvert du mardi au dimanche de 10h00 à 18h00. Il est conseillé de faire appel à un guide officiel du musée pour une visite commentée riche en enseignements historiques.",
  "motsCles": [
    "Guide",
    "Visite",
    "Gorée",
    "Horaires",
    "Chaloupe"
  ],
  "mediaIds": []
}
```

---

### 1.3 Créer un Article en Anglais (`en`)
**Endpoint** : `POST http://localhost:8080/api/v1/contenus`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "ARTICLE",
  "langue": "en",
  "titre": "The House of Slaves: A Universal Memorial of the Atlantic Slave Trade",
  "resume": "Historical and memorial overview of Gorée Island's House of Slaves.",
  "corps": "Located off the coast of Dakar on Gorée Island, the House of Slaves stands as a poignant reminder of human history and resilience. Built in 1776, it attracts thousands of visitors and pilgrims from all over the world.",
  "motsCles": [
    "Gorée",
    "House of Slaves",
    "Memory",
    "Heritage",
    "Senegal"
  ],
  "mediaIds": [
    "efde32be-4032-4f5e-a064-35c9a04d9062"
  ]
}
```

---

### 1.4 Créer un Témoignage / Récit
**Endpoint** : `POST http://localhost:8080/api/v1/contenus`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "TEMOIGNAGE",
  "langue": "fr",
  "titre": "Le combat mémoriel de Boubacar Joseph Ndiaye",
  "resume": "Hommage au célèbre conservateur emblématique de la Maison des Esclaves.",
  "corps": "Pendant plusieurs décennies, Boubacar Joseph Ndiaye a fait résonner la voix des disparus auprès des pèlerins, chefs d'État et visiteurs du monde entier avec une ferveur inoubliable.",
  "motsCles": [
    "Boubacar Joseph Ndiaye",
    "Conservateur",
    "Témoignage",
    "Mémoire"
  ],
  "mediaIds": []
}
```

---

### 1.5 Contenu jetable (pour tester DELETE)
**Endpoint** : `POST http://localhost:8080/api/v1/contenus`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "ACTUALITE",
  "langue": "fr",
  "titre": "Actualité temporaire de test à supprimer",
  "resume": "Contenu créé spécifiquement pour valider le DELETE.",
  "corps": "Ce contenu temporaire sera supprimé après vérification du cycle de vie.",
  "motsCles": ["Test", "Delete"],
  "mediaIds": []
}
```

---

### 1.6 Cas d'erreurs attendus (400 Bad Request)

- **Patrimoine inexistant** (Erreur Feign ms-patrimoine) :
```json
{
  "patrimoineId": "00000000-0000-0000-0000-000000000000",
  "type": "ARTICLE",
  "langue": "fr",
  "titre": "Erreur attendue",
  "corps": "Test"
}
```
*Réponse attendue* : `400 Bad Request` avec `"message": "Patrimoine introuvable : 00000000-0000-0000-0000-000000000000..."`

- **Média inexistant** (Erreur Feign ms-media) :
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "ARTICLE",
  "langue": "fr",
  "titre": "Erreur média attendue",
  "corps": "Test",
  "mediaIds": ["00000000-0000-0000-0000-000000000000"]
}
```
*Réponse attendue* : `400 Bad Request` avec `"message": "Média introuvable : 00000000-0000-0000-0000-000000000000..."`

- **Champs obligatoires manquants** :
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "langue": "fr"
}
```
*Réponse attendue* : `400 Bad Request`

---

## 2. READ (GET)

| Action | Méthode & URL |
|---|---|
| **Tous les contenus** | `GET http://localhost:8080/api/v1/contenus` |
| **Par ID spécifique** | `GET http://localhost:8080/api/v1/contenus/{{contenuId}}` |
| **Filtre par Patrimoine** | `GET http://localhost:8080/api/v1/contenus?patrimoineId=b191a620-b4bf-4f27-b1e6-85bfb8d0dad3` |
| **Filtre par Langue (fr)** | `GET http://localhost:8080/api/v1/contenus?langue=fr` |
| **Filtre par Langue (en)** | `GET http://localhost:8080/api/v1/contenus?langue=en` |
| **Filtre par Statut (BROUILLON)** | `GET http://localhost:8080/api/v1/contenus?statut=BROUILLON` |
| **Filtre par Statut (VALIDE)** | `GET http://localhost:8080/api/v1/contenus?statut=VALIDE` |
| **Filtre par Statut (PUBLIE)** | `GET http://localhost:8080/api/v1/contenus?statut=PUBLIE` |
| **Filtre combiné (Patrimoine + Langue + Statut)** | `GET http://localhost:8080/api/v1/contenus?patrimoineId=b191a620-b4bf-4f27-b1e6-85bfb8d0dad3&langue=fr&statut=PUBLIE` |
| **ID inexistant (404)** | `GET http://localhost:8080/api/v1/contenus/00000000-0000-0000-0000-000000000000` |

---

## 3. UPDATE complet (PUT)

**Endpoint** : `PUT http://localhost:8080/api/v1/contenus/{{contenuId}}`
```json
{
  "patrimoineId": "b191a620-b4bf-4f27-b1e6-85bfb8d0dad3",
  "type": "ARTICLE",
  "langue": "fr",
  "titre": "La Maison des Esclaves de Gorée : Mémoire, Histoire et Recueillement (Mis à jour)",
  "resume": "Présentation enrichie de la Maison des Esclaves et de sa portée mémorielle universelle.",
  "corps": "Texte mis à jour : Bâtiment colonial restauré avec le soutien de l'UNESCO, la Maison des Esclaves symbolise la traite atlantique et continue d'inspirer les générations actuelles à préserver la dignité humaine.",
  "auteurActeurId": "550e8400-e29b-41d4-a716-446655440010",
  "motsCles": [
    "Gorée",
    "Maison des Esclaves",
    "Histoire",
    "Mémoire",
    "UNESCO"
  ],
  "mediaIds": [
    "efde32be-4032-4f5e-a064-35c9a04d9062"
  ]
}
```

---

## 4. WORKFLOW DU STATUT & CYCLE DE VIE (PATCH)

Le microservice applique des règles strictes sur les transitions d'état :
`BROUILLON` ➔ `VALIDE` (ou `EN_REVISION`) ➔ `PUBLIE` ➔ `ARCHIVE`

### 4.1 Valider un contenu (`BROUILLON` ➔ `VALIDE`)
**Endpoint** : `PATCH http://localhost:8080/api/v1/contenus/{{contenuId}}/valider`
*(Pas de body)*
- Règle métier : Ne peut pas être exécuté sur un contenu déjà `PUBLIE` ou `ARCHIVE`.
- Résultat : Champ `statut` passe à `"VALIDE"`.

### 4.2 Publier un contenu (`VALIDE` ➔ `PUBLIE`)
**Endpoint** : `PATCH http://localhost:8080/api/v1/contenus/{{contenuId}}/publier`
*(Pas de body)*
- Règle métier : Le contenu **doit** impérativement avoir le statut `VALIDE` ou `EN_REVISION`. Tenter de publier un contenu directement en `BROUILLON` déclenche une erreur métier `400 Bad Request`.
- Résultat : Champ `statut` passe à `"PUBLIE"` et `datePublication` est alimenté avec l'horodatage courant.

### 4.3 Archiver un contenu (`PUBLIE` ➔ `ARCHIVE`)
**Endpoint** : `PATCH http://localhost:8080/api/v1/contenus/{{contenuId}}/archiver`
*(Pas de body)*
- Règle métier : Ne peut pas être re-archivé si déjà `ARCHIVE`.
- Résultat : Champ `statut` passe à `"ARCHIVE"`.

---

## 5. DELETE

- **Endpoint** : `DELETE http://localhost:8080/api/v1/contenus/{{contenuId}}`
- **Réponse attendue** : `204 No Content`
- **Vérification** : Réexécuter la même requête ou un `GET` renvoie immédiatement `404 Not Found`.

---

## 6. Ordre de test conseillé

1. **POST 1.1** (Créer l'article principal) ➔ Noter l'UUID `{{contenuId}}` retourné.
2. **GET** par ID `{{contenuId}}` et liste filtrée par langue/statut.
3. **PUT 3** (Mettre à jour le texte du contenu).
4. **PATCH 4.1** (Valider : `BROUILLON` ➔ `VALIDE`).
5. **PATCH 4.2** (Publier : `VALIDE` ➔ `PUBLIE`).
6. **POST 1.5** (Créer le contenu temporaire) ➔ Noter l'UUID `{{tempId}}`.
7. **DELETE 5** sur `{{tempId}}` ➔ Confirmer `204 No Content`.
