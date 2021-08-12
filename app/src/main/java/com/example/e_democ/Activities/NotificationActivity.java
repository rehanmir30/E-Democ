package com.example.e_democ.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.e_democ.R;

public class NotificationActivity extends AppCompatActivity {

    Switch aSwitch;

    String db_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        aSwitch=findViewById(R.id.switch1);

        db_id=getIntent().getStringExtra("db_id");

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getApplicationContext(),"Notifications Enabled",Toast.LENGTH_SHORT).show();

                } else {
                    // The toggle is disabled
                    Toast.makeText(getApplicationContext(),"Notifications Disabled",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Candidate_Settings.class);
        i.putExtra("db_id",db_id);
        startActivity(i);
        finish();
    }
}