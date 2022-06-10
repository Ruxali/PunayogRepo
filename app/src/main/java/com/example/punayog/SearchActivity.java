package com.example.punayog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.SearchAdapter;
import com.example.punayog.model.SearchDeal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends Activity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    public ArrayList<SearchDeal> list;
    private Query query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycle_view);
        mRef = FirebaseDatabase.getInstance().getReference("uploads");
        query = mRef.orderByChild("productName");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (query != null) {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(SearchDeal.class));
                        }
                        SearchAdapter adapter = new SearchAdapter(list);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

    }

    private void search(String str) {
        ArrayList<SearchDeal> myList = new ArrayList<>();
        for (SearchDeal object : list) {
            if (object.getProductName().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }
            SearchAdapter adapter = new SearchAdapter(myList);
            recyclerView.setAdapter(adapter);

        }
    }
}