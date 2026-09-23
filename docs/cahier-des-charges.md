
CAHIER DES CHARGES FONCTIONNEL ET TECHNIQUE
Système de suivi des véhicules, des trajets et des opérations de transport
Danay Express SARL
Version : 1.2
Statut : Architecture technique et déploiement clarifiés
Date : Août 2026
 
1. Présentation du projet
1.1. Contexte
Danay Express SARL est une entreprise évoluant dans les domaines du transport, de la logistique et du tourisme.
Dans le cadre de ses activités de transport, l'entreprise exploite un réseau d'agences desservant différentes villes et localités. Les véhicules effectuent des voyages selon des itinéraires prédéfinis et traversent plusieurs agences avant d'atteindre leur destination finale.
Le suivi actuel des opérations repose principalement sur les communications entre les agences et les responsables de l'exploitation, notamment à travers les groupes whatsapp et les appels téléphoniques. Les informations relatives aux départs, arrivées, états des véhicules, effectifs de passagers et incidents sont ensuite communiquées et saisies dans le système.
Ce mode de fonctionnement permet d'assurer les opérations quotidiennes, mais rend plus difficile la centralisation, la consultation historique, la supervision en temps réel des opérations et la production d'indicateurs fiables pour la direction.
Le présent projet vise donc à mettre en place une application permettant de centraliser les informations relatives aux véhicules, aux agences, aux chauffeurs, aux voyages, aux passagers, aux recettes, aux incidents et à la maintenance, tout en fournissant des tableaux de bord et des rapports destinés à la direction.

