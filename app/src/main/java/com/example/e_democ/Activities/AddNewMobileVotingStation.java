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

import java.util.Locale;

public class AddNewMobileVotingStation extends AppCompatActivity {

    String db_id;

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextInputEditText name,email,adress,reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mobile_voting_station);

        db_id = getIntent().getStringExtra("db_id");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("MobileStationRequests");

        name=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        adress=findViewById(R.id.adress);
        reason=findViewById(R.id.reason);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MobileVotingStation_Voter.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void Upload_Request(View view) {
        String Name,Email,Adress,Reason;

        Name=name.getText().toString().trim();
        Email=email.getText().toString().trim();
        Adress=adress.getText().toString().trim();
        Reason=reason.getText().toString().trim();

        if (Name.isEmpty()||Email.isEmpty()||Adress.isEmpty()||Reason.isEmpty()){
            Toast.makeText(getApplicationContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    myRef.child(db_id).child("Name").setValue(Name);
                    myRef.child(db_id).child("Email").setValue(Email);
                    myRef.child(db_id).child("Adress").setValue(Adress);
                    myRef.child(db_id).child("Reason").setValue(Reason);

                    Toast.makeText(getApplicationContext(),"Request Sent",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MobileVotingStation_Voter.class);
                    i.putExtra("db_id", db_id);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}