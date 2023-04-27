package com.example.medcheck;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.core.content.ContextCompat.startForegroundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        Log.println(Log.INFO,"debug","Alarm is running ");
//        Intent intent1 =  new Intent(context, Pending_Implementation_Activity.class);
        Intent intent1 =  new Intent(context, Alarm_View_Activity.class);
//        intent1.setClassName("com.example.medcheck","com.example.medcheck.Pending_Implementation_Activity");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context, "Starting context switch", Toast.LENGTH_LONG).show();
//        context.sendBroadcast(intent1);
        context.startActivity(intent1);
        Toast.makeText(context, "Context switch finished", Toast.LENGTH_LONG).show();
//        MediaPlayer mp=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//        mp.start();
//        mp.stop();
    }
}
