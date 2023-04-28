package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Add_Alarm_Time_Activity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_time);
        alarmTimePicker = findViewById(R.id.timePicker);
    }
    //Gets info from previous activity and timepicker to create alarm.
    public void changeView(View view){
        Log.println(Log.INFO,"debug","The values received for medication is "+ getIntent().getExtras().get("medication"));
        Log.println(Log.INFO,"debug","The values received for description is "+ getIntent().getExtras().get("description"));
        Log.println(Log.INFO,"debug","The values received for date is "+ getIntent().getExtras().get("date"));
        Alarm a = new Alarm(FirebaseAuth.getInstance().getCurrentUser().getUid(),(String) getIntent().getExtras().get("medication"),(String) getIntent().getExtras().get("description"),(String) getIntent().getExtras().get("date"),alarmTimePicker.getHour()+":"+alarmTimePicker.getMinute(),"no");
        Log.println(Log.INFO,"debug","The alarm created is "+ a);
        Home_Activity.user.addAlarm(a);
        Home_Activity.user.uploadUser();
        startActivity(new Intent(this, View_Alarms_Activity.class));
        finish();
    }
}
