package com.example.punayog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.firebase.database.DatabaseReference;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView searchRecyclerView;
    private ImageButton searchInputButton;
    private String searchInput;

    private DatabaseReference searchReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = rootView.findViewById(R.id.search_view);
        searchInputButton = rootView.findViewById(R.id.searchImageButton);
        searchRecyclerView = rootView.findViewById(R.id.searchRecyclerView);

        searchInput = searchView.getQuery().toString();
        System.out.println("Search :" + searchInput);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}