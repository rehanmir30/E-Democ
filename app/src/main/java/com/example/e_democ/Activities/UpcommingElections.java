package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_democ.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcommingElections extends AppCompatActivity {

    Spinner dropdown;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomming_elections);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Upcomming Elections");
        myRef2 = database.getReference("Candidates");

        result=findViewById(R.id.election_result);
        dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Local", " Presidential", "Parlimentry", "Europarliment"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
    }

    public void search(View view) {

        String selected = dropdown.getSelectedItem().toString().trim();

        ArrayList<String> candis = new ArrayList<>();
        ArrayList<String> candi_names = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int count = (int) snapshot.child(selected).getChildrenCount();

                for (int i = 1; i <= snapshot.child(selected).getChildrenCount(); i++) {
                    String nums = snapshot.child(selected).child("Candidate" + i).getValue().toString();
                    candis.add(nums);
                }


                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshots) {

                        for (int i = 0; i < candis.size(); i++) {
                            String name = snapshots.child(candis.get(i)).child("Full Name").getValue().toString();
                            candi_names.add(name);
                        }

                        result.setText("The "+selected+ " elections will be held between \n \n");

                        for (int i = 0; i < candi_names.size(); i++) {
                           result.append(candi_names.get(i)+ "\n");
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
}