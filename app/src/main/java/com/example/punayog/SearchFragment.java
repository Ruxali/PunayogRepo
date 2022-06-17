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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.HorizontalScrollAdapter;
import com.example.punayog.adapter.ListingAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.adapter.SearchAdapter;
import com.example.punayog.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    private DatabaseReference databaseRef;
    private ArrayList<Product> searchProductList;
    private SearchAdapter searchAdapter;
    private Context context;
    String myDataFromActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycler = view.findViewById(R.id.searchRecyclerView);

        MainActivity activity = (MainActivity) getActivity();
        myDataFromActivity = activity.getMyData();
        System.out.println("Search:" + myDataFromActivity);

        databaseRef = FirebaseDatabase.getInstance().getReference();

        search();
        activity.getSearchEdittext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchAdapter!=null){
                    searchAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    private void search() {
        searchProductList = new ArrayList<>();

        Query query = databaseRef.child("uploads");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildren());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = new Product();

                    product.setProductId((String) snapshot.child("productId").getValue());
                    product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                    product.setProductName((String) snapshot.child("productName").getValue());
                    product.setPrice((String) snapshot.child("price").getValue());
                    product.setLocation((String) snapshot.child("location").getValue());
                    product.setLongDesc((String) snapshot.child("longDesc").getValue());
                    product.setShortDesc((String) snapshot.child("shortDesc").getValue());
                    product.setSubCategory((String) snapshot.child("subCategory").getValue());
                    product.setCategory((String) snapshot.child("category").getValue());
                    product.setSellerName((String) snapshot.child("sellerName").getValue());
                    product.setSellerNumber((String) snapshot.child("sellerNumber").getValue());
                    product.setSellerEmail((String) snapshot.child("sellerEmail").getValue());

                    searchProductList.add(product);

                }

                searchAdapter = new SearchAdapter(context, searchProductList);
                LinearLayoutManager linearLayoutManager;
                linearLayoutManager = new LinearLayoutManager(getContext());
                searchRecycler.setLayoutManager(linearLayoutManager);

                searchRecycler.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
                searchAdapter.getFilter().filter(myDataFromActivity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}