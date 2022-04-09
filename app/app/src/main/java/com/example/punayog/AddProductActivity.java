package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {
    private EditText editTextProductName, editTextPrice, editTextTime, editTextLongDesc, editTextLocation;
    private Spinner spinnerCategory, spinnerSubCategory;
    private Button postButton;
    private FirebaseAuth mAuth;
    private String record;
    private String names[] = {"Accessories", "Apparels", "Books", "Electronics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        statusBarColor();
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextTime = findViewById(R.id.editTextTime);
        editTextLongDesc = findViewById(R.id.editTextLongDesc);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        postButton = findViewById(R.id.postButton);
        mAuth = FirebaseAuth.getInstance();
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateProductName() || !validatePrice() || !validateLong() || !validateLocation() || !validateTime()) {
                    return;
                } else {
                    onPostClick();
                }

            }
        });
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, names);
        spinnerCategory.setAdapter(adapter);


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        record = "Accessories";
                        Toast.makeText(AddProductActivity.this, "Accesories", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        record = "Apparels";
                        Toast.makeText(AddProductActivity.this, "Apparels", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        record = "Books";
                        Toast.makeText(AddProductActivity.this, "Books", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        record = "Electronics";
                        Toast.makeText(AddProductActivity.this, "Electronics", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddProductActivity.this, "Please select the category", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateProductName() {
        String inputProductName = editTextProductName.getText().toString().trim();
        if (inputProductName.isEmpty()) {
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!inputProductName.matches("^[a-zA-Z0-9]+([ ]?[a-zA-Z0-9]+)*$")) {
            Toast.makeText(this, "Name pattern is not matched", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validatePrice() {
        String inputPrice = editTextPrice.getText().toString().trim();
        if (inputPrice.isEmpty()) {
            Toast.makeText(this, "Please enter the price", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTime() {
        String inputTime = editTextTime.getText().toString().trim();
        if (inputTime.isEmpty()) {
            Toast.makeText(this, "Please provide the duration of product used", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLong() {
        String inputLongDesc = editTextLongDesc.getText().toString().trim();
        if (inputLongDesc.isEmpty()) {
            Toast.makeText(this, "Please provide the description", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLocation() {
        String addInput = editTextLocation.getText().toString().trim();
        if (addInput.isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onPostClick(View view) {


    }

    private void onPostClick() {
        startActivity(new Intent(AddProductActivity.this,MainActivity.class));
    }
}