package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_democ.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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

public class Get_More_Details_Voter extends AppCompatActivity {

    AutoCompleteTextView occupation_spinner;
    CircleImageView profile_img;

    EditText age, adress;
    private int day, month, year;
    private Calendar mcalendar;

    Uri imgUri;

    String[] occupation_list = {"Student", "Government Employee", "Businessman", "Self Employed", "Private Job", "Freelancer"};

    FirebaseDatabase database;
    DatabaseReference myRef;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_details);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Voters");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        mcalendar = Calendar.getInstance();

        occupation_spinner = findViewById(R.id.occupation_spinner);
        profile_img = findViewById(R.id.select_image);

        age = findViewById(R.id.age);
        adress = findViewById(R.id.adress);


        day = mcalendar.get(Calendar.DAY_OF_MONTH);
        year = mcalendar.get(Calendar.YEAR);
        month = mcalendar.get(Calendar.MONTH);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, occupation_list);

        occupation_spinner.setThreshold(1);
        occupation_spinner.setAdapter(adapter);

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
        DatePickerDialog dpDialog = new DatePickerDialog(Get_More_Details_Voter.this, listener, year, month, day);
        dpDialog.show();
    }

    public void goBack(View view) {
        Intent i = new Intent(Get_More_Details_Voter.this, Signup_voter.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Get_More_Details_Voter.this, Signup_voter.class);
        startActivity(i);
        finish();
    }

    public void move_to_profile(View view) {

        String db_id = getIntent().getStringExtra("db_id");

        String v_dob = age.getText().toString().trim();
        String v_adress = adress.getText().toString().trim();
        String v_occupation = occupation_spinner.getText().toString().trim();


        if (v_dob.isEmpty() || v_adress.isEmpty() || v_occupation.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return;
        } else if (imgUri == null) {
            Toast.makeText(getApplicationContext(), "Select an image please", Toast.LENGTH_SHORT).show();
            return;
        } else {

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    myRef.child(db_id).child("DOB").setValue(v_dob);
                    myRef.child(db_id).child("Adress").setValue(v_adress);
                    myRef.child(db_id).child("Occupation").setValue(v_occupation);

                    uploadImage(db_id);


                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void uploadImage(String db_id) {

        if (imgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Get_More_Details_Voter.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String img_name = UUID.randomUUID().toString();

            StorageReference ref = storageReference.child("images/" + img_name);
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            myRef.child(db_id).child("img_name").setValue(img_name);
                            Toast.makeText(Get_More_Details_Voter.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Get_More_Details_Voter.this, Voter_profile.class);
                            i.putExtra("db_id", db_id);
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Get_More_Details_Voter.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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