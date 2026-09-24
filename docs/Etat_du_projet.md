Danay Express — Backend
État d'avancement du projet et guide de reprise
Document généré pour assurer la continuité du développement — septembre 2026
1. Présentation du projet
Danay Express Backend est le système de suivi des véhicules, des trajets et des opérations de transport pour Danay Express SARL (transport, logistique, tourisme). Le projet remplace un suivi opérationnel actuellement basé sur WhatsApp/téléphone par une plateforme centralisée.
Le cahier des charges (v1.2) découpe l'application en 11 modules fonctionnels. Ce document fait le point sur ce qui est construit, ce qui reste à faire, et les décisions de conception prises en cours de route.
2. Vue d'ensemble des modules
N°	Module	Entités principales	Statut
1	Référentiel	Ville, Agence, Ligne, Sens, ÉtapeItinéraire	TERMINÉ
2	Parc Automobile	Propriétaire, Véhicule	TERMINÉ
3	Ressources Opérationnelles	Chauffeur, Affectation	TERMINÉ
4	Exploitation	Voyage, Escale	TERMINÉ
5	Passagers / Transit	MouvementTransit (+ consultation)	TERMINÉ
6	Finances	Tarifs, Recettes	TERMINÉ
7	Incidents	Incidents, Secours	TERMINÉ
8	Maintenance	Interventions véhicules	À FAIRE
9	Reporting	Tableaux de bord, exports	À FAIRE
10	Audit	Journalisation des actions	À FAIRE
11	Administration	Utilisateurs, rôles, permissions, JWT	À FAIRE (critique)


