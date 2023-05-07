package com.example.medcheck;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm_Scheduler {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public Alarm_Scheduler(){}

    public boolean setUpAlarm(Context context, int AlarmID, Alarm alarm){
        alarmManager = (AlarmManager) Home_Activity.GlobalContext.getSystemService(ALARM_SERVICE);
        long time;

        int Year = alarm.getYear();

        int Month = alarm.getMonth();

        int Day = alarm.getDay();

        int Hour = alarm.getHour();

        int Minute = alarm.getMinute();


        Toast.makeText(context, alarm.toString()+"\n"+"ALARM ON", Toast.LENGTH_SHORT).show();
        Log.println(Log.INFO,"debug","ID: "+AlarmID+"\nYear: "+Year+"\nMonth: "+Month+"\nDay: "+Day+ "\nHour: "+Hour+"\nMinute: "+Minute);
        Calendar calendar = Calendar.getInstance();
        // Set the alarm time using the provided Calendar parameter

        calendar.set(Calendar.YEAR, Year); // Set the desired year
        int TempMonth = Month-1;
        calendar.set(Calendar.MONTH, TempMonth); // Set the desired month

        calendar.set(Calendar.DAY_OF_MONTH, Day); // Set the desired day
        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);

        // using intent i have class AlarmReceiver class which inherits
        // BroadcastReceiver
        Intent intent = new Intent(Home_Activity.GlobalContext, AlarmReceiver.class);
        Log.println(Log.INFO,"debug","Setting AlarmID intent: "+AlarmID);
        intent.putExtra("AlarmID",AlarmID);
        intent.putExtra("Year",Year);
        intent.putExtra("Month",Month);
        intent.putExtra("Day",Day);
        intent.putExtra("Hour",Hour);
        intent.putExtra("Minute",Minute);
        intent.putExtra("Repeats",alarm.getRepeats());
        // we call broadcast using pendingIntent
        Log.println(Log.INFO,"debug","Calling broadcast using pendingIntent......");
        pendingIntent = PendingIntent.getBroadcast(Home_Activity.GlobalContext, AlarmID, intent, PendingIntent.FLAG_IMMUTABLE);

        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            if (Calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }
        // Alarm rings continuously until toggle button is turned off
        //AlarmManager.INTERVAL_DAY
        Log.println(Log.INFO,"debug","Setting Alarm in alarm manager......");
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time,  pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time,  pendingIntent);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  pendingIntent);

        // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
//        alarmManager.
        return true;

    }
}
