package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_democ.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MobileVotingStation_Voter extends AppCompatActivity {

    String db_id;
    FloatingActionButton add_button;
    LinearLayoutCompat found_data;

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView name,email,adress,reason;

    TextView nothing_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_voting_station_voter);

        db_id = getIntent().getStringExtra("db_id");
        nothing_found=findViewById(R.id.nothing_found);
        add_button=findViewById(R.id.add_button);
        found_data=findViewById(R.id.found_data);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        adress=findViewById(R.id.adress);
        reason=findViewById(R.id.reason);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("MobileStationRequests");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count= (int) snapshot.child(db_id).getChildrenCount();

                if (count==0){
                    nothing_found.setVisibility(View.VISIBLE);
                    add_button.setVisibility(View.VISIBLE);
                    found_data.setVisibility(View.INVISIBLE);
                }
                else {
                    found_data.setVisibility(View.VISIBLE);
                    nothing_found.setVisibility(View.INVISIBLE);
                    add_button.setVisibility(View.INVISIBLE);

                    String Adress=snapshot.child(db_id).child("Adress").getValue().toString();
                    String Email=snapshot.child(db_id).child("Email").getValue().toString();
                    String Name=snapshot.child(db_id).child("Name").getValue().toString();
                    String Reason=snapshot.child(db_id).child("Reason").getValue().toString();

                    name.setText(Name);
                    email.setText(Email);
                    adress.setText(Adress);
                    reason.setText(Reason);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Voter_Settings.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void add_new_mobile_voting_station(View view) {
        Intent i = new Intent(getApplicationContext(), AddNewMobileVotingStation.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void Delete_Request(View view) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(db_id).removeValue();

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