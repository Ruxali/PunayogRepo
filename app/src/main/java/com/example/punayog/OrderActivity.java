package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrderActivity extends AppCompatActivity {

    private Button orderNextButton,sameAsButton;
    private ImageView orderBackImageView;
    private EditText shippingName, shippingAddress, shippingNumber;
    private EditText billingName, billingAddress, billingNumber, billingEmail;
    private TextView totalProductAmount, totalDeliveryAmount, completeTotalAmount;

    //firebase
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        statusBarColor();

        //init
        orderNextButton = findViewById(R.id.orderNextButton);
        sameAsButton = findViewById(R.id.sameAsButton);
        orderBackImageView = findViewById(R.id.orderBackImageView);
        shippingName = findViewById(R.id.shippingName);
        shippingAddress = findViewById(R.id.shippingAddress);
        shippingNumber = findViewById(R.id.shippingNumber);
        billingName = findViewById(R.id.billingName);
        billingAddress = findViewById(R.id.billingAddress);
        billingNumber = findViewById(R.id.billingNumber);
        billingEmail = findViewById(R.id.billingEmail);
        totalProductAmount = findViewById(R.id.totalProductAmount);
        totalDeliveryAmount = findViewById(R.id.totalDeliveryAmount);
        completeTotalAmount = findViewById(R.id.completeTotalAmount);

        //intent to get total amount
        Intent intent = getIntent();
        String str = intent.getStringExtra("totalPrice");
        totalProductAmount.setText(str);

        totalDeliveryAmount.setText("100");
        Double amount = (Double.parseDouble(totalProductAmount.getText().toString())+Double.parseDouble(totalDeliveryAmount.getText().toString()));
        completeTotalAmount.setText(String.valueOf(amount));

        //add value of user to billing address
        billingDetails();

        //intent to go to confirm page
        orderNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty()){
                    goToConfirmPage();
                }
            }
        });

        //make billing and shipping details same
        sameAsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShippingDetails();
            }
        });
    }

    private boolean checkEmpty() {
        if(shippingName.getText().toString().isEmpty()||shippingNumber.getText().toString().isEmpty()||shippingAddress.getText().toString().isEmpty()||billingName.getText().toString().isEmpty()||billingEmail.getText().toString().isEmpty()||billingNumber.getText().toString().isEmpty()||billingAddress.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void billingDetails() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("users");
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot);
                DataSnapshot tempSnapshot = null;
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data.child("emailInput").getValue(String.class).equals(userID)) {
                        tempSnapshot = data;
                        break;
                    }
                }
                String userName = tempSnapshot.child("inputUsername").getValue(String.class);
                billingName.setText(userName);
                String phone = tempSnapshot.child("phoneInput").getValue(String.class);
                billingNumber.setText(phone);
                String email = tempSnapshot.child("emailInput").getValue(String.class);
                billingEmail.setText(email);
                String location = tempSnapshot.child("addInput").getValue(String.class);
                billingAddress.setText(location);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setShippingDetails() {
        shippingName.setText(billingName.getText().toString());
        shippingAddress.setText(billingAddress.getText().toString());
        shippingNumber.setText(billingNumber.getText().toString());
    }


    private void goToConfirmPage() {
        Intent intent = new Intent(OrderActivity.this,ConfirmOrderActivity.class);
        intent.putExtra("shippingName",shippingName.getText().toString());
        intent.putExtra("shippingAddress",shippingAddress.getText().toString());
        intent.putExtra("shippingNumber",shippingNumber.getText().toString());
        intent.putExtra("billingName",billingName.getText().toString());
        intent.putExtra("billingAddress",billingAddress.getText().toString());
        intent.putExtra("billingNumber",billingNumber.getText().toString());
        intent.putExtra("billingEmail",billingEmail.getText().toString());
        intent.putExtra("completeTotalAmount",completeTotalAmount.getText().toString());
        startActivity(intent);
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