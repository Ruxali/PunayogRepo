package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register3Activity extends AppCompatActivity {
    private EditText textPassword;
    private EditText finalPassword;
    private Button registerButton;
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        statusBarColor();
        textPassword = findViewById(R.id.editTextPassword);
        finalPassword = findViewById(R.id.editTextRePassword);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });


    }

    public void validateUser() {
        String pswInput = textPassword.getText().toString().trim();
        String pswTwoInput = finalPassword.getText().toString().trim();
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
        if (pswTwoInput.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
//        } else if (!pswTwoInput.matches(pswRegex)) {
//            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
//            return;
//
//        } else if (pswTwoInput.length() < 10) {
//            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
//            return;
        }
        if (!pswInput.equals(pswTwoInput)) {
            Toast.makeText(this, "Passwords are not matched", Toast.LENGTH_SHORT).show();
        }
    }


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }
//
//    public void onRegisterClick(View view) {
//        startActivity(new Intent(this, LoginActivity.class));
//        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
//    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, Register2Activity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}