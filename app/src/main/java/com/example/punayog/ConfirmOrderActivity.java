package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punayog.model.CartModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Button orderConfirmButton;
    private ImageView orderConfirmBackImageView;
    private TextView shippingNameConfirm, shippingAddressConfirm, shippingNumberConfirm;
    private TextView billingNameConfirm, billingAddressConfirm, billingNumberConfirm, billingEmailConfirm;
    private TextView finalAmount;

    //cart
    ArrayList<CartModel> cartModelArrayList;
    //firebase
    private DatabaseReference orderReference,cartReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        statusBarColor();

        //init
        orderConfirmButton = findViewById(R.id.orderConfirmButton);
        orderConfirmBackImageView = findViewById(R.id.orderConfirmBackImageView);
        shippingNameConfirm = findViewById(R.id.shippingNameConfirm);
        shippingAddressConfirm = findViewById(R.id.shippingAddressConfirm);
        shippingNumberConfirm = findViewById(R.id.shippingNumberConfirm);
        billingNameConfirm = findViewById(R.id.billingNameConfirm);
        billingEmailConfirm = findViewById(R.id.billingEmailConfirm);
        billingNumberConfirm = findViewById(R.id.billingNumberConfirm);
        billingAddressConfirm = findViewById(R.id.billingAddressConfirm);
        finalAmount = findViewById(R.id.finalAmount);

        //firebase
        orderReference = FirebaseDatabase.getInstance().getReference("orders");
        cartReference = FirebaseDatabase.getInstance().getReference();

        //add values to textview
        Intent intent = getIntent();
        String shippingName = intent.getStringExtra("shippingName");
        shippingNameConfirm.setText(shippingName);
        String shippingAddress = intent.getStringExtra("shippingAddress");
        shippingAddressConfirm.setText(shippingAddress);
        String shippingNumber = intent.getStringExtra("shippingNumber");
        shippingNumberConfirm.setText(shippingNumber);
        String billingName = intent.getStringExtra("billingName");
        billingNameConfirm.setText(billingName);
        String billingAddress = intent.getStringExtra("billingAddress");
        billingAddressConfirm.setText(billingAddress);
        String billingNumber = intent.getStringExtra("billingNumber");
        billingNumberConfirm.setText(billingNumber);
        String billingEmail = intent.getStringExtra("billingEmail");
        billingEmailConfirm.setText(billingEmail);
        String finalAmounts = intent.getStringExtra("completeTotalAmount");
        finalAmount.setText(finalAmounts);

        //confirm order
        orderConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder orderAlert = new AlertDialog.Builder(ConfirmOrderActivity.this);
                orderAlert.setTitle("Order Confirmation!");
                orderAlert.setMessage("Are you sure you want to confirm your order?");

                orderAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String saveCurrentTime, saveCurrentDate;

                        Calendar calForData = Calendar.getInstance();

                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                        saveCurrentDate = currentDate.format(calForData.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForData.getTime());

                        String orderID = orderReference.push().getKey();

                        orderReference.child(orderID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                final HashMap<String, Object> orderMap = new HashMap<>();
                                orderMap.put("totalPrice", finalAmount.getText().toString());
                                orderMap.put("shippingName", shippingNameConfirm.getText().toString());
                                orderMap.put("shippingAddress", shippingAddressConfirm.getText().toString());
                                orderMap.put("shippingNumber", shippingNumberConfirm.getText().toString());
                                orderMap.put("billingName", billingNameConfirm.getText().toString());
                                orderMap.put("billingAddress", billingAddressConfirm.getText().toString());
                                orderMap.put("billingEmail", billingEmailConfirm.getText().toString());
                                orderMap.put("billingNumber", billingNumberConfirm.getText().toString());
                                orderMap.put("currentTime", saveCurrentTime);
                                orderMap.put("currentDate", saveCurrentDate);

                                orderReference.child(orderID).updateChildren(orderMap).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(ConfirmOrderActivity.this, "Your Order has been Confirmed!", Toast.LENGTH_SHORT).show();

                                                //change product status
//                                                changeStatus();
                                                
                                                Intent intent1 = new Intent(ConfirmOrderActivity.this,MainActivity.class);
                                                startActivity(intent1);

//                                                removeCartItems();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ConfirmOrderActivity.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ConfirmOrderActivity.this, "Something went wrong: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                orderAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                orderAlert.create();
                orderAlert.show();
            }
        });
    }

//    private void changeStatus() {
//
//    }

    //empty cart after order is placed
//    private void removeCartItems() {
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
//        cartReference.child("billingEmail").equalTo(userID);
//        cartReference.removeValue();
//
//    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onOrderBackClick(View view) {
        startActivity(new Intent(this, OrderActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}