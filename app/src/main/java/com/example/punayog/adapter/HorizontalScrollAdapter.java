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
import com.example.punayog.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> productArrayList;


    public HorizontalScrollAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    public HorizontalScrollAdapter() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productArrayList.get(position);

        Picasso.get().load(product.getmImageUrl()).into(holder.productImage);
        holder.productTitle.setText(product.getProductName());
        holder.productShortDesc.setText(product.getShortDesc());
        holder.productPrice.setText(product.getPrice());
        holder.productLocation.setText(product.getLocation());
        holder.productLongDesc.setText(product.getLongDesc());
        holder.productSubCategory.setText(product.getSubCategory());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                intent.putExtra("product",product);
                view.getContext().startActivity(intent);
            }
        });
    }

    private final int limit = 8;
    @Override
    public int getItemCount() {
        if(productArrayList.size() > limit){
            return limit;
        }else{
            return productArrayList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle, productShortDesc,productPrice,productLocation,productLongDesc,productSubCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.h_s_productImage);
            productTitle = itemView.findViewById(R.id.h_s_ProductTitle);
            productShortDesc = itemView.findViewById(R.id.h_s_productShortDesc);
            productPrice = itemView.findViewById(R.id.h_s_productPrice);
            productLocation = itemView.findViewById(R.id.h_s_productLocation);
            productLongDesc = itemView.findViewById(R.id.h_s_productLongDesc);
            productSubCategory = itemView.findViewById(R.id.h_s_productSubCategory);
        }

    }
}
