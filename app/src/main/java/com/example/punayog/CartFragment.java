package com.example.punayog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.CartAdapter;
import com.example.punayog.interfaces.SetOnPriceChange;
import com.example.punayog.model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private RecyclerView cartRecyclerView;
    private TextView totalPrice;

    public MutableLiveData<Double> cartTotalAmount = new MutableLiveData<>();
    private DatabaseReference myRef;
    private ArrayList<CartModel> cartArrayList;
    private Context context;
    private CartAdapter cartAdapter;
    private FirebaseAuth database;
    private ConstraintLayout cartLayout;
    private Button proceedButton;
    FirebaseUser firebaseuser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);
        cartLayout = rootView.findViewById(R.id.cartLayout);

        //total price
        totalPrice = rootView.findViewById(R.id.totalPrice);


        //firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        clearAll();

        database = FirebaseAuth.getInstance();
        firebaseuser = database.getCurrentUser();
        if (firebaseuser == null) {
            AlertDialog.Builder profileAlert = new AlertDialog.Builder(getContext());
            profileAlert.setTitle("You are not Logged in yet!");
            profileAlert.setMessage("Do you want to login?");
            profileAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            profileAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            });
            cartLayout.setVisibility(View.GONE);
            profileAlert.create();
            profileAlert.show();
        } else {
            cartLayout.setVisibility(View.VISIBLE);
            getDataFromFirebase();
        }

        cartTotalAmount.observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                totalPrice.setText(String.valueOf(s));
            }
        });
        return rootView;
    }


    //for cart products
    private void getDataFromFirebase() {
        String buyerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        cartArrayList = new ArrayList<>();

        Query query = myRef.child("cart");

        query.orderByChild("buyerEmail").equalTo(buyerEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartModel cartModel = new CartModel();

                    cartModel.setImage((String) snapshot.child("productImage").getValue());
                    cartModel.setName((String) snapshot.child("productName").getValue());
                    cartModel.setPrice((String) snapshot.child("productPrice").getValue());
                    cartModel.setKey(snapshot.getKey());

                    cartArrayList.add(cartModel);

                }

                cartTotalAmount.setValue(0.0);
                totalPrice.setText(cartTotalAmount.getValue().toString());
                cartAdapter = new CartAdapter(cartTotalAmount, cartArrayList, new SetOnPriceChange() {
                    @Override
                    public void onPriceChange(double amount, int pos) {
//                        System.out.println("total amount" + amount);
//                        System.out.println("total price:"+totalPrice.getText());
////                        cartTotalAmount.setValue(Double.parseDouble(totalPrice.getText().toString().trim()));
//                        cartTotalAmount.setValue(Double.parseDouble(totalPrice.getText().toString().trim()) - amount);
//                        System.out.println("decreased price"+cartTotalAmount.getValue());
//                        totalPrice.setText(cartTotalAmount.getValue().toString());
//                        cartArrayList.remove(pos);
//                        cartAdapter.notifyItemRemoved(pos);

//                        for (CartModel cartModel : cartArrayList) {
//
//                        }
//                        totalPrice.setText(String.valueOf(totalAmount));
//                        cartAdapter.notifyDataSetChanged();
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                cartRecyclerView.setLayoutManager(layoutManager);
                cartRecyclerView.setHasFixedSize(true);

                cartRecyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //calculateTotalPrice();
    }

    private void clearAll() {
        if (cartArrayList != null) {
            cartArrayList.clear();


        }
    }

    private void calculateTotalPrice() {
        double overAllTotalAmount = 0.0;
        for (CartModel cartModel : cartArrayList) {
            double singlePrice = ((Double.parseDouble(cartModel.getPrice())));
            overAllTotalAmount = overAllTotalAmount + singlePrice;
            totalPrice.setText(String.valueOf(overAllTotalAmount));

        }
    }
//
//    public void setTotalPrice(String totalAmount) {
//        System.out.println("Total:" + totalAmount);
//        totalPrice.setText(totalAmount);
//        System.out.println(totalPrice.getText());
//    }
}