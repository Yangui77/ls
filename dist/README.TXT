========================
BUILD OUTPUT DESCRIPTION
========================

Projet ls

Ce projet est une version alternative de ls codé en JAVA. 
    - Liste des fichiers et dossiers dans un dossier
    - Options permettant d'augmenter les informations affichées

Options supplémentaires
* ``-a`` : Inclut les fichiers et dossiers cachés
* ``-R`` : Recherche récursive, descend dans les dossiers
* ``-l`` : Affiche la taille des fichiers
* ``-c`` : Indique le nombre de lignes des fichiers
* ``-d`` : N'affiche que les dossiers et le nombre de fichiers contenus
* ``-r`` : Inverser l'ordre d'affichage

Commandes : 
    "ls" affiche les sous-dossiers ainsi que les fichiers du répertoire.
    "ls -<Options>" permet d'afficher les fichiers/dossiers selon les options.
    Pour utiliser plusieurs option o on utilisera "ls -oo",
    ls -o -o ne fonctionnera pas.
    L'option R ne peut pas être utilisé avec d'autres options.