2. Problématique
Le fonctionnement actuel présente plusieurs besoins :
•	Difficulté à disposer d'une vision centralisée des véhicules ;
•	Difficulté à connaître rapidement les véhicules disponibles dans les différentes agences ;
•	Suivi des voyages reposant largement sur les communications téléphoniques et les groupes whatsapp ;
•	Nécessité de centraliser les informations communiquées par les agences ;
•	Difficulté à retracer l'ensemble des événements d'un voyage ;
•	Evolution du nombre de passagers au cours d'un même voyage ;
•	Nécessité de suivre les incidents et les véhicules de secours ;
•	Besoin de connaître l'état des véhicules après les voyages ;
•	Besoin de suivre les recettes par voyage, agence, véhicule et destination ;
•	Besoin d'une vision synthétique de l'activité pour la direction ;
•	Besoin de rapports historiques et décisionnels ;
•	Nécessité de conserver une trace des actions réalisées dans le système.
3. Objectifs du projet
3.1. Objectif général
Concevoir et mettre en œuvre un système informatique centralisé permettant de suivre les véhicules, les voyages et les opérations de transport de Danay Express, tout en fournissant à la direction des tableaux de bord et des outils de reporting fiables.
3.2. Objectifs spécifiques
Le système devra permettre de :
•	Centraliser le référentiel des agences ;
•	Centraliser les informations relatives aux véhicules et à leurs propriétaires ;
•	Gérer les chauffeurs et leurs affectations ;
•	Gérer les lignes, corridors et itinéraires ;
•	Planifier les besoins de transport ;
•	Programmer les véhicules pour les voyages ;
•	Suivre les départs et les arrivées ;
•	Suivre les passages des véhicules dans les agences intermédiaires ;
•	Suivre l'évolution du nombre de passagers au cours d'un voyage ;
•	Gérer les informations relatives aux transits ;
•	Calculer et suivre les recettes ;
•	Gérer les incidents et les véhicules de secours ;
•	Suivre les opérations de maintenance ;
•	Fournir une vision opérationnelle de l'activité ;
•	Fournir des tableaux de bord destinés à la direction ;
•	Produire des rapports ;
•	Assurer la traçabilité des actions effectuées dans l'application ;
•	Contrôler l'accès aux fonctionnalités selon les responsabilités des utilisateurs.
4. Périmètre du système
4.1. Périmètre inclus
Le système couvre les domaines suivants :
•	Agences ;
•	Lignes et corridors ;
•	Itinéraires ;
•	Véhicules ;
•	Propriétaires ;
•	Chauffeurs ;
•	Affectations ;
•	Planification ;
•	Programmation des véhicules ;
•	Voyages ;
•	Départs ;
•	Arrivées ;
•	Passages dans les agences ;
•	Passagers ;
•	Transit ;
•	Recettes ;
•	Incidents ;
•	Véhicules de secours ;
•	Maintenance ;
•	Tableaux de bord ;
•	Reporting ;
•	Journalisation ;
•	Administration et permissions ;
4.2. Données initiales
Lors de l'implémentation, le système devra pouvoir être initialisé à partir des données existantes de Danay Express.
Les données actuellement disponibles comprennent notamment :
•	La liste des agences ;
•	La liste des véhicules ;
•	La liste des propriétaires ;
•	La liste des chauffeurs.
Ces données seront importées dans le système lors de la phase d'initialisation. Le format exact d'importation sera défini en fonction du fichier source fourni par Danay Express.
4.3. Éléments hors périmètre de la V1
Les éléments suivants ne sont pas retenus comme fonctionnalités principales de la première version :
•	Gestion complète de la billetterie ;
•	Enregistrement détaillé de l'identité de chaque passager ;
•	Gestion des colis et du fret ;
•	Géolocalisation GPS des véhicules ;
•	Application mobile native ;
•	Intelligence artificielle de prédiction ;
•	Comptabilité générale de l'entreprise ;
•	Gestion complète des ressources humaines.
La gestion détaillée des clients/passagers reste liée au système de billetterie existant. La présente application exploite principalement les informations nécessaires au suivi opérationnel : effectifs, mouvements de passagers, transit et recettes.
5. Acteurs du système
5.1. Directeur d'exploitation
Le Directeur d'exploitation assure la supervision globale de l'exploitation. Ses responsabilités comprennent notamment :
•	Planification de l'activité ;
•	Supervision des voyages ;
•	Supervision des véhicules ;
•	Supervision des incidents ;
•	Suivi des performances ;
•	Consultation des tableaux de bord ;
•	Consultation des rapports ;
•	Supervision des collaborateurs ;
•	Gestion des permissions selon les responsabilités définies.
5.2. Collaborateurs du Directeur d'exploitation
Les collaborateurs du Directeur d'exploitation participent à la planification et à la supervision des opérations selon les responsabilités qui leur sont attribuées. Leurs droits seront déterminés par le système de permissions.
5.3. Chef de service de communication
Le Chef de service de communication participe à la planification et assure le suivi des informations opérationnelles provenant des agences. Il peut notamment :
•	Consulter les planifications ;
•	Suivre les voyages ;
•	Consulter les véhicules ;
•	Suivre les événements opérationnels ;
•	Superviser les informations communiquées par les agences ;
•	Consulter les tableaux de bord autorisés.
5.4. Assistant du chef de service de communication
L'assistant constitue un opérateur central de saisie des informations opérationnelles. Il reçoit notamment les informations communiquées par les agences à travers les groupes whatsapp ou les appels téléphoniques et les saisit dans l'application. Il peut notamment enregistrer :
•	Départs ;
•	Arrivées ;
•	Etats des véhicules ;
•	Effectifs de passagers ;
•	Mouvements de passagers ;
•	Incidents ;
•	Informations relatives aux secours ;
•	Autres informations opérationnelles.
5.5. Chef d'agence
Le Chef d'agence est responsable des opérations de son agence. Il peut notamment :
•	Consulter les véhicules disponibles ;
•	Consulter la planification ;
•	Programmer les véhicules ;
•	Décider du départ réel d'un bus ;
•	Communiquer le départ ;
•	Communiquer l'arrivée ;
•	Communiquer l'état du véhicule ;
•	Communiquer les mouvements de passagers ;
•	Signaler un incident ;
•	Demander un véhicule de secours ;
•	Rendre compte au Chef de service de communication ou à son assistant.
6. Organisation du réseau d'agences
6.1. Principe
Les agences de Danay Express ne constituent pas une simple liste indépendante. Elles sont organisées suivant des lignes ou corridors de transport. Une ligne définit une succession ordonnée d'agences.
Exemple
Pour la ligne Yagoua – Garoua :
Yagoua → Kalfou → Guidiguis → Kaélé → Figuil → Garoua
Chaque agence possède donc une position dans l'itinéraire.
6.2. Agences de départ et de destination
Toutes les agences ne sont pas nécessairement des points de départ principaux. Certaines agences constituent des :
•	Agences de départ ;
•	Agences intermédiaires ;
•	Destinations finales.
Les agences de départ sont également des destinations finales possibles. Une même ligne peut être parcourue dans les deux sens.
Exemple
Sens aller : Yagoua → Kalfou → Guidiguis → Kaélé → Figuil → Garoua
Sens retour : Garoua → Figuil → Kaélé → Guidiguis → Kalfou → Yagoua
Cette organisation doit être prise en compte dans la conception du système.
7. Architecture fonctionnelle générale
Le fonctionnement global du système est basé sur la chaîne suivante :
Réseau → Ligne/Corridor → Sens → Agences ordonnées → Planification → Programmation → Voyage → Événements opérationnels → Reporting
Les principales entités métier sont :
•	Agence ;
•	Ligne/Corridor ;
•	Étape d'itinéraire ;
•	Propriétaire ;
•	Véhicule ;
•	Chauffeur ;
•	Affectation ;
•	Planification ;
•	Voyage ;
•	Événement de voyage ;
•	Passager/Transit ;
•	Recette ;
•	Incident ;
•	Véhicule de secours ;
•	Maintenance ;
•	Utilisateur ;
•	Rôle ;
•	Journal d'audit.
8. Description des modules fonctionnels
8.1. Module 1 — Gestion des agences
Objectif : Centraliser le référentiel des agences et leur organisation dans le réseau de transport.
Fonctionnalités
Le système devra permettre :
•	D'ajouter une agence ;
•	De modifier une agence ;
•	De consulter une agence ;
•	De rechercher une agence ;
•	D'activer ou désactiver une agence ;
•	D'attribuer un identifiant à l'agence ;
•	D'enregistrer son nom, sa ville et ses coordonnées ;
•	D'associer un responsable à l'agence ;
•	De rattacher l'agence à une ou plusieurs lignes ;
•	De définir son ordre dans une ligne ;
•	D'identifier son rôle dans un itinéraire.
Informations principales
•	Identifiant agence ;
•	Nom, ville, adresse, téléphone ;
•	Responsable ;
•	Statut ;
•	Lignes desservies ;
•	Position dans les itinéraires.
8.2. Module 2 — Gestion des véhicules et propriétaires
Objectif : Gérer le parc automobile et les propriétaires des véhicules.
Gestion des véhicules
Le système devra permettre :
•	D'enregistrer, modifier, consulter et rechercher un véhicule ;
•	D'associer le véhicule à un propriétaire ;
•	D'indiquer son type, sa capacité et son agence de rattachement ;
•	De consulter son statut et son historique.
Statuts principaux
•	Disponible ; Programmé ; En voyage ; En panne ; En secours ; En maintenance ; Immobilisé.
Gestion des propriétaires
•	Enregistrer et modifier un propriétaire ;
•	Consulter ses véhicules et associer plusieurs véhicules à un propriétaire ;
•	Consulter l'historique de ses véhicules.
8.3. Module 3 — Gestion des chauffeurs et affectations
Objectif : Gérer les chauffeurs, assistants et affectations aux véhicules et voyages.
•	Ajouter, modifier, consulter, activer/désactiver un chauffeur ;
•	Consulter sa disponibilité ;
•	Affecter un chauffeur à un véhicule ou à un voyage ;
•	Affecter plusieurs chauffeurs à un même voyage lorsque nécessaire ;
•	Consulter l'historique des affectations.
Le système pourra également gérer les assistants associés aux voyages.
8.4. Module 4 — Gestion des voyages, itinéraires, départs et arrivées
Objectif : Assurer le suivi du cycle de vie complet d'un voyage.
Gestion des lignes et itinéraires
•	Créer une ligne/corridor et définir son sens ;
•	Définir l'agence de départ et la destination finale ;
•	Ajouter les agences intermédiaires et définir leur ordre ;
•	Définir éventuellement les distances et durées estimées.
Création d'un voyage
Un voyage pourra comporter : identifiant, date, ligne, sens, agence de départ, destination finale, véhicule, chauffeur(s), assistant, heure prévue de départ, statut.
Départ
Le système devra permettre d'enregistrer l'agence, la date, l'heure réelle, l'effectif de passagers, l'état du véhicule et les observations. Après validation, le véhicule passe en statut En voyage.
Arrivée dans une agence intermédiaire
Le système devra permettre d'enregistrer l'agence, l'heure d'arrivée, l'état du véhicule, les passagers descendus/montés, le nombre de passagers à bord et les observations.
Départ d'une agence intermédiaire
L'application devra enregistrer l'heure de départ, le nombre de passagers, l'état du véhicule et les observations.
Arrivée finale
Lorsque le véhicule arrive à sa destination finale : le voyage est marqué Terminé, l'heure réelle d'arrivée et l'état du véhicule sont enregistrés, et le véhicule est orienté vers son prochain statut. Le voyage reste considéré comme un seul voyage jusqu'à son arrivée à la destination finale.
8.5. Module 5 — Gestion des passagers et transit
Objectif : Suivre l'évolution des effectifs de passagers au cours d'un voyage sans remplacer le système de billetterie existant.
•	Enregistrer l'effectif au départ ;
•	Enregistrer les passagers montés et descendus dans une agence ;
•	Calculer automatiquement l'effectif à bord ;
•	Consulter l'évolution des effectifs ;
•	Gérer les informations de transit et les rattacher aux agences concernées.
Exemple
Départ de Yagoua : 45 passagers. À Kalfou : 5 descendent, 8 montent, total à bord : 48. À Guidiguis : 10 descendent, 3 montent, total à bord : 41.
Règle métier
Les informations relatives au transit doivent permettre de conserver l'origine du mouvement afin de déterminer correctement l'agence concernée pour le calcul de la recette.
8.6. Module 6 — Gestion des recettes
Objectif : Calculer et suivre les recettes générées par les opérations de transport.
Gestion des tarifs
Le système devra permettre de définir : origine, destination, type de véhicule, tarif, période d'application, statut. Les tarifs peuvent être différents selon le type de bus, notamment pour les bus VIP.
Calcul
Le système pourra calculer automatiquement la recette à partir de : Nombre de passagers × tarif applicable.
Consultation
Les recettes pourront être consultées :
•	Par voyage, agence, véhicule, propriétaire, destination, ligne, période, type de bus et catégorie tarifaire.
8.7. Module 7 — Gestion des incidents et véhicules de secours
Objectif : Permettre de déclarer, suivre et résoudre les incidents affectant les véhicules et les voyages.
Gestion des incidents
Le système devra permettre de déclarer un incident, d'indiquer le véhicule et le voyage concernés, l'agence ou la localisation déclarée, la date et l'heure, le type, la description, la gravité, le nombre de passagers concernés et de suivre son statut.
Statuts
•	Signalé ; En cours ; Secours affecté ; Résolu ; Clôturé.
Gestion des véhicules de secours
Lorsqu'un véhicule tombe en panne, le système devra permettre :
•	De signaler la panne ;
•	D'identifier l'agence concernée ou la position déclarée ;
•	De rechercher un véhicule disponible dans une agence appropriée ;
•	D'affecter le véhicule de secours et de suivre l'intervention ;
•	De confirmer la prise en charge et de clôturer l'incident.
8.8. Module 8 — Gestion de la maintenance
Objectif : Assurer le suivi de l'état technique des véhicules et des opérations de maintenance.
•	Déclarer une panne et immobiliser un véhicule ;
•	Créer une opération de maintenance ;
•	Enregistrer la nature de l'intervention, les travaux réalisés et les coûts lorsqu'ils sont disponibles ;
•	Enregistrer la date d'intervention et la date de remise en service ;
•	Consulter l'historique des interventions ;
•	Remettre un véhicule en disponibilité après validation.
Cycle simplifié
Disponible → Panne → Maintenance → Réparé → Disponible
8.9. Module 9 — Planification
Objectif : Permettre à la direction de l'exploitation de préparer les besoins futurs en transport.
Planification
Réalisée par le Directeur d'exploitation et ses collaborateurs selon les responsabilités attribuées. Elle permet notamment de :
•	Définir les besoins de transport et planifier les voyages ;
•	Définir les destinations ;
•	Consulter les disponibilités et prévoir les véhicules nécessaires ;
•	Préparer les départs futurs.
Programmation
La programmation opérationnelle est réalisée par le Chef d'agence, qui consulte la planification et les véhicules disponibles, choisit le véhicule, affecte les chauffeurs nécessaires, programme le voyage puis rend compte au Chef de service de communication ou à son assistant.
Contrôles
Le système doit empêcher ou signaler notamment :
•	L'affectation d'un véhicule déjà engagé dans un autre voyage incompatible ;
•	La programmation d'un véhicule en panne ou en maintenance ;
•	L'affectation d'un chauffeur indisponible ;
•	Les conflits d'affectation.
8.10. Module 10 — Tableaux de bord
Objectif : Fournir une vision synthétique et actualisée de l'activité.
Tableau de bord opérationnel
•	Véhicules disponibles, programmés, en voyage, en panne, en maintenance ;
•	Voyages programmés, en cours, terminés ;
•	Incidents actifs ; véhicules de secours mobilisés ;
•	Prochains départs ; dernières arrivées.
Tableau de bord direction
•	Nombre de voyages et de passagers ;
•	Recettes (globales, par agence, véhicule, ligne) ;
•	Disponibilité du parc ;
•	Nombre d'incidents et de pannes ;
•	Evolution de l'activité ; performance des agences et des véhicules.
Les indicateurs devront pouvoir être filtrés par période et, lorsque pertinent, par agence, ligne, véhicule ou propriétaire.
8.11. Module 11 — Reporting
Objectif : Produire des rapports permettant à la direction de suivre et analyser l'activité.
Rapports opérationnels
•	Rapport quotidien des voyages, des départs et des arrivées ;
•	Rapport des véhicules disponibles et en panne ;
•	Rapport des incidents et de maintenance.
Rapports financiers
•	Recettes quotidiennes et mensuelles ;
•	Recettes par agence, véhicule, ligne, destination et type de bus.
Rapports statistiques
•	Nombre de voyages par période et nombre de passagers ;
•	Evolution de la fréquentation et performance des agences ;
•	Utilisation du parc, incidents et pannes.
Filtres et export
Les rapports devront pouvoir être filtrés par période, agence, ligne, véhicule, propriétaire et type de véhicule, et exportés au format PDF et Excel.
8.12. Module 12 — Journalisation et audit
Objectif : Garantir la traçabilité des actions réalisées dans l'application.
Le système devra enregistrer notamment : utilisateur, date, heure, action, module, élément concerné, ancienne valeur et nouvelle valeur lorsqu'elles sont disponibles.
Exemples
Programmation — Utilisateur : Chef d'agence Yagoua ; Action : programmation d'un voyage ; Véhicule : ABC-123 ; Destination : Garoua. Départ — Utilisateur : Assistant Communication ; Action : déclaration de départ ; Agence : Yagoua ; Véhicule : ABC-123 ; Nombre de passagers : 45.
Le journal d'audit devra être consultable selon les droits accordés.
8.13. Module 13 — Administration et permissions
Objectif : Administrer les utilisateurs, les rôles et les droits d'accès.
Gestion des utilisateurs
•	Ajouter, modifier, activer/désactiver un utilisateur ;
•	Réinitialiser les accès ;
•	Associer un rôle et, le cas échéant, une agence.
Rôles initiaux
•	Directeur d'exploitation ; Collaborateur du Directeur d'exploitation ; Chef de service de communication ; Assistant du Chef de service de communication ; Chef d'agence. La liste pourra évoluer.
Gestion des permissions
Les droits devront permettre de contrôler notamment : consultation, création, modification, suppression/désactivation, programmation, déclaration de départ/arrivée, gestion des incidents, consultation des recettes et des tableaux de bord, export des rapports, administration.
9. Règles métier principales
RM-01 — Planification
La planification des besoins et des voyages futurs relève du Directeur d'exploitation et de ses collaborateurs selon les responsabilités attribuées.
RM-02 — Programmation
Le Chef d'agence programme concrètement les bus conformément à la planification.
RM-03 — Compte rendu
Le Chef d'agence rend compte de la programmation et des opérations au Chef de service de communication ou à son assistant.
RM-04 — Saisie des informations
Les informations opérationnelles communiquées par les agences peuvent être saisies dans l'application par l'assistant du Chef de service de communication.
RM-05 — Départ réel
Le Chef d'agence décide du moment réel où le bus quitte son agence.
RM-06 — Voyage unique
Un voyage reste considéré comme un seul voyage depuis son agence de départ jusqu'à sa destination finale.
RM-07 — Agences ordonnées
Les agences traversées par un voyage sont ordonnées selon la ligne/corridor et le sens du voyage.
RM-08 — Départ et destination
Les agences de départ sont également des destinations finales possibles.
RM-09 — Sens de circulation
Une même ligne peut être parcourue dans les deux sens.
RM-10 — Passagers
Le nombre de passagers peut évoluer à chaque agence intermédiaire en fonction des montées et des descentes.
RM-11 — Fin du voyage
Le voyage est terminé lorsque le véhicule atteint la destination finale définie au moment de sa programmation.
RM-12 — État du véhicule
À la fin du voyage, l'état du véhicule doit être enregistré afin de déterminer sa disponibilité, son immobilisation ou sa maintenance.
RM-13 — Panne
Lorsqu'un véhicule tombe en panne, un véhicule disponible peut être recherché dans une agence appropriée afin d'assurer le secours.
RM-14 — Disponibilité
Un véhicule en panne, en maintenance ou déjà engagé dans un voyage incompatible ne doit pas être proposé comme véhicule disponible pour une nouvelle programmation.
RM-15 — Tarification
Le calcul de la recette repose sur le nombre de passagers et le tarif applicable au trajet.
RM-16 — Bus VIP
Les bus VIP peuvent appliquer une tarification différente des bus standards.
RM-17 — Transit
Les informations relatives aux passagers en transit doivent permettre de déterminer l'origine du mouvement afin de rattacher correctement la recette à l'agence concernée.
10. Cycle de vie d'un voyage
Le cycle général d'un voyage est :
Planifié →Programmé →En attente de départ →Départ de l'agence →
En voyage →Arrivée dans une agence intermédiaire →Opérations passagers / transit→Départ de l'agence intermédiaire →Agence suivante (répétition pour chaque étape) →Arrivée à destination finale →Voyage terminé →
Évaluation de l'état du véhicule →Disponible / Maintenance / Immobilisé / autre statut approprié
11. Cycle de vie d'un véhicule
Le système devra pouvoir représenter les principaux états suivants :
Disponible → Programmé → En voyage → Arrivée → Disponible
Ou
Disponible → En panne → En maintenance → Réparation terminée → Disponible
Un véhicule peut également être affecté temporairement à une opération de secours.
12. Gestion des événements opérationnels
Le système devra conserver les principaux événements liés à un voyage, notamment :
•	Programmation ;
•	Départ ; arrivée ; passage dans une agence ;
•	Montée et descente de passagers ;
•	Changement d'état ;
•	Incident ; affectation d'un véhicule de secours ; départ du véhicule de secours ; résolution d'un incident ;
•	Arrivée finale ; fin du voyage.
Cette approche permettra de reconstituer l'historique complet d'un voyage.




