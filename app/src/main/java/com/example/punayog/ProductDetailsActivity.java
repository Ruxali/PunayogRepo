package com.example.punayog;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.adapter.CommentAdapter;
import com.example.punayog.model.Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.punayog.model.Product;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ProductDetailsActivity extends AppCompatActivity {

    BottomNavigationView productBottomNavigationView;

    TextView productNameTextView, categorytextField, productPriceTextView, productDetailsTextView, sellerNameTextView, sellerNumberTextView, sellerEmailTextView, editComment;
    ImageView productImageView;
    Button addToCartButton, commentButton;
    RecyclerView recyclerView;
    private Product product;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private CommentAdapter commentAdapter;
    private List<Comment> listComment;
    private static String COMMENT_KEY = "Comment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        statusBarColor();
        //onRecyclerViewComment();
        Intent intent = getIntent();
        if (intent != null) {
            product = (Product) intent.getParcelableExtra("product");
        }

        //product details init
        categorytextField = findViewById(R.id.categoryTextField);
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDetailsTextView = findViewById(R.id.productDetailsTextView);
        sellerNameTextView = findViewById(R.id.sellerNameTextView);
        sellerEmailTextView = findViewById(R.id.sellerEmailTextView);
        sellerNumberTextView = findViewById(R.id.sellerNumberTextView);

        productImageView = findViewById(R.id.productImageView);

        addToCartButton = findViewById(R.id.addToCartButton);
        //comment
        editComment = findViewById(R.id.editComment);
        commentButton = findViewById(R.id.commentButton);
        recyclerView = findViewById(R.id.recycle_view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //product details
        categorytextField.setText(product.getSubCategory());
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(product.getPrice());
        productDetailsTextView.setText(product.getLongDesc());
        Picasso.get().load(product.getmImageUrl()).into(productImageView);

        //Bottom Navigation init
        productBottomNavigationView = findViewById(R.id.productBottomNavigationView);
        productBottomNavigationView.setBackground(null);
        //for comment button
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                commentButton.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference().child("uploads").child("comments").push();
                String comment_content = editComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                Comment comment = new Comment(comment_content, uid, uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        showMessage("comment added");
                        editComment.setText("");
                        commentButton.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@android.support.annotation.NonNull Exception e) {
                        showMessage("fail to add comment : " + e.getMessage());
                    }
                });


            }
        });

        productBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.call:
                        String s = "tel:" + "1234567890";
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(s));
                        startActivity(intent);

                        return true;

                    case R.id.email:
                        Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                        String receipent = "abcd@gmail.com";
                        String str = "mailto:";

                        intent1.setData(Uri.parse(str));
                        intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{receipent});
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });


    }

//    private void onRecyclerViewComment() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        DatabaseReference commentRef = firebaseDatabase.getReference().child("uploads");
//        commentRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
//                listComment = new ArrayList<>();
//                for (DataSnapshot snap : dataSnapshot.getChildren()) {
//
//                    Comment comment = snap.getValue(Comment.class);
//                    listComment.add(comment);
//
//                }
//
//                commentAdapter = new CommentAdapter(getApplicationContext(), listComment);
//                recyclerView.setAdapter(commentAdapter);
//
//
//            }
//
//
//            @Override
//            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
//
//            }
//        });

//    }

    private void showMessage(String comment_added) {
        Toast.makeText(this, comment_added, Toast.LENGTH_LONG).show();
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

    //for comment date
    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;


    }
}
