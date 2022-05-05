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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText mailreset;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mailreset =(EditText) findViewById(R.id.edtReset);
        auth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view){
        String email = mailreset.getText().toString().trim();
        if(email.isEmpty()) {
            mailreset.setError("Email cannot be empty");
            mailreset.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mailreset.setError("Please provide a valid email");
            mailreset.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this,"Please check your email",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ForgotPassword.this,"Try again ",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    public void goToLogin(View view){
        startActivity(new Intent(ForgotPassword.this,Login.class));
    }
}