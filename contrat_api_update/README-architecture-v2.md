# PGVPT — Cohérence Contract-First v2

### Bounded contexts
- **Identité** : ms-identite
- **Référentiel patrimonial** : ms-patrimoine
- **Géographie** : ms-geolocalisation
- **Éditorial** : ms-contenu
- **Médias** : ms-media
- **Circuits** : ms-circuit
- **Événements** : ms-evenement
- **Commercialisation** : ms-tourisme
- **Réservation** : ms-reservation

### Relations inter-services
Toutes les relations sont des UUID :
`patrimoineId`, `zoneTouristiqueId`, `circuitId`, `acteurId`, `utilisateurId`, `ressourceId`.

### Règles structurantes
1. Une base de données par microservice.
2. Aucun `@ManyToOne`, `@OneToMany` ou FK JPA vers une base d'un autre microservice.
3. ms-patrimoine est la source de vérité de l'identité du patrimoine.
4. ms-geolocalisation est la source de vérité des zones touristiques et coordonnées.
5. ms-media est propriétaire des médias; ms-patrimoine ne les duplique plus.
6. ms-contenu est propriétaire du texte éditorial et peut référencer des `mediaIds`.
7. ms-circuit est propriétaire des circuits, étapes et activités.
8. ms-evenement est propriétaire des événements, sessions et capacités.
9. ms-tourisme compose des offres commerciales à partir de ressources existantes.
10. ms-reservation orchestre la réservation; la disponibilité reste chez le propriétaire.
11. Les prix confirmés sont historisés dans Reservation via des snapshots.
12. Les transitions de cycle de vie utilisent PATCH et retournent l'état de l'agrégat.

### Flux métier
Patrimoine → Géolocalisation / Contenu / Media → Circuit ou Événement → Offre → Réservation.
