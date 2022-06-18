package com.example.punayog;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.punayog.notification.APIService;
import com.example.punayog.notification.Client;
import com.example.punayog.notification.Data;
import com.example.punayog.notification.MyResponse;
import com.example.punayog.notification.NotificationSender;
import com.example.punayog.notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.CommonNotificationBuilder;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotification extends Activity {
    private EditText commentEditText;
    ImageButton commentButton;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        commentEditText = findViewById(R.id.editComment);
        commentButton = findViewById(R.id.commentButton);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(commentEditText.getText().toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userToken = snapshot.getValue(String.class);
                        sendNotifications(userToken, commentEditText.getText().toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
            }
        });
        UpdateToken();
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging refreshToken = FirebaseMessaging.getInstance();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void sendNotifications(String userToken, String title) {
        Data data = new Data(title);
        NotificationSender sender = new NotificationSender(data, userToken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(PushNotification.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        String title = message.getNotification().getTitle();
//        String body = message.getNotification().getBody();
//        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
//        NotificationChannel channel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            channel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "notification",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//        }
//        getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        Notification.Builder notification = new Notification.Builder(
//                this, CHANNEL_ID).setContentTitle(title).setContentText(body).setSmallIcon(R.drawable.ic_baseline_notifications_24).setAutoCancel(true);
//        NotificationManagerCompat.from(this).notify(1, notification.build());
//        super.onMessageReceived(message);
//
//    }
    }

}
