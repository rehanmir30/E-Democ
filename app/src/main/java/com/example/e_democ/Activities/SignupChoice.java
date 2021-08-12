package com.example.e_democ.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_democ.R;

public class SignupChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_choice);
    }

    public void voter_signup(View view) {
        Intent i=new Intent(SignupChoice.this,Signup_voter.class);
        startActivity(i);
        finish();

    }

    public void candidate_signup(View view) {
        Intent i=new Intent(SignupChoice.this,Signup_candidate.class);
        startActivity(i);
        finish();
    }
}