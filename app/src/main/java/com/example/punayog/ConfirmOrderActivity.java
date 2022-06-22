package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punayog.adapter.OrderAdapter;
import com.example.punayog.model.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {

    private ImageButton logoutButton;
    private static final String FILE_NAME = "myFile";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button orderConfirmButton;
    private ImageView orderConfirmBackImageView;
    private TextView shippingNameConfirm, shippingAddressConfirm, shippingNumberConfirm;
    private TextView billingNameConfirm, billingAddressConfirm, billingNumberConfirm, billingEmailConfirm, orderId;
    private TextView orderedProductName, orderedProductPrice, orderedProductId, orderedBuyerEmail, orderedCartId,orderedSellerEmail,orderedProductImage;
    private TextView orderedPaymentMethod,orderedPaymentStatus;
    private TextView finalAmount;
    String orderID;
    private Button codPayment;
    KhaltiButton khaltiPayment;

    //firebase
    private DatabaseReference orderReference, reference, productReference;

    //ordered products
    private RecyclerView confirmOrderRecyclerView;
    private ArrayList<CartModel> orderArrayList;
    private OrderAdapter orderAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        statusBarColor();

        //init
        logoutButton = findViewById(R.id.logoutButton);
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
        confirmOrderRecyclerView = findViewById(R.id.confirmOrderRecyclerView);
        orderedPaymentMethod = findViewById(R.id.orderedPaymentMethod);
        orderedPaymentStatus = findViewById(R.id.orderedPaymentStatus);
        orderId = findViewById(R.id.orderID);
        khaltiPayment = findViewById(R.id.khaltiPayment);
        codPayment = findViewById(R.id.codPayment);

        //for ordered products
        orderedProductName = findViewById(R.id.orderedProductName);
        orderedProductId = findViewById(R.id.orderedProductId);
        orderedProductPrice = findViewById(R.id.orderedProductPrice);
        orderedBuyerEmail = findViewById(R.id.orderedBuyerEmail);
        orderedCartId = findViewById(R.id.orderedCartId);
        orderedSellerEmail = findViewById(R.id.orderedSellerEmail);
        orderedProductImage = findViewById(R.id.orderedProductImage);

        //firebase
        orderReference = FirebaseDatabase.getInstance().getReference("orders");

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
        long price= 0;
        DecimalFormat decimalFormat = new DecimalFormat("#");
        try {
            price = decimalFormat.parse(finalAmounts).longValue()*100;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //values for products
        reference = FirebaseDatabase.getInstance().getReference();
        String buyerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        orderArrayList = new ArrayList<>();

        Query query = reference.child("cart");

        query.orderByChild("buyerEmail").equalTo(buyerEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartModel cartModel = new CartModel();

                    String image = cartModel.setImage((String) snapshot.child("productImage").getValue());
                    String name = cartModel.setName((String) snapshot.child("productName").getValue());
                    String price = cartModel.setPrice((String) snapshot.child("productPrice").getValue());
                    String id = cartModel.setProductId((String) snapshot.child("productId").getValue());
                    String email = cartModel.setBuyerEmail((String) snapshot.child("buyerEmail").getValue());
                    String cartId = cartModel.setCartId((String) snapshot.child("cartId").getValue());
                    String sellerEmail = cartModel.setBuyerEmail((String) snapshot.child("sellerEmail").getValue());

                    orderArrayList.add(cartModel);

                    orderedProductName.setText(name);
                    orderedProductId.setText(id);
                    orderedProductPrice.setText(price);
                    orderedBuyerEmail.setText(email);
                    orderedCartId.setText(cartId);
                    orderedSellerEmail.setText(sellerEmail);
                    orderedProductImage.setText(image);
                }

                orderAdapter = new OrderAdapter(context, orderArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                confirmOrderRecyclerView.setLayoutManager(layoutManager);
                confirmOrderRecyclerView.setHasFixedSize(true);

                confirmOrderRecyclerView.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //cod
        codPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConfirmOrderActivity.this, "Cash On Delivery Selected", Toast.LENGTH_SHORT).show();
                orderedPaymentMethod.setText("Cash On Delivery");
                orderedPaymentStatus.setText("Pending");
            }
        });

        //payment gateway
        khaltiImplement(orderedProductId.getText().toString(),orderedProductName.getText().toString(),price);

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

                        orderID = orderReference.push().getKey();
                        orderId.setText(orderID);

                        orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                HashMap<String, Object> orderMap = new HashMap<>();
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
                                orderMap.put("orderId", orderId.getText().toString());
                                orderMap.put("paymentMethod", orderedPaymentMethod.getText().toString());
                                orderMap.put("paymentStatus", orderedPaymentStatus.getText().toString());

                                orderMap.put("orderedProductName", orderedProductName.getText().toString());
                                orderMap.put("orderedProductId", orderedProductId.getText().toString());
                                orderMap.put("orderedProductPrice", orderedProductPrice.getText().toString());
                                orderMap.put("orderedBuyerEmail", orderedBuyerEmail.getText().toString());
                                orderMap.put("orderedCartId", orderedCartId.getText().toString());
                                orderMap.put("sellerEmail",orderedSellerEmail.getText().toString());
                                orderMap.put("productImage",orderedProductImage.getText().toString());
                                orderMap.put("orderStatus","Ordered");

//                                orderMap.put("orderProducts",orders.toString());


                                orderReference.child(orderID).updateChildren(orderMap).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(ConfirmOrderActivity.this, "Your Order has been Confirmed!", Toast.LENGTH_SHORT).show();

                                                //change product status
                                                changeStatus();

                                                Intent intent1 = new Intent(ConfirmOrderActivity.this, MainActivity.class);
                                                startActivity(intent1);

                                                removeCartItems();

                                                //for product
//                                                ArrayList<CartModel> orderedProductsAL = new ArrayList<>();
//                                                orderedProductsAL.addAll(orderArrayList);
//                                                productReference = FirebaseDatabase.getInstance().getReference("orders");
//                                                productReference.push().setValue(orderedProductsAL).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                                    }
//                                                });

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

        //for logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderActivity.this);
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);

                                finish();
                                auth.signOut();
                                Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class);
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

    //khalti

    public void khaltiImplement(String productId, String productName,long productPrice){
        Config.Builder builder = new Config.Builder(Constant.pub, productId, productName, productPrice, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                Toast.makeText(ConfirmOrderActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                Toast.makeText(ConfirmOrderActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                orderedPaymentMethod.setText("Khalti");
                orderedPaymentStatus.setText("Paid");
            }
        });

        Config config = builder.build();
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, config);
        khaltiPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khaltiCheckOut.show();
            }
        });
    }

    //to change status of product
    private void changeStatus() {
        String usedProductId = orderedProductId.getText().toString();
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference();
        Query productQuery = productRef.child("uploads").orderByChild("productId").equalTo(usedProductId);
        productQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", "0");

                    productRef.child("uploads").child(ds.getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //empty cart after order placed
    private void removeCartItems() {
        String usedCartId = orderedCartId.getText().toString();
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference();
        Query cartQuery = cartRef.child("cart").orderByChild("cartId").equalTo(usedCartId);
        cartQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    public void onOrderBackClick(View view) {
        startActivity(new Intent(this, OrderActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}