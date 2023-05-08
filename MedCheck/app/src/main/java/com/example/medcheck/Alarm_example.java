package com.example.medcheck;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medcheck.AlarmReceiver;

import java.util.Calendar;

public class Alarm_example extends AppCompatActivity {
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    CalendarView calendar_view;


    int Day;
    int Month;
    int Year;
    int Hour;
    int Minute;
    int Alarm_Id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example);
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar_view = (CalendarView) findViewById(R.id.calendarView2);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Month = calendar.get(Calendar.MONTH);
        Year = calendar.get(Calendar.YEAR);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        Alarm_Id = 0;
        calendar_view.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Day = dayOfMonth;
            Year = year;
            Month = month;
            Log.e("date", Year + "/" + Month + "/" + Day);
        });

        alarmTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            Hour = hourOfDay;
            Minute = minute;
            Log.e("time", hourOfDay + ":" + minute);
        });
    }

    // method for starting the service


    // OnToggleClicked() method is implemented the time functionality
    public void OnToggleClicked(View view) {
        long time;
      //  Toast.makeText(Alarm_example.this, "ALARM ON", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        Alarm_Id = Alarm_Id + 1;
        // Set the alarm time using the provided Calendar parameter

        calendar.set(Calendar.YEAR, Year); // Set the desired year

        calendar.set(Calendar.MONTH, Month); // Set the desired month

        calendar.set(Calendar.DAY_OF_MONTH, Day); // Set the desired day
        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);

        // using intent i have class AlarmReceiver class which inherits
        // BroadcastReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);


        // we call broadcast using pendingIntent
        pendingIntent = PendingIntent.getBroadcast(this, Alarm_Id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            if (Calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }
        // Alarm rings continuously until toggle button is turned off
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);

    }


}

