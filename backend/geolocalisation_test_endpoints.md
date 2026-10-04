# Endpoints de test - MS Géolocalisation via Gateway

Voici les requêtes HTTP/cURL prêtes à l'emploi pour tester vos différentes données via l'API Gateway (`localhost:8080`). Les routes de ce microservice sont généralement préfixées par `/api/v1` selon votre configuration OpenAPI. 
*Note : Remplacez les variables comme `{{paysId}}` par les vrais UUID renvoyés lors de la création de l'élément parent.*

### 1. Créer le pays
**Endpoint** : `POST http://localhost:8080/api/v1/pays`
```json
{
  "nom": "Sénégal",
  "code": "SEN",
  "codeIso2": "SN",
  "codeIso3": "SEN",
  "devise": "Franc CFA BCEAO",
  "codeDevise": "XOF",
  "indicatifTelephonique": "+221",
  "langueOfficielle": "Français",
  "continent": "Afrique",
  "actif": true
}
```

---

### 2. Créer la région de Dakar
**Endpoint** : `POST http://localhost:8080/api/v1/regions`
```json
{
  "paysId": "{{paysId}}",
  "code": "DK",
  "nom": "Dakar",
  "codePostal": "10000",
  "description": "Région administrative de Dakar"
}
```

---

### 3. Créer le département de Dakar
**Endpoint** : `POST http://localhost:8080/api/v1/departements`
```json
{
  "regionId": "{{regionId}}",
  "code": "DKR",
  "nom": "Dakar",
  "description": "Département de Dakar"
}
```

---

### 4. Créer la commune de Gorée
**Endpoint** : `POST http://localhost:8080/api/v1/communes`
```json
{
  "departementId": "{{departementId}}",
  "code": "GOR",
  "nom": "Gorée",
  "description": "Commune de l'île de Gorée"
}
```

---

### 5. Créer le village
**Endpoint** : `POST http://localhost:8080/api/v1/villages`
```json
{
  "communeId": "{{communeId}}",
  "code": "GOR-001",
  "nom": "Île de Gorée",
  "description": "Localité historique de l'île de Gorée"
}
```

---

### 6. Créer le quartier
**Endpoint** : `POST http://localhost:8080/api/v1/quartiers`
```json
{
  "communeId": "{{communeId}}",
  "villageId": "{{villageId}}",
  "code": "GOR-CENTRE",
  "nom": "Centre historique",
  "description": "Centre historique de l'île de Gorée"
}
```

---

### 7. Créer la zone géographique
**Endpoint** : `POST http://localhost:8080/api/v1/zones-geographiques`
```json
{
  "code": "ZG-OUEST",
  "nom": "Zone géographique Ouest",
  "description": "Zone géographique couvrant la région de Dakar",
  "regionIds": [
    "{{regionId}}"
  ],
  "superficieKm2": 550,
  "latitudeCentre": 14.7167,
  "longitudeCentre": -17.4677
}
```

---

### 8. Créer la zone touristique
**Endpoint** : `POST http://localhost:8080/api/v1/zones-touristiques`
```json
{
  "code": "ZT-DAKAR",
  "nom": "Zone touristique de Dakar",
  "description": "Zone touristique regroupant les principaux sites culturels et historiques de Dakar",
  "zoneGeographiqueId": "{{zoneGeoId}}",
  "superficieKm2": 550,
  "departementId": "{{departementId}}",
  "typeZone": "ZONE_URBAINE_ET_CULTURELLE",
  "latitudeCentre": 14.7167,
  "longitudeCentre": -17.4677
}
```

---

