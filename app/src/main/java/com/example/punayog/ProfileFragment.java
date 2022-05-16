package com.example.punayog;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    TextView emailText;
    TextView genderText;
    TextView userNameText;
    TextView phoneText;
    TextView addressText;
    TextView textDoB;
    private FirebaseAuth database;
    FirebaseUser firebaseuser;
    private DatabaseReference reference;
    private ShapeableImageView shapeableImageView;

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
            Toast.makeText(getContext(), "No User logged in", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile();
        }
        return view;
    }


    private void showUserProfile() {
        String userID = firebaseuser.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("users").child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("inputUsername").getValue(String.class);
                userNameText.setText(userName);
                String dob = snapshot.child("inputDOB").getValue(String.class);
                textDoB.setText(dob);
                String gender = snapshot.child("userGender").getValue(String.class);
                genderText.setText(gender);
                String phone = snapshot.child("phoneInput").getValue(String.class);
                phoneText.setText(phone);
                String email = snapshot.child("emailInput").getValue(String.class);
                emailText.setText(email);
                String location = snapshot.child("addInput").getValue(String.class);
                addressText.setText(location);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
    
