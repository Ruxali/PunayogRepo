package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.punayog.adapter.CustomExpandableListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    ListView listView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ChatFragment chatFragment = new ChatFragment();
    CartFragment cartFragment = new CartFragment();

    ExpandableListView expandableListView;
    private String[] items;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        listView = findViewById(R.id.productListView);


        statusBarColor();

        //side navigation

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.sideNavView);
        navigationView.bringToFront();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.navList);
        initItems();

        View listHeaderView = getLayoutInflater().inflate(R.layout.header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setUpDrawer();

        //fragments
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;

                    case R.id.chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
                        return true;

                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                        return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setUpDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened (View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed (View drawerView){
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("");
                invalidateOptionsMenu();
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
    }

    private void addDrawersItem() {
        adapter = new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
    }

    private void genData() {
        List<String> title = Arrays.asList("Accessories","Apparels","Books","Electronics");
        List<String> childItem=Arrays.asList("Bags", "Shoes", "Sunglasses", "Watches");
        List<String> childItem2=Arrays.asList("Children", "Men", "Unisex", "Women");
        List<String> childItem3=Arrays.asList("Course", "Fantasy", "Fiction", "Non-Fiction");
        List<String> childItem4=Arrays.asList("Laptop", "Microwave", "Mobile Phones", "Television");


        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),childItem);
        lstChild.put(title.get(1),childItem2);
        lstChild.put(title.get(2),childItem3);
        lstChild.put(title.get(3),childItem4);

        lstTitle = new ArrayList<>(lstChild.keySet());
    }

    private void initItems() {
        items = new String[]{"Accessories", "Apparels", "Books", "Electronics"};
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onAddClick(View view) {
        startActivity(new Intent(this, AddProductActivity.class));
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation_menu, menu);
        menuInflater.inflate(R.menu.side_navigation, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

}