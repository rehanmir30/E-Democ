package com.example.e_democ.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_democ.Fragments.Login_as_Voter;
import com.example.e_democ.R;

public class Candidate_Settings extends AppCompatActivity {

    String db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_settings);

        db_id = getIntent().getStringExtra("db_id");

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Candidate_profile.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    public void Logout(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Log out")
//set message
                .setMessage("Are you sure you want to Log out")
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        Intent h = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(h);
                        finish();
                    }
                })
//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                    }
                })
                .show();
    }

    public void editEmail(View view) {
        Intent i=new Intent(getApplicationContext(),Edit_Candidate.class);
        i.putExtra("db_id",db_id);
        i.putExtra("edit","email");
        startActivity(i);
        finish();

    }

    public void editFgoals(View view) {
        Intent i=new Intent(getApplicationContext(),Edit_Candidate.class);
        i.putExtra("db_id",db_id);
        i.putExtra("edit","fGoals");
        startActivity(i);
        finish();
    }

    public void editp_history(View view) {
        Intent i=new Intent(getApplicationContext(),Edit_Candidate.class);
        i.putExtra("db_id",db_id);
        i.putExtra("edit","history");
        startActivity(i);
        finish();
    }

    public void editDp(View view) {
        Intent i=new Intent(getApplicationContext(),Edit_Candidate.class);
        i.putExtra("db_id",db_id);
        i.putExtra("edit","image");
        startActivity(i);
        finish();
    }

    public void editPassword(View view) {
        Intent i=new Intent(getApplicationContext(),Edit_Candidate.class);
        i.putExtra("db_id",db_id);
        i.putExtra("edit","password");
        startActivity(i);
        finish();
    }

    public void volumeAdjustment(View view) {
        Toast.makeText(getApplicationContext(),"This feature isn't available at the moment.",Toast.LENGTH_SHORT).show();
        return;
    }

    public void notify(View view) {
        Intent i=new Intent(getApplicationContext(),NotificationActivity.class);
        i.putExtra("db_id",db_id);
        startActivity(i);
        finish();
    }

    public void nopage(View view) {
        Toast.makeText(getApplicationContext(),"No Web Page found",Toast.LENGTH_SHORT).show();
        return;
    }

    public void upcommingelections(View view) {
        Intent i=new Intent(getApplicationContext(),UpcommingElections.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
    }
}