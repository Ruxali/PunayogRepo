package com.example.punayog.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.SellerOrderDetailsActivity;
import com.example.punayog.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BuyerOrderAdapter extends RecyclerView.Adapter<BuyerOrderAdapter.OrderViewHolder> {

    private Context context;
    public ArrayList<Order> orderArrayList;

    public BuyerOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @Override
    public BuyerOrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_order_history_item, viewGroup, false);
        return new BuyerOrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuyerOrderAdapter.OrderViewHolder orderViewHolder, int index) {
        Order order = orderArrayList.get(index);

        //setting value
        orderViewHolder.sellerSideOrderId.setText(order.getOrderId());
        orderViewHolder.orderCurrentDate.setText(order.getCurrentDate());
        orderViewHolder.orderCurrentTime.setText(order.getCurrentTime());
        orderViewHolder.orderProductName.setText(order.getOrderedProductName());
        orderViewHolder.orderProductPrice.setText(order.getOrderedProductPrice());
        orderViewHolder.orderSellerEmail.setText(order.getSellerEmail());
        orderViewHolder.orderTotalPrice.setText(order.getTotalPrice());
        orderViewHolder.orderStatus.setText(order.getOrderStatus());
        Picasso.get().load(order.getProductImage()).into(orderViewHolder.orderProductImage);

        String status = order.getOrderStatus();
        if(status.equals("Ordered")){
            orderViewHolder.deleteOrderButton.setVisibility(View.VISIBLE);
            orderViewHolder.rateSellerBUtton.setVisibility(View.GONE);
        }else if(status.equals("Delivered")){
            orderViewHolder.rateSellerBUtton.setVisibility(View.VISIBLE);
            orderViewHolder.deleteOrderButton.setVisibility(View.GONE);
        }
        else{
            orderViewHolder.rateSellerBUtton.setVisibility(View.GONE);
            orderViewHolder.deleteOrderButton.setVisibility(View.GONE);
        }

        orderViewHolder.deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("orderStatus","Cancelled");

                FirebaseDatabase.getInstance().getReference().child("orders")
                        .child(order.getOrderId()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                orderViewHolder.rateSellerBUtton.setVisibility(View.GONE);
                orderViewHolder.deleteOrderButton.setVisibility(View.GONE);
            }
        });

        //rate the seller
        orderViewHolder.rateSellerBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder rating = new AlertDialog.Builder(view.getContext());
                rating.setTitle("Rate the Seller");

                LayoutInflater layoutInflater = rating.create().getLayoutInflater();
                View ratingView = layoutInflater.inflate(R.layout.activity_rating,null);
                rating.setView(ratingView);

                RatingBar ratingBar;

                ratingBar = ratingView.findViewById(R.id.ratingBar);

                rating.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String ratings = "" + ratingBar.getRating();

                        //set to db: db> users> ratings
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ratings");
                        String ratingID = reference.push().getKey();
                        reference.child(ratingID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //set data in hashmap
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("userId",""+userID);
                                hashMap.put("rating",""+ratings);
                                hashMap.put("ratingID", ""+ratingID);
                                hashMap.put("sellerEmail", ""+ order.getSellerEmail());

                                reference.child(ratingID).updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(rating.getContext(), "Review Added Successfully", Toast.LENGTH_SHORT).show();
                                                orderViewHolder.rateSellerBUtton.setVisibility(View.GONE);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(rating.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                rating.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        orderViewHolder.rateSellerBUtton.setVisibility(View.VISIBLE);
                    }
                });
                rating.create();
                rating.show();
                orderViewHolder.rateSellerBUtton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    protected class OrderViewHolder extends RecyclerView.ViewHolder{
        private ImageView orderProductImage;
        private TextView sellerSideOrderId;
        private TextView orderCurrentDate;
        private TextView orderCurrentTime;
        private TextView orderProductName;
        private TextView orderProductPrice,orderStatus;
        private TextView orderSellerEmail,orderTotalPrice;
        public Button deleteOrderButton;
        public Button rateSellerBUtton;


        public OrderViewHolder(View itemView) {
            super(itemView);
            orderProductImage = itemView.findViewById(R.id.orderedItemImageView);
            sellerSideOrderId = itemView.findViewById(R.id.sideOrderId);
            orderCurrentDate = itemView.findViewById(R.id.orderDate);
            orderCurrentTime = itemView.findViewById(R.id.orderTime);
            orderProductName = itemView.findViewById(R.id.orderedItemTitle);
            orderProductPrice = itemView.findViewById(R.id.orderedItemPrice);
            orderSellerEmail = itemView.findViewById(R.id.orderSellerEmailAddress);
            orderTotalPrice = itemView.findViewById(R.id.orderTotalPrice);
            orderStatus = itemView.findViewById(R.id.orderStatusBuyer);
            deleteOrderButton = itemView.findViewById(R.id.deleteOrderButton);
            rateSellerBUtton = itemView.findViewById(R.id.rateSellerButton);
        }
    }
}
