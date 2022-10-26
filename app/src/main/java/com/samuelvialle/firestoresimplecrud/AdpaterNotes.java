package com.samuelvialle.firestoresimplecrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdpaterNotes extends RecyclerView.Adapter<AdpaterNotes.notesViewHolder> {

    private ShowNotesActivity activity; // Création d'une variable du type de la classe qui recevra ce recyclerView pour le contexte
    private List<ModelNotes> listNotes;

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
