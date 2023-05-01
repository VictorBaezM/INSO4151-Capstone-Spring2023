package com.example.medcheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Alarm_View_Activity extends AppCompatActivity {
    public MediaPlayer mp;
    int AlarmID;

    PendingIntent pendingIntent;

    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_view);
        AlarmID =(int)getIntent().getExtras().get("AlarmID");
        ((TextView) findViewById(R.id.MedicationName)).setText(Home_Activity.user.getAlarms().get(AlarmID).getMedication());
        ((TextView)findViewById(R.id.Description)).setText(Home_Activity.user.getAlarms().get(AlarmID).getDescription());
        mp=MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mp.start();
    }

    public void StopAlarm(View view){
        mp.stop();
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, AlarmID, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        this.finish();
    }

}
