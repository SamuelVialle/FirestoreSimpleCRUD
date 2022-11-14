package com.samuelvialle.firestoresimplecrud;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowNotesActivity extends AppCompatActivity {

    private static final String TAG = "ShowNotesActivity";
    // Var global des widgets
    RecyclerView rvShowNotes;

    // Var Firebase
    private FirebaseFirestore db;

    // Var de l'adapter
    private AdpaterNotes adapter;
    private List<ModelNotes> listNotes;

    // Méthode initUI pour la liaison des widgets dans le code
    private void initUI() {
        rvShowNotes = findViewById(R.id.rvShowNotes);
        rvShowNotes.hasFixedSize(); // Annonce que le recycler ne changer pas de taille = Gain en mémoire
        rvShowNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    // Méthode d'initialisation de la db Firestore
    private void initFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    // Méthode d'initialisation des composants de l'adapter
    private void initAdapter() {
        listNotes = new ArrayList<>();
        adapter = new AdpaterNotes(this, listNotes);
        rvShowNotes.setAdapter(adapter);
    }

    public void showData() {
        db.collection("Notes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listNotes.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            ModelNotes modelNotes = new ModelNotes
                                    (snapshot.getString("id"),
                                            snapshot.getString("title"),
                                            snapshot.getString("desc"));
                            listNotes.add(modelNotes);
                            String id = snapshot.getString("id");
                            Log.i(TAG, "onComplete: " + id);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShowNotesActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        // Appel des méthodes d'initialisations
        initUI();
        initFirebase();
        initAdapter();
        // Gestion du swipe
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(rvShowNotes);
        // Afficher les données
        showData();
    }
}