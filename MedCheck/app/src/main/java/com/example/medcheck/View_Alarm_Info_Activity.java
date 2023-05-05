package com.example.medcheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class View_Alarm_Info_Activity extends AppCompatActivity {
    public static Alarm alarm;
    TextView alarmDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alarm_info);
        alarmDetails = findViewById(R.id.AlarmDetails);
        alarmDetails.setText(alarm.alarmInfo());

    }
    public void closeWindow(View view) {
        startActivity(new Intent(this, View_Alarms_Activity.class));
        finish();
    }

    public void deleteAlarm(View view) {
        int AlarmID =(Integer) getIntent().getExtras().get("AlarmNumber");
        Home_Activity.user.getAlarms().remove(alarm);
        Intent intent = new Intent(Home_Activity.GlobalContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Home_Activity.GlobalContext, AlarmID, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Home_Activity.user.uploadUser();
        startActivity(new Intent(this, View_Alarms_Activity.class));
        finish();
    }
}
