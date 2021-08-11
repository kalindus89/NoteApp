package com.noteapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Random;

public class FirebaseNoteAdapter extends FirestoreRecyclerAdapter<FirebaseModel,FirebaseNoteAdapter.NoteViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FirebaseNoteAdapter(@NonNull FirestoreRecyclerOptions<FirebaseModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FirebaseModel model) {

        holder.mTitle.setText(model.getTitle());
        holder.noteContent.setText(model.getContent());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.noteCard.setBackgroundColor(color);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_layout,parent,false);
        return new NoteViewHolder(view);
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
       private TextView mTitle,noteContent;
        CardView noteCard;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle =itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.noteContent);
            noteCard = itemView.findViewById(R.id.noteCard);
        }
    }
}
