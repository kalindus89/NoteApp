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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverbutton;
    private LinearLayout mgobacktologin;

    private FirebaseAuth firebaseAuth;
    ProgressBar progressbarofmainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passowrd);

        mforgotpassword= findViewById(R.id.forgotPassword);
        mpasswordrecoverbutton= findViewById(R.id.passwordRecoverButton);
        mgobacktologin= findViewById(R.id.goToLogin);
        progressbarofmainactivity= findViewById(R.id.progressbarofmainactivity);

        firebaseAuth= FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mpasswordrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mforgotpassword.getText().toString().trim();

                if(email.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your mail first",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressbarofmainactivity.setVisibility(View.VISIBLE);
                    mpasswordrecoverbutton.setEnabled(false);
                    //we have to send password recover email
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressbarofmainactivity.setVisibility(View.GONE);
                                mpasswordrecoverbutton.setEnabled(true);

                                Toast.makeText(getApplicationContext(),"Reset password link to sent your e-Mail,",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email is wrong or Account is not exist",Toast.LENGTH_SHORT).show();
                                progressbarofmainactivity.setVisibility(View.GONE);
                                mpasswordrecoverbutton.setEnabled(true);
                            }

                        }
                    });

                }

            }
        });
    }
}