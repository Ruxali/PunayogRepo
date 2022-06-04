package com.example.punayog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.punayog.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGES_CODE = 0;
    private Spinner spinnerCategory, spinnerSubCategory;
    private EditText editTextPrice, editTextShortText, editTextLongDesc, editTextLocation, mEdittextFile,sellerName,sellerNumber,sellerEmail;
    private ImageView imageViewer;
    private Button  choseBtn, mButtonUpload;
    private int position = 0;

    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    FirebaseUser firebaseuser;
    private FirebaseAuth database;

    private StorageTask mUploadTask;
    private Uri imageUri;
    private String item;

    private String[] category = {"Choose a category", "Accessories", "Apparels", "Books", "Electronics"};

    private String[] subCategory1 = {"Choose a sub category", "Bags", "Shoes", "Sunglasses", "Watches"};
    private String[] subCategory2 = {"Choose a sub category", "Children", "Men", "Unisex", "Women"};
    private String[] subCategory3 = {"Choose a sub category", "Course", "Fantasy", "Fiction", "Non-Fiction"};
    private String[] subCategory4 = {"Choose a sub category", "Laptop", "Microwave", "Mobile Phones", "Television"};

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        statusBarColor();
        
        
        mEdittextFile = findViewById(R.id.edit_text_file_name);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextLongDesc = findViewById(R.id.editTextLongDesc);
        editTextShortText = findViewById(R.id.editTextShortDesc);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        imageViewer = findViewById(R.id.image_view_picture);
        choseBtn = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload_file);
        sellerName = findViewById(R.id.sellerName);
        sellerNumber = findViewById(R.id.sellerNumber);
        sellerEmail = findViewById(R.id.sellerEmail);


        firebaseDatabase = FirebaseDatabase.getInstance();
        database = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        progressDialog = new ProgressDialog(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = category[position];
                if(position == 1){
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategory1);
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubCategory.setAdapter(arrayAdapter1);
                }
                if(position == 2){
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategory2);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubCategory.setAdapter(arrayAdapter2);
                }
                if(position == 3){
                    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategory3);
                    arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubCategory.setAdapter(arrayAdapter3);
                }
                if(position == 4){
                    ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item,subCategory4);
                    arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubCategory.setAdapter(arrayAdapter4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        choseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageIntent();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateProductName() || validatePrice() || validateLong() || validateLocation() || validateShort()) {
                    progressDialog.setTitle("Uploading...");
                    progressDialog.setMessage("Your product is being listed!");
                    progressDialog.show();
                    uploadFile();


                } else {
                    return;

                }

            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        firebaseuser = database.getCurrentUser();
        String userID = firebaseuser.getUid();
        Query query = databaseReference.child("users").child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("inputUsername").getValue(String.class);
                sellerName.setText(userName);
                String phone = snapshot.child("phoneInput").getValue(String.class);
                sellerNumber.setText(phone);
                String email = snapshot.child("emailInput").getValue(String.class);
                sellerEmail.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            if(imageUri !=null){
                StorageReference fileReference = storageReference.child(System.currentTimeMillis()+ "." + getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {

                            firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");
                            Upload upload = new Upload(
                                    imageViewer.toString(),
                                    mEdittextFile.getText().toString().trim(),
                                    editTextPrice.getText().toString().trim(),
                                    editTextShortText.getText().toString().trim(),
                                    editTextLongDesc.getText().toString().trim(),
                                    editTextLocation.getText().toString().trim(),
                                    spinnerCategory.getSelectedItem().toString().trim(),
                                    spinnerSubCategory.getSelectedItem().toString().trim());

                            upload.setmImageUrl(String.valueOf(uri));
                            String uploadId = databaseReference.push().getKey();
                            assert uploadId != null;
                            databaseReference.child(uploadId).setValue(upload);
//
                            Toast.makeText(AddProductActivity.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                            //intent
                            Intent send = new Intent(AddProductActivity.this, MainActivity.class);
                            startActivity(send);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this, "Something is Wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            }


    }


    private void pickImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGES_CODE);
    }

    //for choosing multiple images at a time
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGES_CODE && resultCode == Activity.RESULT_OK  && data !=null &&  data.getData()!=null) {
                imageUri = data.getData();

            Picasso.get().load(imageUri).into(imageViewer);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    //validating text,price,decs
    private boolean validateProductName() {
        String inputProductName = mEdittextFile.getText().toString().trim();
        if (inputProductName.isEmpty()) {
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!inputProductName.matches("^[a-zA-Z0-9]+([ ]?[a-zA-Z0-9]+)*$")) {
            Toast.makeText(this, "Name pattern is not matched", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validatePrice() {
        String inputPrice = editTextPrice.getText().toString().trim();
        if (inputPrice.isEmpty()) {
            Toast.makeText(this, "Please enter the price", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateShort() {
        String inputShort = editTextShortText.getText().toString().trim();
        if (inputShort.isEmpty()) {
            Toast.makeText(this, "Please provide the short description", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLong() {
        String inputLongDesc = editTextLongDesc.getText().toString().trim();
        if (inputLongDesc.isEmpty()) {
            Toast.makeText(this, "Please provide the description", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLocation() {
        String addInput = editTextLocation.getText().toString().trim();
        if (addInput.isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //for spinner item selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spinnerCategory.getSelectedItem().toString();
        if (item == "Choose a category") {
            Toast.makeText(AddProductActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
        } else {
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}