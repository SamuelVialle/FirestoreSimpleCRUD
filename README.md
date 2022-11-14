# APP expliquant les opérartions de bases de connexion à la base de données Firestore

## 1 Connecter l'application à Cloud Firestore

Création ou sélection du projet en utilisant l'onglet Tools > Firebase > Cloud Firestore (1)
Association du projet avec Android Studio Ajout du SDK Firestore Cloud (2)
Activation de Cloud Firestore dans la console (choisir le mode test car l'app n'a pas
d'authentification)

## 2 C : Create data and store it to Firestore

- Design d'activity_main -> le formulaire d'ajout de note + 1 bouton save + 1 bouton shaw all note
- Code ActivityMain :
    - Liaison des widgets avec le code
    - Liaison avec la db
    - Gestion du bouton Save

## 2 R : Read data from Firestore

- Design d'activity_show_notes -> Ajouter un RecyclerView
- Code ShowNotesActivity :
- Liaison des widgets avec le code
- Liaison avec la db
- Création du layout de l'item du recyclerView
- Ajout de l'adapter
- Ajout du Model
- Association au recycler

## 3 U : Update data to existing ones

- Ajout de la gestion du swipe to update
- 