### 9. Créer une géolocalisation de patrimoine
**Endpoint** : `POST http://localhost:8080/api/v1/geolocalisations`
```json
{
  "latitude": 14.6667,
  "longitude": -17.4000,
  "altitude": 12.5,
  "precisionMetres": 5.0,
  "adresse": "Rue du Chevalier de Boufflers, Île de Gorée",
  "lieuDit": "Centre historique de Gorée",
  "repere": "À proximité du débarcadère",
  "systemeReference": "WGS84",
  "codeEpsg": 4326,
  "source": "GPS",
  "dateAcquisition": "2026-10-03",
  "methodeAcquisition": "GPS",
  "niveauFiabilite": "TRES_ELEVE",
  "zoneTouristiqueId": "{{zoneTouristiqueId}}",
  "villageId": "{{villageId}}",
  "quartierId": "{{quartierId}}",
  "patrimoineId": "{{patrimoineId}}"
}
```
*(Remarque : Si la géolocalisation d'un patrimoine s'effectue via l'ID de patrimoine, l'URL peut également être `PUT /api/v1/patrimoines/{{patrimoineId}}/geolocalisation` selon vos besoins)*

---

### 10. Ajouter un point d'accès
**Endpoint** : `POST http://localhost:8080/api/v1/patrimoines/{{patrimoineId}}/points-acces`
```json
{
  "nom": "Entrée principale de la Maison des Esclaves",
  "type": "ENTREE_PRINCIPALE",
  "description": "Entrée principale destinée aux visiteurs",
  "latitude": 14.6669,
  "longitude": -17.3998,
  "adresse": "Île de Gorée, Dakar",
  "accessiblePMR": true,
  "parkingDisponible": false,
  "transportPublic": true,
  "horaires": "09:00-18:00"
}
```

---

## Requêtes de Lecture (GET)

Voici les requêtes pour lister tous les éléments (GetAll) et pour récupérer un élément spécifique par son ID (GetById).

### 1. Pays
- **GetAll** : `GET http://localhost:8080/api/v1/pays`
- **GetById** : `GET http://localhost:8080/api/v1/pays/{{paysId}}`

### 2. Régions
- **GetAll** : `GET http://localhost:8080/api/v1/regions`
- **GetById** : `GET http://localhost:8080/api/v1/regions/{{regionId}}`

### 3. Départements
- **GetAll** : `GET http://localhost:8080/api/v1/departements`
- **GetById** : `GET http://localhost:8080/api/v1/departements/{{departementId}}`

### 4. Communes
- **GetAll** : `GET http://localhost:8080/api/v1/communes`
- **GetById** : `GET http://localhost:8080/api/v1/communes/{{communeId}}`

### 5. Villages
- **GetAll** : `GET http://localhost:8080/api/v1/villages`
- **GetById** : `GET http://localhost:8080/api/v1/villages/{{villageId}}`

### 6. Quartiers
- **GetAll** : `GET http://localhost:8080/api/v1/quartiers`
- **GetById** : `GET http://localhost:8080/api/v1/quartiers/{{quartierId}}`

### 7. Zones Géographiques
- **GetAll** : `GET http://localhost:8080/api/v1/zones-geographiques`
- **GetById** : `GET http://localhost:8080/api/v1/zones-geographiques/{{zoneGeoId}}`

### 8. Zones Touristiques
- **GetAll** : `GET http://localhost:8080/api/v1/zones-touristiques`
- **GetById** : `GET http://localhost:8080/api/v1/zones-touristiques/{{zoneTouristiqueId}}`

### 9. Géolocalisations
- **GetAll** : `GET http://localhost:8080/api/v1/geolocalisations`
- **GetById** : `GET http://localhost:8080/api/v1/geolocalisations/{{geolocalisationId}}`
- **Get by PatrimoineId** : `GET http://localhost:8080/api/v1/patrimoines/{{patrimoineId}}/geolocalisation`

### 10. Points d'accès
- **GetAll (par Patrimoine)** : `GET http://localhost:8080/api/v1/patrimoines/{{patrimoineId}}/points-acces`
- **GetById** : `GET http://localhost:8080/api/v1/points-acces/{{pointAccesId}}`