3. Architecture technique
3.1. Stack
•	Backend : Spring Boot 3.3 (Java 21)
•	Persistance : Spring Data JPA / Hibernate + PostgreSQL 16
•	Migrations : Flyway (scripts versionnés V1 à V5 pour l'instant)
•	Cache : Redis (configuré, pas encore exploité par un module métier)
•	Sécurité : Spring Security + JWT (jjwt) — configuration actuellement PROVISOIRE (voir section 5)
•	Documentation API : springdoc-openapi (Swagger UI)
•	Mapping DTO ↔ entité : MapStruct (génération à la compilation)
•	Tests : JUnit 5 + Mockito + H2 (infrastructure en place, peu de tests écrits à ce stade)
•	Conteneurisation : Docker + Docker Compose (Postgres, Redis, API)
3.2. Organisation du code — monolithe modulaire
Architecture "package by feature" : chaque module métier est un package autonome sous cm.danayexpress.backend, avec la même structure interne :
•	entity/ — entités JPA
•	enums/ — énumérations du module
•	repository/ — interfaces Spring Data JPA
•	dto/ — Request/Response (records Java immuables)
•	mapper/ — interfaces MapStruct (entité ↔ DTO)
•	service/ — logique métier et règles de gestion
•	controller/ — endpoints REST
•	exception/ — exceptions métier dédiées au module (NotFound / Conflict)
Packages transverses : config/ (CORS, sécurité), common/ (AuditableEntity, BusinessException, GlobalExceptionHandler), security/ (config Spring Security provisoire).
3.3. Dépendances entre modules
Les modules ne sont pas cloisonnés : ils réutilisent directement les entités et exceptions d'autres modules quand c'est légitime (ex : Vehicule référence Agence du Référentiel ; Voyage référence Ligne/Sens/Agence + Vehicule ; Affectation référence Voyage). C'est un choix assumé du monolithe modulaire — pas de duplication de données entre modules.
3.4. Base de données
7 migrations Flyway appliquées à ce jour :
•	V1 — Référentiel (villes, agences, lignes, sens, etapes_itineraire)
•	V2 — Parc Automobile (proprietaires, vehicules)
•	V3 — Ressources Opérationnelles (chauffeurs, affectations)
•	V4 — Exploitation (voyages, escales) + ajout colonne voyage_id sur affectations
•	V5 — Passagers (mouvements_transit)
•	V6 — Finances (tarifs, recettes)
•	V7 — Incidents & Secours (incidents, interventions_secours)
Convention de nommage : tables en snake_case pluriel, contraintes CHECK pour les enums, clés étrangères explicites, index sur les colonnes de recherche fréquente.
3.5. Environnement de développement
Le projet tourne en local avec Docker Compose pour Postgres/Redis, et l'application elle-même lancée depuis IntelliJ (profil dev).
•	Postgres exposé sur le port 5433 (5432 est occupé par un autre projet sur cette machine — changer si ce n'est plus le cas ailleurs)
•	Redis sur le port 6379
•	API sur le port 8080, context-path /api → Swagger UI sur /api/swagger-ui.html
•	Fichier .env local (jamais commité) pour DB_PASSWORD et JWT_SECRET
•	docker-compose.yml prévoit aussi un service api pour un déploiement conteneurisé complet
 
4. Détail des modules terminés
4.1. Module 1 — Référentiel
Réseau d'agences organisées par lignes avec ordre et sens de circulation.
•	Ville, Agence (statut ACTIVE/INACTIVE), Ligne, Sens (ALLER/RETOUR), ÉtapeItinéraire (ordre des agences par sens)
•	Règles vérifiées : agence de départ ≠ arrivée (RM-08), un seul sens ALLER et un seul RETOUR par ligne (RM-09), ordre et agence uniques par sens (RM-07)
•	CRUD complet (5 entités × DTO/Mapper/Service/Controller)
4.2. Module 2 — Parc Automobile
•	Propriétaire, Véhicule (immatriculation unique, statuts : DISPONIBLE, PROGRAMME, EN_VOYAGE, EN_PANNE, EN_SECOURS, EN_MAINTENANCE, IMMOBILISE)
•	Véhicule rattaché à un Propriétaire et à une Agence (module Référentiel)
•	CRUD complet, filtre par statut
4.3. Module 3 — Ressources Opérationnelles
•	Chauffeur (numéro de permis unique, statut ACTIF/INACTIF, endpoints activer/désactiver dédiés)
•	Affectation chauffeur ↔ véhicule, avec lien optionnel vers un Voyage (ajouté à la migration V4)
•	Règle : un chauffeur ne peut pas avoir deux affectations ACTIVE simultanément (disponibilité)
•	Historique consultable par chauffeur ou par véhicule, action "terminer" une affectation
4.4. Module 4 — Exploitation (le cœur du système)
Cycle de vie complet d'un voyage, une seule ligne en base du départ à l'arrivée finale (RM-06).
•	Voyage : programmation (ligne, sens, agence de départ, destination finale, véhicule) → départ réel → arrivée finale
•	Statuts : PROGRAMME → EN_VOYAGE → TERMINE, avec actions dédiées (endpoints /depart et /arrivee-finale) plutôt qu'un PUT générique
•	Le passage EN_VOYAGE met le véhicule en statut EN_VOYAGE ; l'arrivée finale fixe le nouveau statut du véhicule choisi explicitement par l'utilisateur (RM-12)
•	Escale : arrêts intermédiaires, avec calcul automatique de l'effectif à bord (RM-07 pour l'ordre unique par voyage)
•	Vérification qu'un véhicule n'est pas programmé deux fois le même jour sur des voyages non terminés
4.5. Module 5 — Passagers / Transit
Construit par-dessus les données d'Exploitation plutôt qu'en dupliquant des tables.
•	MouvementTransit : chaque montée/descente est enregistrée avec son agence d'origine réelle (règle métier section 8.5 : nécessaire pour attribuer correctement la recette à la bonne agence au module 6)
•	Une montée a toujours pour origine l'agence où elle a lieu (automatique)
•	Une descente exige une répartition explicite par agence d'origine, validée contre le solde réellement disponible par origine (impossible de faire "descendre" plus de passagers qu'il n'y en a d'une origine donnée)
•	À l'arrivée finale, la répartition doit couvrir la totalité des passagers restants à bord
•	Endpoints de consultation : journal des mouvements, soldes par origine, vue d'ensemble "évolution des effectifs"
4.6. Module 6 — Finances
•	Tarif (origine, destination, typeVehicule, montant, dates d'application, statut ACTIF/INACTIF)
•	Recette (rattachée à un MouvementTransit et un Tarif applicatif, calcul automatique)
•	Rattachement comptable de la recette à l'agence d'origine réelle du passager (RM-17)
4.7. Module 7 — Incidents & Véhicules de Secours
•	Incident (véhicule, voyage optionnel, agence optionnelle, typeIncident, gravite, description, passagers affectés, statut : SIGNALE, EN_COURS, SECOURS_AFFECTE, RESOLU, CLOTURE)
•	Mise en panne automatique du véhicule pour pannes mécaniques graves / accidents
•	Recherche assistée de véhicules de secours disponibles (RM-13, RM-14 : filtrés par statut DISPONIBLE et agence)
•	InterventionSecours (rattachement du véhicule de secours et du chauffeur, passage du véhicule en EN_SECOURS, suivi du workflow AFFECTE → EN_ROUTE → PRIS_EN_CHARGE → TERMINE, puis libération du véhicule)
 
5. Ce qu'il reste à faire
5.1. Module 8 — Maintenance
•	Suivi des interventions sur les véhicules (déclaration de panne, passage en EN_MAINTENANCE, historique des révisions, remise en DISPONIBLE)
5.2. Module 9 — Reporting
•	Tableaux de bord et exports (le CDC mentionne un export PDF/Excel — dépendances déjà présentes dans le pom.xml : Apache POI, OpenPDF)
5.3. Module 10 — Audit
•	Journalisation des actions utilisateurs (dépend du module 11 pour savoir qui fait quoi)
5.4. Module 11 — Administration (PRIORITAIRE avant toute mise en production)
Utilisateurs, rôles, permissions, authentification JWT complète.
•	La configuration de sécurité actuelle (SecurityConfig) autorise TOUTES les requêtes sans authentification (permitAll) — c'est volontaire pour développer vite, mais ne doit jamais partir en production tel quel
•	dépendances JWT (jjwt) déjà présentes dans le pom.xml, juste pas encore branchées
•	C'est aussi ce module qui débloquera le module Audit (traçabilité par utilisateur)
 
6. Points d'attention pour la suite
•	Sécurité ouverte : voir 5.6, c'est le point le plus important avant toute mise en ligne réelle.
•	Assistants de voyage : mentionnés dans le CDC comme fonctionnalité optionnelle ("le système pourra gérer les assistants") — volontairement laissés de côté, à ajouter si Danay Express le demande.
•	Frontend : aucun frontend Angular n'a encore été commencé ; seul le backend existe à ce stade.
•	Déploiement : le CDC prévoit un déploiement via Cloudflare Tunnel (serveur local, accès agences distantes par sous-domaine) — pas encore mis en place.
•	Tarification : les règles tarifaires précises ne sont pas détaillées dans le CDC v1.2 (section 28, points restants identifiés par l'auteur du CDC lui-même) — à clarifier avec Danay Express avant de construire le module 6.
•	Format d'import des données existantes : agences, véhicules, chauffeurs actuels de l'entreprise — le format source n'est pas encore défini.
7. Démarrage rapide du projet
•	Prérequis : JDK 21, IntelliJ IDEA, Docker Desktop
•	Cloner le dépôt Git, créer un fichier .env à la racine avec DB_PASSWORD et JWT_SECRET
•	docker compose up postgres redis -d
•	Ouvrir le projet dans IntelliJ (laisser Maven importer les dépendances)
•	Lancer l'application avec le profil dev actif — Flyway applique automatiquement les migrations
•	Swagger UI disponible sur http://localhost:8080/api/swagger-ui.html pour tester tous les endpoints
8. Historique et suivi
Le code est versionné avec Git et poussé sur GitHub. Chaque module a été développé, testé manuellement via Swagger, puis committé avant de passer au suivant. Se référer à l'historique des commits pour l'ordre exact de construction.
