# Projet PGVPT (Plateforme de Gestion et de Valorisation du Patrimoine et du Tourisme)

Ce dépôt contient le code de l'application PGVPT, visant à mettre en valeur le patrimoine culturel, historique et naturel du Sénégal. 

## 🚀 Réalisations Frontend (Angular)

Voici le résumé des étapes de création et d'intégration du projet Frontend :

### 1. Initialisation du Projet
- Création du projet **Angular 17+** (Standalone Components) dans le dossier `frontend/app_pgvpt`.
- Installation et configuration de **Tailwind CSS** pour le styling rapide et utilitaire.
- Intégration de la bibliothèque **Leaflet** pour la cartographie interactive.

### 2. Génération des Contrats API (OpenAPI)
- Génération automatique du client HTTP et des modèles TypeScript dans le frontend à partir des spécifications OpenAPI (Swagger) du backend Spring Boot.
- Le code généré permet au frontend de communiquer de manière strictement typée avec les microservices (Patrimoine, Média, Contenu, etc.).

### 3. Architecture et Pattern Adaptateur
- Mise en place d'une interface `PatrimoineUI` servant de *ViewModel*.
- Création d'un `MockService` générique (`getMockData<T>`) pour simuler les données backend de manière typée et sécurisée en attendant le branchement final des vrais endpoints.
- Le frontend est ainsi découplé des éventuels changements de structure de la base de données.

### 4. Création des Pages et Composants (Refonte UI Premium)
L'interface utilisateur a été conçue pour refléter "l'esprit Teranga" (authentique, immersif, premium) en utilisant du CSS sémantique :
- **Page d'accueil (`HomeComponent`) :** Hero section avec slider dynamique, barre de recherche, catégories, et une carte interactive Leaflet centrée sur le Sénégal.
- **Liste des Patrimoines (`PatrimoinesListComponent`) :** Affichage en grille, barre de filtrage latérale (glassmorphism), et animations des cartes au chargement.
- **Détail d'un Patrimoine (`PatrimoineDetailComponent`) :** Présentation sous forme de magazine (grande image 70vh), avec encarts pour les données GPS, la description, et les sites similaires.
- **Composant Réutilisable (`PatrimoineCardComponent`) :** Carte interactive avec zoom de l'image au survol et badges de catégories.

---
*Ce README trace les fondations du projet frontend, de sa génération à son interface finalisée.*
