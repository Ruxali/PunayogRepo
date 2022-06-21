package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punayog.model.Order;
import com.example.punayog.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SellerOrderDetailsActivity extends AppCompatActivity {
    private Order order;
    private ImageView orderedItemImage,orderStatusImageView;
    private TextView orderedItemTitle,orderedOrderId,orderedItemPrice,orderedSellerEmailAdd,orderedBuyerId,orderedProductId;
    private TextView orderStatus, orderedTime, orderedDate,finalAmount;
    private TextView billingEmail,billingNumber,billingAddress,billingName;
    private TextView shippingNumber,shippingAddress,shippingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_details);
        statusBarColor();

        //init
        orderedOrderId = findViewById(R.id.orderedOrderId);
        orderedItemImage = findViewById(R.id.orderedItemImage);
        orderedItemTitle = findViewById(R.id.orderedItemTitle);
        orderedItemPrice = findViewById(R.id.orderedItemPrice);
        orderedOrderId = findViewById(R.id.orderedOrderId);
        orderStatus = findViewById(R.id.orderStatus);
        orderedTime = findViewById(R.id.orderedTime);
        orderedDate = findViewById(R.id.orderedDate);
        finalAmount = findViewById(R.id.finalAmount);
        billingEmail = findViewById(R.id.billingEmail);
        billingNumber = findViewById(R.id.billingNumber);
        billingAddress = findViewById(R.id.billingAddress);
        billingName = findViewById(R.id.billingName);
        shippingNumber = findViewById(R.id.shippingNumber);
        shippingAddress = findViewById(R.id.shippingAddress);
        shippingName = findViewById(R.id.shippingName);
        orderedBuyerId = findViewById(R.id.orderedBuyerId);
        orderedSellerEmailAdd = findViewById(R.id.orderedSellerEmailAdd);
        orderedProductId = findViewById(R.id.orderedProductId);

        orderStatusImageView = findViewById(R.id.orderStatusImageView);

        Intent intent = getIntent();
        if (intent != null) {
            order = (Order) intent.getParcelableExtra("order");
        }

        //set value
        Picasso.get().load(order.getProductImage()).into(orderedItemImage);
        orderedItemTitle.setText(order.getOrderedProductName());
        orderedItemPrice.setText(order.getOrderedProductPrice());
        orderStatus.setText(order.getOrderStatus());
        orderedTime.setText(order.getCurrentTime());
        orderedDate.setText(order.getCurrentDate());
        finalAmount.setText(order.getTotalPrice());
        billingEmail.setText(order.getBillingEmail());
        billingNumber.setText(order.getBillingNumber());
        billingAddress.setText(order.getBillingAddress());
        billingName.setText(order.getBillingName());
        shippingNumber.setText(order.getShippingNumber());
        shippingAddress.setText(order.getShippingAddress());
        shippingName.setText(order.getShippingName());
        orderedOrderId.setText(order.getOrderId());
        orderedSellerEmailAdd.setText(order.getSellerEmail());
        orderedBuyerId.setText(order.getOrderedBuyerEmail());
        orderedProductId.setText(order.getOrderedProductId());

        //change status of order
        orderStatusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SellerOrderDetailsActivity.this, orderStatusImageView);
                popupMenu.inflate(R.menu.order_status_menu);

                //menu listener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ordered:
                                Map<String,Object> map = new HashMap<>();
                                map.put("orderStatus","Ordered");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to Ordered.", Toast.LENGTH_SHORT).show();

                                            }

                                        });
                                return true;

                            case R.id.packaged:
                                Map<String,Object> map2 = new HashMap<>();
                                map2.put("orderStatus","Packaged");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to Packaged.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SellerOrderDetailsActivity.this, MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                                            }
                                        });
                                return true;

                            case R.id.sentForDelivery:
                                Map<String,Object> map3 = new HashMap<>();
                                map3.put("orderStatus","Sent for Delivery");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map3)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to Sent for Delivery.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SellerOrderDetailsActivity.this, MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                                            }
                                        });
                                return true;

                            case R.id.onTheWay:
                                Map<String,Object> map4 = new HashMap<>();
                                map4.put("orderStatus","On the Way");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map4)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to On the Way.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SellerOrderDetailsActivity.this, MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                                            }
                                        });
                                return true;

                            case R.id.delivered:
                                Map<String,Object> map5 = new HashMap<>();
                                map5.put("orderStatus","Delivered");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map5)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to Delivered.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SellerOrderDetailsActivity.this, MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                                            }
                                        });
                                return true;

                            case R.id.cancelled:
                                Map<String,Object> map6 = new HashMap<>();
                                map6.put("orderStatus","Cancelled");

                                FirebaseDatabase.getInstance().getReference().child("orders")
                                        .child(orderedOrderId.getText().toString()).updateChildren(map6)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SellerOrderDetailsActivity.this, "Order status changed to Cancelled.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SellerOrderDetailsActivity.this, MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);}
                                        });
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
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

    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}