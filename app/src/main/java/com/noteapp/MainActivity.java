 package com.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

     private EditText mloginemail,mloginpassword;
     private Button mlogin;
     private TextView mgotoforgotpassword;
     private LinearLayout mgotosignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mloginemail= findViewById(R.id.loginEmail);
        mloginpassword= findViewById(R.id.loginPassword);
        mlogin= findViewById(R.id.login);
        mgotosignup= findViewById(R.id.goToSignup);
        mgotoforgotpassword= findViewById(R.id.goToForgotPassword);

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mloginemail.getText().toString().trim().isEmpty() || mloginpassword.getText().toString().trim().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}