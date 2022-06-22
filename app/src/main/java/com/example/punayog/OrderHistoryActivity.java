package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.punayog.adapter.BuyerOrderAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.adapter.SellerOrderAdapter;
import com.example.punayog.model.Order;
import com.example.punayog.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private ImageButton logoutButton;
    private static final String FILE_NAME = "myFile";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private RecyclerView orderRecyclerView;
    private ArrayList<Order> orderArrayList;
    private Context context;
    private BuyerOrderAdapter buyerAdapter;
    DatabaseReference reference;

    //for no orders
    LinearLayout noOrderLayout;
    ScrollView orderScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        statusBarColor();

        logoutButton = findViewById(R.id.logoutButton);
        orderRecyclerView = findViewById(R.id.orderedProductRecyclerViewBuyer);
        reference = FirebaseDatabase.getInstance().getReference();

        noOrderLayout = findViewById(R.id.noOrderLayout);
        orderScrollView = findViewById(R.id.orderScrollView);

        //fetch data
        if(orderArrayList == null){
            noOrderLayout.setVisibility(View.VISIBLE);
            orderScrollView.setVisibility(View.GONE);
        }else {
            noOrderLayout.setVisibility(View.GONE);
            orderScrollView.setVisibility(View.VISIBLE);
            orderArrayList = new ArrayList<>();
            String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Query query = reference.child("orders");

            query.orderByChild("orderedBuyerEmail").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Order order = new Order();

                        order.setOrderId((String) snapshot.child("orderId").getValue());
                        order.setOrderStatus((String) snapshot.child("orderStatus").getValue());
                        order.setBillingAddress((String) snapshot.child("billingAddress").getValue());
                        order.setBillingEmail((String) snapshot.child("billingEmail").getValue());
                        order.setBillingName((String) snapshot.child("billingName").getValue());
                        order.setBillingNumber((String) snapshot.child("billingNumber").getValue());
                        order.setCurrentDate((String) snapshot.child("currentDate").getValue());
                        order.setCurrentTime((String) snapshot.child("currentTime").getValue());
                        order.setOrderedProductId((String) snapshot.child("orderedProductId").getValue());
                        order.setOrderedProductName((String) snapshot.child("orderedProductName").getValue());
                        order.setOrderedProductPrice((String) snapshot.child("orderedProductPrice").getValue());
                        order.setSellerEmail((String) snapshot.child("sellerEmail").getValue());
                        order.setShippingAddress((String) snapshot.child("shippingAddress").getValue());
                        order.setShippingName((String) snapshot.child("shippingName").getValue());
                        order.setShippingNumber((String) snapshot.child("shippingNumber").getValue());
                        order.setProductImage((String) snapshot.child("productImage").getValue());
                        order.setTotalPrice((String) snapshot.child("totalPrice").getValue());

                        orderArrayList.add(order);

                    }

                    buyerAdapter = new BuyerOrderAdapter(context, orderArrayList);
                    LinearLayoutManager linearLayoutManager;
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    orderRecyclerView.setLayoutManager(linearLayoutManager);

                    orderRecyclerView.setAdapter(buyerAdapter);
                    buyerAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //for logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderHistoryActivity.this);
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

                                finish();
                                auth.signOut();
                                Intent intent = new Intent(OrderHistoryActivity.this, LoginActivity.class);
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
    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onOrderHistoryBackClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}