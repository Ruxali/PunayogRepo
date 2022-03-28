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
    private EditText textName, dateOfBirth;
    private Button continueOneButton;
    private RadioButton radioMale, radioFemale, radioOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        statusBarColor();
        textName = findViewById(R.id.editTextName);
        dateOfBirth = findViewById(R.id.editTextDoB);
        continueOneButton = findViewById(R.id.continueOneButton);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOthers = findViewById(R.id.radioOthers);
        continueOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });

    }

    public void validateUser() {
        String inputDOB = dateOfBirth.getText().toString().trim();
        String inputUsername = textName.getText().toString().trim();
        String radioButtonMale=radioMale.getText().toString().trim();
        String radioButtonFemale=radioFemale.getText().toString().trim();
        String radioButtonOthers=radioOthers.getText().toString().trim();

        if (inputUsername.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (textName.length() < 10) {
            Toast.makeText(this, "Name cannot be this short", Toast.LENGTH_SHORT).show();
            return;

        }
        if (inputDOB.isEmpty()) {
            Toast.makeText(this, "Date  is required", Toast.LENGTH_SHORT).show();
            return;
        } else if (!inputDOB.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Date pattern is wrong", Toast.LENGTH_SHORT).show();
            return;
        }
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

    public void onContinueClick(View view) {

        Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
        startActivity(intent);
    }
}