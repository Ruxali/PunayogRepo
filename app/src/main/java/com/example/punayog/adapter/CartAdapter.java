package com.example.punayog.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.CartFragment;
import com.example.punayog.LoginActivity;
import com.example.punayog.MainActivity;
import com.example.punayog.ProductDetailsActivity;
import com.example.punayog.R;
import com.example.punayog.model.CartModel;
import com.example.punayog.model.Product;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>  {

    private Context context;
    public ArrayList<CartModel> cartArrayList;


    public CartAdapter(Context context, ArrayList<CartModel> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_list, viewGroup, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CartViewHolder cartViewHolder, int index) {
        CartModel cartModel = cartArrayList.get(index);

        //setting value
        cartViewHolder.productName.setText(cartModel.getName());
        cartViewHolder.productPrice.setText(cartModel.getPrice());
        Picasso.get().load(cartModel.getImage()).into(cartViewHolder.productImage);




//        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
//                intent.putExtra("product", (Parcelable) cartModel);
//                view.getContext().startActivity(intent);
//            }
//        });

        //delete cart items
        cartViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cartBuilder = new AlertDialog.Builder(view.getContext());
                cartBuilder.setMessage("Are you sure you want to delete the item?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = firebaseDatabase.getReference("cart").child(cartModel.getKey());
                                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete( DatabaseError error,  DatabaseReference ref) {
                                        if(ref == null){
                                            Toast.makeText(view.getContext(), "Error:" +error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }else{
                                            int pos = cartArrayList.indexOf(cartModel);
                                            cartArrayList.remove(pos);
                                            notifyItemRemoved(pos);

                                            Toast.makeText(view.getContext(), "Deleted From Cart!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = cartBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return cartArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView totalPrice;
        private ImageButton deleteButton;

        public CartViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartProductTitle);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

    }

}

