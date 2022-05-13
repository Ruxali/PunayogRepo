package com.example.punayog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.interfaces.NavigationManager;
import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;


public class FragmentForSideNav extends Fragment {


    public static final String KEY_TITLE = "Contents";

    private RecyclerView recyclerView;
    private ImageButton listImageButton, gridImageButton;

    //Firebase
    private DatabaseReference myRef;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductAdapter productAdapter;

public static String titleLabel;
    public FragmentForSideNav() {
        // Required empty public constructor
    }


    public static FragmentForSideNav newInstance(String title) {

        titleLabel = title;

        FragmentForSideNav fragment = new FragmentForSideNav();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_for_side_nav, container, false);

        recyclerView = rootView.findViewById(R.id.productRecyclerView);

        listImageButton = rootView.findViewById(R.id.listImageButton);
        gridImageButton = rootView.findViewById(R.id.gridImageButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        clearAll();

        GetDataFromFirebase();



        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for setting category title
        TextView categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryTitle.setText(titleLabel);

    }

    //for products
    private void GetDataFromFirebase() {
        productArrayList = new ArrayList<>();

        Query query = myRef.child("uploads");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    com.example.punayog.model.Product product = new Product();

                    product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                    product.setProductName((String) snapshot.child("productName").getValue());
                    product.setPrice((String) snapshot.child("price").getValue());
                    product.setLocation((String) snapshot.child("location").getValue());
                    product.setLongDesc((String) snapshot.child("longDesc").getValue());
                    product.setShortDesc((String) snapshot.child("shortDesc").getValue());

                    productArrayList.add(product);

                }

                productAdapter = new ProductAdapter(context, productArrayList);
                listImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayoutManager linearLayoutManager;
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });

                gridImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                    }
                });

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