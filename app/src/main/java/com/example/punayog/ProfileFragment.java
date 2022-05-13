package com.example.punayog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.punayog.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    TextView emailText;
    TextView genderText;
    View userNameText;
    TextView phoneText;
    TextView addressText;
    TextView textDoB;
    private String email, password;
    private FirebaseAuth database;
    FirebaseUser firebaseuser;
    private DatabaseReference reference;
    private ArrayList<User> userArrayList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        emailText = view.findViewById(R.id.emailText);
        genderText = view.findViewById(R.id.genderText);
        userNameText = view.findViewById(R.id.userNameText);
        phoneText = view.findViewById(R.id.phoneText);
        addressText = view.findViewById(R.id.addressText);
        textDoB = view.findViewById(R.id.textDoB);
        database = FirebaseAuth.getInstance();


        firebaseuser = database.getCurrentUser();
        if (firebaseuser == null) {
            Toast.makeText(getContext(), "User is null", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile();
        }
        return view;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_profile,container,false);




     return rootView;

    }

    private void showUserProfile() {
        String userID = firebaseuser.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("users").child(userID);
        System.out.println(query);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    userNameText = (View) s.child("inputUsername").getValue();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
