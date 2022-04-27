package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.ProductAdapter;
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

    private RecyclerView recyclerView;

    //Firebase
    private DatabaseReference myRef;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = rootView.findViewById(R.id.productRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();


        clearAll();

        GetDataFromFirebase();

        return rootView;
    }

    private void GetDataFromFirebase() {
        productArrayList = new ArrayList<>();

        Query query = myRef.child("uploads");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = new Product();

                    product.setmImageUrl(snapshot.child("mImageUrl").getValue().toString());
                    product.setProductName(snapshot.child("productName").getValue().toString());
                    product.setPrice(snapshot.child("price").getValue().toString());
                    product.setLocation(snapshot.child("location").getValue().toString());
                    product.setLongDesc(snapshot.child("longDesc").getValue().toString());
                    product.setShortDesc(snapshot.child("shortDesc").getValue().toString());

                    productArrayList.add(product);

                }

                productAdapter = new ProductAdapter(context,productArrayList);
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDateFromFirebase(){
        Query query = myRef.child("uploads");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = new Product();

                    product.setmImageUrl(snapshot.child("mImageUrl").getValue().toString());
                    product.setProductName(snapshot.child("productName").getValue().toString());
                    product.setPrice(snapshot.child("price").getValue().toString());
                    product.setLocation(snapshot.child("location").getValue().toString());
                    product.setLongDesc(snapshot.child("longDesc").getValue().toString());
                    product.setShortDesc(snapshot.child("shortDesc").getValue().toString());

                    productArrayList.add(product);

                }

                productAdapter = new ProductAdapter(context,productArrayList);
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        getDateFromFirebase();
        super.onResume();
    }

    private void clearAll(){
        if(productArrayList!=null){
            productArrayList.clear();

            if(productAdapter !=null){
                productAdapter.notifyDataSetChanged();
            }
        }
        productArrayList = new ArrayList<>();
    }
}
