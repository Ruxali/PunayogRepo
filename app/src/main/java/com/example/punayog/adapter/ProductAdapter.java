package com.example.punayog.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.punayog.AddProductActivity;
import com.example.punayog.ProductDetailsActivity;
import com.example.punayog.R;
import com.example.punayog.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder, int index) {

        Product product = productArrayList.get(index);

        //setting value
        productViewHolder.productName.setText(product.getProductName());
        productViewHolder.productPrice.setText(product.getPrice());
        productViewHolder.productShortDesc.setText(product.getShortDesc());
        productViewHolder.productLocation.setText(product.getLocation());
        Picasso.get().load(product.getmImageUrl()).into(productViewHolder.productImage);

        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return productArrayList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productShortDesc;
        private TextView productLocation;



        public ProductViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productShortDesc = itemView.findViewById(R.id.productShortDescription);
            productLocation = itemView.findViewById(R.id.productLocation);
        }

    }

}
