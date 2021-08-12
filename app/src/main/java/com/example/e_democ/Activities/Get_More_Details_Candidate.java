package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_democ.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_More_Details_Candidate extends AppCompatActivity {

    Spinner dropdown;

    CircleImageView profile_img;

    EditText age,adress,politicalHistory,future_goals;
    private int day, month, year;
    private Calendar mcalendar;

    Uri imgUri;

    FirebaseDatabase database;
    DatabaseReference myRef;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_details_candidate);

        mcalendar = Calendar.getInstance();
        profile_img = findViewById(R.id.select_image);
        adress=findViewById(R.id.adress);
        politicalHistory=findViewById(R.id.political_history);
        future_goals=findViewById(R.id.future_goals);

        age = findViewById(R.id.age);
        day = mcalendar.get(Calendar.DAY_OF_MONTH);
        year = mcalendar.get(Calendar.YEAR);
        month = mcalendar.get(Calendar.MONTH);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Candidates");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        dropdown=findViewById(R.id.spinner1);
        String[] items = new String[]{"local", " presidential", "parlament","europarliament"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                imgUri = data.getData();
                // Toast.makeText(getApplicationContext(),String.valueOf(imgUri),Toast.LENGTH_SHORT).show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);

                profile_img.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }
    private void DateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                age.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }
        };
        DatePickerDialog dpDialog = new DatePickerDialog(Get_More_Details_Candidate.this, listener, year, month, day);
        dpDialog.show();
    }


    public void move_to_profile(View view) {

        String db_id = getIntent().getStringExtra("db_id");


        String c_dob = age.getText().toString().trim();
        String c_adress = adress.getText().toString().trim();
        String c_politicalhistory = politicalHistory.getText().toString().trim();
        String c_state=dropdown.getSelectedItem().toString().trim();
        String c_future_goals=future_goals.getText().toString().trim();

        if (c_dob.isEmpty() || c_adress.isEmpty() || c_politicalhistory.isEmpty() || c_state.isEmpty() || c_future_goals.isEmpty()){
            Toast.makeText(getApplicationContext(),"All Fields required",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (imgUri==null){
            Toast.makeText(getApplicationContext(),"Select image please!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    myRef.child(db_id).child("DOB").setValue(c_dob);
                    myRef.child(db_id).child("Adress").setValue(c_adress);
                    myRef.child(db_id).child("Political History").setValue(c_politicalhistory);
                    myRef.child(db_id).child("Election Catagory").setValue(c_state);
                    myRef.child(db_id).child("Supporters").setValue("0");
                    myRef.child(db_id).child("Future Goals").setValue(c_future_goals);

                    uploadImage(db_id);

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

    }

    public void goBack(View view) {
        Intent i = new Intent(Get_More_Details_Candidate.this, Signup_candidate.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Get_More_Details_Candidate.this, Signup_candidate.class);
        startActivity(i);
        finish();
    }

    private void uploadImage(String db_id) {

        if (imgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Get_More_Details_Candidate.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String img_name = UUID.randomUUID().toString();

            StorageReference ref = storageReference.child("images/" +img_name);
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            myRef.child(db_id).child("img_name").setValue(img_name);
                            Toast.makeText(Get_More_Details_Candidate.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Get_More_Details_Candidate.this, Candidate_profile.class);
                            i.putExtra("db_id",db_id);
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Get_More_Details_Candidate.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

}