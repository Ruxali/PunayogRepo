package com.example.punayog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() || !validatePassword() || !validateUser()){
                    return;
                }else{
                    onLoginButtonClick();
                }


            }
        });

    }

    private Boolean validateEmail() {

        String emailInput = textInputEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!emailInput.matches(emailRegex)) {
            Toast.makeText(this, "Email pattern is not correct", Toast.LENGTH_SHORT).show();
            return false;

        }
        else{
            return true;
        }
    }

    private boolean validatePassword() {
        String pswInput = textInputPassword.getText().toString().trim();

        if (pswInput.isEmpty()){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!pswInput.matches(pswRegex)){
            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
            return false;
        }else if(pswInput.length() < 10) {
            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
public boolean validateUser(){
    String emailInput = textInputEmail.getText().toString().trim();
    String pswInput = textInputPassword.getText().toString().trim();
    mAuth.signInWithEmailAndPassword(emailInput, pswInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.isEmailVerified()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    user.sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Login is failed", Toast.LENGTH_SHORT).show();


            }

        }

    });
    return true;
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

    public void onLoginButtonClick() {
        startActivity(new Intent(this, VerificationActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);;
    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(this, VerificationActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}