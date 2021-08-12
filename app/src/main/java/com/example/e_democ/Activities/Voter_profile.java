package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_democ.Adapters.ExampleAdapter;
import com.example.e_democ.Model.Candidate;
import com.example.e_democ.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Voter_profile extends AppCompatActivity {

    ImageView profile_picture;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;

    private ArrayList<Candidate> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Voters");
        myRef2 = database.getReference("Candidates");

        profile_picture = findViewById(R.id.profile_pic);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        String db_id = getIntent().getStringExtra("db_id");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.child(db_id).child("Full Name").getValue().toString();
                String img_name = snapshot.child(db_id).child("img_name").getValue().toString();
                String img_url = "https://firebasestorage.googleapis.com/v0/b/e-democ.appspot.com/o/images%2F" + img_name + "?alt=media&token=fe347521-367b-4d59-8c8b-53c9d08c45c0";
                Picasso.get().load(img_url).into(profile_picture);
                collapsingToolbarLayout.setTitle(name);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        mExampleList = new ArrayList<>();

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (int i = 1; i <= snapshot.getChildrenCount(); i++) {
                    String adress = snapshot.child(String.valueOf(i)).child("Adress").getValue().toString();
                    String dob = snapshot.child(String.valueOf(i)).child("DOB").getValue().toString();
                    String election_catagory = snapshot.child(String.valueOf(i)).child("Election Catagory").getValue().toString();
                    String email = snapshot.child(String.valueOf(i)).child("Email").getValue().toString();
                    String full_name = snapshot.child(String.valueOf(i)).child("Full Name").getValue().toString();
                    String id = snapshot.child(String.valueOf(i)).child("Id").getValue().toString();
                    String password = snapshot.child(String.valueOf(i)).child("Password").getValue().toString();
                    String political_history = snapshot.child(String.valueOf(i)).child("Political History").getValue().toString();
                    String supporters = snapshot.child(String.valueOf(i)).child("Supporters").getValue().toString();
                    String username = snapshot.child(String.valueOf(i)).child("Username").getValue().toString();
                    String img_name = snapshot.child(String.valueOf(i)).child("img_name").getValue().toString();

                    Candidate candidate = new Candidate(String.valueOf(i), full_name, username, email, password, id, political_history, supporters, img_name, election_catagory, dob);

                    mExampleList.add(candidate);

                }
                mRecyclerView = findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(Voter_profile.this);

                mAdapter = new ExampleAdapter(mExampleList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        Candidate candidate=mExampleList.get(position);
                        String db_id_c=candidate.getC_db_id();

                        Intent i = new Intent(getApplicationContext(), Candidate_Detail.class);
                        i.putExtra("db_id_c",db_id_c);
                        i.putExtra("db_id",db_id);
                        startActivity(i);
                        finish();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void Open_Settings(View view) {

        String db_id = getIntent().getStringExtra("db_id");
        Intent i = new Intent(getApplicationContext(), Voter_Settings.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }
}