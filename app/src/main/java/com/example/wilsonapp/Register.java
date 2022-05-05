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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
   private EditText fname,lname,email,pass;
   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.fnameId);
        lname = (EditText) findViewById(R.id.lnameId);
        email = (EditText) findViewById(R.id.emailId);
        pass = (EditText) findViewById(R.id.passId);
        mAuth = FirebaseAuth.getInstance();
    }

    //function to redirect to login
    public void goToLogin(View view){
        Intent i = new Intent(this,Login.class);
        startActivity(i);
    }
    //code to register a new user
    public void registerUser(View view){
        String firstname = fname.getText().toString();
        String lastname = lname.getText().toString();
        String myemail = email.getText().toString();
        String password = pass.getText().toString();

        if(firstname.isEmpty()){
            fname.setError("First name cannot be empty");
            fname.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            lname.setError("LastName cannot be empty");
            lname.requestFocus();
            return;
        }
        if(myemail.isEmpty()){
            email.setError("Email cannot be empty");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(password.length()<6){
            pass.setError("Min password length should be 6 characters");
            pass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(myemail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                    User user = new User(firstname,lastname,myemail,password);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Register.this,"Failed to register! Try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
               }else{
                 Toast.makeText(Register.this,"Failed to register! Try again",Toast.LENGTH_SHORT).show();
               }

            }
        });


    }

}