13. Exigences non fonctionnelles
13.1. Facilité d'utilisation
L'application devra être simple à utiliser, adaptée aux utilisateurs non techniques, organisée par modules, et utilisable sur ordinateur et sur les terminaux mobiles via une interface web responsive.
13.2. Performance
Les opérations courantes (consultation des véhicules et des voyages, enregistrement d'un départ ou d'une arrivée, mise à jour des passagers, consultation des tableaux de bord) devront s'exécuter dans des délais raisonnables — à titre indicatif, moins de 2 secondes pour un affichage de liste ou de tableau de bord en usage normal. Les seuils précis seront confirmés après les premiers tests de charge.
13.3. Disponibilité
Le système devra être suffisamment disponible pour accompagner les opérations quotidiennes des agences. Les modalités précises d'hébergement et de disponibilité seront définies lors de la conception technique.
13.4. Évolutivité
L'application devra être conçue de manière à permettre l'ajout ultérieur de nouvelles fonctionnalités sans remise en cause majeure de l'existant — notamment grâce à une organisation modulaire du code (voir section 16).
14. Sécurité
Le système devra prévoir :
•	Authentification des utilisateurs ;
•	Gestion des rôles et des permissions ;
•	Contrôle d'accès aux fonctionnalités ;
•	Protection des sessions ;
•	Stockage sécurisé des mots de passe (hachage, jamais en clair) ;
•	Journalisation des actions sensibles ;
•	Validation des données saisies ;
•	Sauvegarde des données.



