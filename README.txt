-- READ ME --

Application multitiers - Mysticat

Prérequis
---------

 - Avoir Git sur la machine, url: https://git-scm.com/downloads

 - Avoir Java d'installé sur la machine, url: https://java.com/fr/download/

 - Avoir NodeJs d'installé sur la machine, url: https://nodejs.org/en/download/

 - Avoir STS (Spring Tool Suite), url: https://spring.io/tools/sts/all

 - Avoir une instance d'un serveur AWS (seulement pour l'installation serveur)

 - Avoir Maven d'installé sur la machine, url: https://maven.apache.org/download.cgi

Première étape
--------------

 - Forker le repo du projet.
   url: https://bitbucket.org/AL-CCG/projet_multitiers 

 - Cloner le projet sur votre instance (git clone)


Première manière d'éxécuter le programme (local)
------------------------------------------------

 - S'assurer que le port dans le fichier application.properties et dans Constantes.js
   doivent être le même 

 - Pour lancer le serveur d'API, faire un run sur le fichier ProjetMultitiersApplication.java
   dans le package com.multitiers du dossier source src/main/java

 - Avant de lancer le service web, ouvir un terminal à la racine du projet et exécuter npm i
 
 - Pour lancer le service web, exécuter npm start

 - Ouvrir un navigateur

 - Aller sur l'addresse suivante : localhost:3000


Deuxième manière d'éxécuter le programme (local)
------------------------------------------------

 - Dans un terminal à la racine du projet, éxécutez npm run build

 - Copiez les fichiers du dossier build vers le dossier src/main/resources/static

 - Pour lancer le serveur d'API, faire un run sur le fichier ProjetMultitiersApplication.java
   dans le package com.multitiers du dossier source src/main/java

 - Ouvrir un navigateur

 - Aller sur l'addresse suivante : localhost avec le port écrit dans le fichier 
   application.properties


Troisième manière d'éxécuter le programme (serveur)
---------------------------------------------------

 - Répéter les étapes de la deuxième manière sauf pour le démarrage de l'application

 - Dans un terminal à la racine du projet, éxécutez mvn clean package

 - Envoyer le fichier .jar généré du projet (par la commande précédente) se trouvant dans le
   dossier target du projet avec par exemple, une commande comme scp.

 - Se connecter sur son instance AWS

 - Éxécuter le fichier .jar du projet avec la commande: sudo nohup java -jar <nom du fichier .jar> & 

 - Ouvrir un navigateur

 - Aller sur l'addresse suivante : <addresse AWS> avec le port écrit dans le fichier 
   application.properties lors du packaging







