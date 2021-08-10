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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText msignupemail, msignuppassword;
    private Button msignup;
    private LinearLayout mgotologin;
    ProgressBar progressbarofmainactivity;

    private FirebaseAuth firebaseAuth;

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
        progressbarofmainactivity = findViewById(R.id.progressbarofmainactivity);

        firebaseAuth= FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail =msignupemail.getText().toString().trim();
                String password =msignuppassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                }
                else if (msignuppassword.getText().toString().trim().length()<7) {
                    Toast.makeText(getApplicationContext(), "Password should greater than 7 characters", Toast.LENGTH_SHORT).show();
                }else{

                    progressbarofmainactivity.setVisibility(View.VISIBLE);
                    msignup.setEnabled(false);

                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }else{

                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

                                progressbarofmainactivity.setVisibility(View.GONE);
                                msignup.setEnabled(true);
                            }
                        }
                    });


                }

            }
        });
    }

    //send email verification
    private void sendEmailVerification() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    progressbarofmainactivity.setVisibility(View.GONE);
                    msignup.setEnabled(true);

                    firebaseAuth.signOut();

                    Toast.makeText(getApplicationContext(), "Verification  Email is sent, Verify and Login Again", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        else{

            progressbarofmainactivity.setVisibility(View.GONE);
            msignup.setEnabled(true);

            Toast.makeText(getApplicationContext(), "Failed to sent verification Email", Toast.LENGTH_SHORT).show();

        }

    }
}