15. Architecture technique
15.1. Principe
Pour cette V1, l'application est conçue comme un monolithe modulaire : une seule application backend, organisée en modules internes par domaine métier (voir section 16), et non en microservices. Ce choix est délibéré :
•	Un seul dépôt de code et un seul déploiement à gérer, adapté à une petite équipe de développement ;
•	Une seule base de données, ce qui évite les problèmes de cohérence entre services ;
•	Aucune infrastructure de communication inter-services (bus de messages, service discovery) à mettre en place ;
•	Une évolution vers une architecture distribuée reste possible plus tard si le volume d'activité le justifie, sans remettre en cause le modèle de données ni les règles métier.
Redis (cache) et Docker (conteneurisation) sont conservés dans cette V1 : ils n'ajoutent pas de complexité de conception (contrairement aux microservices), mais permettent d'appliquer dès ce projet des pratiques professionnelles utiles — mise en cache des données consultées fréquemment (référentiels, indicateurs de tableaux de bord) et packaging reproductible de l'application.
15.2. Stack technique
Couche	Technologie	Rôle
Frontend	Angular	Interface web : formulaires de saisie, écrans de gestion et tableaux de bord.
Backend	Spring Boot (monolithe modulaire)	API REST et logique métier, organisée en modules par domaine (voir section 16).
Langage backend	Java 21 (LTS)	Langage principal du backend.
Base de données	Postgresql	Stockage des données métier (référentiels, voyages, recettes, audit…).
ORM	Spring Data JPA / Hibernate	Persistance des entités et accès aux données.
Cache	Redis	Cache des données fréquemment consultées (référentiels, indicateurs de tableaux de bord) et gestion des sessions.
Sécurité	Spring Security + JWT	Authentification et autorisation par rôle.
Documentation API	Openapi / Swagger	Documentation et test interactif des endpoints.
Reporting	Apache POI (Excel) + openpdf (PDF)	Génération des rapports exportables, sans dépendre d'un moteur de templates lourd comme jasperreports.
Conteneurisation	Docker + Docker Compose	Packaging de l'api, de postgresql et de Redis dans des conteneurs séparés, pour un déploiement reproductible.
Reverse proxy	Nginx	Exposition du frontend et de l'api, terminaison HTTPS.
Versionnement	Git + github/gitlab	Gestion du code source et historique des modifications.
Tests backend	Junit 5 + Mockito	Tests unitaires des services et règles métier.
Tests API	Postman / Newman	Tests d'intégration des endpoints.
CI/CD (ultérieur)	Github Actions	Automatisation des tests et du déploiement, à mettre en place après la V1.
Exposition Internet	Cloudflare Tunnel (cloudflared)	Rend l'application accessible depuis l'extérieur du réseau local (agences distantes, téléphones) sans ouvrir de port sur le routeur du bureau (voir section 24).
15.3. Vue d'ensemble de l'architecture
UTILISATEURS
Direction, Chef d'agence, Communication, Assistant
▼
FRONTEND WEB — Angular
HTTPS / REST
▼
API — SPRING BOOT (monolithe modulaire)
Spring Security / JWT
Référentiel (Agences, Lignes)	Parc automobile (Véhicules, Propriétaires)	RH opérationnelles (Chauffeurs)
Exploitation (Planif., Programmation, Voyages)	Passagers / Transit	Finances (Tarifs, Recettes)
Incidents & Secours	Maintenance	Reporting, Audit, Administration
▼
Postgresql
Données métier persistantes
	Redis
