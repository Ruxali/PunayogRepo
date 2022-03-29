package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText textName, dateOfBirth,phoneNum,textEmail, address,textPassword,finalPassword;
    private Button registerButton;
    private RadioButton radioMale, radioFemale, radioOthers;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String numRegex = "^[+]?[0-9]{10,13}$";
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        statusBarColor();
        textName = findViewById(R.id.editTextName);
        dateOfBirth = findViewById(R.id.editTextDoB);
        phoneNum = findViewById(R.id.editTextNumber);
        textEmail = findViewById(R.id.editTextEmail);
        address = findViewById(R.id.editTextLocation);
        textPassword = findViewById(R.id.editTextPassword);
        finalPassword = findViewById(R.id.editTextRePassword);
        registerButton = findViewById(R.id.registerButton);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOthers = findViewById(R.id.radioOthers);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });

    }

    public void validateUser() {
        String inputDOB = dateOfBirth.getText().toString().trim();
        String inputUsername = textName.getText().toString().trim();
        String emailInput = textEmail.getText().toString().trim();
        String phoneInput = phoneNum.getText().toString().trim();
        String addInput = address.getText().toString().trim();
        String pswInput = textPassword.getText().toString().trim();
        String pswTwoInput = finalPassword.getText().toString().trim();
        String radioButtonMale=radioMale.getText().toString().trim();
        String radioButtonFemale=radioFemale.getText().toString().trim();
        String radioButtonOthers=radioOthers.getText().toString().trim();

        if (inputUsername.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            onRegisterClick();
        } else if (textName.length() < 10) {
            Toast.makeText(this, "Name cannot be this short", Toast.LENGTH_SHORT).show();
            return;

        }
//        else if(!inputUsername.matches("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")){
//            Toast.makeText(this, "Name pattern is not matched", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (inputDOB.isEmpty()) {
//            Toast.makeText(this, "Date  is required", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!inputDOB.matches("\\d{4}-\\d{2}-\\d{2}")) {
//            Toast.makeText(this, "Date pattern is wrong", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(radioButtonMale.matches(" ")){
//            Toast.makeText(this, "Gender should be selected", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(radioButtonFemale.matches(" ")){
//            Toast.makeText(this, "Gender should be selected", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(radioButtonOthers.matches(" ")){
//            Toast.makeText(this, "Gender should be selected", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (emailInput.isEmpty()) {
//            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!emailInput.matches(emailRegex)) {
//            Toast.makeText(this, "Email pattern is not correct", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (phoneInput.isEmpty()) {
//            Toast.makeText(this, "Phone-Number is required", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!phoneInput.matches(emailRegex)) {
//            Toast.makeText(this, "Number pattern is not correct", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (addInput.isEmpty()) {
//            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (pswInput.isEmpty()) {
//            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!pswInput.matches(pswRegex)) {
//            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
//            return;
//
//        } else if (pswInput.length() < 10) {
//            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (pswTwoInput.isEmpty()) {
//            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
//            return;
////        } else if (!pswTwoInput.matches(pswRegex)) {
////            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
////            return;
////
////        } else if (pswTwoInput.length() < 10) {
////            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
////            return;
//        }
//        if (!pswInput.equals(pswTwoInput)) {
//            Toast.makeText(this, "Passwords are not matched", Toast.LENGTH_SHORT).show();
//        }


    }


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }


    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    public void onRegisterClick() {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}