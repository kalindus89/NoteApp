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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.noteapp.R;

public class NoteShowDetails extends AppCompatActivity {

    TextView titleOfNote,contentOfNote;
    FloatingActionButton editNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_show_details_activity);

        titleOfNote=findViewById(R.id.titleOfNote);
        contentOfNote=findViewById(R.id.contentOfNote);
        editNote=findViewById(R.id.editNote);

        Intent data = getIntent();
        String title= data.getExtras().get("title").toString();
        String content= data.getExtras().get("content").toString();
        String noteUniqueId= data.getExtras().get("noteUniqueId").toString();

        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP); // actionBar back button color

        titleOfNote.setText(title);
        contentOfNote.setText(content);

        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NoteShowDetails.this, EditNoteActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("noteUniqueId",noteUniqueId);
                startActivity(intent);
                finish();
            }
        });

    } @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed(); // custom tooBar back button click action
        }
        return super.onOptionsItemSelected(item);
    }
}