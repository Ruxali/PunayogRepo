package com.example.punayog.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.CartFragment;
import com.example.punayog.R;
import com.example.punayog.interfaces.SetOnPriceChange;
import com.example.punayog.model.CartModel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>  {

    SetOnPriceChange setOnPriceChange;
   // TextView totalPrice;
    MutableLiveData<Double> totalPrice;
    private CartFragment context;
    public ArrayList<CartModel> cartArrayList;
    double overAllTotalAmount = 0.0;


    public CartAdapter(MutableLiveData<Double> totalPrice, ArrayList<CartModel> cartArrayList,SetOnPriceChange setOnPriceChange) {
        this.totalPrice = totalPrice;
        this.cartArrayList = cartArrayList;
        this.setOnPriceChange = setOnPriceChange;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_list, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CartViewHolder cartViewHolder, int index) {
        CartModel cartModel = cartArrayList.get(index);

        //setting value
        cartViewHolder.productName.setText(cartModel.getName());
        cartViewHolder.productPrice.setText(cartModel.getPrice());
        cartViewHolder.buyerName.setText(cartModel.getBuyerName());
        cartViewHolder.buyerEmail.setText(cartModel.getBuyerEmail());
        cartViewHolder.buyerNumber.setText(cartModel.getBuyerNumber());
        Picasso.get().load(cartModel.getImage()).into(cartViewHolder.productImage);

        double singlePrice = ((Double.parseDouble(cartModel.getPrice())));
        overAllTotalAmount = overAllTotalAmount + singlePrice;
        totalPrice.setValue((overAllTotalAmount));



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
                                            setOnPriceChange.onPriceChange((Double.parseDouble(cartArrayList.get(pos).getPrice())),pos);
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
        private TextView totalAmount;
        private ImageButton deleteButton;
        private TextView buyerName, buyerEmail,  buyerNumber;

        public CartViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cartProductImage);
            productName = itemView.findViewById(R.id.cartProductTitle);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            totalAmount = itemView.findViewById(R.id.totalPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            buyerName = itemView.findViewById(R.id.buyerName);
            buyerNumber = itemView.findViewById(R.id.buyerNumber);
            buyerEmail = itemView.findViewById(R.id.buyerEmail);
        }

    }

}


