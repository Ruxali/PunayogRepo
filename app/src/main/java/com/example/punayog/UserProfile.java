//package com.example.punayog;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firestore.v1.TransactionOptions;
//
//public class UserProfile extends AppCompatActivity {
//    private TextView showfullName, email, gender, date, map, viewShow;
//    private String fullname, showEmail, showgender, showDate, showMap;
//    private ImageView imageView;
//    private FirebaseAuth authProfile;
//    private FirebaseUser firebaseUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile);
//        showfullName = findViewById(R.id.textView_showFullname);
//        email = findViewById(R.id.textView_email);
//        gender = findViewById(R.id.textView_gender);
//        date = findViewById(R.id.textView_dob);
//        map = findViewById(R.id.textView_address);
//        viewShow = findViewById(R.id.textViewShow);
//        authProfile = FirebaseAuth.getInstance();
//        firebaseUser = authProfile.getCurrentUser();
//        if (firebaseUser == null) {
//            Toast.makeText(UserProfile.this, "User needs to be logged in", Toast.LENGTH_SHORT).show();
//        } else {
//            //  progressBar.setVisibility(View.VISIBLE);
//            showProfile();
//            checkIfEmailVerified();
//        }
//    }
//
//    private void checkIfEmailVerified() {
//        if (!firebaseUser.isEmailVerified()) {
//            showAlertDialog();
//        }else{
//            firebaseUser.sendEmailVerification();
//        }
//    }
//
//    public void showAlertDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
//        builder.setTitle("Email is not verified");
//        builder.setMessage("Please verify your email now");
//        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_APP_EMAIL);//launch in email
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//launch in new page
//                startActivity(intent);
//
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    private void showProfile() {
//        String userId = firebaseUser.getUid();
//        //extracting data from database
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
//        referenceProfile.child(userId);
//        referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User users = snapshot.getValue(User.class);
//                if (users != null) {
//                    fullname = firebaseUser.getDisplayName();
//                    showEmail = firebaseUser.getEmail();
//                    showDate = users.inputDOB;
//                    showgender = users.userGender;
//                    showMap = users.addInput;
//                    viewShow.setText("Welcome:" + fullname + "!");
//                    showfullName.setText(fullname);
//                    email.setText(showEmail);
//                    date.setText(showDate);
//                    gender.setText(showgender);
//                    map.setText(showMap);
//
//
//                }
//                // progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.common_menu, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.menu_refresh) {
//            startActivity(getIntent());
//            finish();
//            overridePendingTransition(0, 0);
//        } else if (id == R.id.menu_update_profile) {
//            startActivity(new Intent(UserProfile.this, UpdateProfile.class));
//        } else if (id == R.id.logout) {
//            authProfile.signOut();
//            Toast.makeText(UserProfile.this, "You are now logged out", Toast.LENGTH_SHORT).show();
//
//        }
//        return true;
//    }
//}