package com.example.e_democ.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Candidate extends AppCompatActivity {

    TextView heading;

    String db_id, check;

    EditText Email, Password, Political_History, F_Goals;
    CircleImageView image;

    LinearLayoutCompat email_l, password_l, political_l, fgoals_l, dp_l;

    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseStorage storage;
    StorageReference storageReference;

    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_candidate);

        heading = findViewById(R.id.heading);

        email_l = findViewById(R.id.email_layout);
        password_l = findViewById(R.id.password_layout);
        political_l = findViewById(R.id.political_layout);
        fgoals_l = findViewById(R.id.fgoals_layout);
        dp_l = findViewById(R.id.dp_layout);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Candidates");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Political_History = findViewById(R.id.poly_history);
        F_Goals = findViewById(R.id.fgoals);
        image = findViewById(R.id.update_image);

        db_id = getIntent().getStringExtra("db_id");
        check = getIntent().getStringExtra("edit");

        if (check.equals("email")) {
            email_l.setVisibility(View.VISIBLE);
            heading.setText("Edit Email");
        } else if (check.equals("password")) {
            password_l.setVisibility(View.VISIBLE);
            heading.setText("Edit Password");
        } else if (check.equals("dp")) {
            dp_l.setVisibility(View.VISIBLE);
            heading.setText("Edit Picture");
        } else if (check.equals("history")) {
            political_l.setVisibility(View.VISIBLE);
            heading.setText("Edit Political History");
        } else if (check.equals("fGoals")) {
            fgoals_l.setVisibility(View.VISIBLE);
            heading.setText("Edit Future Goals");
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String img_name = snapshot.child(db_id).child("img_name").getValue().toString();
                String img_url = "https://firebasestorage.googleapis.com/v0/b/e-democ.appspot.com/o/images%2F" + img_name + "?alt=media&token=fe347521-367b-4d59-8c8b-53c9d08c45c0";
                Picasso.get().load(img_url).into(image);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
        i.putExtra("db_id", db_id);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                imgUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);

                image.setImageBitmap(bitmap);

            } catch (Exception e) {

            }
        }
    }

    public void updateFgoals(View view) {
        String goals = F_Goals.getText().toString().trim();
        if (!goals.isEmpty()) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myRef.child(db_id).child("Future Goals").setValue(goals);
                    Toast.makeText(getApplicationContext(), "Future Goals updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
                    i.putExtra("db_id", db_id);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    public void updatePolitical(View view) {
        String his = Political_History.getText().toString().trim();
        if (!his.isEmpty()) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myRef.child(db_id).child("Political History").setValue(his);
                    Toast.makeText(getApplicationContext(), "History updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
                    i.putExtra("db_id", db_id);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    public void updatePassword(View view) {
        String pass = Password.getText().toString().trim();
        if (!pass.isEmpty()) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myRef.child(db_id).child("Password").setValue(pass);
                    Toast.makeText(getApplicationContext(), "Password updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
                    i.putExtra("db_id", db_id);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;

        }
    }

    public void updateEmail(View view) {
        String email = Email.getText().toString().trim();

        if (!email.isEmpty()) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myRef.child(db_id).child("Email").setValue(email);

                    Toast.makeText(getApplicationContext(), "Email updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Candidate_Settings.class);
                    i.putExtra("db_id", db_id);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void updateDp(View view) {

        uploadImage(db_id);
    }

    private void uploadImage(String db_id) {
        if (imgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Edit_Candidate.this);
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
                            Toast.makeText(Edit_Candidate.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Edit_Candidate.this, Voter_Settings.class);
                            i.putExtra("db_id", db_id);
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Candidate.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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