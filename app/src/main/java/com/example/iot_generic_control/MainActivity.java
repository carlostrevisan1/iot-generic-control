package com.example.iot_generic_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//Classe utilizada para a criação inicial do aplicativo por isso "Main", uma atividade principal, a primeira.
public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";
    PendingIntent myPendingIntent;
    AlarmManager alarmManager;
    BroadcastReceiver myBroadcastReceiver;
    Calendar firingCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firingCal= Calendar.getInstance();
        firingCal.set(Calendar.HOUR, 19); // At the hour you want to fire the alarm
        firingCal.set(Calendar.MINUTE, 59); // alarm minute
        firingCal.set(Calendar.SECOND, 0); // and alarm second
        long intendedTime = firingCal.getTimeInMillis();

        registerMyAlarmBroadcast();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, myPendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, intendedTime, myPendingIntent);

        //Definindo o layout para a atividade
        setContentView(R.layout.activity_main);
    }
    private void registerMyAlarmBroadcast()
    {
        Log.i(TAG, "Going to register Intent.RegisterAlramBroadcast");

        //This is the call back function(BroadcastReceiver) which will be call when your
        //alarm time will reached.
        myBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.i(TAG,"BroadcastReceiver::OnReceive()");
                Toast.makeText(context, "Your Alarm is there", Toast.LENGTH_LONG).show();
                Log.d("AAAAAAA", "CARAIOOO");
            }
        };

        registerReceiver(myBroadcastReceiver, new IntentFilter("com.alarm.example") );
        myPendingIntent = PendingIntent.getBroadcast( this, 0, new Intent("com.alarm.example"),0 );
        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }
    private void UnregisterAlarmBroadcast()
    {
        alarmManager.cancel(myPendingIntent);
        getBaseContext().unregisterReceiver(myBroadcastReceiver);
    }



}