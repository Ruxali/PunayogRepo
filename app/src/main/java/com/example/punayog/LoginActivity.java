package com.example.punayog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.Object;


import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText textInputEmail;
    private EditText textInputPassword;
    private Button loginButton;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        statusBarColor();
        textInputPassword = findViewById(R.id.editTextPassword);
        textInputEmail = findViewById(R.id.editTextEmail);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });

    }

    public void validateUser() {
        String emailInput = textInputEmail.getText().toString().trim();
        String pswInput = textInputPassword.getText().toString().trim();
        if (emailInput.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!emailInput.matches(emailRegex)) {
            Toast.makeText(this, "Email pattern is not correct", Toast.LENGTH_SHORT).show();
            return;

        }

        if (pswInput.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pswInput.matches(pswRegex)) {
            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
            return;

        } else if (pswInput.length() < 10) {
            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
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

    public void onLoginClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onSkip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onLoginButtonClick(View view) {
        startActivity(new Intent(this, VerificationActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(this, VerificationActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}