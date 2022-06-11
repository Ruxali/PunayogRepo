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
import com.example.punayog.model.SearchDeal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>  {

    private Context context;
    public ArrayList<SearchDeal> searchProductArrayList;


    public SearchAdapter(Context context, ArrayList<SearchDeal> searchProductArrayList) {
        this.context = context;
        this.searchProductArrayList = searchProductArrayList;
    }

    public SearchAdapter(ArrayList<SearchDeal> myList) {
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        return new SearchAdapter.SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder productViewHolder, int index) {

        SearchDeal search = searchProductArrayList.get(index);

        //setting value
        productViewHolder.productName.setText(search.getProductName());
        productViewHolder.productPrice.setText(search.getPrice());
        productViewHolder.productShortDesc.setText(search.getShortDesc());
        productViewHolder.productLocation.setText(search.getLocation());
        productViewHolder.productLongDesc.setText(search.getLongDesc());
        productViewHolder.productSubCategory.setText(search.getSubCategory());

        Picasso.get().load(search.getmImageUrl()).into(productViewHolder.productImage);

        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                intent.putExtra("product",search);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return searchProductArrayList.size();
    }




    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productShortDesc;
        private TextView productLocation;
        private TextView productLongDesc;
        private TextView productSubCategory;



        public SearchViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.dealId);


            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productShortDesc = itemView.findViewById(R.id.productShortDescription);
            productLocation = itemView.findViewById(R.id.productLocation);
            productLongDesc = itemView.findViewById(R.id.productLongDesc);
            productSubCategory = itemView.findViewById(R.id.productsubCategory);

        }

    }

}
