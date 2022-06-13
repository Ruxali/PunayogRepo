package com.example.punayog;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
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

    private static RecyclerView recyclerView;
    private static ImageButton listImageButton;
    private static ImageButton gridImageButton;
    private static ImageView errorImageView;

    //Firebase
    private static DatabaseReference myRef;

    //variables
    private static ArrayList<Product> productArrayList;
    private static Context context;
    private static ProductAdapter productAdapter;

    private static TextView categoryTitle;

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

        if (categoryTitle != null) {
            categoryTitle.setText(titleLabel);
            getDataFromFirebase(titleLabel);
        }


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
        errorImageView = rootView.findViewById(R.id.errorImageView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        clearAll();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for setting category title
        categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryTitle.setText(titleLabel);

    }

    //for products
    private static void getDataFromFirebase(String titleLabel) {

        productArrayList = new ArrayList<>();

        Query query = myRef.child("uploads");

        query.orderByChild("subCategory").equalTo(titleLabel).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Product product = new Product();

                    product.setmImageUrl(snapshot.child("mImageUrl").getValue(String.class));
                    product.setProductName(snapshot.child("productName").getValue(String.class));
                    product.setPrice(snapshot.child("price").getValue(String.class));
                    product.setLocation(snapshot.child("location").getValue(String.class));
                    product.setLongDesc(snapshot.child("longDesc").getValue(String.class));
                    product.setShortDesc(snapshot.child("shortDesc").getValue(String.class));
                    product.setSubCategory(snapshot.child("subCategory").getValue(String.class));
                    product.setCategory((String) snapshot.child("category").getValue());
                    product.setSellerName((String) snapshot.child("sellerName").getValue());
                    product.setSellerNumber((String) snapshot.child("sellerNumber").getValue());
                    product.setSellerEmail((String) snapshot.child("sellerEmail").getValue());

                    productArrayList.add(product);

                }

                productAdapter = new ProductAdapter(context, productArrayList);

                listImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayoutManager linearLayoutManager;
                        linearLayoutManager = new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });

                gridImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                    }
                });

                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorImageView.setVisibility(View.VISIBLE);
            }

        });

    }

    private void clearAll() {
        if (productArrayList != null) {
            productArrayList.clear();
        }

    }


}