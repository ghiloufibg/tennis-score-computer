# Tennis Score Computer

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Test Coverage](https://img.shields.io/badge/Coverage-JaCoCo-green.svg)
![Build](https://img.shields.io/badge/Build-Maven-blue.svg)

## Description

Ce projet implémente un **kata de calcul de score de tennis** suivant les règles officielles du tennis. Il s'agit d'un exercice de programmation qui simule le système de comptage des points dans un jeu de tennis, incluant la gestion des états particuliers comme l'égalité (deuce) et l'avantage.

L'application permet de suivre le score d'un match point par point et d'afficher le résultat en temps réel.

## Architecture

Ce projet suit les principes du **Domain-Driven Design (DDD)** avec une architecture en couches claire.

![Architecture Diagram](diagram/Score%20Computer-Tennis%20Score%20Computer%20-%20Clean%20Architecture.png)

## Commandes Maven

```bash
# Compilation du projet
mvn compile

# Exécution des tests
mvn test

# Génération du rapport de couverture
mvn jacoco:report

# Exécution de l'application
mvn exec:java

# Formatage du code
mvn spotless:apply
```

## Fonctionnalités

- ✅ Calcul automatique du score selon les règles du tennis
- ✅ Gestion des états spéciaux (égalité, avantage)
- ✅ Affichage en temps réel du score
- ✅ Architecture basée sur les principes DDD
- ✅ Couverture de tests avec JaCoCo
- ✅ Formatage automatique du code avec Spotless

## Règles du tennis implémentées

- **Score standard** : 0, 15, 30, 40
- **Égalité (Deuce)** : Quand les deux joueurs atteignent 40
- **Avantage** : Un joueur doit marquer deux points consécutifs après l'égalité
- **Victoire** : Le premier joueur à gagner le jeu après avoir eu l'avantage

## Technologies utilisées

- **Java 21** : Utilisation des Records et du Pattern Matching
- **JUnit 5** : Framework de tests
- **JaCoCo** : Couverture de code
- **Maven** : Gestion des dépendances et build
- **Spotless** : Formatage automatique du code