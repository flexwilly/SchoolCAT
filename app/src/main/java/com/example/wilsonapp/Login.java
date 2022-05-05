package com.example.wilsonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.edtEmailId);
        pass = (EditText) findViewById(R.id.edtPasswordId);

        mAuth = FirebaseAuth.getInstance();
    }
    //function to redirect user
    public void goToForgotPassword(View view){
        startActivity(new Intent(Login.this,ForgotPassword.class));
    }

    public void goToSignUp(View view){
        Intent i = new Intent(this,Register.class);
        startActivity(i);
    }

    public void userLogin(View view){
        String myemail = email.getText().toString().trim();
        String myPassword = pass.getText().toString().trim();

        if(myemail.isEmpty()){
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }
        if(myPassword.isEmpty()){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }
        if(myPassword.length()<6){
            pass.setError("Min password length should be 6 characters");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(myemail,myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(Login.this,UserDashboard.class));
                        Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(Login.this,"Check your email to verify account",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Login.this,"Login failed ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}