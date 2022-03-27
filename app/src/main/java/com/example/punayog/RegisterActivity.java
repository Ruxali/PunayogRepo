package com.example.punayog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText textName, dateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        statusBarColor();
        textName=findViewById(R.id.editTextName);
        dateOfBirth=findViewById(R.id.editTextDoB);
         userName();
         validatedob();
    }

    private boolean validatedob() {
        String inputDOB=dateOfBirth.getText().toString().trim();
        if(inputDOB.isEmpty()){
            dateOfBirth.setError("Field cant be empty");
            return false;
        }else if(!inputDOB.matches("\\d{4}-\\d{2}-\\d{2}")){
            dateOfBirth.setError("Please enter the validate date");
            return false;
        }
        else {
            dateOfBirth.setError(null);
            return true;
        }

    }

    private boolean userName(){
        String inputUsername= textName.getText().toString().trim();
        if (inputUsername.isEmpty()) {
            textName.setError("Field cant be empty");
            return false;
        } else if(textName.length()<10){
            textName.setError("Name cannot be this short");
            return false;
        }
        else {
            textName.setError(null);
            return true;
        }


    }
    public void statusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,Register2Activity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}