 package com.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class LoginActivity extends AppCompatActivity {

     private EditText mloginemail,mloginpassword;
     private Button mlogin;
     private TextView mgotoforgotpassword;
     private LinearLayout mgotosignup;

     private FirebaseAuth firebaseAuth;
     ProgressBar progressbarofmainactivity;

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
        progressbarofmainactivity = findViewById(R.id.progressbarofmainactivity);

        firebaseAuth= FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser !=null){

            Intent intent = new Intent(LoginActivity.this, NotesActivity.class);
            startActivity(intent);
            finish();

        }

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail =mloginemail.getText().toString().trim();
                String password =mloginpassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else{
                    progressbarofmainactivity.setVisibility(View.VISIBLE);
                    mlogin.setEnabled(false);

                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                checkEmailVerification();
                            }else{
                                Toast.makeText(getApplicationContext(), "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                                progressbarofmainactivity.setVisibility(View.GONE);
                                mlogin.setEnabled(true);
                            }

                        }
                    });


                }

            }
        });

    }

     private void checkEmailVerification() {

         FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

         if(firebaseUser.isEmailVerified()==true){

             progressbarofmainactivity.setVisibility(View.GONE);
             mlogin.setEnabled(true);

             Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();

             Intent intent = new Intent(LoginActivity.this, NotesActivity.class);
             startActivity(intent);
             finish();
         }
         else{
             progressbarofmainactivity.setVisibility(View.GONE);
             mlogin.setEnabled(true);

             Toast.makeText(getApplicationContext(), "Verify Your mail first", Toast.LENGTH_SHORT).show();
             firebaseAuth.signOut();
         }

     }
 }