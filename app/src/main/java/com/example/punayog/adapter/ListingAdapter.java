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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListingAdapter extends FirebaseRecyclerAdapter<Product,ListingAdapter.productViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListingAdapter( FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull productViewHolder productViewHolder, @SuppressLint("RecyclerView") final int position, @NonNull Product product) {
        //setting value
        productViewHolder.productName.setText(product.getProductName());
        productViewHolder.productPrice.setText("" + product.getPrice());
        productViewHolder.productShortDesc.setText(product.getShortDesc());
        productViewHolder.productLocation.setText(product.getLocation());
        productViewHolder.productLongDesc.setText(product.getLongDesc());
        productViewHolder.productSubCategory.setText(product.getSubCategory());
        productViewHolder.sellerName.setText(product.getSellerName());
        productViewHolder.sellerNumber.setText(product.getSellerNumber());
        productViewHolder.sellerEmail.setText(product.getSellerEmail());
        Picasso.get().load(product.getmImageUrl()).into(productViewHolder.productImage);

        //for edit product
        productViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(productViewHolder.productImage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_product_popup_activity))
                        .setExpanded(true,1650)
                        .create();


                View updateView = dialogPlus.getHolderView();

                EditText updateProductName, updateProductPrice, updateProductShortDescription, updateProductSpecification,updateProductImageURL;
                Button updateButton;

                updateProductName = updateView.findViewById(R.id.updateProductName);
                updateProductPrice = updateView.findViewById(R.id.updateProductPrice);
                updateProductShortDescription = updateView.findViewById(R.id.updateProductShortDescription);
                updateProductSpecification = updateView.findViewById(R.id.updateProductSpecification);
//                updateProductImageURL = updateView.findViewById(R.id.updateProductImageURL);

                updateButton = updateView.findViewById(R.id.updateButton);

                updateProductName.setText(product.getProductName());
                updateProductPrice.setText("" + product.getPrice());
                updateProductShortDescription.setText(product.getShortDesc());
                updateProductSpecification.setText(product.getLongDesc());
//                updateProductImageURL.setText(product.getmImageUrl());

                dialogPlus.show();

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("productName",updateProductName.getText().toString());
                        map.put("price",updateProductPrice.getText().toString());
                        map.put("shortDesc",updateProductShortDescription.getText().toString());
                        map.put("longDesc",updateProductSpecification.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("uploads")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(updateView.getContext(), "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(updateView.getContext(), "Product Update Failed", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        //for delete button
        productViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteAlertDialog = new AlertDialog.Builder(view.getContext());
                deleteAlertDialog.setTitle("Are you sure you want to delete?");
                deleteAlertDialog.setMessage("Deleted Products Can Not Be Retrieved Back!");

                deleteAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("uploads")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(view.getContext(), "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                deleteAlertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Product Deletion Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                deleteAlertDialog.show();
            }
        });
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_items, viewGroup, false);
        return new productViewHolder(view);
    }

    public class productViewHolder extends RecyclerView.ViewHolder{
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

        private Button editButton, deleteButton;


        public productViewHolder(View itemView) {
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
            editButton = itemView.findViewById(R.id.editSellerItemButton);
            deleteButton = itemView.findViewById(R.id.deleteSellerItemButton);
        }

    }

}
