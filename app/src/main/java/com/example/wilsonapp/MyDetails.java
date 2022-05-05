package com.example.wilsonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyDetails extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference dbref;
    private String userID;
    private TextView fname,lname,email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        fname = (TextView) findViewById(R.id.viewfname);
        lname = (TextView) findViewById(R.id.viewlname);
        email = (TextView)findViewById(R.id.viewemail);
        password = (TextView) findViewById(R.id.viewpassword);

        dbref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String firstname = userProfile.getFirstName();
                    String lastname = userProfile.getLastName();
                    String myemail = userProfile.getEmail();
                    String mypass = userProfile.getPassword();
                    
                    fname.setText("FirstName :"+firstname);
                    lname.setText("lastName :"+lastname);
                    email.setText("Email :"+myemail);
                    password.setText("Password :"+mypass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyDetails.this,"Catastrophic Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToDashboard(View view){
        startActivity(new Intent(MyDetails.this,UserDashboard.class));
    }
}