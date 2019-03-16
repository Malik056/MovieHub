package com.semicolon.moviehub;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.semicolon.moviehub.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    public ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();


        ref = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        Button mSignupBtn = findViewById(R.id.SignupBtn);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="test@test.com",password="pwd";
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                     firebaseUser = mAuth.getCurrentUser();

                TextView textView = findViewById(R.id.emailSignup);
                String email = textView.getText().toString();
                textView = findViewById(R.id.nameSignup);
                String name = textView.getText().toString();
                textView = findViewById(R.id.passwordSignup);
                String pwd = textView.getText().toString();
                TextView textView2 = findViewById(R.id.confirmPasswordSignup);
                String cpwd = textView2.getText().toString();
                RadioGroup radioGroup = findViewById(R.id.types);
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String type = radioButton.getText().toString();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
                if(pwd.equals(cpwd)) {
                    mAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        hideProgressDialog();
                                        finish();
                                    }
                                    else {
                                        hideProgressDialog();
                                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    //password hashing here
//                    StringBuilder hash = new StringBuilder();
//                    try
//                    {
//                        MessageDigest sha = MessageDigest.getInstance("SHA-1");
//                        byte[] hashedBytes = sha.digest(pwd.getBytes());
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    }
                    ref.push().setValue(new User(email, name, pwd, type));
                }
                else
                {
                    textView.setText("");
                    textView2.setText("");
                    TextInputLayout inputLayout = findViewById(R.id.passwordLayout);
                    inputLayout.setError("Passwords don't match");
                }
            }
        });

        TextView mLoginBtn= findViewById(R.id.LoginBtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
