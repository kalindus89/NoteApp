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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    FirestoreRecyclerOptions<FirebaseModel> fireStoreRecyclerOptions;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    public FirebaseNoteAdapter(Context context, @NonNull FirestoreRecyclerOptions<FirebaseModel> fireStoreRecyclerOptions, FirebaseFirestore firebaseFirestore, FirebaseUser firebaseUser) {
        super(fireStoreRecyclerOptions);
        this.context = context;
        this.fireStoreRecyclerOptions = fireStoreRecyclerOptions;
        this.firebaseUser = firebaseUser;
        this.firebaseFirestore = firebaseFirestore;
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
                Intent intent=new Intent(context, NoteShowDetails.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("content",model.getContent());
                intent.putExtra("noteUniqueId",fireStoreRecyclerOptions.getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId());
                context.startActivity(intent);
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

                        Intent intent=new Intent(context, EditNoteActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("noteUniqueId",fireStoreRecyclerOptions.getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId());
                        context.startActivity(intent);
                        return false;
                    }
                });
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        DocumentReference documentReference = firebaseFirestore.collection("Notes").document(firebaseUser.getUid()).collection("MyNotes").document(fireStoreRecyclerOptions.getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getId());
                        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, model.getTitle()+" Item Deleted", Toast.LENGTH_SHORT).show();
                                popupMenu.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, model.getTitle()+" Item failed Deleted ", Toast.LENGTH_SHORT).show();
                                popupMenu.dismiss();
                            }
                        });

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
