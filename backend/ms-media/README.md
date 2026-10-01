# ms-media

## Rôle
Microservice du PGVPT. Endpoints contractuels de référence : GET/POST /medias, GET/DELETE /medias/{id}.

## Architecture implémentée
Controller → Service → Repository → JPA Entity, avec DTO + MapStruct.
`BaseEntity` est une `@MappedSuperclass`; les entités métier héritent de `BaseEntity`.

## Lancer
```bash
mvn spring-boot:run
```

## Tester
```bash
mvn clean test
```

## Construire
```bash
mvn clean package
docker build -t pgvpt/ms-media:1.0.0 .
```

> La base H2 est destinée au développement. En production, remplacer la configuration par PostgreSQL/PostGIS ou le moteur propre au bounded context.
