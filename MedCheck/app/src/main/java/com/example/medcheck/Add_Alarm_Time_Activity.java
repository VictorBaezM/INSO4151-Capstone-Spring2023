package com.example.medcheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Add_Alarm_Time_Activity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_time);
        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }
    //Gets info from previous activity and timepicker to create alarm.
    public void changeView(View view){
        Log.println(Log.INFO,"debug","The values received for medication is "+ getIntent().getExtras().get("medication"));
        Log.println(Log.INFO,"debug","The values received for description is "+ getIntent().getExtras().get("description"));
        Log.println(Log.INFO,"debug","The values received for date is "+getIntent().getExtras().get("Year")+"/"+getIntent().getExtras().get("Month")+"/"+ getIntent().getExtras().get("Day")+"/");
        Alarm alarm = new Alarm(FirebaseAuth.getInstance().getCurrentUser().getUid(),(String) getIntent().getExtras().get("medication"),(String) getIntent().getExtras().get("description"),new Date((int)getIntent().getExtras().get("Year"),(int)getIntent().getExtras().get("Month"),(int) getIntent().getExtras().get("Day"),alarmTimePicker.getHour(),alarmTimePicker.getMinute()));
        Log.println(Log.INFO,"debug","The alarm created is "+ alarm);
        alarm.setRepeats((String) getIntent().getExtras().get("repeats"));
        Home_Activity.user.addAlarm(alarm);

        Home_Activity.user.uploadUser();
        Alarm_Scheduler alarm_scheduler = new Alarm_Scheduler();
        alarm_scheduler.setUpAlarm(this,Home_Activity.user.getAlarms().size()-1,alarm);
        startActivity(new Intent(this, View_Alarms_Activity.class));
        finish();
    }


}
