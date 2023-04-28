package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class View_Alarm_Info_Activity extends AppCompatActivity {
    static Alarm alarm;
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

        startActivity(new Intent(this, View_Alarms_Activity.class));
        finish();
    }
}
