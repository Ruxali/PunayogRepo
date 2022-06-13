package com.example.punayog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = CheckInternet.getNetworkInfo(context);
        if (status.equals("connected")) {
            Toast.makeText(context, "Welcome to Punayog", Toast.LENGTH_LONG).show();
        } else if (status.equals("disconnected")) {
            Toast.makeText(context, "Please check your connection", Toast.LENGTH_LONG).show();
        }
    }
}
