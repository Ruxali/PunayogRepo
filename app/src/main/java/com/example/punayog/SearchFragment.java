package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.HorizontalScrollAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.remote.Datastore;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView searchRecycler;
    private TextView searchTextView;
    String searchEdittext;
    private DatabaseReference databaseRef;
    private ArrayList<Product> searchProductList;
    private ProductAdapter searchAdapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchTextView = view.findViewById(R.id.searchTextView);
        searchRecycler = view.findViewById(R.id.searchRecyclerView);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        Bundle data = getArguments();
        if (data != null) {
            searchEdittext = data.getString("data");

        }
        searchTextView.setText(searchEdittext);
        search();
        return view;
    }

    private void search() {
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseReference dateRef = databaseRef.child("uploads");
                searchProductList = new ArrayList<>();
                Query query = dateRef.orderByChild("productName").equalTo(searchTextView.toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Product product = new Product();
                            product.setmImageUrl((String) postSnapshot.child("mImageUrl").getValue());
                            product.setProductName((String) postSnapshot.child("productName").getValue());
                            product.setPrice((String) postSnapshot.child("price").getValue());
                            product.setShortDesc((String) postSnapshot.child("shortDesc").getValue());
                            product.setLongDesc((String) postSnapshot.child("longDesc").getValue());
                            product.setSubCategory((String) postSnapshot.child("subCategory").getValue());
                            product.setLocation((String) postSnapshot.child("location").getValue());
                            product.setCategory((String) postSnapshot.child("category").getValue());
                            product.setSellerName((String) postSnapshot.child("sellerName").getValue());
                            product.setSellerNumber((String) postSnapshot.child("sellerNumber").getValue());
                            product.setSellerEmail((String) postSnapshot.child("sellerEmail").getValue());

                            searchProductList.add(product);

                        }
                        searchAdapter = new ProductAdapter(context, searchProductList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        searchRecycler.setLayoutManager(linearLayoutManager);
                        searchRecycler.setAdapter(searchAdapter);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

}
