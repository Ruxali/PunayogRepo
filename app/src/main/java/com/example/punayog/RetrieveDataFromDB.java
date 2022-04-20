package com.example.punayog;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.punayog.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class RetrieveDataFromDB extends AppCompatActivity {
    private DatabaseReference reference;
    private StorageReference storageReference;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding=ActivityMainBinding.inflate(getLayoutInflater().inflate());
        setContentView(binding.getRoot());
        statusBarColor();
    }
    //binding.getImage.setOnClickListener(new View);

    public void statusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
        }
    }
}
