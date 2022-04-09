package com.example.punayog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.model.Product;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ArrayList<Product> productArrayList;
    private ProductAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = rootView.findViewById(R.id.productRecyclerView);
        loadData();

        adapter = new ProductAdapter(productArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//
//
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                productDetails();
//            }
//        });

        return rootView;
    }

//    private void productDetails() {
//        Intent i = new Intent(getActivity().getBaseContext(),
//                ProductDetailsActivity.class);
//        getActivity().startActivity(i);
//    }

    private void loadData() {
        productArrayList = new ArrayList<>();

        Product product = new Product("https://www.xda-developers.com/files/2022/02/Samsung-Galaxy-S22-Ultra-Both-Devices-Snapdragon-Exynos.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

        product = new Product("https://images.samsung.com/uk/smartphones/galaxy-s22/buy/02_carousel/01_group/s22_carousel_groupkv_mo.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

        product = new Product("https://www.xda-developers.com/files/2022/02/Samsung-Galaxy-S22-Ultra-Both-Devices-Snapdragon-Exynos.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

        product = new Product("https://images.samsung.com/uk/smartphones/galaxy-s22/buy/02_carousel/01_group/s22_carousel_groupkv_mo.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

        product = new Product("https://www.xda-developers.com/files/2022/02/Samsung-Galaxy-S22-Ultra-Both-Devices-Snapdragon-Exynos.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

        product = new Product("https://images.samsung.com/uk/smartphones/galaxy-s22/buy/02_carousel/01_group/s22_carousel_groupkv_mo.jpg","Samsung Galaxy S22 Ultra 12/576","Rs.1,00,000","Used for 1 month","Sunakothi");
        productArrayList.add(product);

    }

}
