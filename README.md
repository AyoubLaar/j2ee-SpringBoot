                            PROGRAMMATION WEB/J2EE
                            
                              Rapport du Projet
                        CREATION D'UN SITE MEDECINE


                        

        *Réalisé par:*                                 *Encadré par:*
        Cisse Mariame                                   Pr Mahmoud El Hamlaoui
        Cisse Pape Waly
        Alahyane Nour-Eddine
        Laarouchi Ayoub
        
*SOMMAIRE:*

I. Introduction;

II. Conception:
1. Phase Analyse
1-1.Présentation des acteurs et actions 
2. Language de Modélisation Unifié (UML);
2.1. Diagramme de Cas d'Utilisation
2-2 Diagramme de Classe
   
III. Réalisation:

3. Langages et Environnement de Développement
3-1. Langages Utilisés
3-2. Framework Utilisé
3-3. Bibliothèque Utilisé
3-4. Outils Utilisés 

*Tables Des Figures:*
1. Diagramme de Cas d'Utilisation
2. Diagramme de Classe
3. page d'acceuil
4. Interface signin(medecin et patient)
5. Interface signup(medecin et patient)
6. prise de rendez-vous patient
7. Interface  dashboard(patient, medecin , admin)

*Table Des Tableaux:*
1. Table User
2. Table Medecin
3. Table Patient
4. Table Rendez-vous
5. Table Specialité
6. table médicament
7. table prescription

I. Introduction:

Enterprise Edition (J2EE) est une plateforme qui fournit une architecture pour le développement, la construction et le déploiement d’applications Web robustes, sécurisées et évolutives du site web. En effet, l'utilisation de J2EE dans ce projet permet de faciliter la tâche pour développer la plateforme web de téléconsultation médicale qui à pour objectif de simplifier au patient et au médecin le gain de temps et la distance. 

II. Conception: 

1. Phase d'analyse:
La phase de l'analyse vise à définir et à justifier la solution optimale qui répondra aux exigences du projet et aux besoins identifiés ainsi que les acteurs du système et les tâches associées à chacun.

1-1.Présentation des acteurs et actions:

  ❖Visiteur : 
C’est un individu qui est entrain de fouiller sur le net, cherchant un médecin pour une consultation ou pour avoir une idée sur les prix. Jusqu’à ce stade c’est un utilisateur inconnu donc il n’est pas encore un patient ou un médecin.

  ❖ Le Médecin : 
C’est un visiteur ayant déjà créer un compte sur notre site en tant que médecin, il peut donc suivre le processus de la téléconsultation en toute sécurité sachant que notre système doit être l’unique responsable de la confidentialité des données personnelles et des dossiers médicaux de ses patients.

  ❖ Le Patient : 
C’est un visiteur ayant déjà créer un compte sur notre site en tant que patient, il peut donc suivre le processus de la téléconsultation en toute sécurité sachant que notre système doit être l’unique responsable de la confidentialité des données personnelles et de son dossier médical.

  ❖ L’administrateur : 
Pour les sites web on l’appelle généralement « le webmaster ». C’est celui qui assure le dynamisme du site et veille sur les mises à jour des médecins.

2. Language de Modélisation Unifié (UML):
   
2.1. Diagramme de Cas d'Utilisation

 ![WhatsApp Image 2023-11-26 à 21 40 47_ec10d282](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/ab039dd7-4b74-41df-9e92-befadf401e13)

![Image2](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/0d22cd30-b270-4401-91a1-5e5ce8555848)

2-2 Diagramme de Classe

.......

2.3page d'acceuil
2.4 Interface signin(medecin ,patient,admin)

L'implémentation de linterface Signin permettra aux medecins et patients à se connecter au site medical réaliser pour des différentes opérations. 

![Image4](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/2a543d9a-451a-43c3-9ca4-43656c909d99)

2.5. Interface signup(medecin et patient)

L'implémentation de linterface Signup permettra aux medecins et patients à s'inscrire sur le site médical réaliser pour des différentes opérations. 

![Image5](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/5aee0ded-2bb5-4500-9b24-cca21b393f68)
2.6 prise de rendez-vous

2.7. Interface  dashboard(patient,medecin,admin)
L'implémentation de linterface dashboard permettra aux patients et medecins de réaliser des différentes opérations telle que consulter les demandes de rendez-vous et les rendez-vous à éffectuer .il permettra aussi a l'admin de gérer les patients et les medecins.
   dashboard Admin:
   dashboard patient:
   dashboard Medecin

III. Réalisation:

3. Langages et Environnement de Développement:

   *3.1: Langages utilisés:*

Nous avons utilisés quatres (4) langages essentiels que nous citons ci-dessous:

- Langage JAVASCRIPIT: est un langage de programmation orientée objet de scripts employé dans les pages web interactives afin d’effectuer des contrôles sur les formulaires avant leur validation 
mais aussi il permet l’interaction des objets des pages web.

- Langage Java: Java est utilisé dans une variété de domaines, tels que le développement d'applications Web, d'applications mobiles (avec Android), d'applications d'entreprise, de logiciels embarqués, et plus encore.

- Langage HTML signifie Hyper Text Markup Language :
  - Hyper Text : est un élément textuel (ou pas) au sein d'une page web qui point vers une autre page web. Dans l'acronyme HTML, le H de HyperText correspond à la fonction de création de ces liens.
  - Markup : instruction de styles détaillés insérées dans un document texte destiné à être publié sur le World Wide Web.
- Langage CSS est un sigle qui désigne « Cascading Style Sheets » qui veut dire feuilles de styles en cascade, servent à mettre en forme des documents web, type page HTML ou XML.
  
  *3.2: Framework utilisé:*

Nous avons utilisé (1) Framework pour la réalisation de ce projet qui est:
  - Framework Springboot:
Spring Boot est un framework Java qui facilite le développement d'applications Java basées sur Spring.

   *3.3: Bibliothèque utilisé*
   Nous avons utilisé une (1) bibliothèque pour la réalisation de ce projet qui est: 
  - Bibliothèque Reacti.js:

React.js, souvent appelé simplement React, est une bibliothèque JavaScript open-source pour créer des interfaces utilisateur. Il a été développé par Meta (anciennement Facebook) et permet aux développeurs d’accéder à des composants de code pour créer des interfaces utilisateur interactives et flexibles23.

  *3.4: Les Outils utilisés:*

Nous avons utilisés deux (2) outils essentiels que nous citons ci-dessous:
  - Postman:
Postman est une plateforme de développement d’API qui permet aux développeurs de créer, tester, documenter et partager des Interfaces de programmation d'application (API) de manière efficace.
  - Le Github:
Le GitHub est une plateforme de développement qui facilite la collaboration, la productivité et la sécurité pour les développeurs de tous niveaux et projets.
  
![Image6](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/02fd6422-7324-43d2-b588-59018c8795c0)
![Image7](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/371b32cd-b127-4473-adeb-ac663cb63cb3)
![Image8](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/a656da78-ced0-4a82-bfd2-7e4fc3cde733)
![Image3](https://github.com/AyoubLaar/j2ee-SpringBoot/assets/151503849/b588293f-0638-4061-a000-b60c6364608f)
 

