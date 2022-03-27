package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register2Activity extends AppCompatActivity {
    private EditText phoneNum;
    private EditText textEmail;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String numRegex = "^[+]?[0-9]{10,13}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        statusBarColor();
        phoneNum=findViewById(R.id.editTextNumber);
        textEmail=findViewById(R.id.editTextEmail);
        validateEmail();
        validateNum();
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
    private boolean validateNum(){
        String numInput=phoneNum.getText().toString().trim();
        if(numInput.isEmpty()){
            phoneNum.setError("Field cant be empty");
            return  false;
        }
        else
        {
            phoneNum.setError(null);
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

    public void onContinueClick(View view) {
        startActivity(new Intent(this, Register3Activity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}