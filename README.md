# Projet JEE 2023-2024

## Table des matières

1. [Installation](#Installation)
2. [Utilisation](#utilisation)
3. [Connexion](#connexion)
4. [Dépendances](#dépendances)
5. [Contribution](#contribution)


## Installation

#### Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre système :

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) - Version 8 ou supérieure
- [Eclipse](https://www.eclipse.org/) - Environnement de développement intégré (IDE) pour la programmation
- [Git](https://git-scm.com/downloads) - Système de contrôle de version (facultatif, mais recommandé pour cloner le référentiel)

#### Étapes d'Installation
   Commencez par cloner le référentiel depuis GitHub en utilisant Git (ou téléchargez le code source au format ZIP et extrayez-le) :

  Avec la commande git dans le terminal: git clone https://github.com/TiffanyGB/Projet_J2E.git

  Placez vous dans votre nouveau dossier:
  cd votre/dossier

  Ouvrez Eclipse

  Importer votre dossier

  Une fois cela fait, ouvrez le fichier:
  src/main/ressources/application.properties

  A la ligne 4, remplacer '/home/cytech/Cours/Projet_JEE'
  par le chemin absolu de votre dossier.

  Attention, il faut veiller à bien avoir '/Bdd' à la fin du chemin.

  Enregistrez le fichier et ouvrez:
  src/main/java/projetJEE/ProjetEE/ProjetEEApplication.java

  Appuyez sur RUN à partir de ce fichier.

  L'application est lancée.

  Vous pouvez accéder au site avec l'url: `http://localhost:8080/`

## Utilisation

Une fois sur la page d'accueil, vous pouvez naviguer sur le site avec trois profils différents.

- Visiteur (Non connecté)
- Compte client

- Compte administrateur

1) Visiteur non connecté
 
- Voir les catégories et les voyages du site

- Faire une recherche d'un voyage

- Doit se connecter pour réserver

2) Compte client

- Il peut réserver des voyages

- Accès au profil, où il peut supprimer son compte, modifier ses informations et voir ses réservations.

- Peut annuler une réservation

3) Compte administrateur

- Ajouter, modifier et supprimer une catégorie

- Ajouter, modfier et supprimer un voyage

- Voir la liste des voyages ayant une réservations

- Voir toutes réservations d'un voyage

- Annuler des réservations

## Connexion

- Administrateur: 

email: admin@admin.fr

Mot de passe: admin2023

- Client:

email: client@client.fr

Mot de passe : client2023
 
Via l'inscription également


## Dépendances
- [Spring Boot Starter Data JPA](https://spring.io/guides/gs/accessing-data-jpa/) - Starter pour l'accès aux données avec JPA (Java Persistence API).
  - **Description** : Utilisé pour interagir avec la base de données.

- [Spring Boot Starter Thymeleaf](https://spring.io/guides/gs/serving-web-content/) - Starter pour créer des vues web avec Thymeleaf.
  - **Description** : Thymeleaf est utilisé pour créer des pages web dynamiques.

- [Spring Security Crypto](https://spring.io/guides/gs/securing-web/) - Dépendance de sécurité pour Spring Boot.
  - **Description** : Utilisé pour gérer la sécurité de l'application.

- [JJWT (Java JWT: JSON Web Tokens)](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt) - Bibliothèque pour la gestion des tokens JWT.
  - **Description** : Utilisé pour la gestion de l'authentification et de l'autorisation.

- [JAXB API](https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api) - API pour le traitement XML.
  - **Description** : Utilisé pour le traitement des données XML.

- [H2 Database](https://www.h2database.com/html/main.html) - Base de données en mémoire.
  - **Description** : Base de données utilisée pour le développement et les tests.

- [MySQL Connector](https://dev.mysql.com/downloads/connector/j/) - Pilote JDBC pour MySQL.
  - **Description** : Utilisé pour la connexion à une base de données MySQL en production.

- [Lombok](https://projectlombok.org/) - Bibliothèque pour la réduction du code boilerplate.
  - **Description** : Simplifie la création de classes POJO.

## Autres technologies

- [Bootstrap](https://getbootstrap.com/) - Framework CSS pour la conception de sites web
  - **Description** : Bootstrap est utilisé pour la conception et la mise en page des éléments visuels de l'interface utilisateur de l'application. Il offre des composants prêts à l'emploi pour un design web réactif et moderne.

## Contribution

- **Tiffany GAY-BELLILE**
   : gaybellile@cy-tech.fr

- **Aya ADNINE**
    : adnineaya@cy-tech.fr

- **Kenza ABDELLAOUI MAANE**
  : abdellaoui@cy-tech.fr
