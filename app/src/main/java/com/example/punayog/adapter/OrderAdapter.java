package com.example.punayog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.model.CartModel;
import com.example.punayog.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends  RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private Context context;
    private ArrayList<CartModel> orderArrayList;

    public OrderAdapter(Context context, ArrayList<CartModel> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_item, viewGroup, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int index) {
        CartModel cartModel = orderArrayList.get(index);

        //setting value
        orderViewHolder.productName.setText(cartModel.getName());
        orderViewHolder.productPrice.setText(cartModel.getPrice());
        orderViewHolder.buyerName.setText(cartModel.getBuyerName());
        orderViewHolder.buyerEmail.setText(cartModel.getBuyerEmail());
        orderViewHolder.buyerNumber.setText(cartModel.getBuyerNumber());
        orderViewHolder.productId.setText(cartModel.getProductId());
        Picasso.get().load(cartModel.getImage()).into(orderViewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView totalAmount;
        private ImageButton deleteButton;
        private TextView buyerName, buyerEmail,  buyerNumber,productId,orderId;

        public OrderViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartProductTitle);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            totalAmount = itemView.findViewById(R.id.totalPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            buyerName = itemView.findViewById(R.id.buyerName);
            buyerNumber = itemView.findViewById(R.id.buyerNumber);
            buyerEmail = itemView.findViewById(R.id.buyerEmail);
            productId = itemView.findViewById(R.id.cartProductID);
            orderId = itemView.findViewById(R.id.orderID);
        }

    }
}
