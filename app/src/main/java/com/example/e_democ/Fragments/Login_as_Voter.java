package com.example.e_democ.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_democ.Activities.Candidate_profile;
import com.example.e_democ.Activities.Voter_profile;
import com.example.e_democ.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_as_Voter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_as_Voter extends Fragment {

    AppCompatButton login;
    TextInputEditText email,password;

    FirebaseDatabase database;
    DatabaseReference myRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_as_Voter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_as_Voter.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_as_Voter newInstance(String param1, String param2) {
        Login_as_Voter fragment = new Login_as_Voter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_login_as__voter, container, false);
        email=v.findViewById(R.id.email);
        password=v.findViewById(R.id.password);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Voters");

        login=v.findViewById(R.id.log_voter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String v_email=email.getText().toString().trim();
                String v_password=password.getText().toString().trim();

                if (v_email.isEmpty()){
                    email.setError("Email required");
                    return;
                }
                else if (v_password.isEmpty()){
                    password.setError("Password Required");
                    return;
                }
                else {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (int i=1;i<=snapshot.getChildrenCount();i++){
                                String e=snapshot.child(String.valueOf(i)).child("Email").getValue().toString();
                                if (v_email.equals(e)){
                                    String p=snapshot.child(String.valueOf(i)).child("Password").getValue().toString();
                                    if (v_password.equals(p)){
                                        Intent j=new Intent(Login_as_Voter.this.getActivity(), Voter_profile.class);
                                        j.putExtra("db_id",String.valueOf(i));
                                        startActivity(j);
                                        getActivity().finish();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }


            }
        });

    return v;
    }
}