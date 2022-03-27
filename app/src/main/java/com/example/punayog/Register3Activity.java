package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register3Activity extends AppCompatActivity {
    private EditText textPassword;
    private EditText finalPassword;
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        statusBarColor();
        textPassword = findViewById(R.id.editTextPassword);
        finalPassword=findViewById(R.id.editTextRePassword);
        validatePassword();


    }
    private boolean validatePassword() {
        String pswInput = textPassword.getText().toString().trim();
        if(!textPassword.equals(finalPassword)) {
            Toast.makeText(Register3Activity.this, "Passwords are not matched", Toast.LENGTH_SHORT).show();
        }
        if (pswInput.isEmpty()) {
            textPassword.setError("Field cant be empty");
            return false;
        } else if (!pswInput.matches(pswRegex)) {
            textPassword.setError("Please enter a valid password");
            return false;
        } else{
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

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, Register2Activity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}