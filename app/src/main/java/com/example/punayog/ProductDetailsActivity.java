package com.example.punayog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.punayog.adapter.ImageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    BottomNavigationView productBottomNavigationView;

    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        statusBarColor();

        //Bottom Navigation
        productBottomNavigationView = findViewById(R.id.productBottomNavigationView);
        productBottomNavigationView.setBackground(null);

        //view pager
        //viewPager = findViewById(R.id.viewPager);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.accessories);
        imageList.add(R.drawable.books);
        imageList.add(R.drawable.accessories);
        imageList.add(R.drawable.clothes);

        ImageAdapter imageAdapter = new ImageAdapter(imageList);
        viewPager.setAdapter(imageAdapter);


    }
    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}
