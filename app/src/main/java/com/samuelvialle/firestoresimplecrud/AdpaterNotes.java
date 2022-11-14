package com.samuelvialle.firestoresimplecrud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdpaterNotes extends RecyclerView.Adapter<AdpaterNotes.notesViewHolder> {

    private ShowNotesActivity activity; // Création d'une variable du type de la classe qui recevra ce recyclerView pour le contexte
    private List<ModelNotes> listNotes;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public AdpaterNotes(ShowNotesActivity activity, List<ModelNotes> listNotes) {
        this.activity = activity;
        this.listNotes = listNotes;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_note, parent, false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        holder.title.setText(listNotes.get(position).getTitle());
        holder.desc.setText(listNotes.get(position).getDesc());
    }

    // Ajout de l'update
    public void updateData(int position){
        Log.i("TAG", "Entering updateData");
        // En fonction de la ou l'on déclenche l'update, on récupère la position
        ModelNotes item = listNotes.get(position);
        // On crée un bundle avec les données de la ligne
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uTitle", item.getTitle());
        bundle.putString("uDesc", item.getDesc());
        Log.i("TAG", "updateData: " + item.getId() +" "+ item.getTitle() );
        // On crée un Intent pour nous mener vers la page des modifications
        Intent intent = new Intent(activity, MainActivity.class);
        // On ajoute les données comprises dans le bundle
        intent.putExtras(bundle);
        // On lance le tout depuis le context
        activity.startActivity(intent);
    }

    // Delete
    public void deleteData(int position){
        ModelNotes item = listNotes.get(position);
        db.collection("Notes").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        listNotes.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public static class notesViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvShowTitle);
            desc = itemView.findViewById(R.id.tvShowDesc);
        }
    }
}
