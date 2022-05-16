package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.punayog.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {
    private EditText textName, dateOfBirth, phoneNum, textEmail, address, textPassword, finalPassword;
    private Button registerButton;
    private String userGender = "";
    private RadioButton radioMale, radioFemale, radioOthers;
    private CheckBox tcCheckBox;
    private static final String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String numRegex = "^[+]?[0-9]{10,13}$";
    private static final String pswRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private static final int PICK_IMAGES_CODE = 0;
    private FloatingActionButton floatingActionButton;
    private ShapeableImageView shapeableImageView;
    private Uri imageUri;
    private StorageTask mUploadTask;

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
        tcCheckBox = findViewById(R.id.tcCheckBox);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        shapeableImageView = findViewById(R.id.shapeableImageView);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("users");
        reference = FirebaseDatabase.getInstance().getReference("users");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageIntent();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUserName() || !validateDoB() || !validateContact() || !validateEmail() || !validateLocation() ||
                        !validatePassword() || !validateTC() || !validateUser()) {
                    return;
                } else {
                    onRegisterClick();
                }
            }
        });

    }


    //for opening the gallery
    private void pickImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGES_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGES_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(shapeableImageView);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //for validation
    private Boolean validateUserName() {
        String inputUsername = textName.getText().toString().trim();
        if (inputUsername.isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (textName.length() < 10) {
            Toast.makeText(this, "Name cannot be this short", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!inputUsername.matches("^[a-zA-Z0-9]+([ ]?[a-zA-Z0-9]+)*$")) {
            Toast.makeText(this, "Name pattern is not matched", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateDoB() {

        String inputDOB = dateOfBirth.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        Date date1 = null;
        try {
            date1 = sdf.parse(inputDOB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (inputDOB.isEmpty()) {
            Toast.makeText(this, "Date  is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!inputDOB.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Date pattern is wrong", Toast.LENGTH_SHORT).show();
            return false;
        } else if (date1.compareTo(date2) > 0) {
            Toast.makeText(this, "Date should be smaller than current date", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private Boolean validateTC() {

        if (!tcCheckBox.isChecked()) {
            Toast.makeText(this, "Please Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateContact() {
        String phoneInput = phoneNum.getText().toString().trim();
        if (phoneInput.isEmpty()) {
            Toast.makeText(this, "Phone-Number is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!phoneInput.matches(numRegex)) {
            Toast.makeText(this, "Number pattern is not correct", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }

    private Boolean validateEmail() {

        String emailInput = textEmail.getText().toString().trim();
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

    private Boolean validateLocation() {
        String addInput = address.getText().toString().trim();
        if (addInput.isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validatePassword() {
        String pswInput = textPassword.getText().toString().trim();
        String pswTwoInput = finalPassword.getText().toString().trim();

        if (pswInput.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pswInput.matches(pswRegex)) {
            Toast.makeText(this, "Password pattern is not correct", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pswInput.length() < 10) {
            Toast.makeText(this, "Password cannot be less than 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pswTwoInput.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pswInput.equals(pswTwoInput)) {
            Toast.makeText(this, "Passwords do not matched", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    Toast.makeText(RegisterActivity.this, "Male is selected", Toast.LENGTH_SHORT).show();
                radioFemale.setChecked(false);
                radioOthers.setChecked(false);
                userGender = "male";
                break;
            case R.id.radioFemale:
                if (checked) {
                    Toast.makeText(RegisterActivity.this, "Female is selected", Toast.LENGTH_SHORT).show();
                    radioMale.setChecked(false);
                    radioOthers.setChecked(false);
                    userGender = "female";
                }
                break;
            case R.id.radioOthers:
                if (checked) {
                    Toast.makeText(RegisterActivity.this, "Others is selected", Toast.LENGTH_SHORT).show();
                    radioMale.setChecked(false);
                    radioFemale.setChecked(false);
                    userGender = "others";
                }
                break;
        }
    }

    public boolean validateUser() {
        String inputDOB = dateOfBirth.getText().toString().trim();
        String inputUsername = textName.getText().toString().trim();
        String emailInput = textEmail.getText().toString().trim();
        String phoneInput = phoneNum.getText().toString().trim();
        String addInput = address.getText().toString().trim();
        String pswInput = textPassword.getText().toString().trim();
        String pswTwoInput = finalPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(emailInput, pswInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    User user = new User(inputUsername, inputDOB, emailInput, phoneInput, pswInput, pswTwoInput, addInput, userGender);
                    StorageReference filepath = storageReference.child(System.currentTimeMillis() + ".");
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mUploadTask = filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    reference = FirebaseDatabase.getInstance().getReference().child("users");
                                    user.setImageUri(String.valueOf(uri));
                                    String uploadId = reference.push().getKey();
                                    assert uploadId != null;
                                    reference.child(uploadId).setValue(user);
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, "User has been successfully registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registration is failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Registration is failed", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    public void onRegisterClick() {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}
