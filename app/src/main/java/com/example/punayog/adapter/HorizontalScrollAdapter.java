package com.example.punayog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.model.HorizontalScrollModel;

import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {

   private List<HorizontalScrollModel> horizontalScrollModelList;

    public HorizontalScrollAdapter(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = horizontalScrollModelList.get(position).getProductImage();
        String title = horizontalScrollModelList.get(position).getProductTitle();
        String shortDesc = horizontalScrollModelList.get(position).getProductShortDesc();
        String price = horizontalScrollModelList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductShortDesc(shortDesc);
        holder.setProductPrice(price);
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

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductShortDesc(String shortDesc){
            productShortDesc.setText(shortDesc);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }
    }
}
