package com.example.iot_generic_control.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadcastReceiver","BroadcastReceiver::OnReceive()");
        Toast.makeText(context, "Your Alarm is there", Toast.LENGTH_LONG).show();
        Log.d("AAAAAAA", "CARAIOOO");

    }

}
