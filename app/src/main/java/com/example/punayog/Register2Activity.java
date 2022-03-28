package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register2Activity extends AppCompatActivity {
    private EditText phoneNum;
    private EditText textEmail, address;
    private Button continueTwoButton;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String numRegex = "^[+]?[0-9]{10,13}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        statusBarColor();
        phoneNum = findViewById(R.id.editTextNumber);
        textEmail = findViewById(R.id.editTextEmail);
        address = findViewById(R.id.editTextLocation);
        continueTwoButton = findViewById(R.id.continueTwoButton);
        continueTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });
    }

    public void validateUser() {
        String emailInput = textEmail.getText().toString().trim();
        String phoneInput = phoneNum.getText().toString().trim();
        String addInput = address.getText().toString().trim();
        if (emailInput.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!emailInput.matches(emailRegex)) {
            Toast.makeText(this, "Email pattern is not correct", Toast.LENGTH_SHORT).show();
            return;

        }
        if (phoneInput.isEmpty()) {
            Toast.makeText(this, "Phone-Number is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!phoneInput.matches(emailRegex)) {
            Toast.makeText(this, "Number pattern is not correct", Toast.LENGTH_SHORT).show();
            return;

        }
        if (addInput.isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onContinueTwoClick(View view) {
        startActivity(new Intent(this, Register3Activity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}
















































