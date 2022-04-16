package com.example.punayog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class AddProductActivity extends AppCompatActivity {
    private EditText editTextPrice, editTextShortText, editTextLongDesc, editTextLocation, mEdittextFile;
    private Spinner spinnerCategory, spinnerSubCategory;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String record;
    private Product product;
    private int maxid = 0;
    private String names[] = {"Accessories", "Apparels", "Books", "Electronics"};
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage, mButtonUpload;
    private ImageView mImageView;
    private Uri ImageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        statusBarColor();
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextLongDesc = findViewById(R.id.editTextLongDesc);
        editTextShortText = findViewById(R.id.editTextShortDesc);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        mImageView = findViewById(R.id.image_view_picture);
        mButtonUpload = findViewById(R.id.button_upload_file);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mEdittextFile = findViewById(R.id.edit_text_file_name);
        product = new Product();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        reference = database.getInstance().getReference("uploads").child("Spinner");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateProductName() || !validatePrice() || !validateLong() || !validateLocation() || !validateShort()) {
                    return;
                }else{uploadFile();}

            }
        });


    }

    //for file extension
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {

        if (ImageUri != null) {
            StorageReference fileReference = storageReference.child("uploads" + System.currentTimeMillis() + "." + getFileExtension(ImageUri));
            mUploadTask = fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    Upload upload = new Upload(editTextLocation.getText().toString().trim(),
                            editTextLongDesc.getText().toString().trim(),
                            editTextPrice.getText().toString().trim(),
                            mEdittextFile.getText().toString().trim(),
                            editTextShortText.getText().toString().trim()
                            ,downloadUri.toString());
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(upload);
                    Toast.makeText(AddProductActivity.this, "Image is uploaded successfully", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddProductActivity.this, "Please select a image", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).fit().centerCrop().into(mImageView);
        }
    }

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


    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }

    public void onPostClick(View view) {
        product.setSpinnerCat(spinnerCategory.getSelectedItem().toString());
        reference.child(String.valueOf(maxid + 1)).setValue(product);

    }

    private void onPostClick() {
        startActivity(new Intent(AddProductActivity.this, MainActivity.class));
    }
}