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
import com.example.punayog.model.HorizontalScrollModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HorizontalScrollModel> horizontalScrollModelList;


    public HorizontalScrollAdapter(Context context, ArrayList<HorizontalScrollModel> horizontalScrollModelList) {
        this.context = context;
        this.horizontalScrollModelList = horizontalScrollModelList;
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
        HorizontalScrollModel horizontalScrollModel = horizontalScrollModelList.get(position);

        Picasso.get().load(horizontalScrollModel.getProductImage()).into(holder.productImage);
        holder.productTitle.setText(horizontalScrollModel.getProductTitle());
        holder.productShortDesc.setText(horizontalScrollModel.getProductShortDesc());
        holder.productPrice.setText(horizontalScrollModel.getProductPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return horizontalScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle, productShortDesc,productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.h_s_productImage);
            productTitle = itemView.findViewById(R.id.h_s_ProductTitle);
            productShortDesc = itemView.findViewById(R.id.h_s_productShortDesc);
            productPrice = itemView.findViewById(R.id.h_s_productPrice);
        }

    }
}
