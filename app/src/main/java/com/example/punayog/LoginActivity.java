package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText textEmail;
    private EditText textPassword;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        statusBarColor();
        textPassword = findViewById(R.id.editTextPassword);
        textEmail = findViewById(R.id.editTextEmail);
    }

    private boolean validateEmail() {
        String emailInput = textEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            textEmail.setError("Field cant be empty");
            return false;
        } else if (!emailInput.matches(emailRegex)) {
            textEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textEmail.setError(null);
            return true;
        }


    }

    private boolean validatePassword() {
        String pswInput = textPassword.getText().toString().trim();
        if (pswInput.isEmpty()) {
            textPassword.setError("Field cant be empty");
            return false;
        } else if (!pswInput.matches(pswRegex)) {
            textPassword.setError("Please enter a valid password");
            return false;
        } else {
            textPassword.setError(null);
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