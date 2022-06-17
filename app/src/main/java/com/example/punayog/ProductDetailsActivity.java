package com.example.punayog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.CommentAdapter;
import com.example.punayog.adapter.HorizontalScrollAdapter;
import com.example.punayog.adapter.ProductAdapter;
import com.example.punayog.adapter.SearchAdapter;
import com.example.punayog.model.Comment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.punayog.model.Product;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class ProductDetailsActivity extends AppCompatActivity {

    BottomNavigationView productBottomNavigationView;

    TextView productNameTextView, categoryTextField, subCategoryTextField, productPriceTextView, productDetailsTextView, sellerNameTextView, sellerNumberTextView, sellerEmailTextView, productIdDetails;
    ImageView productImageView;
    Button addToCartButton;
    ImageButton commentButton;
    TextView cartId;

    private RecyclerView recyclerView;

    private RecyclerView commentRecyclerView;

    private Product product;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference cartReference;
    float overAllTotalAmount = 0.00F;

    private CommentAdapter commentAdapter;
    private EditText commentEditText;
    private ArrayList<Comment> listComment;
    private DatabaseReference reference, upRef;

    //horizontal similar products
    private Context context;
    private RecyclerView sameProductRecyclerView;
    private ArrayList<Product> horizontalScrollModelList;
    private HorizontalScrollAdapter horizontalScrollAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        statusBarColor();


        Intent intent = getIntent();
        if (intent != null) {
            product = (Product) intent.getParcelableExtra("product");
        }

        //product details init
        categoryTextField = findViewById(R.id.categoryTextField);
        subCategoryTextField = findViewById(R.id.subCategoryTextField);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDetailsTextView = findViewById(R.id.productDetailsTextView);
        sellerNameTextView = findViewById(R.id.sellerNameTextView);
        sellerEmailTextView = findViewById(R.id.sellerEmailTextView);
        sellerNumberTextView = findViewById(R.id.sellerNumberTextView);
        productImageView = findViewById(R.id.productImageView);
        productIdDetails = findViewById(R.id.productIdDetails);

        //add to cart
        addToCartButton = findViewById(R.id.addToCartButton);
        cartReference = FirebaseDatabase.getInstance().getReference("cart");
        cartId = findViewById(R.id.cartId);


        //comment
        commentEditText = findViewById(R.id.editComment);
        commentButton = findViewById(R.id.commentButton);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        upRef = FirebaseDatabase.getInstance().getReference("comment");

        //product details
        reference = FirebaseDatabase.getInstance().getReference();
        categoryTextField.setText(product.getSubCategory());
        categoryTextField.setText(product.getCategory());
        subCategoryTextField.setText(product.getSubCategory());
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(""+product.getPrice());
        productDetailsTextView.setText(product.getLongDesc());
        sellerNameTextView.setText(product.getSellerName());
        sellerNumberTextView.setText(product.getSellerNumber());
        sellerEmailTextView.setText(product.getSellerEmail());
        Picasso.get().load(product.getmImageUrl()).into(productImageView);
        productIdDetails.setText(product.getProductId());

        //Bottom Navigation init
        productBottomNavigationView = findViewById(R.id.productBottomNavigationView);
        productBottomNavigationView.setBackground(null);

        onRecyclerViewComment();

        //for similar products
        sameProductRecyclerView = findViewById(R.id.sameProductRecyclerView);
        horizontalScrollModelList = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("uploads");
        String seller = sellerEmailTextView.getText().toString();
        String thisProductId = productIdDetails.getText().toString();

        query.orderByChild("sellerEmail").equalTo(seller).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (!snapshot.child("productId").equals(thisProductId)) {


                        Collections.shuffle(horizontalScrollModelList);

                        Product product = new Product();


                        product.setProductId((String) snapshot.child("productId").getValue());
                        product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                        product.setProductName((String) snapshot.child("productName").getValue());
                        product.setPrice((String) snapshot.child("price").getValue());
                        product.setShortDesc((String) snapshot.child("shortDesc").getValue());
                        product.setLongDesc((String) snapshot.child("longDesc").getValue());
                        product.setSubCategory((String) snapshot.child("subCategory").getValue());
                        product.setLocation((String) snapshot.child("location").getValue());
                        product.setCategory((String) snapshot.child("category").getValue());
                        product.setSellerName((String) snapshot.child("sellerName").getValue());
                        product.setSellerNumber((String) snapshot.child("sellerNumber").getValue());
                        product.setSellerEmail((String) snapshot.child("sellerEmail").getValue());

                    product.setProductId((String) snapshot.child("productId").getValue());
                    product.setmImageUrl((String) snapshot.child("mImageUrl").getValue());
                    product.setProductName((String) snapshot.child("productName").getValue());
                    product.setPrice(Integer.parseInt( snapshot.child("price").getValue().toString()));
                    product.setShortDesc((String) snapshot.child("shortDesc").getValue());
                    product.setLongDesc((String) snapshot.child("longDesc").getValue());
                    product.setSubCategory((String) snapshot.child("subCategory").getValue());
                    product.setLocation((String) snapshot.child("location").getValue());
                    product.setCategory((String) snapshot.child("category").getValue());
                    product.setSellerName((String) snapshot.child("sellerName").getValue());
                    product.setSellerNumber((String) snapshot.child("sellerNumber").getValue());
                    product.setSellerEmail((String) snapshot.child("sellerEmail").getValue());


                        horizontalScrollModelList.add(product);
                    }

                }

                horizontalScrollAdapter = new HorizontalScrollAdapter(context, horizontalScrollModelList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                sameProductRecyclerView.setLayoutManager(linearLayoutManager);
                sameProductRecyclerView.setAdapter(horizontalScrollAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //for comment button
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentEditText.getText().toString();
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    AlertDialog.Builder profileAlert = new AlertDialog.Builder(ProductDetailsActivity.this);
                    profileAlert.setTitle("You are not Logged in yet!");
                    profileAlert.setMessage("Do you want to login?");
                    profileAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProductDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    profileAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                            dialogInterface.cancel();
                        }
                    });
                    profileAlert.create();
                    profileAlert.show();
                } else if (comment.isEmpty()) {
                    Toast.makeText(ProductDetailsActivity.this, "Please enter comment first!", Toast.LENGTH_SHORT).show();

                } else {
                    addToComment();
                }

            }
        });

        //navigation bar
        productBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call:
                        String s = "tel:" + sellerNumberTextView.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(s));
                        startActivity(intent);

                        return true;

                    case R.id.email:
                        Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                        String recipient = sellerEmailTextView.getText().toString();
                        String str = "mailto:";

                        intent1.setData(Uri.parse(str));
                        intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });

        //add to cart
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    AlertDialog.Builder profileAlert = new AlertDialog.Builder(ProductDetailsActivity.this);
                    profileAlert.setTitle("You are not Logged in yet!");
                    profileAlert.setMessage("Do you want to login?");
                    profileAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProductDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    profileAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                            dialogInterface.cancel();
                        }
                    });
                    profileAlert.create();
                    profileAlert.show();
                } else {
                    addToCart();
                }

            }
        });
    }

    //for adding comment
    private void addToComment() {
        commentButton.setVisibility(View.INVISIBLE);
        DatabaseReference commentReference = upRef;
        String comment_content = commentEditText.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String commentID = upRef.push().getKey();

        String saveCurrentTime, saveCurrentDate;
        Calendar calForData = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calForData.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForData.getTime());


        commentReference.child(commentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("userId", uid);
                hashMap.put("commentId", commentID);
                hashMap.put("comment", comment_content);
                hashMap.put("currentTime", saveCurrentTime);
                hashMap.put("currentDate", saveCurrentDate);
                hashMap.put("productId", productIdDetails.getText().toString());
                commentReference.child(commentID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProductDetailsActivity.this, "Comment Added", Toast.LENGTH_SHORT).show();
                        commentButton.setVisibility(View.VISIBLE);
                        commentEditText.setText(" ");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //for fetching comment
    private void onRecyclerViewComment() {

        listComment = new ArrayList<>();

        DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference();
        String productIdForComment = productIdDetails.getText().toString();
        Query query = commentRef.child("comment");
        query.orderByChild("productId").equalTo(productIdForComment).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Comment comment = new Comment();

                    comment.setUid((String) snap.child("userId").getValue());
                    comment.setContent((String) snap.child("comment").getValue());

                    listComment.add(comment);
                }

                LinearLayoutManager linearLayoutManager;
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                commentRecyclerView.setLayoutManager(linearLayoutManager);
                commentAdapter = new CommentAdapter(getApplicationContext(), listComment);
                commentRecyclerView.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //for adding to cart
    private void addToCart() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String saveCurrentTime, saveCurrentDate;

        Calendar calForData = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calForData.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForData.getTime());

        String cartID = cartReference.push().getKey();
        cartId.setText(cartID);

        cartReference.child(cartID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("cartID").exists()) {
                    Toast.makeText(ProductDetailsActivity.this, "Product already added to cart!", Toast.LENGTH_SHORT).show();
                } else {

                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("cartId", cartId.getText().toString());
                    cartMap.put("productImage", product.getmImageUrl());
                    cartMap.put("productName", productNameTextView.getText().toString());
                    cartMap.put("productPrice", productPriceTextView.getText().toString());
                    cartMap.put("currentTime", saveCurrentTime);
                    cartMap.put("currentDate", saveCurrentDate);
                    cartMap.put("buyerEmail", userID);

                    cartReference.child(cartID).updateChildren(cartMap).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(ProductDetailsActivity.this, "Added to Cart Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProductDetailsActivity.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductDetailsActivity.this, "Something went wrong: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }


}
