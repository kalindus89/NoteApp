package com.noteapp.note_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.noteapp.FirebaseModel;
import com.noteapp.FirebaseNoteAdapter;
import com.noteapp.login_signup.LoginActivity;
import com.noteapp.R;

public class AllNotesActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FloatingActionButton createNoteFab;

    FirebaseNoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setTitle("Notes App");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        createNoteFab = findViewById(R.id.createNoteFab);
        recyclerView = findViewById(R.id.recyclerView);

        createNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllNotesActivity.this, CreateNoteActivity.class));
            }
        });

        syncDataFromFirebase();

    }


    public void syncDataFromFirebase(){
        Query query = firebaseFirestore.collection("Notes").document(firebaseUser.getUid()).collection("MyNotes").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<FirebaseModel> allUserNotes = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();

        noteAdapter = new FirebaseNoteAdapter(allUserNotes,this);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager); // this will crash when back press
        //recyclerView.setLayoutManager(new WrapContentStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(AllNotesActivity.this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(noteAdapter!=null)
        {
           noteAdapter.stopListening();
            //noteAdapter.startListening();
        }
    }
}