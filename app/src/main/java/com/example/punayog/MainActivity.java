package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.punayog.adapter.CustomExpandableListAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.punayog.helper.FragmentNavigationManager;
import com.example.punayog.interfaces.NavigationManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FILE_NAME = "myFile";
    BottomNavigationView bottomNavigationView;
    NestedScrollView scrollView;
    CoordinatorLayout mainCoordinatorLayout;

    ListView listView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    YourListingsFragment listingsFragment = new YourListingsFragment();
    CartFragment cartFragment = new CartFragment();

    SearchFragment searchFragment;
    private ImageButton logoutButton, searchButton;

    //connectivity checking
    BroadcastReceiver broadcastReceiver = null;

    //side navigation
    ExpandableListView expandableListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String, List<String>> lstChild;
    private NavigationManager navigationManager;
    private String titleLabel;
    private EditText searchEdittext;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private DatabaseReference ref;
    private RecyclerView rv;
    String searchText;

    private FirebaseAuth database;
    FirebaseUser firebaseuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        mainCoordinatorLayout = findViewById(R.id.mainCoordinatorLayout);

        logoutButton = findViewById(R.id.logoutButton);


        listView = findViewById(R.id.productListView);

        statusBarColor();
        ref = FirebaseDatabase.getInstance().getReference("uploads");

        //side navigation

        drawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        navigationView = findViewById(R.id.sideNavView);
        navigationView.bringToFront();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //search
        searchButton = findViewById(R.id.searchButton);
        searchEdittext = findViewById(R.id.searchEdittext);
        searchFragment = new SearchFragment();

        //connectivity checking
        broadcastReceiver = new InternetReceiver();
        checkStatus();

        expandableListView = findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getmInstance(this);
        initItems();

        View listHeaderView = getLayoutInflater().inflate(R.layout.header, null, false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setUpDrawer();

        if (savedInstanceState == null) {
            selectFirstItemAsDefault();
        }
//
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        //check if user is logged in
        database = FirebaseAuth.getInstance();
        firebaseuser = database.getCurrentUser();
        if(firebaseuser == null){
            logoutButton.setVisibility(View.GONE);
        }else{
            logoutButton.setVisibility(View.VISIBLE);
        }

        //for logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

                                finish();
                                auth.signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

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
                    case R.id.yourListings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, listingsFragment).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                        return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        //search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                searchText = searchEdittext.getText().toString();
                fragmentTransaction.replace(R.id.container, searchFragment).commit();
                closeKeyboard();
//                searchEdittext.setText(" ");
            }
        });


        //scroll view
        scrollView = findViewById(R.id.mainActivityScroll);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY >= oldScrollY){
                    mainCoordinatorLayout.setVisibility(View.GONE);
                }
                else{
                    mainCoordinatorLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_CALL);
        filter.addAction(Intent.ACTION_ANSWER);
        registerReceiver(mIntentReceiver, filter);
    }

    //connectivity checking
    private void checkStatus() {
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_CALL);
//        filter.addAction(Intent.ACTION_ANSWER);
//
//        registerReceiver(mIntentReceiver, filter);
    }



    public String getMyData() {
        return searchText;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    //side navigation
    private void selectFirstItemAsDefault() {
        if (navigationManager != null) {
            String firstItem = lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setUpDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawersItem() {
        adapter = new CustomExpandableListAdapter(this, lstTitle, lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                String selectedItem = ((List) (lstChild.get(lstTitle.get(groupPosition))))
                        .get(childPosition).toString();
                titleLabel = selectedItem;
                System.out.println(titleLabel);


                if (items[0].equals(lstTitle.get(groupPosition))) {
                    navigationManager.showFragment(selectedItem);
                } else if (items[1].equals(lstTitle.get(groupPosition))) {
                    navigationManager.showFragment(selectedItem);
                } else if (items[2].equals(lstTitle.get(groupPosition))) {
                    navigationManager.showFragment(selectedItem);
                } else if (items[3].equals(lstTitle.get(groupPosition))) {
                    navigationManager.showFragment(selectedItem);
                } else {
                    throw new IllegalArgumentException();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    private void genData() {
        List<String> title = Arrays.asList("Accessories", "Apparels", "Books", "Electronics");
        List<String> childItem = Arrays.asList("Bags", "Shoes", "Sunglasses", "Watches");
        List<String> childItem2 = Arrays.asList("Children", "Men", "Unisex", "Women");
        List<String> childItem3 = Arrays.asList("Course", "Fantasy", "Fiction", "Non-Fiction");
        List<String> childItem4 = Arrays.asList("Laptop", "Microwave", "Mobile Phones", "Television");


        lstChild = new TreeMap<>();
        lstChild.put(title.get(0), childItem);
        lstChild.put(title.get(1), childItem2);
        lstChild.put(title.get(2), childItem3);
        lstChild.put(title.get(3), childItem4);

        lstTitle = new ArrayList<>(lstChild.keySet());
    }


    private void initItems() {
        items = new String[]{"Accessories", "Apparels", "Books", "Electronics"};
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    public EditText getSearchEdittext() {
        return searchEdittext;
    }

//    //for signing out
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_CALL);
//        filter.addAction(Intent.ACTION_ANSWER);
//
//        registerReceiver(mIntentReceiver, filter);
//    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equalsIgnoreCase("android.intent.category.HOME")) {
                auth.signOut();
            } else if (action.equalsIgnoreCase("android.intent.action.SCREEN_OFF")) {
                auth.signOut();


            } else if (action.equalsIgnoreCase("android.intent.action.DIAL")) {
                auth.signOut();

            } else if (action.equalsIgnoreCase("android.intent.action.CALL")) {
                auth.signOut();

            }


        }
    };

}