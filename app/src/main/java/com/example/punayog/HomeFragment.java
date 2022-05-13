package com.example.punayog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;

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
import com.example.punayog.model.HorizontalScrollModel;

import com.example.punayog.adapter.SearchAdapter;
import com.example.punayog.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private ImageButton listImageButton, gridImageButoon;

    //Firebase
    private DatabaseReference myRef;
    private ArrayList<SearchDeal>list;

    //variables
    private ArrayList<Product> productArrayList;
    private Context context;
    private ProductAdapter productAdapter;

    //banner slider
    private ViewPager bannerSliderViewPager;
    private List<Banner> bannerList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    //banner slider

    //horizontal product
        private TextView horizontalLayoutTitle;
        private RecyclerView horizontalRecyclerView;
        private ArrayList<HorizontalScrollModel> horizontalScrollModelList;
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

        GetDataFromFirebase();


        //banner slider
        bannerSliderViewPager = rootView.findViewById(R.id.bannerSliderViewPager);
        bannerList = new ArrayList<Banner>();

        bannerList.add(new Banner(R.drawable.ic_baseline_shopping_cart_24,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.slider,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.accessories,"#ADD8E6"));

        bannerList.add(new Banner(R.drawable.ic_baseline_person_24,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.add_button,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.ic_baseline_logout_24,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.clothes,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.ic_baseline_call_24,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.ic_baseline_shopping_cart_24,"#ADD8E6"));

        bannerList.add(new Banner(R.drawable.slider,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.accessories,"#ADD8E6"));
        bannerList.add(new Banner(R.drawable.ic_baseline_person_24,"#ADD8E6"));

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
            Query query = myRef.child("uploads").orderByChild("subCategory");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HorizontalScrollModel horizontalScrollModel = new HorizontalScrollModel();

                    horizontalScrollModel.setProductImage((String) snapshot.child("mImageUrl").getValue());
                    horizontalScrollModel.setProductTitle((String) snapshot.child("productName").getValue());
                    horizontalScrollModel.setProductPrice((String) snapshot.child("price").getValue());
                    horizontalScrollModel.setProductShortDesc((String) snapshot.child("shortDesc").getValue());

                    horizontalScrollModelList.add(horizontalScrollModel);

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
    private void GetDataFromFirebase() {
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

            if (productAdapter != null) {
                productAdapter.notifyDataSetChanged();
            }
        }
        productArrayList = new ArrayList<>();
    }
}
