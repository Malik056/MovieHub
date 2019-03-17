package com.semicolon.moviehub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog mProgressDialog;

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        Button mLoginBtn = findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.email);
                String email = textView.getText().toString();
                textView = findViewById(R.id.password);
                String pwd = textView.getText().toString();
                Toast.makeText(LoginActivity.this, "email: "+email+" pwd: "+pwd, Toast.LENGTH_SHORT).show();
                Log.d("TAG","");
                if(email.equals("") || pwd.equals("")) {
                    Log.d("TAG","");
                }
                else
                {

                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        hideProgressDialog();

                                        Intent intent = new Intent(getApplicationContext(), GenreListActivity.class);
                                        startActivity(intent);
                                   //     finish();
                                    }
                                    else
                                        {
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        hideProgressDialog();
                                    }

                                }
                            });
                }
            }
        });

        TextView mForgotPwd = findViewById(R.id.forgotPwd);
        mForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView textView = findViewById(R.id.email);
                String em=textView.getText().toString();
                if(!em.equals("")){
                    mAuth.sendPasswordResetEmail(textView.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Password reset email sent.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        TextView mSignupBtn= findViewById(R.id.signupBtn);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="",password="";/*
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                   // Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
                        */

                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
	}




}
