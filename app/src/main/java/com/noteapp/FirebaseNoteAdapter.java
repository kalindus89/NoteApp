package com.noteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.noteapp.note_activities.EditNoteActivity;
import com.noteapp.note_activities.NoteShowDetails;

import java.util.Random;

public class FirebaseNoteAdapter extends FirestoreRecyclerAdapter<FirebaseModel,FirebaseNoteAdapter.NoteViewHolder2> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;

    public FirebaseNoteAdapter(@NonNull FirestoreRecyclerOptions<FirebaseModel> options, Context context) {
        super(options);
        this.context = context;
    }

    public FirebaseNoteAdapter(@NonNull FirestoreRecyclerOptions<FirebaseModel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder2 holder, int position, @NonNull FirebaseModel model) {

        holder.mTitle.setText(model.getTitle());
        holder.noteContent.setText(model.getContent());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.noteCard.setCardBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, NoteShowDetails.class));
            }
        });

        holder.menuPopButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        context.startActivity(new Intent(context, EditNoteActivity.class));
                        return false;
                    }
                });
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(context, model.getTitle()+" item Deleted", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_layout,parent,false);
        return new NoteViewHolder2(view);
    }


    public class NoteViewHolder2 extends RecyclerView.ViewHolder {
       private TextView mTitle,noteContent;
       private CardView noteCard;
       private ImageView menuPopButton;

        public NoteViewHolder2(@NonNull View itemView) {
            super(itemView);

            mTitle =itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.noteContent);
            noteCard = itemView.findViewById(R.id.noteCard);
            menuPopButton = itemView.findViewById(R.id.menuPopButton);
        }
    }
}
