package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.CartAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.model.CartModel;
import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private RecyclerView cartRecyclerView;
    private ImageButton listImageButton, gridImageButoon;
    private TextView totalPrice;
    float overAllTotalAmount = 0.00F;

    private DatabaseReference myRef;
    private ArrayList<CartModel> cartArrayList;
    private Context context;
    private CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);


        cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);

        listImageButton = rootView.findViewById(R.id.listImageButton);
        gridImageButoon = rootView.findViewById(R.id.gridImageButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(true);

        //total price
        totalPrice = rootView.findViewById(R.id.totalPrice);
//        float singlePrice = ((Float.parseFloat(cartModel.getPrice())));
//        overAllTotalAmount = overAllTotalAmount + singlePrice;
//        totalPrice.setText(String.valueOf(overAllTotalAmount));

        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        clearAll();

        getDataFromFirebase();

        return rootView;
    }


    //for cart products
    private void getDataFromFirebase() {
        cartArrayList = new ArrayList<>();

        Query query = myRef.child("cart");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartModel cartModel = new CartModel();

                    cartModel.setImage((String) snapshot.child("productImage").getValue());
                    cartModel.setName((String) snapshot.child("productName").getValue());
                    cartModel.setPrice((String) snapshot.child("productPrice").getValue());
                    cartModel.setKey(snapshot.getKey());

                    cartArrayList.add(cartModel);

                }


                cartAdapter = new CartAdapter(context, cartArrayList);
                listImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayoutManager linearLayoutManager;
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        cartRecyclerView.setLayoutManager(linearLayoutManager);
                    }
                });

                gridImageButoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        cartRecyclerView.setLayoutManager(gridLayoutManager);
                    }
                });

                cartRecyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (cartArrayList != null) {
            cartArrayList.clear();


        }
    }
}