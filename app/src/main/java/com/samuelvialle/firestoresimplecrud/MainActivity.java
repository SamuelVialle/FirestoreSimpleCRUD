package com.samuelvialle.firestoresimplecrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // Var globales des widgets
    private TextView tvNoteState;
    private EditText etNoteTitle, etNoteDescription;
    private Button btnSaveNote, btnShowAllNotes;

    // Var Firebase
    private FirebaseFirestore db;

    // Var pour les datas de l'update
    String uId, uTitle, uDesc;

    // Méthode initUI pour la liaison des widgets dans le code
    private void initUI() {
        tvNoteState = findViewById(R.id.tvNoteState);
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteDescription = findViewById(R.id.etNoteDescription);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        btnShowAllNotes = findViewById(R.id.btnShowAllNotes);
    }

    // Méthode d'initialisation de la db Firestore
    private void initFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    // Méthode de gestion du bouton save du formulaire
    private void saveButton() {
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs du formulaire
                String title = etNoteTitle.getText().toString();
                String desc = etNoteDescription.getText().toString();
                // Id en fonction C ou U
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    String id = uId;
                    updateDataToFirestore(id, title, desc);
                } else {
                    String id = UUID.randomUUID().toString(); // Génération random d'un ID
                    // Appel de la méthode d'enregistrement dans Firestore
                    saveDataToFirestore(id, title, desc);
                }
            }
        });
    }

    // Méthode C : création / ajout de données dans la base
    private void saveDataToFirestore(String id, String title, String desc) {
        if (!title.isEmpty() && !desc.isEmpty()){
            // Création du du tableau qui contiendra les données à envoyer
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("desc", desc);

            // Envoi des data dans la base
            db.collection("Notes").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(MainActivity.this, "Note saved !", Toast.LENGTH_SHORT).show();
                           }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Empty fields is not allow", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour l'update des données dans db
    private void updateDataToFirestore(String id, String title, String desc){
        db.collection("Notes").document(id).update("title", title, "desc", desc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Data updated !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Méthode pour afficher toutes les notes
    private void showAllNotes(){
        btnShowAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowNotesActivity.class));
            }
        });
    }

    // Méthode pour récupérer les infos enregistrées dans le bundle de l'item sélectionné
    private void showDataToUpdate(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // Changement du texte des widgets
            btnSaveNote.setText("Update");
            tvNoteState.setText("Update note");
            // Récupération des données dans le bundle
            uId = bundle.getString("uId");
            uTitle = bundle.getString("uTitle");
            uDesc = bundle.getString("uDesc");
            // Association des données aux widgets
            etNoteTitle.setText(uTitle);
            etNoteDescription.setText(uDesc);
        } else {
            btnSaveNote.setText("Save");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Appel des méthodes d'initialisations
        initUI();
        initFirebase();
        saveButton();
        showAllNotes();
        showDataToUpdate();
    }
}