Cache & sessions

▼
DOCKER — conteneurs (API, postgresql, Redis) déployés derrière Nginx

16. Organisation du code backend
Le backend Spring Boot est organisé par module métier (« package by feature ») plutôt que par couche technique globale, afin de garder chaque domaine autonome et facile à faire évoluer.
16.1. Organisation interne d'un module
Exemple pour le module Voyage :
Voyage/
├── controller/
│   └── voyagecontroller.java
├── service/
│   ├── voyageservice.java
│   └── voyageserviceimpl.java
├── repository/
│   └── voyagerepository.java
├── entity/
│   └── Voyage.java
├── dto/
│   ├── voyagerequest.java
│   └── voyageresponse.java
├── mapper/
│   └── voyagemapper.java
├── exception/
│   └── voyageexception.java
└── enums/
    └── statutvoyage.java

16.2. Organisation générale des modules
Avec les 13 modules fonctionnels validés (section 8), le backend est organisé ainsi :
Danay-express-backend/
├── 01-referentiel/        (Agences, Villes, Lignes, Itinéraires)
├── 02-parc-automobile/    (Véhicules, Propriétaires)
├── 03-ressources-operationnelles/  (Chauffeurs, Affectations)
├── 04-exploitation/      (Planification, Programmation, Voyages)
├── 05-passagers/         (Transit)
├── 06-finances/         (Tarifs, Recettes)
├── 07-incidents/        (Incidents, Secours)
├── 08-maintenance/      (Interventions)
├── 09-reporting/
├── 10-audit/
└── 11-administration/     (Utilisateurs, Rôles, Permissions)
Chaque module reste indépendant dans son organisation interne (section 16.1), ce qui permettra, si besoin dans une version future, de l'extraire plus facilement en service séparé.
17. Base de données — entités principales et diagramme de classes
La conception devra notamment prendre en compte les entités suivantes :
Référentiel
•	Agence ; Ville ; Ligne ; Sens ; étapeitinéraire ; Propriétaire ; Véhicule ; Chauffeur.
Exploitation
•	Affectation ; Planification ; Voyage ; événementvoyage ; Passager/Transit ; Tarif ; Recette.
Incidents
•	Incident ; véhiculesecours ; interventionsecours.
Maintenance
•	Maintenance ; interventionmaintenance.
Administration
•	Utilisateur ; Rôle ; Permission ; journalaudit.
Cette liste constitue une base de conception et pourra être ajustée pendant la modélisation détaillée.
17.1. Diagramme de classes

