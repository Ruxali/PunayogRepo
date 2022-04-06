package com.example.punayog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPassword;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText=findViewById(R.id.editTextEmail);
        resetPassword=findViewById(R.id.resetButton);
        auth=FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }

            private void forgetPassword() {
                String email=emailEditText.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(ForgetPassword.this, "Email is empty", Toast.LENGTH_SHORT).show();
                }
                else if(!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
                    Toast.makeText(ForgetPassword.this,"Pattern is not valid",Toast.LENGTH_SHORT).show();
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPassword.this,"Check your email to reset your password",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(ForgetPassword.this,"Something went wrong please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
