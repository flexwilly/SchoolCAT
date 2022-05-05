package com.example.wilsonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateUser extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference dbref;
    private String userID;
    private EditText fname,lname,email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        fname = (EditText)findViewById(R.id.fnameEdt);
        lname = (EditText) findViewById(R.id.lnameEdt);
        email =(EditText) findViewById(R.id.emailEdt);
        pass = (EditText) findViewById(R.id.passEdt);


        dbref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String firstname = userProfile.getFirstName();
                    String lastname = userProfile.getLastName();
                    String myemail = userProfile.getEmail();
                    String mypass = userProfile.getPassword();

                    fname.setText(firstname);
                    lname.setText(lastname);
                    email.setText(myemail);
                    pass.setText(mypass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUser.this,"Catastrophic Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToDashBoard(View view){
        startActivity(new Intent(UpdateUser.this,UserDashboard.class));
    }

    public void upDateUser(View view){
        user = FirebaseAuth.getInstance().getCurrentUser();
        String firstname = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String myemail = email.getText().toString().trim();
        String mypass = pass.getText().toString().trim();

        HashMap hashMap = new HashMap();
        hashMap.put("email",myemail);
        hashMap.put("firstName",firstname);
        hashMap.put("lastName",lastname);
        hashMap.put("password",mypass);

        userID = user.getUid();

        dbref.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateUser.this,"Update Successful",Toast.LENGTH_SHORT).show();
            }
        });
    }
}