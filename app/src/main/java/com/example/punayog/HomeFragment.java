package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.adapter.SearchAdapter;
import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ProductAdapter adapter;

    private RecyclerView recyclerView,rv;

    //Firebase
    private DatabaseReference myRef, ref;
    private ArrayList<SearchDeal>list;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.productRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();
//        ref = FirebaseDatabase.getInstance().getReference().child("uploads");
        clearAll();

        GetDataFromFirebase();

        return rootView;
    }

    //for searching purpose


    private void GetDataFromFirebase() {
        productArrayList = new ArrayList<>();

        Query query = myRef.child("uploads");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = new Product();

                    product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                    product.setProductName((String) snapshot.child("productName").getValue());
                    product.setPrice((String) snapshot.child("price").getValue());
                    product.setLocation((String) snapshot.child("location").getValue());
                    product.setLongDesc((String) snapshot.child("longDesc").getValue());
                    product.setShortDesc((String) snapshot.child("shortDesc").getValue());

                    productArrayList.add(product);

                }

                productAdapter = new ProductAdapter(context, productArrayList);
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (productArrayList != null) {
            productArrayList.clear();

            if (productAdapter != null) {
                productAdapter.notifyDataSetChanged();
            }
        }
        productArrayList = new ArrayList<>();
    }
}
