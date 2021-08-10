package com.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverbutton;
    private LinearLayout mgobacktologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passowrd);

        mforgotpassword= findViewById(R.id.forgotPassword);
        mpasswordrecoverbutton= findViewById(R.id.passwordRecoverButton);
        mgobacktologin= findViewById(R.id.goToLogin);

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mpasswordrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mforgotpassword.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your mail first",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //we have to send password recover email
                    Toast.makeText(getApplicationContext(),"sent",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}