package com.example.punayog;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LoginActivity extends AppCompatActivity {
    private static final String FILE_NAME = "myFile";
    private FirebaseAuth mAuth;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private Button loginButton, forgetPassword;
    private CheckBox rememberMeCheckBox;

    private ImageView registerImageView;
    private View registerView;
    private LinearLayout loginLinearLayout;
    private LinearLayout noInternetlayout;
    private Button reloadButton;
    BroadcastReceiver broadcastReceiver = null;

    private ProgressDialog loginProgressDialog;

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

        mAuth = FirebaseAuth.getInstance();

        //internet status
        registerImageView = findViewById(R.id.registerImageView);
        registerView = findViewById(R.id.registerView);
        loginLinearLayout = findViewById(R.id.loginLinearLayout);
        noInternetlayout = findViewById(R.id.noInternetLayout);
        reloadButton = findViewById(R.id.reloadButton);
        internetStatus();

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetStatus();
            }
        });

        loginProgressDialog = new ProgressDialog(LoginActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        String emailInput = sharedPreferences.getString("email","");
        textInputEmail.setText(emailInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  textInputEmail.getText().toString().trim();
                if (validateEmail() || validatePassword()) {
                    loginProgressDialog.setTitle("Logging in...");
                    loginProgressDialog.setMessage("You are being redirected to the app!");
                    loginProgressDialog.show();
                    validateUser();

                } else {
                    return;
                }

                if (rememberMeCheckBox.isChecked()){
                    StoredDataUsingSharedPref(email);
                }
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
                finish();
            }
        });

    }
    //internet connection checking
    private void internetStatus() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info != null){
            if(info.isConnected()){
                loginLinearLayout.setVisibility(View.VISIBLE);
                registerView.setVisibility(View.VISIBLE);
                registerImageView.setVisibility(View.VISIBLE);
                noInternetlayout.setVisibility(View.GONE);
                reloadButton.setVisibility(View.GONE);
            }else{
                loginLinearLayout.setVisibility(View.GONE);
                registerView.setVisibility(View.GONE);
                registerImageView.setVisibility(View.GONE);
                noInternetlayout.setVisibility(View.VISIBLE);
                reloadButton.setVisibility(View.VISIBLE);
            }
        }else{
            loginLinearLayout.setVisibility(View.GONE);
            registerView.setVisibility(View.GONE);
            registerImageView.setVisibility(View.GONE);
            noInternetlayout.setVisibility(View.VISIBLE);
            reloadButton.setVisibility(View.VISIBLE);
        }
    }


    //for remember me
    private void StoredDataUsingSharedPref(String email) {
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString("email",email);
        editor.apply();
    }

    @Override
    public void onBackPressed(){
        finishAffinity();
        System.exit(0);
    }


    private boolean validateEmail() {

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



    public void validateUser() {
        String emailInput = textInputEmail.getText().toString().trim();
        String pswInput = textInputPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(emailInput, pswInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String show= user.getUid();
                    System.out.println(show);

                    loginProgressDialog.dismiss();

                    if (user.isEmailVerified()) {
                        loginProgressDialog.dismiss();
                       // Toast.makeText(LoginActivity.this, "Email is verified", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();

                    } else {
                        user.sendEmailVerification();
                      //  Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        showAlertDialog();
                    }
                } else {
                    loginProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error: " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });
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

    public void onLoginClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onSkip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onLoginButtonClick() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onForgotPassword(View view) {
        startActivity(new Intent(this, ForgetPassword.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


}