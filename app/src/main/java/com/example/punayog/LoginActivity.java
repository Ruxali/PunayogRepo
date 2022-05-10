package com.example.punayog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private Button loginButton, forgetPassword;
    private CheckBox rememberMeCheckBox;
    private ProgressBar loginProgressBar;

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
        forgetPassword = findViewById(R.id.forgetPassword);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        loginProgressBar = findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();

        //for remember me
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = sharedPreferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            return;
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() || !validatePassword() || !validateUser()) {
                    return;
                } else {
                    onLoginButtonClick();
                }

                switch (view.getId()) {
                    case R.id.forgetPassword:
                        startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
                }

            }
        });

        //for remember me
        rememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }else if(!compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
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

        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        String pswInput = textInputPassword.getText().toString().trim();

        if (pswInput.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pswInput.matches(pswRegex)) {
            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pswInput.length() < 10) {
            Toast.makeText(this, "Password cannot be this short", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public boolean validateUser() {
        String emailInput = textInputEmail.getText().toString().trim();
        String pswInput = textInputPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(emailInput, pswInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    loginProgressBar.setVisibility(View.VISIBLE);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "Email is verified", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();

                    } else {
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        return true;
    }
//if email is not verified
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email is not verified");
        builder.setMessage("Please verify your email before signing inside the app");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);//launch in email
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//launch in new page
                startActivity(intent);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
//checking if user is already logged in
//    @Override
//    protected void onStart() {
//        if (mAuth.getCurrentUser() != null) {
////            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//           // startActivity(new Intent(LoginActivity.this,UserProfile.class));
//           // finish();
//        } else {
//            Toast.makeText(this, "You can log-in now", Toast.LENGTH_SHORT).show();
//        }
//
//        super.onStart();
//    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onAddClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onSkip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onLoginButtonClick() {

//       Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);


    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(this, ForgetPassword.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


    public void onLoginClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}