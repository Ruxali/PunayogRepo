package com.example.punayog.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.ProductDetailsActivity;
import com.example.punayog.R;
import com.example.punayog.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder>  {

    private Context context;
    public ArrayList<Product> searchProductList;


    public SearchAdapter(Context context, ArrayList<Product> searchProductList) {
        this.context = context;
        this.searchProductList = searchProductList;
    }

    @Override
    public SearchAdapter.ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new SearchAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchAdapter.ProductViewHolder productViewHolder, int index) {

        Product product = searchProductList.get(index);

        //setting value
        productViewHolder.productName.setText(product.getProductName());
        productViewHolder.productPrice.setText(product.getPrice());
        productViewHolder.productShortDesc.setText(product.getShortDesc());
        productViewHolder.productLocation.setText(product.getLocation());
        productViewHolder.productLongDesc.setText(product.getLongDesc());
        productViewHolder.productSubCategory.setText(product.getSubCategory());
        productViewHolder.sellerName.setText(product.getSellerName());
        productViewHolder.sellerNumber.setText(product.getSellerNumber());
        productViewHolder.sellerEmail.setText(product.getSellerEmail());


        Picasso.get().load(product.getmImageUrl()).into(productViewHolder.productImage);

        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
                intent.putExtra("product",product);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return searchProductList.size();
    }




    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productShortDesc;
        private TextView productLocation;
        private TextView productLongDesc;
        private TextView productSubCategory;
        private TextView productCategory;
        private TextView sellerName;
        private TextView sellerEmail;
        private TextView sellerNumber;


        public ProductViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productShortDesc = itemView.findViewById(R.id.productShortDescription);
            productLocation = itemView.findViewById(R.id.productLocation);
            productLongDesc = itemView.findViewById(R.id.productLongDesc);
            productCategory = itemView.findViewById(R.id.productCategory);
            productSubCategory = itemView.findViewById(R.id.productsubCategory);
            sellerName = itemView.findViewById(R.id.productSellerName);
            sellerEmail = itemView.findViewById(R.id.productSellerEmail);
            sellerNumber = itemView.findViewById(R.id.productSellerNumber);
        }


    }


}
