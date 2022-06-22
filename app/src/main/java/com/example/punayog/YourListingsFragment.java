package com.example.punayog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.ListingAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.adapter.SellerOrderAdapter;
import com.example.punayog.model.Order;
import com.example.punayog.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourListingsFragment extends Fragment {

    private TextView topic,orders;
    private RecyclerView listingRecyclerView,orderedProductRecyclerView;
    private DatabaseReference myRef;
    private FirebaseAuth database;
    private ConstraintLayout listingLayout;
    FirebaseUser firebaseuser;
    ScrollView listingScrollView, orderScrollView;

    //variables for listing
    private ArrayList<Product> productArrayList;
    private Context context;
    private ListingAdapter listProductAdapter;
    ImageView noProductImage;

    //variables for order
    private ArrayList<Order> orderArrayList;
    private SellerOrderAdapter sellerOrderAdapter;
    LinearLayout noOrderLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_your_listings, container, false);

        listingRecyclerView = rootView.findViewById(R.id.listedProductRecyclerView);
        listingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingLayout = rootView.findViewById(R.id.listingLayout);

        orderedProductRecyclerView = rootView.findViewById(R.id.orderedProductRecyclerView);
        orderedProductRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orders = rootView.findViewById(R.id.orders);
        topic = rootView.findViewById(R.id.topic);
        listingScrollView = rootView.findViewById(R.id.listingScrollView);
        orderScrollView = rootView.findViewById(R.id.orderScrollView);
        noProductImage=rootView.findViewById(R.id.noProductImage);
        noOrderLayout=rootView.findViewById(R.id.noOrderLayout);

        orderScrollView.setVisibility(View.GONE);
        noProductImage.setVisibility(View.GONE);
        noOrderLayout.setVisibility(View.GONE);


        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listingScrollView.setVisibility(View.VISIBLE);
                orderScrollView.setVisibility(View.GONE);

                topic.setBackgroundResource(R.drawable.shape_rect);
                orders.setBackgroundColor(getResources().getColor(R.color.navigation));
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listingScrollView.setVisibility(View.GONE);
                orderScrollView.setVisibility(View.VISIBLE);

               orders.setBackgroundResource(R.drawable.shape_rect);
               topic.setBackgroundColor(getResources().getColor(R.color.navigation));
            }
        });

        database = FirebaseAuth.getInstance();
        firebaseuser = database.getCurrentUser();
        if (firebaseuser == null) {
            AlertDialog.Builder profileAlert = new AlertDialog.Builder(getContext());
            profileAlert.setTitle("You are not Logged in yet!");
            profileAlert.setMessage("Do you want to login?");
            profileAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            });
            profileAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            });
            listingLayout.setVisibility(View.GONE);
            profileAlert.create();
            profileAlert.show();
        } else {
            listingLayout.setVisibility(View.VISIBLE);
            getDataFromFirebase();
            getOrderDetails();
        }
        return rootView;
    }

    private void getOrderDetails() {
        if (orderArrayList ==null){
            orderScrollView.setVisibility(View.GONE);
            noOrderLayout.setVisibility(View.VISIBLE);
        }else {
            orderScrollView.setVisibility(View.VISIBLE);
            noOrderLayout.setVisibility(View.GONE);
            orderArrayList = new ArrayList<>();

            Query query = myRef.child("orders");
            String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            query.orderByChild("sellerEmail").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        order.setOrderedBuyerEmail((String) snapshot.child("orderedBuyerEmail").getValue());
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

                    sellerOrderAdapter = new SellerOrderAdapter(context, orderArrayList);
                    LinearLayoutManager linearLayoutManager;
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    orderedProductRecyclerView.setLayoutManager(linearLayoutManager);
                    orderedProductRecyclerView.setAdapter(sellerOrderAdapter);
                    sellerOrderAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void getDataFromFirebase(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child("uploads");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query.orderByChild("sellerEmail").equalTo(userID),Product.class)
                .build();

        listProductAdapter = new ListingAdapter(options);
        listingRecyclerView.setAdapter(listProductAdapter);
        listProductAdapter.startListening();

    }


}
