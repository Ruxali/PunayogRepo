package com.example.punayog.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.ProductDetailsActivity;
import com.example.punayog.R;
import com.example.punayog.SellerOrderDetailsActivity;
import com.example.punayog.model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SellerOrderAdapter extends RecyclerView.Adapter<SellerOrderAdapter.OrderViewHolder> {

    private Context context;
    public ArrayList<Order> orderArrayList;

    public SellerOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @Override
    public SellerOrderAdapter.OrderViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_order_item_list, viewGroup, false);
        return new SellerOrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SellerOrderAdapter.OrderViewHolder orderViewHolder, int index) {
        Order order = orderArrayList.get(index);

        //setting value
        orderViewHolder.sellerSideOrderId.setText(order.getOrderId());
        orderViewHolder.orderBillingAddress.setText(order.getBillingAddress());
        orderViewHolder.orderBillingEmail.setText(order.getBillingEmail());
        orderViewHolder.orderBillingName.setText(order.getBillingName());
        orderViewHolder.orderBillingNumber.setText(order.getBillingNumber());
        orderViewHolder.orderShippingAddress.setText(order.getShippingAddress());
        orderViewHolder.orderShippingName.setText(order.getShippingName());
        orderViewHolder.orderShippingNumber.setText(order.getShippingNumber());
        orderViewHolder.orderCurrentDate.setText(order.getCurrentDate());
        orderViewHolder.orderCurrentTime.setText(order.getCurrentTime());
        orderViewHolder.orderProductId.setText(order.getOrderedProductId());
        orderViewHolder.orderProductName.setText(order.getOrderedProductName());
        orderViewHolder.orderProductPrice.setText(order.getOrderedProductPrice());
        orderViewHolder.orderSellerEmail.setText(order.getSellerEmail());
        orderViewHolder.orderTotalPrice.setText(order.getTotalPrice());
        orderViewHolder.orderBuyerId.setText(order.getOrderedBuyerEmail());
        orderViewHolder.orderStatus.setText(order.getOrderStatus());
        Picasso.get().load(order.getProductImage()).into(orderViewHolder.orderProductImage);

        orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SellerOrderDetailsActivity.class);
                intent.putExtra("order", order);
                view.getContext().startActivity(intent);
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
        private TextView orderBillingAddress;
        private TextView orderBillingName;
        private TextView orderBillingNumber;
        private TextView orderBillingEmail;
        private TextView orderCurrentDate;
        private TextView orderCurrentTime;
        private TextView orderProductId;
        private TextView orderProductName;
        private TextView orderProductPrice,orderStatus;
        private TextView orderSellerEmail,orderShippingAddress,orderShippingName,orderShippingNumber,orderTotalPrice,orderBuyerId;



        public OrderViewHolder(View itemView) {
            super(itemView);
            orderProductImage = itemView.findViewById(R.id.sellerSideProductImage);
            sellerSideOrderId = itemView.findViewById(R.id.sellerSideOrderId);
            orderBillingAddress = itemView.findViewById(R.id.orderBillingAddress);
            orderBillingName = itemView.findViewById(R.id.orderBillingName);
            orderBillingNumber = itemView.findViewById(R.id.orderBillingNumber);
            orderBillingEmail = itemView.findViewById(R.id.orderBillingEmail);
            orderCurrentDate = itemView.findViewById(R.id.orderCurrentDate);
            orderCurrentTime = itemView.findViewById(R.id.orderCurrentTime);
            orderProductId = itemView.findViewById(R.id.orderProductId);
            orderProductName = itemView.findViewById(R.id.orderProductName);
            orderProductPrice = itemView.findViewById(R.id.orderProductPrice);
            orderSellerEmail = itemView.findViewById(R.id.orderSellerEmail);
            orderShippingAddress = itemView.findViewById(R.id.orderShippingAddress);
            orderShippingName = itemView.findViewById(R.id.orderShippingName);
            orderShippingNumber = itemView.findViewById(R.id.orderShippingNumber);
            orderTotalPrice = itemView.findViewById(R.id.orderTotalPrice);
            orderBuyerId = itemView.findViewById(R.id.orderBuyerId);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}
