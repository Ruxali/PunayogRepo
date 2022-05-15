package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.punayog.adapter.BannerAdapter;
import com.example.punayog.adapter.HorizontalScrollAdapter;
import com.example.punayog.adapter.ProductAdapter;

import com.example.punayog.model.Banner;

import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private ImageButton listImageButton, gridImageButoon;

    //Firebase


//    private ArrayList<SearchDeal>list;
    private DatabaseReference myRef;
    private ArrayList<SearchDeal>list;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductAdapter productAdapter;

    //banner slider
    private StorageReference mStorageReference;
    private ViewPager bannerSliderViewPager;
    private List<Banner> bannerList;
    private int currentPage = 0;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    //banner slider

    //horizontal product
        private TextView horizontalLayoutTitle;
        private RecyclerView horizontalRecyclerView;
        private ArrayList<Product> horizontalScrollModelList;
        private HorizontalScrollAdapter horizontalScrollAdapter;
    //horizontal product

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.productRecyclerView);

        listImageButton = rootView.findViewById(R.id.listImageButton);
        gridImageButoon = rootView.findViewById(R.id.gridImageButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        clearAll();

        getDataFromFirebase();


        //banner slider
        bannerSliderViewPager = rootView.findViewById(R.id.bannerSliderViewPager);
        bannerList = new ArrayList<Banner>();

        bannerList.add(new Banner(R.drawable.banner4,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner5,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner,"#FFFFFF"));

        bannerList.add(new Banner(R.drawable.banner2,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner3,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner4,"#FFFFFF"));

        bannerList.add(new Banner(R.drawable.banner5,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner,"#FFFFFF"));
        bannerList.add(new Banner(R.drawable.banner2,"#FFFFFF"));

        BannerAdapter bannerAdapter = new BannerAdapter(bannerList);
        bannerSliderViewPager.setAdapter(bannerAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };

        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        bannerAdapter.notifyDataSetChanged();

        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopBannerSlideShow();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
        //bannerslider

        //horizontal Layout
         horizontalLayoutTitle = rootView.findViewById(R.id.horizontalScrollTitle);
         horizontalRecyclerView = rootView.findViewById(R.id.horizontalScrollRecyclerView);

          horizontalScrollModelList = new ArrayList<>();
            Query query = myRef.child("uploads");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Product product = new Product();

                    product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                    product.setProductName((String) snapshot.child("productName").getValue());
                    product.setPrice((String) snapshot.child("price").getValue());
                    product.setShortDesc((String) snapshot.child("shortDesc").getValue());
                    product.setLongDesc((String) snapshot.child("longDesc").getValue());
                    product.setSubCategory((String) snapshot.child("subCategory").getValue());
                    product.setLocation((String) snapshot.child("location").getValue());

                    horizontalScrollModelList.add(product);

                }

                horizontalScrollAdapter = new HorizontalScrollAdapter(context,horizontalScrollModelList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                horizontalRecyclerView.setLayoutManager(linearLayoutManager);
                horizontalRecyclerView.setAdapter(horizontalScrollAdapter);
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


        //horizontal Layout

        return rootView;
    }

    //bannerslider
    private void pageLooper(){
        if(currentPage == bannerList.size() -2){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if(currentPage == 1){
            currentPage = bannerList.size() -3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }

    private void startBannerSlideShow(){
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage >= bannerList.size()){
                    currentPage =1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }

    private void stopBannerSlideShow(){
        timer.cancel();
    }
        //bannerslider

    //for searching purpose


    //for poducts
    private void getDataFromFirebase() {
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
                    product.setSubCategory((String) snapshot.child("subCategory").getValue());

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

                gridImageButoon.setOnClickListener(new View.OnClickListener() {
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


        }
    }
}
