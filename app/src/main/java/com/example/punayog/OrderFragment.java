package com.example.punayog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrderFragment extends Fragment {

    private TextView totalPriceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        totalPriceTextView = rootView.findViewById(R.id.totalPriceTextView);
//        Bundle bundle = this.getArguments();
//        String data = bundle.getString("totalAmount");
//        System.out.println("Data: " + data);

        return rootView;
    }


}