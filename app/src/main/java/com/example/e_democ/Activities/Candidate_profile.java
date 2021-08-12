package com.example.e_democ.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_democ.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Candidate_profile extends AppCompatActivity {

    ImageView profile_picture;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView n_sup, p_history, f_goals;

    String db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Candidates");

        n_sup = findViewById(R.id.n_sup);

        p_history = findViewById(R.id.p_history);
        f_goals = findViewById(R.id.f_goals);

        profile_picture = findViewById(R.id.profile_pic);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        db_id = getIntent().getStringExtra("db_id");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.child(db_id).child("Full Name").getValue().toString();
                String img_name = snapshot.child(db_id).child("img_name").getValue().toString();
                String no_of_supporters = snapshot.child(db_id).child("Supporters").getValue().toString();
                String history = snapshot.child(db_id).child("Political History").getValue().toString();
                String future = snapshot.child(db_id).child("Future Goals").getValue().toString();

                p_history.setText(history);
                f_goals.setText(future);
                n_sup.setText(no_of_supporters);

                String img_url = "https://firebasestorage.googleapis.com/v0/b/e-democ.appspot.com/o/images%2F" + img_name + "?alt=media&token=fe347521-367b-4d59-8c8b-53c9d08c45c0";

                Picasso.get().load(img_url).into(profile_picture);
                collapsingToolbarLayout.setTitle(name);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void Open_Settings(View view) {
        Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }
}