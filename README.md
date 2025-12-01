# CQRS & Event Sourcing

Ce projet est une application **Spring Boot** qui implimente l’architecture **CQRS (Command Query Responsibility Segregation)** et **Event Sourcing** pour la gestion de comptes bancaires simples (création de compte, crédit, débit).

Les commandes et les événements sont gérés avec **Axon Framework**, les données sont stockées dans une base **PostgreSQL** (et **H2** en mémoire pour les tests), et l’API REST est documentée avec **Swagger**.

## Architecture

- **Layer Commandes** : reçoit les requêtes d’écriture (par ex. `POST /commands/accounts/add` pour créer un compte).
- **Aggregates Axon** : appliquent les règles métier et publient des événements (compte créé, compte crédité, compte débité…).
- **Event Store** : persiste la suite d’événements, qui représente la source de vérité.
- **Layer Requêtes (Query)** : lit les vues matérialisées (read models) pour exposer l’état actuel des comptes.

