package com.example.punayog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGES_CODE = 0;
    private Spinner spinnerCategory, spinnerSubCategory;
    private EditText editTextPrice, editTextShortText, editTextLongDesc, editTextLocation, mEdittextFile;
    private ImageSwitcher imageSwitcher;
    private ArrayList<Uri> imageUris;
    private Button previousBtn, nextBtn, choseBtn, mButtonUpload;
    private int position = 0;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask mUploadTask;
    private Uri imageUri;
    private int uploads;
    private String item;
    private String[] category = {"Choose a category", "Accessories", "Apparels", "Books", "Electronics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        statusBarColor();
        imageUris = new ArrayList<>();//inti list
        mEdittextFile = findViewById(R.id.edit_text_file_name);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextLongDesc = findViewById(R.id.editTextLongDesc);
        editTextShortText = findViewById(R.id.editTextShortDesc);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        imageSwitcher = findViewById(R.id.image_view_picture);
        choseBtn = findViewById(R.id.button_choose_image);
        previousBtn = findViewById(R.id.previous);
        nextBtn = findViewById(R.id.next);
        mButtonUpload = findViewById(R.id.button_upload_file);
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        spinnerCategory.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    imageSwitcher.setImageURI(imageUris.get(position));
                } else {
                    Toast.makeText(AddProductActivity.this, "No previous image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < imageUris.size() - 1) {
                    position++;
                    imageSwitcher.setImageURI(imageUris.get(position));
                } else {
                    Toast.makeText(AddProductActivity.this, "No more images", Toast.LENGTH_SHORT).show();
                }
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
                if (!validateProductName() || !validatePrice() || !validateLong() || !validateLocation() || !validateShort()) {
                    return;
                } else {
                    uploadFile();
                }

            }
        });

    }

    private void uploadFile() {
        for (uploads = 0; uploads < imageUris.size(); uploads++) {
            Uri image = imageUris.get(uploads);
            Log.i("last path segment", image.getLastPathSegment());
            StorageReference fileReference = FirebaseStorage.getInstance().getReference().child("uploads" + image.getLastPathSegment());
            mUploadTask = (StorageTask) fileReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("uploads");
                            Upload upload = new Upload(
                                    mEdittextFile.getText().toString().trim(),
                                    editTextPrice.getText().toString().trim(),
                                    editTextShortText.getText().toString().trim(),
                                    editTextLongDesc.getText().toString().trim(),
                                    editTextLocation.getText().toString().trim(),
                                    spinnerCategory.getSelectedItem().toString());
                            upload.setmImageUrl(downloadUri.toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("Link", String.valueOf((uri)));
                            databaseReference.push().setValue(hashMap);
                            Toast.makeText(AddProductActivity.this, "Image is uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this, "Please select a image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void pickImageIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_CODE);
    }

    //for choosing multiple images at a time
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    //picked multiple images
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                    }
                    //set 1st image to imageSwitcher
                    imageSwitcher.setImageURI(imageUris.get(0));
                    position = 0;
                } else {
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                }
                //set  image to imageSwitcher
                imageSwitcher.setImageURI(imageUris.get(0));
                position = 0;
            }
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