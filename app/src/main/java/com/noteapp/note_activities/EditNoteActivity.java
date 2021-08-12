package com.noteapp.note_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.noteapp.R;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {


    EditText editTitleOfNote,editContentOfNote;
    FloatingActionButton updateNote;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitleOfNote=findViewById(R.id.editTitleOfNote);
        editContentOfNote=findViewById(R.id.editContentOfNote);
        updateNote=findViewById(R.id.updateNote);

        Intent data = getIntent();
        String title= data.getExtras().get("title").toString();
        String content= data.getExtras().get("content").toString();
        String noteUniqueId= data.getExtras().get("noteUniqueId").toString();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP); // actionBar back button color

        editTitleOfNote.setText(title);
        editContentOfNote.setText(content);
        editContentOfNote.requestFocus();

        updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= editTitleOfNote.getText().toString().trim();
                String content= editContentOfNote.getText().toString().trim();
                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Both field are Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("Notes").document(firebaseAuth.getUid()).collection("MyNotes").document(noteUniqueId);
                    Map<String,Object> note = new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);

                    documentReference.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(getApplicationContext(), "Note Updated Successfully", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(CreateNoteActivity.this,NotesActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Failed To Update Note", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed(); // custom tooBar back button click action
        }
        return super.onOptionsItemSelected(item);
    }
}