package com.example.punayog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.ListingAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourListingsFragment extends Fragment {

    private RecyclerView listingRecyclerView;
    private DatabaseReference myRef;
    private FirebaseAuth database;
    private ConstraintLayout listingLayout;
    FirebaseUser firebaseuser;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ListingAdapter listProductAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_your_listings, container, false);

        listingRecyclerView = rootView.findViewById(R.id.listedProductRecyclerView);
        listingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingLayout = rootView.findViewById(R.id.listingLayout);

        database = FirebaseAuth.getInstance();
        firebaseuser = database.getCurrentUser();
        if (firebaseuser == null) {
            AlertDialog.Builder profileAlert = new AlertDialog.Builder(getContext());
            profileAlert.setTitle("You are not Logged in yet!");
            profileAlert.setMessage("Do you want to login?");
            profileAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            });
            profileAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            });
            listingLayout.setVisibility(View.GONE);
            profileAlert.create();
            profileAlert.show();
        } else {
            listingLayout.setVisibility(View.VISIBLE);
            getDataFromFirebase();
        }
        return rootView;
    }

    private void getDataFromFirebase(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child("uploads");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query.orderByChild("sellerEmail").equalTo(userID),Product.class)
                .build();

        System.out.println("order"+ options);

        listProductAdapter = new ListingAdapter(options);
        listingRecyclerView.setAdapter(listProductAdapter);
        listProductAdapter.startListening();

    }

}
