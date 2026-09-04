# 📋 Données de Test — API ms-patrimoine

> **Base URL** : `http://localhost:3001/api/v1`
>
> Toutes les données de test ci-dessous sont basées sur des patrimoines touristiques réels du Sénégal.

---

## Table des matières

1. [Patrimoines — CRUD](#1-patrimoines--crud)
   - [1.1 Créer un Monument](#11-créer-un-monument)
   - [1.2 Créer un Site Naturel](#12-créer-un-site-naturel)
   - [1.3 Créer un Musée](#13-créer-un-musée)
   - [1.4 Obtenir un patrimoine](#14-obtenir-un-patrimoine-par-id)
   - [1.5 Modifier un patrimoine](#15-modifier-un-patrimoine)
   - [1.6 Supprimer un patrimoine](#16-supprimer-un-patrimoine)
   - [1.7 Lister les patrimoines (paginé)](#17-lister-les-patrimoines-paginé--filtres)
2. [Publication](#2-publication)
3. [Recherche](#3-recherche-avancée)
4. [Photos](#4-photos)
5. [Horaires](#5-horaires)
6. [Conservation](#6-conservation)
7. [Scénario complet](#7-scénario-complet-de-test)

---

## 1. Patrimoines — CRUD

### 1.1 Créer un Monument

```
POST /patrimoines
Content-Type: application/json
```

```json
{
  "nom": "Maison des Esclaves",
  "nomLocal": "Keur Njaay",
  "nomHistorique": "Maison hollandaise de Gorée",
  "type": "MONUMENT",
  "categorie": "PATRIMOINE_MEMORIEL",
  "sousCategorie": "Mémorial de la traite négrière",
  "periode": "COLONIALE",
  "siecle": "XVIIIe siècle",
  "dateOuverture": "1962-01-01",
  "description": "La Maison des Esclaves est un édifice historique situé sur l'île de Gorée, au large de Dakar. Construite vers 1776, elle est devenue le symbole mondial de la traite transatlantique des esclaves. Le bâtiment, de style colonial hollandais, abrite un musée dédié à la mémoire de millions d'Africains déportés vers les Amériques. La célèbre 'Porte du voyage sans retour' ouvre sur l'océan Atlantique.",
  "descriptionCourte": "Symbole mondial de la traite négrière, situé sur l'île de Gorée au large de Dakar.",
  "historique": "Construite vers 1776 par les Hollandais, cette maison a servi de lieu de transit pour les esclaves avant leur déportation. Restaurée en 1990 grâce au soutien de l'UNESCO, elle accueille aujourd'hui des visiteurs du monde entier.",
  "importanceHistorique": "Lieu de mémoire majeur de la traite transatlantique, reconnu mondialement.",
  "importanceCulturelle": "Symbole de la résistance africaine et de la dignité humaine.",
  "importanceTouristique": "Site le plus visité du Sénégal, accueillant plus de 200 000 visiteurs par an.",
  "valeurPatrimoniale": "Patrimoine mondial de l'UNESCO depuis 1978.",
  "valeurSpirituelle": "Lieu de recueillement et de mémoire pour la diaspora africaine.",
  "traditionsAssociees": [
    "Cérémonie de commémoration annuelle",
    "Pèlerinage de la diaspora"
  ],
  "langues": ["Français", "Wolof", "Anglais", "Espagnol"],
  "etatConservation": "BON",
  "statut": "BROUILLON",
  "classePatrimoine": true,
  "referenceClassement": "MH-SN-1978-001",
  "dateClassement": "1978-09-01",
  "inscritUnesco": true,
  "nomSiteUnesco": "Île de Gorée",
  "dateInscriptionUnesco": "1978-09-01",
  "protectionJuridique": "Classé patrimoine mondial UNESCO et monument historique national du Sénégal.",
  "organismeGestionnaire": "Direction du Patrimoine Culturel du Sénégal",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "Conservateur de la Maison des Esclaves",
  "contact": {
    "nom": "Eloi Coly",
    "fonction": "Conservateur en chef",
    "telephone": "+221 33 822 29 62",
    "email": "conservateur.goree@culture.gouv.sn",
    "siteWeb": "https://www.goree.sn"
  },
  "siteWeb": "https://www.goree.sn",
  "email": "info@goree.sn",
  "telephone": "+221 33 822 29 62",
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": false,
    "accesFauteuilRoulant": false,
    "accesTransportPublic": true,
    "parking": false,
    "guideDisponible": true,
    "guideAudio": true,
    "langueVisite": ["Français", "Anglais", "Espagnol", "Portugais"],
    "accesEnfant": true,
    "conditionsAcces": "Accès par chaloupe depuis le port de Dakar (20 min). Départ toutes les heures.",
    "restrictions": [
      "Pas d'accès PMR (escaliers étroits)",
      "Capacité limitée à 50 personnes simultanément"
    ]
  },
  "tarification": {
    "gratuit": false,
    "tarifNormal": 500.0,
    "devise": "XOF",
    "tarifEnfant": 200.0,
    "tarifEtudiant": 300.0,
    "tarifResident": 200.0,
    "tarifGroupe": 350.0,
    "tarifGuide": 5000.0,
    "conditionsTarifaires": "Gratuit pour les enfants de moins de 6 ans. Tarif groupe à partir de 10 personnes."
  },
  "horaires": [
    { "jour": "LUNDI", "ouvert": false, "commentaire": "Fermé le lundi" },
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:30:00", "surReservation": false },
    { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:30:00", "surReservation": false },
    { "jour": "JEUDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:30:00", "surReservation": false },
    { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "pauseDebut": "13:00:00", "pauseFin": "14:30:00", "surReservation": false },
    { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "17:00:00", "surReservation": false, "commentaire": "Fermeture anticipée le dimanche" }
  ],
  "conservation": {
    "etat": "BON",
    "dateEvaluation": "2025-03-15",
    "evaluateur": "Mission de l'UNESCO",
    "descriptionEtat": "Structure globalement stable avec des signes d'érosion marine sur les fondations côté océan.",
    "degradations": ["Érosion marine des fondations", "Humidité dans les cellules souterraines"],
    "causesDegradation": ["Exposition aux embruns marins", "Remontées capillaires"],
    "travauxNecessaires": ["Consolidation des fondations", "Traitement anti-humidité"],
    "dernierTravaux": "Restauration complète financée par l'UNESCO",
    "dateDerniersTravaux": "2019-06-01",
    "organismeConservation": "UNESCO / Direction du Patrimoine Culturel",
    "budgetEstime": 150000000.0,
    "recommandations": [
      "Renforcer la digue de protection côtière",
      "Installer un système de drainage",
      "Limiter le nombre de visiteurs quotidiens"
    ]
  },
  "menaces": [
    {
      "type": "Érosion côtière",
      "niveau": "ELEVE",
      "description": "La montée du niveau de la mer et les tempêtes menacent les fondations de la Maison des Esclaves.",
      "mesuresPrevention": "Construction d'une digue de protection et surveillance régulière des fondations."
    },
    {
      "type": "Surfréquentation touristique",
      "niveau": "MOYEN",
      "description": "Le flux important de visiteurs accélère l'usure du bâtiment.",
      "mesuresPrevention": "Limitation du nombre de visiteurs à 50 personnes simultanément."
    }
  ],
  "recommandationsVisiteur": [
    "Prévoir des chaussures confortables",
    "Emporter de l'eau et de la crème solaire",
    "Réserver la chaloupe tôt le matin en haute saison"
  ],
  "reglesVisite": [
    "Photographies autorisées sans flash",
    "Silence recommandé dans les cellules",
    "Ne pas toucher les murs et objets exposés"
  ],
  "activitesTouristiques": [
    "Visite guidée",
    "Photographie",
    "Visite du musée",
    "Promenade sur l'île de Gorée"
  ],
  "meilleurePeriodeVisite": "Novembre à mai (saison sèche)",
  "dureeVisiteEstimeeMinutes": 90,
  "styleArchitectural": "Architecture coloniale hollandaise",
  "anneeConstruction": 1776,
  "identiteArchitecte": "Architecte hollandais inconnu",
  "natureMonument": "MONUMENT_HISTORIQUE",
  "materiauxConstruction": ["Pierre de latérite", "Bois", "Chaux"],
  "dimensions": "Bâtiment de 2 étages, environ 200 m² au sol",
  "commanditaire": "Administration coloniale hollandaise",
  "contexteHistorique": "Construite durant la période de la traite transatlantique, cette maison servait de comptoir commercial et de lieu de transit pour les esclaves.",
  "personnagesAssocies": ["Boubacar Joseph Ndiaye", "Nelson Mandela", "Barack Obama"]
}
```

**Réponse attendue** : `201 Created`

---

### 1.2 Créer un Site Naturel

```
POST /patrimoines
Content-Type: application/json
```

```json
{
  "nom": "Parc National du Djoudj",
  "nomLocal": "Djoudj",
  "type": "SITE_NATUREL",
  "categorie": "PATRIMOINE_NATUREL",
  "sousCategorie": "Sanctuaire ornithologique",
  "periode": "CONTEMPORAINE",
  "description": "Le Parc National des Oiseaux du Djoudj (PNOD) est l'une des plus importantes réserves ornithologiques au monde. Situé à 60 km au nord de Saint-Louis, il couvre 16 000 hectares de zones humides. Chaque année, plus de 3 millions d'oiseaux migrateurs y font halte, dont des pélicans blancs, flamants roses, cormorans et spatules.",
  "descriptionCourte": "Troisième réserve ornithologique mondiale, sanctuaire de millions d'oiseaux migrateurs.",
  "historique": "Créé en 1971 et classé au patrimoine mondial de l'UNESCO en 1981, le Djoudj est un maillon essentiel de la voie de migration paléarctique occidentale.",
  "importanceHistorique": "Premier parc ornithologique créé en Afrique de l'Ouest.",
  "importanceCulturelle": "Patrimoine naturel exceptionnel du Sénégal, fierté nationale.",
  "importanceTouristique": "Destination phare de l'écotourisme au Sénégal. Attire environ 15 000 visiteurs par an.",
  "valeurPatrimoniale": "Inscrit au patrimoine mondial de l'UNESCO depuis 1981.",
  "etatConservation": "BON",
  "statut": "BROUILLON",
  "classePatrimoine": true,
  "referenceClassement": "PN-SN-1971-002",
  "dateClassement": "1971-04-14",
  "inscritUnesco": true,
  "nomSiteUnesco": "Parc national des oiseaux du Djoudj",
  "dateInscriptionUnesco": "1981-12-01",
  "protectionJuridique": "Parc national protégé par la loi sénégalaise et classé patrimoine mondial UNESCO.",
  "organismeGestionnaire": "Direction des Parcs Nationaux du Sénégal (DPN)",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "Conservateur du Parc du Djoudj",
  "contact": {
    "nom": "Amadou Diallo",
    "fonction": "Conservateur",
    "telephone": "+221 33 961 88 23",
    "email": "djoudj@parcs.gouv.sn"
  },
  "telephone": "+221 33 961 88 23",
  "email": "info@djoudj.sn",
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": false,
    "accesFauteuilRoulant": false,
    "accesTransportPublic": false,
    "parking": true,
    "guideDisponible": true,
    "guideAudio": false,
    "langueVisite": ["Français", "Anglais"],
    "accesEnfant": true,
    "conditionsAcces": "Accessible par piste depuis Saint-Louis (60 km, 1h30). Véhicule 4x4 recommandé en saison des pluies.",
    "restrictions": [
      "Interdit de sortir des sentiers balisés",
      "Pas de baignade dans les marigots"
    ]
  },
  "tarification": {
    "gratuit": false,
    "tarifNormal": 5000.0,
    "devise": "XOF",
    "tarifEnfant": 1000.0,
    "tarifEtudiant": 2000.0,
    "tarifResident": 2000.0,
    "tarifGroupe": 3500.0,
    "tarifGuide": 15000.0,
    "conditionsTarifaires": "Le tarif inclut l'accès au parc. Pirogue en supplément : 25 000 XOF par embarcation (max 10 personnes)."
  },
  "horaires": [
    { "jour": "LUNDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "JEUDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "07:00:00", "heureFermeture": "18:00:00", "surReservation": false }
  ],
  "menaces": [
    {
      "type": "Plantes envahissantes (Typha australis)",
      "niveau": "CRITIQUE",
      "description": "Le Typha envahit les plans d'eau et réduit l'habitat des oiseaux migrateurs.",
      "mesuresPrevention": "Programme de fauche mécanique et valorisation du Typha en biocarburant."
    },
    {
      "type": "Sécheresse",
      "niveau": "ELEVE",
      "description": "La réduction du débit du fleuve Sénégal affecte les zones humides du parc.",
      "mesuresPrevention": "Gestion coordonnée avec l'OMVS pour garantir les lâchers d'eau."
    }
  ],
  "recommandationsVisiteur": [
    "Venir tôt le matin pour l'observation des oiseaux",
    "Apporter des jumelles et un appareil photo avec téléobjectif",
    "Prévoir un chapeau, de l'eau et de la crème solaire",
    "Meilleure période : novembre à avril"
  ],
  "reglesVisite": [
    "Silence obligatoire lors des observations ornithologiques",
    "Ne pas nourrir les animaux",
    "Rester sur les sentiers balisés et dans les pirogues"
  ],
  "activitesTouristiques": [
    "Observation des oiseaux",
    "Promenade en pirogue",
    "Photographie animalière",
    "Randonnée pédestre"
  ],
  "meilleurePeriodeVisite": "Novembre à avril (saison sèche, période de migration)",
  "dureeVisiteEstimeeMinutes": 240,
  "superficie": 16000.0,
  "natureSite": "RESERVE_NATURELLE",
  "ecosysteme": "Écosystème de zones humides fluviales (delta du fleuve Sénégal)",
  "especesProtegees": [
    {
      "nomCommun": "Pélican blanc",
      "nomScientifique": "Pelecanus onocrotalus",
      "nomLocal": "Gélaar",
      "type": "FAUNE",
      "statutConservation": "Préoccupation mineure (LC)",
      "niveauProtection": "Protégé par la CITES",
      "description": "Plus grande colonie de pélicans blancs d'Afrique de l'Ouest avec environ 15 000 couples nicheurs.",
      "photoUrl": "https://images.pgvpt.sn/djoudj/pelican-blanc.jpg"
    },
    {
      "nomCommun": "Flamant rose",
      "nomScientifique": "Phoenicopterus roseus",
      "nomLocal": "Flamaan",
      "type": "FAUNE",
      "statutConservation": "Préoccupation mineure (LC)",
      "niveauProtection": "Espèce protégée au Sénégal",
      "description": "Des milliers de flamants roses migrent au Djoudj chaque hiver.",
      "photoUrl": "https://images.pgvpt.sn/djoudj/flamant-rose.jpg"
    },
    {
      "nomCommun": "Baobab",
      "nomScientifique": "Adansonia digitata",
      "nomLocal": "Guy",
      "type": "FLORE",
      "statutConservation": "Préoccupation mineure (LC)",
      "niveauProtection": "Arbre sacré protégé",
      "description": "Arbre emblématique du Sénégal, présent dans les zones sèches du parc."
    }
  ],
  "biodiversite": "Plus de 350 espèces d'oiseaux, 60 espèces de poissons, crocodiles, phacochères, gazelles et varans du Nil.",
  "zoneProtegee": true,
  "categorieProtection": "Parc national / Site Ramsar",
  "risquesEnvironnementaux": [
    "Invasion du Typha australis",
    "Sécheresse et baisse du niveau des eaux",
    "Salinisation des sols"
  ],
  "ressourcesNaturelles": [
    "Eau douce du fleuve Sénégal",
    "Zones de nidification",
    "Forêt riveraine"
  ],
  "activitesEcotouristiques": [
    "Safari ornithologique en pirogue",
    "Observation des pélicans au nid",
    "Randonnée dans la savane",
    "Photographie de la faune"
  ],
  "capaciteAccueil": 200
}
```

**Réponse attendue** : `201 Created`

---

### 1.3 Créer un Musée

```
POST /patrimoines
Content-Type: application/json
```

```json
{
  "nom": "Musée des Civilisations Noires",
  "nomLocal": "Musée bi ci kanam",
  "type": "MUSEE",
  "categorie": "PATRIMOINE_MUSEAL",
  "sousCategorie": "Musée de civilisation",
  "periode": "CONTEMPORAINE",
  "dateOuverture": "2018-12-06",
  "description": "Le Musée des Civilisations Noires (MCN) est un musée d'art et d'histoire situé à Dakar. Inauguré le 6 décembre 2018, il est dédié aux civilisations noires du monde entier. D'une superficie de 14 000 m², il abrite des collections permanentes et temporaires couvrant l'art, l'archéologie, l'ethnographie et l'histoire contemporaine de l'Afrique et de sa diaspora.",
  "descriptionCourte": "Musée dédié à l'art et à l'histoire des civilisations noires, inauguré en 2018 à Dakar.",
  "historique": "Le projet du MCN a été initié par le président Léopold Sédar Senghor lors du 1er Festival mondial des Arts nègres en 1966. Il a fallu plus de 50 ans pour concrétiser cette vision. Le bâtiment a été conçu par l'architecte chinois Li Peng et construit avec le soutien de la Chine.",
  "importanceHistorique": "Concrétisation de la vision panafricaine de Senghor, symbole de la renaissance culturelle africaine.",
  "importanceCulturelle": "Plus grand musée consacré aux civilisations noires au monde.",
  "importanceTouristique": "Attraction culturelle majeure de Dakar, attirant plus de 100 000 visiteurs par an.",
  "etatConservation": "EXCELLENT",
  "statut": "BROUILLON",
  "classePatrimoine": false,
  "inscritUnesco": false,
  "organismeGestionnaire": "Ministère de la Culture du Sénégal",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "Direction du MCN",
  "contact": {
    "nom": "Hamady Bocoum",
    "fonction": "Directeur général",
    "telephone": "+221 33 889 12 34",
    "email": "direction@mcn.sn",
    "siteWeb": "https://www.mcn.sn"
  },
  "siteWeb": "https://www.mcn.sn",
  "email": "info@mcn.sn",
  "telephone": "+221 33 889 12 34",
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": true,
    "accesFauteuilRoulant": true,
    "accesTransportPublic": true,
    "parking": true,
    "guideDisponible": true,
    "guideAudio": true,
    "langueVisite": ["Français", "Anglais", "Wolof", "Arabe"],
    "accesEnfant": true,
    "conditionsAcces": "Situé dans le centre de Dakar, Place du Souvenir Africain. Accessible en bus DDD et taxi.",
    "restrictions": []
  },
  "tarification": {
    "gratuit": false,
    "tarifNormal": 3000.0,
    "devise": "XOF",
    "tarifEnfant": 500.0,
    "tarifEtudiant": 1000.0,
    "tarifResident": 1500.0,
    "tarifGroupe": 2000.0,
    "tarifGuide": 10000.0,
    "conditionsTarifaires": "Gratuit pour les enfants de moins de 5 ans et les personnes à mobilité réduite. Gratuit le premier dimanche du mois."
  },
  "horaires": [
    { "jour": "LUNDI", "ouvert": false, "commentaire": "Fermé le lundi" },
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "JEUDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "18:00:00", "surReservation": false },
    { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "20:00:00", "surReservation": false, "commentaire": "Nocturne le samedi" },
    { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "10:00:00", "heureFermeture": "17:00:00", "surReservation": false }
  ],
  "langues": ["Français", "Wolof", "Anglais", "Arabe"],
  "recommandationsVisiteur": [
    "Prévoir au moins 2h pour la visite complète",
    "Audioguide recommandé",
    "Boutique et cafétéria disponibles sur place"
  ],
  "reglesVisite": [
    "Photographies sans flash uniquement",
    "Sacs à dos à déposer au vestiaire",
    "Ne pas toucher les œuvres"
  ],
  "activitesTouristiques": [
    "Visite guidée",
    "Exposition permanente",
    "Exposition temporaire",
    "Ateliers pédagogiques",
    "Conférences"
  ],
  "meilleurePeriodeVisite": "Toute l'année (musée couvert et climatisé)",
  "dureeVisiteEstimeeMinutes": 150,
  "nombreCollections": 5,
  "nombreOeuvres": 18000,
  "collections": [
    {
      "nom": "Archéologie africaine",
      "description": "Objets archéologiques datant de la préhistoire à la période médiévale africaine.",
      "nombreOeuvres": 3500,
      "periode": "Préhistoire - XVe siècle",
      "origine": "Afrique de l'Ouest et centrale",
      "themes": ["Outils lithiques", "Céramique ancienne", "Métallurgie du fer"]
    },
    {
      "nom": "Art contemporain africain",
      "description": "Œuvres d'artistes contemporains africains et de la diaspora.",
      "nombreOeuvres": 2000,
      "periode": "XXe - XXIe siècle",
      "origine": "Afrique et diaspora",
      "themes": ["Peinture", "Sculpture", "Installation", "Art numérique"]
    },
    {
      "nom": "Textiles et parures",
      "description": "Collection de tissus traditionnels, bijoux et parures d'Afrique.",
      "nombreOeuvres": 4500,
      "periode": "XVIIIe - XXIe siècle",
      "origine": "Afrique de l'Ouest",
      "themes": ["Kente", "Bogolan", "Bijoux touareg", "Perles"]
    }
  ],
  "expositions": [
    {
      "titre": "Civilisations africaines : des origines à nos jours",
      "description": "Exposition permanente retraçant l'histoire des civilisations noires depuis les origines de l'humanité.",
      "dateDebut": "2018-12-06",
      "commissaire": "Dr. Hamady Bocoum",
      "lieu": "Niveau 1 - Salle principale",
      "expositionPermanente": true,
      "imageUrl": "https://images.pgvpt.sn/mcn/expo-permanente.jpg"
    },
    {
      "titre": "Dakar Biennale 2024 : Nouvelles voix",
      "description": "Sélection d'œuvres de jeunes artistes africains présentées lors de la Biennale de Dakar 2024.",
      "dateDebut": "2025-05-15",
      "dateFin": "2025-09-30",
      "commissaire": "Koyo Kouoh",
      "lieu": "Niveau 2 - Salle d'exposition temporaire",
      "expositionPermanente": false,
      "imageUrl": "https://images.pgvpt.sn/mcn/biennale-2024.jpg"
    }
  ],
  "typesCollections": ["Archéologie", "Art contemporain", "Ethnographie", "Textiles", "Arts visuels"],
  "servicesMusee": [
    "Visite guidée",
    "Audioguide multilingue",
    "Boutique",
    "Cafétéria",
    "Centre de documentation",
    "Salle de conférences",
    "Ateliers pédagogiques"
  ],
  "capaciteAccueil": 1500,
  "museographie": "Muséographie moderne et interactive avec supports numériques, écrans tactiles et projections immersives."
}
```

**Réponse attendue** : `201 Created`

---

### 1.4 Créer un Musée (Théodore Monod)

```
POST /patrimoines
Content-Type: application/json
```

```json
{
  "type": "MUSEE",
  "code": "MUS-DKR-001",
  "nom": "Musée Théodore Monod",
  "nomLocal": "IFAN",
  "nomHistorique": "Musée de l'IFAN",
  "categorie": "PATRIMOINE_CULTUREL",
  "sousCategorie": "Musée d'Art",
  "periode": "CONTEMPORAINE",
  "siecle": "XXe",
  "dateOuverture": "1936-01-01",
  "description": "Un des plus anciens musées d'art d'Afrique de l'Ouest.",
  "descriptionCourte": "Musée d'art africain classique.",
  "historique": "Créé en 1936.",
  "importanceHistorique": "Élevée",
  "importanceCulturelle": "Majeure",
  "importanceTouristique": "Très visité",
  "valeurPatrimoniale": "Inestimable",
  "valeurSpirituelle": "Néant",
  "traditionsAssociees": [
    "Expositions"
  ],
  "langues": ["Français"],
  "etatConservation": "BON",
  "statut": "PUBLIE",
  "classePatrimoine": true,
  "referenceClassement": "SN-PAT-1936",
  "dateClassement": "1936-05-10",
  "inscritUnesco": false,
  "protectionJuridique": "Domaine public",
  "organismeGestionnaire": "UCAD",
  "proprietaire": "État du Sénégal",
  "gestionnaire": "IFAN",
  "siteWeb": "http://ifan.ucad.sn",
  "email": "contact@ifan.sn",
  "telephone": "+221338231010",
  "accessibilite": {
    "accessiblePublic": true,
    "accessibilitePMR": true,
    "accesFauteuilRoulant": true,
    "accesTransportPublic": true,
    "accesEnfant": true,
    "parking": true,
    "guideDisponible": true,
    "guideAudio": false,
    "langueVisite": ["Français"],
    "conditionsAcces": "Billet requis",
    "restrictions": ["Pas de flash"]
  },
  "tarification": {
    "gratuit": false,
    "tarifNormal": 2000.0,
    "tarifEnfant": 500.0,
    "tarifEtudiant": 1000.0,
    "tarifGroupe": 1500.0,
    "devise": "XOF",
    "conditionsTarifaires": "Gratuit mercredi"
  },
  "geolocalisation": { 
    "id": "REMPLACER_PAR_ID_GEOLOCALISATION_ICI" 
  },
  "horaires": [
    { "jour": "LUNDI", "ouvert": false, "commentaire": "Fermé le lundi" },
    { "jour": "MARDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false },
    { "jour": "MERCREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false },
    { "jour": "JEUDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false },
    { "jour": "VENDREDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false },
    { "jour": "SAMEDI", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false },
    { "jour": "DIMANCHE", "ouvert": true, "heureOuverture": "09:00:00", "heureFermeture": "17:00:00", "surReservation": false }
  ],
  "photos": [
    {
      "url": "http://example.com/photo.jpg",
      "legende": "Façade",
      "credit": "IFAN",
      "estPrincipale": true
    }
  ],
  "conservation": {
    "dateEvaluation": "2023-01-15",
    "etat": "BON",
    "descriptionEtat": "Rénové",
    "degradations": ["Infiltrations"],
    "causesDegradation": ["Pluie"],
    "travauxNecessaires": ["Réfection"]
  },
  "nombreOeuvres": 9500,
  "capaciteAccueil": 500,
  "typesCollections": ["Masques", "Statues"],
  "servicesMusee": ["Boutique"],
  "museographie": "Parcours thématique"
}
```

**Réponse attendue** : `201 Created`

---

### 1.4 Obtenir un patrimoine par ID

```
GET /patrimoines/{id}
```

Exemple :
```
GET /patrimoines/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Réponse attendue** : `200 OK` avec le JSON complet du patrimoine.

---

### 1.5 Modifier un patrimoine

```
PUT /patrimoines/{id}
Content-Type: application/json
```

> ⚠️ Utiliser l'`id` retourné lors de la création.

```json
{
  "nom": "Maison des Esclaves de Gorée",
  "type": "MONUMENT",
  "categorie": "PATRIMOINE_MEMORIEL",
  "description": "La Maison des Esclaves est un édifice historique situé sur l'île de Gorée, au large de Dakar. Construite vers 1776, elle est devenue le symbole mondial de la traite transatlantique des esclaves. Le bâtiment a été entièrement restauré et modernisé avec de nouveaux panneaux informatifs en 2024.",
  "descriptionCourte": "Symbole mondial de la traite négrière, récemment restauré, situé sur l'île de Gorée.",
  "etatConservation": "EXCELLENT",
  "tarification": {
    "gratuit": false,
    "tarifNormal": 1000.0,
    "devise": "XOF",
    "tarifEnfant": 300.0,
    "tarifEtudiant": 500.0,
    "tarifResident": 500.0,
    "tarifGroupe": 700.0,
    "tarifGuide": 7500.0,
    "conditionsTarifaires": "Nouveaux tarifs 2025. Gratuit pour les moins de 6 ans et les personnes handicapées."
  },
  "dureeVisiteEstimeeMinutes": 120,
  "styleArchitectural": "Architecture coloniale hollandaise (restaurée)",
  "anneeConstruction": 1776,
  "anneeRenovation": 2024,
  "natureMonument": "MONUMENT_HISTORIQUE"
}
```

**Réponse attendue** : `200 OK`

---

### 1.6 Supprimer un patrimoine

```
DELETE /patrimoines/{id}
```

Exemple :
```
DELETE /patrimoines/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Réponse attendue** : `204 No Content`

---

### 1.7 Lister les patrimoines (paginé + filtres)

#### Sans filtres (page par défaut)

```
GET /patrimoines
```

#### Avec pagination

```
GET /patrimoines?page=0&size=10&sort=nom,asc
```

#### Filtrer par catégorie

```
GET /patrimoines?categorie=PATRIMOINE_NATUREL
```

#### Filtrer par type

```
GET /patrimoines?type=MONUMENT
```

#### Filtres combinés

```
GET /patrimoines?type=SITE_NATUREL&categorie=PATRIMOINE_NATUREL&etatConservation=BON&inscritUnesco=true&page=0&size=5
```

#### Recherche textuelle

```
GET /patrimoines?q=Gorée
```

#### Filtre par localisation

```
GET /patrimoines?region=Dakar&commune=Gorée
```

**Réponse attendue** : `200 OK` avec un objet `PagePatrimoine` :

```json
{
  "content": [ ... ],
  "page": 0,
  "size": 10,
  "totalElements": 3,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

---

## 2. Publication

### 2.1 Publier un patrimoine

```
PATCH /patrimoines/{id}/publier
```

> Le patrimoine doit avoir le statut `BROUILLON`, `EN_VALIDATION` ou `VALIDE`.

**Réponse attendue** : `200 OK` avec le patrimoine mis à jour (statut → `PUBLIE`, `publishedAt` renseigné).

---

### 2.2 Dépublier un patrimoine

```
PATCH /patrimoines/{id}/depublier
```

> Le patrimoine doit avoir le statut `PUBLIE`.

**Réponse attendue** : `200 OK` avec le patrimoine mis à jour (statut → `BROUILLON`, `publishedAt` → null).

---

## 3. Recherche avancée

```
GET /patrimoines/recherche
```

#### Recherche par mot-clé

```
GET /patrimoines/recherche?q=esclave
```

#### Recherche par type + région

```
GET /patrimoines/recherche?type=MONUMENT&region=Dakar
```

#### Recherche combinée

```
GET /patrimoines/recherche?q=oiseaux&type=SITE_NATUREL&categorie=PATRIMOINE_NATUREL&region=Saint-Louis
```

**Réponse attendue** : `200 OK` avec un tableau de `PatrimoineSummary` :

```json
[
  {
    "id": "...",
    "nom": "Parc National du Djoudj",
    "type": "SITE_NATUREL",
    "categorie": "PATRIMOINE_NATUREL",
    "descriptionCourte": "Troisième réserve ornithologique mondiale...",
    "photoPrincipale": "https://images.pgvpt.sn/djoudj/principale.jpg",
    "etatConservation": "BON",
    "inscritUnesco": true
  }
]
```

---

## 4. Photos

### 4.1 Lister les photos d'un patrimoine

```
GET /patrimoines/{id}/photos
```

**Réponse attendue** : `200 OK` avec un tableau de `Photo`.

---

### 4.2 Ajouter une photo

```
POST /patrimoines/{id}/photos
Content-Type: application/json
```

```json
{
  "url": "https://images.pgvpt.sn/goree/maison-esclaves-facade.jpg",
  "miniatureUrl": "https://images.pgvpt.sn/goree/maison-esclaves-facade-thumb.jpg",
  "titre": "Façade principale de la Maison des Esclaves",
  "description": "Vue de la façade principale avec les escaliers menant au premier étage et la célèbre porte du voyage sans retour.",
  "auteur": "Moussa Diop",
  "datePrise": "2024-11-15",
  "droits": "© PGVPT Sénégal 2024",
  "licence": "CC BY-NC-SA 4.0",
  "photoPrincipale": true,
  "ordre": 1
}
```

**Réponse attendue** : `201 Created`

#### Photo secondaire

```json
{
  "url": "https://images.pgvpt.sn/goree/porte-sans-retour.jpg",
  "miniatureUrl": "https://images.pgvpt.sn/goree/porte-sans-retour-thumb.jpg",
  "titre": "La Porte du Voyage sans Retour",
  "description": "La porte donnant sur l'océan Atlantique, par laquelle les esclaves quittaient définitivement le continent africain.",
  "auteur": "Aïssatou Fall",
  "datePrise": "2024-11-15",
  "droits": "© PGVPT Sénégal 2024",
  "licence": "CC BY-NC-SA 4.0",
  "photoPrincipale": false,
  "ordre": 2
}
```

---

### 4.3 Supprimer une photo

```
DELETE /patrimoines/{id}/photos/{photoId}
```

Exemple :
```
DELETE /patrimoines/a1b2c3d4-.../photos/f9e8d7c6-...
```

**Réponse attendue** : `204 No Content`

---

## 5. Horaires

### 5.1 Obtenir les horaires d'un patrimoine

```
GET /patrimoines/{id}/horaires
```

**Réponse attendue** : `200 OK` avec un tableau de `HoraireOuverture`.

---

### 5.2 Mettre à jour les horaires

```
PUT /patrimoines/{id}/horaires
Content-Type: application/json
```

```json
[
  {
    "jour": "LUNDI",
    "ouvert": false,
    "commentaire": "Fermé le lundi (journée de maintenance)"
  },
  {
    "jour": "MARDI",
    "ouvert": true,
    "heureOuverture": "09:30:00",
    "heureFermeture": "17:30:00",
    "pauseDebut": "12:30:00",
    "pauseFin": "14:00:00",
    "surReservation": false
  },
  {
    "jour": "MERCREDI",
    "ouvert": true,
    "heureOuverture": "09:30:00",
    "heureFermeture": "17:30:00",
    "pauseDebut": "12:30:00",
    "pauseFin": "14:00:00",
    "surReservation": false
  },
  {
    "jour": "JEUDI",
    "ouvert": true,
    "heureOuverture": "09:30:00",
    "heureFermeture": "17:30:00",
    "pauseDebut": "12:30:00",
    "pauseFin": "14:00:00",
    "surReservation": false
  },
  {
    "jour": "VENDREDI",
    "ouvert": true,
    "heureOuverture": "09:30:00",
    "heureFermeture": "17:30:00",
    "pauseDebut": "12:30:00",
    "pauseFin": "14:00:00",
    "surReservation": false,
    "commentaire": "Dernière entrée à 16h30"
  },
  {
    "jour": "SAMEDI",
    "ouvert": true,
    "heureOuverture": "10:00:00",
    "heureFermeture": "19:00:00",
    "surReservation": false,
    "commentaire": "Horaire étendu le samedi"
  },
  {
    "jour": "DIMANCHE",
    "ouvert": true,
    "heureOuverture": "10:00:00",
    "heureFermeture": "16:00:00",
    "surReservation": true,
    "commentaire": "Visite sur réservation uniquement le dimanche"
  }
]
```

**Réponse attendue** : `200 OK`

---

## 6. Conservation

### 6.1 Obtenir les infos de conservation

```
GET /patrimoines/{id}/conservation
```

**Réponse attendue** : `200 OK` avec un objet `Conservation`.

---

### 6.2 Mettre à jour la conservation

```
PUT /patrimoines/{id}/conservation
Content-Type: application/json
```

```json
{
  "etat": "MOYEN",
  "dateEvaluation": "2025-08-15",
  "evaluateur": "Dr. Ibrahima Sow - Architecte du patrimoine",
  "descriptionEtat": "Les fondations présentent des fissures dues à l'érosion marine. L'humidité affecte les murs intérieurs des cellules. La toiture nécessite des réparations mineures.",
  "degradations": [
    "Fissures dans les fondations côté océan",
    "Humidité importante dans les cellules souterraines",
    "Décollement de l'enduit extérieur",
    "Usure des marches d'escalier"
  ],
  "causesDegradation": [
    "Érosion marine et embruns salins",
    "Remontées capillaires",
    "Surfréquentation touristique",
    "Vieillissement naturel des matériaux"
  ],
  "travauxNecessaires": [
    "Injection de résine dans les fondations",
    "Installation d'un système de drainage souterrain",
    "Réfection de l'enduit extérieur avec du mortier de chaux",
    "Remplacement des marches d'escalier usées"
  ],
  "dernierTravaux": "Restauration de la toiture et renforcement de la structure porteuse",
  "dateDerniersTravaux": "2023-11-20",
  "organismeConservation": "Direction du Patrimoine Culturel / UNESCO",
  "budgetEstime": 250000000.0,
  "recommandations": [
    "Intervention urgente sur les fondations avant la saison des pluies",
    "Limiter le nombre de visiteurs à 40 personnes simultanément",
    "Installer des capteurs d'humidité dans les cellules",
    "Programmer une évaluation structurelle complète sous 6 mois"
  ]
}
```

**Réponse attendue** : `200 OK`

---

## 7. Scénario complet de test

Voici l'ordre recommandé pour tester l'API de bout en bout :

| Étape | Action | Endpoint | Vérification |
|-------|--------|----------|--------------|
| 1 | Créer un monument | `POST /patrimoines` (§1.1) | Récupérer l'`id` retourné |
| 2 | Créer un site naturel | `POST /patrimoines` (§1.2) | Récupérer l'`id` retourné |
| 3 | Créer un musée | `POST /patrimoines` (§1.3) | Récupérer l'`id` retourné |
| 4 | Lister tous | `GET /patrimoines` | Vérifier 3 résultats |
| 5 | Consulter le monument | `GET /patrimoines/{id1}` | Vérifier toutes les données |
| 6 | Filtrer par type | `GET /patrimoines?type=MUSEE` | 1 seul résultat |
| 7 | Recherche textuelle | `GET /patrimoines?q=oiseaux` | Le Djoudj apparaît |
| 8 | Recherche avancée | `GET /patrimoines/recherche?q=esclave` | La Maison des Esclaves apparaît |
| 9 | Ajouter 2 photos | `POST /patrimoines/{id1}/photos` (§4.2) | 2 photos créées |
| 10 | Lister les photos | `GET /patrimoines/{id1}/photos` | Vérifier 2 photos |
| 11 | Modifier les horaires | `PUT /patrimoines/{id1}/horaires` (§5.2) | Horaires mis à jour |
| 12 | Consulter les horaires | `GET /patrimoines/{id1}/horaires` | 7 jours retournés |
| 13 | Modifier la conservation | `PUT /patrimoines/{id1}/conservation` (§6.2) | Conservation mise à jour |
| 14 | Consulter la conservation | `GET /patrimoines/{id1}/conservation` | Données cohérentes |
| 15 | Publier le monument | `PATCH /patrimoines/{id1}/publier` | Statut → `PUBLIE` |
| 16 | Filtrer les publiés | `GET /patrimoines?statut=PUBLIE` | 1 seul résultat |
| 17 | Dépublier | `PATCH /patrimoines/{id1}/depublier` | Statut → `BROUILLON` |
| 18 | Modifier le monument | `PUT /patrimoines/{id1}` (§1.5) | Données mises à jour |
| 19 | Supprimer une photo | `DELETE /patrimoines/{id1}/photos/{photoId}` | 1 photo restante |
| 20 | Supprimer le monument | `DELETE /patrimoines/{id1}` | `204 No Content` |
| 21 | Vérifier suppression | `GET /patrimoines/{id1}` | `404 Not Found` |
| 22 | Lister tous | `GET /patrimoines` | Vérifier 2 résultats restants |

---

## Valeurs des énumérations

| Énumération | Valeurs |
|-------------|---------|
| **TypePatrimoine** | `SITE_NATUREL`, `MUSEE`, `MONUMENT` |
| **CategoriePatrimoine** | `PATRIMOINE_NATUREL`, `PATRIMOINE_HISTORIQUE`, `PATRIMOINE_CULTUREL`, `PATRIMOINE_ARCHITECTURAL`, `PATRIMOINE_RELIGIEUX`, `PATRIMOINE_COLONIAL`, `PATRIMOINE_ARCHEOLOGIQUE`, `PATRIMOINE_MUSEAL`, `PATRIMOINE_MEMORIEL`, `PATRIMOINE_MARITIME`, `PATRIMOINE_RURAL`, `AUTRE` |
| **StatutPatrimoine** | `BROUILLON`, `EN_VALIDATION`, `VALIDE`, `PUBLIE`, `ARCHIVE` |
| **EtatConservation** | `EXCELLENT`, `BON`, `MOYEN`, `MAUVAIS`, `CRITIQUE`, `RUINE` |
| **NatureSiteNaturel** | `PARC_NATIONAL`, `RESERVE_NATURELLE`, `RESERVE_COMMUNAUTAIRE`, `AIRE_MARINE_PROTEGEE`, `FORET`, `MANGROVE`, `LAC`, `LAGON`, `FLEUVE`, `CASCADE`, `PLAGE`, `FALAISE`, `DESERT`, `SAVANE`, `ZONE_HUMIDE`, `DELTA`, `AUTRE` |
| **NatureMonument** | `MONUMENT_HISTORIQUE`, `MONUMENT_COMMEMORATIF`, `EDIFICE_RELIGIEUX`, `EDIFICE_COLONIAL`, `EDIFICE_ADMINISTRATIF`, `FORTIFICATION`, `PHARE`, `PORTE_HISTORIQUE`, `MAUSOLEE`, `STATUE`, `AUTRE` |
| **PeriodeHistorique** | `PREHISTORIQUE`, `ANTIQUITE`, `MEDIEVALE`, `PRECOLONIALE`, `COLONIALE`, `POSTCOLONIALE`, `CONTEMPORAINE`, `INDETERMINEE` |
| **JourSemaine** | `LUNDI`, `MARDI`, `MERCREDI`, `JEUDI`, `VENDREDI`, `SAMEDI`, `DIMANCHE` |
| **TypeMedia** | `PHOTO`, `VIDEO`, `AUDIO`, `DOCUMENT`, `VISITE_VIRTUELLE`, `MODELE_3D` |
| **Niveau menace** | `FAIBLE`, `MOYEN`, `ELEVE`, `CRITIQUE` |
| **Type espèce** | `FAUNE`, `FLORE` |
