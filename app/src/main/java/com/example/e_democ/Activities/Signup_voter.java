package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_democ.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup_voter extends AppCompatActivity {

    TextInputEditText fullname, username, email, password, confirm_password, id;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_voter);

        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.c_password);
        id = findViewById(R.id.id);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Voters");
    }

    public void goBack(View view) {
        Intent i = new Intent(Signup_voter.this, SignupChoice.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Signup_voter.this, SignupChoice.class);
        startActivity(i);
        finish();
    }

    public void next_details(View view) {

        String v_fullname, v_username, v_email, v_password, v_c_password, v_id;

        v_fullname = fullname.getText().toString().trim();
        v_username = username.getText().toString().trim();
        v_email = email.getText().toString().trim();
        v_password = password.getText().toString().trim();
        v_c_password = confirm_password.getText().toString().trim();
        v_id = id.getText().toString().trim();

        final int[] count = new int[1];

        if (v_fullname.isEmpty()) {
            fullname.setError("Name Required");
            return;
        } else if (v_username.isEmpty()) {
            username.setError("Username Required");
            return;
        } else if (v_email.isEmpty()) {
            email.setError("Email Required");
            return;
        } else if (v_password.isEmpty()) {
            password.setError("Password Required");
            return;
        } else if (v_c_password.isEmpty()) {
            confirm_password.setError("Enter Password again");
            return;
        } else if (v_id.isEmpty()) {
            id.setError("Id Required");
            return;
        } else if (!v_password.equals(v_c_password)) {
            confirm_password.setError("Passwords don't match");
            return;
        } else {

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    count[0] = (int) snapshot.getChildrenCount() + 1;
                    myRef.child(String.valueOf(count[0])).child("Full Name").setValue(v_fullname);
                    myRef.child(String.valueOf(count[0])).child("Username").setValue(v_username);
                    myRef.child(String.valueOf(count[0])).child("Email").setValue(v_email);
                    myRef.child(String.valueOf(count[0])).child("Password").setValue(v_password);
                    myRef.child(String.valueOf(count[0])).child("Id").setValue(v_id);

                    Intent i = new Intent(Signup_voter.this, Get_More_Details_Voter.class);
                    i.putExtra("db_id",String.valueOf(count[0]));
                    startActivity(i);
                    finish();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                }
            });

        }




    }
}