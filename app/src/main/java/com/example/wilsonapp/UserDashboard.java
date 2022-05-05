package com.example.wilsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signOut(View view){
        mAuth.signOut();
        startActivity(new Intent(UserDashboard.this,Login.class));
    }
    public void viewDetails(View view){
        startActivity(new Intent(UserDashboard.this,MyDetails.class));
    }
    public void goToUpdateUser(View view){
        startActivity(new Intent(UserDashboard.this,UpdateUser.class));
    }
}