17.2. Diagramme de cas d’utilisation


17.3. Diagramme de séquence



17.4. Diagramme d’activité


18. Interfaces principales envisagées
L'application pourra être organisée autour des écrans suivants :
Tableau de bord
•	Indicateurs ; voyages en cours ; véhicules disponibles ; incidents ; prochains départs ; dernières arrivées.
Agences
•	Liste ; création ; modification ; détails ; lignes desservies.
Véhicules
•	Liste ; disponibilité ; détails ; propriétaire ; historique.
Chauffeurs
•	Liste ; disponibilité ; affectations ; historique.
Planification
•	Calendrier/planning ; besoins ; voyages planifiés.
Programmation
•	Choix de l'agence, du véhicule, du voyage ; affectation des chauffeurs ; validation.
Suivi des voyages
•	Voyages programmés/en cours ; historique ; détail du voyage ; timeline des événements.
Passagers / Transit
•	Effectif au départ ; mouvements ; transit ; historique.
Recettes
•	Tarifs ; recettes ; statistiques.
Incidents
•	Incidents actifs ; déclaration ; secours ; résolution.
Maintenance
•	Véhicules en panne ; interventions ; historique.
Reporting
•	Rapports ; filtres ; export.
Administration
•	Utilisateurs ; rôles ; permissions ; audit.
19. Tableaux de bord — indicateurs principaux
Indicateurs opérationnels
•	Nombre total de véhicules ; disponibles ; programmés ; en voyage ; en panne ; en maintenance ;
•	Voyages programmés, en cours, terminés ; incidents actifs.
Indicateurs d'activité
•	Nombre de voyages par période ; nombre de passagers et de mouvements ;
•	Fréquentation par ligne et par agence.
Indicateurs financiers
•	Recette du jour et de la période ; recette par agence, véhicule, ligne, destination ; recette Standard/VIP.
Indicateurs de performance
•	Utilisation du parc ; disponibilité des véhicules ; nombre de pannes et d'incidents ; performance des agences.
Les indicateurs définitifs seront validés avec la direction après observation des besoins réels.
20. Gestion des notifications
Dans la V1, les groupes whatsapp et les appels téléphoniques restent des canaux de communication opérationnelle entre les agences et les responsables. L'application sert de système central de saisie, de suivi et d'historisation. Les notifications automatiques pourront être envisagées ultérieurement.
21. Importation des données initiales
Avant la mise en production, les données existantes (agences, véhicules, propriétaires, chauffeurs) devront être intégrées.
Processus
1.	Réception du fichier source
2.	Contrôle de la structure
3.	Nettoyage éventuel
4.	Vérification des doublons
5.	Correspondance avec les champs de l'application
6.	Importation
7.	Contrôle après importation
Les règles précises d'importation seront définies lorsque le fichier source sera analysé.
22. Sauvegarde et restauration
Le système devra prévoir :
•	Sauvegarde régulière de la base de données ;
•	Conservation de plusieurs sauvegardes ;
•	Possibilité de restauration ;
•	Procédure de récupération en cas de panne.
La fréquence et la politique de conservation seront définies selon l'infrastructure disponible.
23. Tests et validation
Tests fonctionnels
Vérification des fonctionnalités de chaque module.
Tests d'intégration
•	Agences et itinéraires ;
•	Véhicules et voyages ;
•	Chauffeurs et affectations ;
•	Voyages et passagers ;
•	Voyages et recettes ;
•	Incidents et véhicules de secours ;
•	Maintenance et disponibilité des véhicules.
Tests de sécurité
•	Authentification ; autorisation ; permissions ; accès aux données ; journalisation.
Tests utilisateurs
Les principaux utilisateurs devront participer à la validation : Direction de l'exploitation, Chef de service de communication, Assistant, Chefs d'agence.
24. Déploiement
24.1. Infrastructure locale
L'application est déployée sur un serveur local de Danay Express, sous forme de conteneurs Docker (API, postgresql, Redis) décrits en section 15, derrière Nginx. Ce serveur héberge déjà d'autres applications, actuellement accessibles uniquement depuis le réseau local du bureau.
24.2. Accès distant des agences et des téléphones
Les agences étant réparties dans plusieurs villes (Yagoua, Kalfou, Guidiguis, Kaélé, Figuil, Garoua…), les chefs d'agence doivent pouvoir accéder à l'application depuis leur téléphone, en dehors du réseau local du bureau. Le serveur n'étant pas exposé publiquement (pas d'ouverture de port sur le routeur), l'accès distant est assuré par Cloudflare Tunnel :
•	Un agent léger (cloudflared) est installé sur le serveur local et établit une connexion sortante chiffrée vers le réseau Cloudflare, sans qu'aucun port entrant ne soit ouvert sur le routeur du bureau ;
•	L'application Danay Express est publiée sur un sous-domaine dédié (par exemple suivi.danayexpress.cm), routé en interne vers Nginx puis vers l'api ;
•	Les autres applications déjà présentes sur le serveur restent inchangées et peuvent, si besoin, être publiées chacune sur leur propre sous-domaine, sans conflit entre elles ;
•	Côté utilisateur, l'accès se fait simplement en ouvrant l'url depuis le navigateur du téléphone — aucune application ni configuration réseau supplémentaire à installer ;
•	L'accès reste protégé par l'authentification et les permissions du système (section 14), la connexion étant chiffrée en HTTPS de bout en bout.
Cette solution nécessite l'acquisition d'un nom de domaine et un compte Cloudflare (offre gratuite suffisante pour ce besoin), mais ne modifie pas l'architecture applicative définie en section 15.
24.3. Autres modalités
Les autres modalités seront définies ultérieurement selon les ressources disponibles chez Danay Express : caractéristiques du serveur, connexion Internet, politique de sauvegarde, sécurité réseau.
25. Phasage proposé
Phase 1 — Analyse et conception
•	Validation du cahier des charges ; analyse détaillée des processus ; modélisation des données ; conception de l'architecture et des interfaces ; définition des rôles et permissions.
Phase 2 — Mise en place des référentiels
•	Agences ; lignes ; itinéraires ; véhicules ; propriétaires ; chauffeurs.
Phase 3 — Exploitation
•	Planification ; programmation ; voyages ; départs ; arrivées ; passagers ; transit ; recettes.
Phase 4 — Gestion des incidents
•	Incidents ; véhicules de secours ; maintenance.
Phase 5 — Supervision
•	Tableaux de bord ; reporting ; audit.
Phase 6 — Tests et mise en production
•	Tests ; correction ; importation des données ; formation ; déploiement ; accompagnement initial.
26. Évolutions futures
Le système devra être conçu pour pouvoir évoluer. Les fonctionnalités susceptibles d'être étudiées dans les versions ultérieures comprennent notamment :
•	Géolocalisation GPS des véhicules ;
•	Notifications automatisées ;
•	Intégration avec whatsapp ou d'autres canaux ;
•	Application mobile dédiée ;
•	Intégration avec le système de billetterie ;
•	Automatisation avancée des recettes ;
•	Statistiques avancées et prévision des temps de trajet ;
•	Analyse avancée des performances ;
•	Extraction éventuelle de certains modules en services séparés si le volume d'activité le justifie ;
•	Gestion d'autres activités de Danay Express.
Ces fonctionnalités ne font pas partie du périmètre obligatoire de la V1.
27. Critères généraux de réussite
La solution sera considérée comme fonctionnellement réussie lorsqu'elle permettra notamment :
8.	De retrouver les agences et leur organisation
9.	De connaître les véhicules et leur état
10.	De connaître les véhicules disponibles par agence
11.	De consulter les chauffeurs et leurs affectations
12.	De planifier les besoins futurs
13.	De programmer les véhicules
14.	De suivre un voyage depuis son départ jusqu'à sa destination finale
15.	De connaître la dernière agence déclarée pour un véhicule en voyage
16.	De suivre l'évolution des passagers
17.	De calculer les recettes selon les règles définies
18.	De déclarer et suivre une panne
19.	D'identifier un véhicule de secours disponible
20.	De suivre les opérations de maintenance
21.	De consulter les indicateurs d'exploitation
22.	De produire des rapports
23.	De retracer les actions des utilisateurs
24.	De garantir un accès sécurisé aux différentes fonctionnalités
28. Éléments restant à préciser
La présente version constitue une V1 du cahier des charges. Certains éléments devront être précisés progressivement avec Danay Express, notamment :
•	Liste définitive des rôles et responsabilités ;
•	Liste exacte des agences de départ et des lignes/corridors ;
•	Organisation exacte des sens de circulation ;
•	Distances et durées moyennes entre agences ;
•	Règles tarifaires et de calcul des recettes détaillées ;
•	Règles détaillées du transit ;
•	Informations techniques des véhicules ;
•	Règles précises d'affectation des chauffeurs ;
•	Processus détaillé de maintenance ;
•	Niveaux de priorité des incidents ;
•	Infrastructure de déploiement et politique de sauvegarde ;
•	Modalités d'intégration avec les systèmes existants.
Ces éléments pourront être intégrés dans les versions ultérieures du cahier des charges sans remettre en cause le périmètre général défini dans cette V1.
29. Conclusion
Le système de suivi des véhicules, trajets et opérations de transport de Danay Express a pour objectif de centraliser et de structurer les informations actuellement échangées entre les agences, la direction de l'exploitation et le service de communication.
La solution devra permettre de suivre l'ensemble du cycle opérationnel, depuis la planification d'un voyage jusqu'à son arrivée à destination finale, tout en prenant en compte les agences intermédiaires, les mouvements de passagers, les recettes, les incidents, les véhicules de secours et la maintenance.
La solution devra également fournir à la direction une vision synthétique de l'exploitation grâce aux tableaux de bord et au reporting.
Cette première version constitue une base fonctionnelle évolutive, avec une architecture volontairement simple (monolithe modulaire) pour rester maîtrisable par une petite équipe, tout en intégrant des pratiques professionnelles (cache, conteneurisation, tests, audit) qui accompagneront la montée en compétence du porteur du projet. Elle sera enrichie progressivement à partir des observations du fonctionnement quotidien des agences, des échanges avec les responsables et de la validation des règles métier encore à préciser.