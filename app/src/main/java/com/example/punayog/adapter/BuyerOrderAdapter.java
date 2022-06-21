package com.example.punayog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.SellerOrderDetailsActivity;
import com.example.punayog.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        }else{
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
                orderViewHolder.deleteOrderButton.setVisibility(View.GONE);
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
        }
    }
}
