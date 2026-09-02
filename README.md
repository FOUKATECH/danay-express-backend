# Danay Express — Backend

Système de suivi des véhicules, des trajets et des opérations de transport
pour **Danay Express SARL**. Voir le cahier des charges (v1.2) pour le détail
fonctionnel complet.

## Architecture

Monolithe modulaire (Spring Boot), organisation **package by feature**
(voir CDC section 15 et 16) : chaque module métier est autonome et suit
la même structure interne.

```
src/main/java/cm/danayexpress/backend/
├── referentiel/              (module 1 — Agences, Villes, Lignes, Itinéraires)
├── parcautomobile/           (module 2 — Véhicules, Propriétaires)
├── ressourcesoperationnelles/ (module 3 — Chauffeurs, Affectations)
├── exploitation/             (module 4 — Planification, Programmation, Voyages)
├── passagers/                (module 5 — Transit)
├── finances/                 (module 6 — Tarifs, Recettes)
├── incidents/                (module 7 — Incidents, Secours)
├── maintenance/              (module 8 — Interventions)
├── reporting/                (module 9)
├── audit/                    (module 10 — Journalisation)
├── administration/           (module 11 — Utilisateurs, Rôles, Permissions)
├── security/                 (config Spring Security / JWT — provisoire)
├── config/                   (CORS, OpenAPI, Redis...)
└── common/                   (classes transverses : AuditableEntity, BusinessException, GlobalExceptionHandler)
```

Chaque module contient : `controller/ service/ repository/ entity/ dto/
mapper/ exception/ enums/` (voir CDC section 16.1).

> **Note sur les noms de packages** : le CDC nomme les modules
> `01-referentiel`, `02-parc-automobile`, etc. Ces noms ne sont pas des
> identifiants Java valides (chiffre en tête, tiret). Les packages
> utilisent donc des noms simples (`referentiel`, `parcautomobile`...) ;
> l'ordre logique des modules reste documenté ici et dans le CDC.

## Stack technique

| Couche | Technologie |
|---|---|
| Backend | Spring Boot 3.3 (Java 21) |
| Persistance | Spring Data JPA / Hibernate + PostgreSQL 16 |
| Migrations | Flyway |
| Cache | Redis |
| Sécurité | Spring Security + JWT (jjwt) |
| Doc API | springdoc-openapi (Swagger UI) |
| Reporting | Apache POI (Excel) + OpenPDF (PDF) |
| Tests | JUnit 5 + Mockito + H2 (tests d'intégration repository) |
| Conteneurisation | Docker + Docker Compose |

## Lancer le projet en local (développement)

Prérequis : Java 21, Maven, Docker.

```bash
# 1. Démarrer Postgres + Redis seulement (l'API tourne en local, hors Docker, pour itérer vite)
docker compose up postgres redis -d

# 2. Lancer l'API avec le profil dev (lit application-dev.yml)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

L'API est alors disponible sur `http://localhost:8080/api`, la doc
Swagger sur `http://localhost:8080/api/swagger-ui.html`.

## Lancer le projet complet en conteneurs (comme en production)

```bash
export DB_PASSWORD=change-moi
export JWT_SECRET=une-longue-cle-secrete-aleatoire
docker compose up --build
```

## État actuel du squelette

- [x] Structure des 11 modules (package by feature)
- [x] Configuration Spring Boot (profils dev/prod)
- [x] Docker Compose (Postgres, Redis, API)
- [x] Gestion d'erreurs globale + entité de base auditable
- [x] Sécurité minimale (permitAll — **provisoire**, JWT à implémenter avec le module 11)
- [ ] Migrations Flyway (à commencer avec le module Référentiel)
- [ ] Premier module fonctionnel implémenté
- [ ] Frontend Angular

## Prochaine étape

Modélisation et implémentation du **module 1 — Référentiel** (Agence,
Ville, Ligne, Sens, ÉtapeItinéraire), en commençant par les migrations
Flyway et les entités JPA.
