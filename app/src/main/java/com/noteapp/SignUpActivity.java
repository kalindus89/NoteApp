package com.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText msignupemail, msignuppassword;
    private Button msignup;
    private LinearLayout mgotologin;

    public SignUpActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        msignupemail = findViewById(R.id.signUpEmail);
        msignuppassword = findViewById(R.id.signUpPassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.goToLogin);

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (msignupemail.getText().toString().trim().isEmpty() || msignuppassword.getText().toString().trim().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                }
                else if (msignuppassword.getText().toString().trim().length()<7) {
                    Toast.makeText(getApplicationContext(), "Password should greater than 7 characters", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}