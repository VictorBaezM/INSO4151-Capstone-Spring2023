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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Calendar c = Calendar.getInstance();
//        Date date = c.getTime();
//        int Month = Integer.valueOf(date.toString().substring(4,7));
//        int Year = Integer.valueOf(date.toString().substring(date.toString().length()-4));
//        int Day = Integer.valueOf(date.toString().substring(8,10));
//        int Hour = Integer.valueOf(date.toString().substring(11,13));
//        int Minute = Integer.valueOf(date.toString().substring(14,16));


        int Year = (int) intent.getExtras().get("Year");
        int Month = (int) intent.getExtras().get("Month");
        int Day = (int) intent.getExtras().get("Day");
        int Hour = (int) intent.getExtras().get("Hour");
        int Minute = (int) intent.getExtras().get("Minute");
        String Repeats =  (String)intent.getExtras().get("Repeats");

        ArrayList<Integer> Data = new ArrayList<>();
        Data.add(Year);
        Data.add(Month);
        Data.add(Day);
        Data.add(Hour);
        Data.add(Minute);


        Date d = new Date();
        String DayOfWeek = Calendar.getInstance().getTime().toString().substring(0,3);
        Log.println(Log.INFO, "debug", "Day Of Week: " + DayOfWeek);
        Format f = new SimpleDateFormat("YYYY");
        String sCurrentYear = f.format(new Date());
        Log.println(Log.INFO, "debug", "Year Number = " + sCurrentYear);
        // displaying month number
        f = new SimpleDateFormat("MM");
        String sCurrentMonth = f.format(new Date());
        Log.println(Log.INFO, "debug", "Month Number = " + sCurrentMonth);
        // displaying day number
        f = new SimpleDateFormat("dd");
        String sCurrentDay = f.format(new Date());
        Log.println(Log.INFO, "debug", "Day Number = " + sCurrentDay);
        // displaying hour
        f = new SimpleDateFormat("H");
        String sCurrentHour = f.format(new Date());
        Log.println(Log.INFO, "debug", "Hour = " + sCurrentHour);
        // displaying minutes
        f = new SimpleDateFormat("mm");
        String sCurrentMinute = f.format(new Date());

        int CurrentYear =Integer.valueOf(sCurrentYear);
        int CurrentMonth =Integer.valueOf(sCurrentMonth);
        int CurrentDay =Integer.valueOf(sCurrentDay);
        int CurrentHour =Integer.valueOf(sCurrentHour);
        int CurrentMinute =Integer.valueOf(sCurrentMinute);
        ArrayList<Integer> CurrentData = new ArrayList<>();
        CurrentData.add(CurrentYear);
        CurrentData.add(CurrentMonth);
        CurrentData.add(CurrentDay);
        CurrentData.add(CurrentHour);
        CurrentData.add(CurrentMinute);

        Log.println(Log.INFO, "debug", "Minute = " + CurrentMinute);
        Log.println(Log.INFO, "debug", "For ID: " + (int) intent.getExtras().get("AlarmID"));


        if(Repeats.length()>0){
            Log.println(Log.INFO, "debug", "Is " +Calendar.getInstance().getTime().toString().substring(0,3)  + " in " + Repeats);
            if(Repeats.contains(Calendar.getInstance().getTime().toString().substring(0,3))) {
                if (checkTime(Data, CurrentData, intent, context)){
                    return;
            }
            }
        }
            checkDate(Data,CurrentData,intent,context);



//
////        Log.println(Log.INFO,"debug","The data received is the following: "+"\nYear: "+Year+"\nMonth: "+Month+"\nDay: "+Day+ "\nHour: "+Hour+"\nMinute: "+Minute);
//        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
//        Log.println(Log.INFO,"debug","Alarm is running ");
////        Intent intent1 =  new Intent(context, Pending_Implementation_Activity.class);
//        Intent intent1 =  new Intent(context, Alarm_View_Activity.class);
//        intent1.putExtra("AlarmID",(int)intent.getExtras().get("AlarmID"));
////        intent1.setClassName("com.example.medcheck","com.example.medcheck.Pending_Implementation_Activity");
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Toast.makeText(context, "Starting context switch", Toast.LENGTH_LONG).show();
////        context.sendBroadcast(intent1);
//        context.startActivity(intent1);
//        Toast.makeText(context, "Context switch finished", Toast.LENGTH_LONG).show();
//        MediaPlayer mp=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//        mp.start();
//        mp.stop();
    }

    public boolean checkDate(ArrayList<Integer> Data,ArrayList<Integer> CurrentData,Intent intent,Context context){
        Log.println(Log.INFO, "debug", "Trying " + Data.get(0) + " " + CurrentData.get(0));
        if (Data.get(0).equals(CurrentData.get(0))) {
            Log.println(Log.INFO, "debug", "Entered Here 1");
            Log.println(Log.INFO, "debug", "Trying " + Data.get(1) + " " + CurrentData.get(1));
            if (Data.get(1).equals(CurrentData.get(1))) {
                Log.println(Log.INFO, "debug", "Entered Here 2");
                Log.println(Log.INFO, "debug", "Trying " + Data.get(2) + " " + CurrentData.get(2));
                if (Data.get(2).equals(CurrentData.get(2))) {
                    return checkTime(Data,CurrentData,intent,context);
                }
            }
        }
        return false;
    }
    public boolean checkTime(ArrayList<Integer> Data,ArrayList<Integer> CurrentData,Intent intent,Context context){
        Log.println(Log.INFO, "debug", "Entered Here 3");
        Log.println(Log.INFO, "debug", "Trying " + Data.get(3) + " " + CurrentData.get(3));
        if (Data.get(3).equals(CurrentData.get(3))) {
            Log.println(Log.INFO, "debug", "Entered Here 4");
            Log.println(Log.INFO, "debug", "Trying " +Data.get(4) + " " + CurrentData.get(4));
            if (Data.get(4).intValue() >= CurrentData.get(4).intValue() && Data.get(4).intValue()<= CurrentData.get(4).intValue()) {
                Log.println(Log.INFO, "debug", "AlarmID: " + (int) intent.getExtras().get("AlarmID"));
            //    Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
                Log.println(Log.INFO, "debug", "Alarm is running ");
                Intent intent1 = new Intent(context, Alarm_View_Activity.class);
                intent1.putExtra("AlarmID", (int) intent.getExtras().get("AlarmID"));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // Toast.makeText(context, "Starting context switch", Toast.LENGTH_LONG).show();
                context.startActivity(intent1);
                //Toast.makeText(context, "Context switch finished", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
       }
}
