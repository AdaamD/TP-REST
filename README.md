# Projet de Recherche et Réservation de Chambres d'Hôtel

## Table des matières

- [Introduction](#introduction)
- [Structure du Projet](#structure-du-projet)
- [Fonctionnalités du Projet](#fonctionnalités-du-projet)
- [Configuration requise](#configuration-requise)
- [Utilisation](#utilisation)
- [Auteur](#auteur)

## Introduction

Ce projet Java propose un système de recherche et de réservation de chambres d'hôtel, incluant une fonctionnalité permettant de passer par une agence pour effectuer des recherches dans des hôtels partenaires, faire des réservations de chambres d'hôtel, ainsi qu'un comparateur permettant de comparer différentes offres. Le projet est structuré en deux parties distinctes : la partie back-end et la partie front-end.

## Structure du Projet

La conception du projet a été orientée vers une séparation claire entre la partie back-end et front-end, adoptant ainsi un modèle de conception MVC (Modèle-Vue-Contrôleur) pour garantir une structure bien définie et une gestion efficace des responsabilités.

### Partie Back-End

La partie back-end du projet, organisée dans le répertoire `Rest`, est subdivisée en plusieurs packages afin de maintenir une structure claire et modulaire.

#### Packages

- **Config:** Gère la configuration web nécessaire au bon fonctionnement du projet, assurant une initialisation adéquate des composants.
- **Controller:** Contient les classes responsables des appels aux services web, facilitant ainsi la communication avec le front-end. Cette couche agit comme un intermédiaire entre les requêtes utilisateur et la logique métier.
- **Data:** Comprend une classe d'initialisation de données, garantissant une base de données préremplie pour des tests cohérents.
- **Exceptions:** Gère les exceptions pour renforcer la robustesse du système.
- **Models:** Contient les entités de base qui décrivent la structure des données du projet.
- **Repository:** Regroupe les classes responsables de l'interaction avec la base de données.
- **Service:** Contient la logique métier des contrôleurs, assurant une séparation claire entre les responsabilités de gestion de données et de traitement métier.

### Partie Front-End

Du côté du front-end, le répertoire `resources` comprend les éléments suivants :

#### Ressources

- **Img:** Stocke les images des chambres.
- **Static/Web:** Contient les fichiers HTML du comparateur, de la consultation des disponibilités (ConsulteDispo), de la réservation, ainsi qu'un fichier CSS pour le style.

## Fonctionnalités du Projet

1. **Recherche de Chambre d'Hôtel par Agence**
    - Les utilisateurs peuvent rechercher des chambres d'hôtel disponibles auprès d'une agence spécifique.

2. **Critères de Recherche Personnalisés**
    - Les utilisateurs peuvent spécifier des critères tels que la date de début du séjour, le nombre de lits, etc., pour affiner leur recherche.

3. **Réservation de Chambre**
    - Les utilisateurs peuvent réserver une chambre en fournissant la date de début et de fin du séjour, ainsi que les informations du client.

4. **Comparateur d'Hôtels**
    - Les utilisateurs peuvent utiliser la fonction de comparaison pour trouver des hôtels disponibles en fonction de critères tels que la date de début et de fin du séjour, le nombre minimal de lits, et le nombre minimal d'étoiles de l'hôtel.

## Configuration requise

- **Java 8:** Assurez-vous d'avoir Java 8 installé sur votre système.
- **Compiler 1.8:** Utilisez un compilateur compatible avec Java 8.
- **IntelliJ:** Compilez et exécutez le projet en utilisant IntelliJ IDEA. Cliquez sur la flèche verte ("Run") dans l'IDE pour lancer le projet.

## Utilisation

Suivez ces étapes pour exécuter le projet sur votre machine :

1. Téléchargez et décompressez le projet TP3.
2. Ouvrez le projet dans l'IDE de votre choix.
3. Construisez le projet en utilisant l'option 'BUILD Project'.
4. Exécutez la classe `RestV2Application`.
5. Ouvrez **ConsulteDispo** et renseignez les informations souhaitées.
6. Possibilité de basculer directement vers le comparateur d'offres via le bouton *Comparateur*.

**Remarque:** Assurez-vous d'avoir les dépendances nécessaires installées et configurées correctement, comme la base de données, avant d'exécuter le projet.


## Auteur

Ce projet a été réalisé par :

- DAIA Adam
- DAFAOUI Mohammed

Étudiants en M1 Génie Logiciel à l'Université de Montpellier

