package com.example.punayog;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;
import com.example.punayog.model.Product;
import com.squareup.picasso.Picasso;


public class ProductDetailsActivity extends AppCompatActivity {

    BottomNavigationView productBottomNavigationView;

    TextView productNameTextView, categorytextField, productPriceTextView, productDetailsTextView, sellerNameTextView, sellerNumberTextView, sellerEmailTextView;
    ImageView productImageView;
    Button addToCartButton;

    private Product product;

    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        statusBarColor();

        Intent intent = getIntent();
        if(intent != null){
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

        //product details
        categorytextField.setText(product.getSubCategory());
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(product.getPrice());
        productDetailsTextView.setText(product.getLongDesc());
        Picasso.get().load(product.getmImageUrl()).into(productImageView);

        //Bottom Navigation init
        productBottomNavigationView = findViewById(R.id.productBottomNavigationView);
        productBottomNavigationView.setBackground(null);

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
                        intent1.putExtra(Intent.EXTRA_EMAIL,new String[]{receipent});
                        startActivity(intent1);
                        return true;
                }
                return false;
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
