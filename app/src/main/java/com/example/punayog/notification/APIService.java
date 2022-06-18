package com.example.punayog.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAuVD4G9U:APA91bEJzo4AoLDh6sQwNRUvXlJHrwkIHmlanlLPejgKi0R0YvdPqqs35g7bRt4eAcCiNFMy_KIhrGPUU8S4B7AqoCedSK9ms6Da-5yIF6DbZQL3jRQ0AWjCbegqRegRR46dpJIK4IEn" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    public Call<MyResponse> sendNotification(@Body NotificationSender body) {
        return null;
    }
}
