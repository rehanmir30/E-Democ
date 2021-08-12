package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_democ.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Candidate_Detail extends AppCompatActivity {

    String db_id, db_id_c;

    FirebaseDatabase database;
    DatabaseReference myRef;

    ImageView main_img;
    TextView Candi_name, p_history,f_goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_detail);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Candidates");

        main_img = findViewById(R.id.main_img);
        Candi_name = findViewById(R.id.candi_name);
        p_history = findViewById(R.id.p_history);
        f_goals=findViewById(R.id.f_goals);

        db_id = getIntent().getStringExtra("db_id");
        db_id_c = getIntent().getStringExtra("db_id_c");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.child(db_id_c).child("Full Name").getValue().toString();
                String img_name = snapshot.child(db_id_c).child("img_name").getValue().toString();
                String politi_history = snapshot.child(db_id_c).child("Political History").getValue().toString();
                String future_goals= snapshot.child(db_id_c).child("Future Goals").getValue().toString();

                String img_url = "https://firebasestorage.googleapis.com/v0/b/e-democ.appspot.com/o/images%2F" + img_name + "?alt=media&token=fe347521-367b-4d59-8c8b-53c9d08c45c0";

                Picasso.get().load(img_url).into(main_img);
                Candi_name.setText(name);
                p_history.setText(politi_history);
                f_goals.setText(future_goals);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Voter_profile.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void Dislike_candi(View view) {
        Toast.makeText(getApplicationContext(), "You Disliked the Candidate", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), Voter_profile.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void Like_candi(View view) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String supporter_count= snapshot.child(db_id_c).child("Supporters").getValue().toString();
                int count=1+ Integer.parseInt(supporter_count);

                myRef.child(db_id_c).child("Supporters").setValue(String.valueOf(count));

                Toast.makeText(getApplicationContext(), "You Liked the Candidate", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Voter_profile.class);
                i.putExtra("db_id", db_id);
                startActivity(i);
                finish();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
}