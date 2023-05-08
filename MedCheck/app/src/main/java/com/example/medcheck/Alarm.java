package com.example.medcheck;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Alarm implements Serializable {
    String Owner;String Medication;String Description;
    com.example.medcheck.Date Date;
    String Repeats;
    ArrayList<com.example.medcheck.Date> DatesAcknowledged;

    public ArrayList<com.example.medcheck.Date> getDatesAcknowledged() {
        return DatesAcknowledged;
    }

    public void setDatesAcknowledged(ArrayList<com.example.medcheck.Date> datesAcknowledged) {
        DatesAcknowledged = datesAcknowledged;
    }

    public Alarm(){}

    /*
    Creates an Alarm object that holds the following information
    Owner = holds the name of the user that owns the alarm
    Medication = holds the name of the medication to be taken
    Description = holds relevant information regarding medication usage
    Date = holds the date for which the alarm is set (DD/MM/YYYY)
    Time = holds the time set for the alarm to go of
    Repeats = stores weather the alarm repeats.
     */
    public Alarm(String owner, String medication, String description, com.example.medcheck.Date date ) {
        Owner = owner;
        Medication = medication;
        Description = description;
        Date = date;
        Repeats = "";
        DatesAcknowledged = new ArrayList<>();
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getMedication() {
        return Medication;
    }

    public void setMedication(String medication) {
        Medication = medication;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public com.example.medcheck.Date getDate() {
        return Date;
    }

    public void setDate(com.example.medcheck.Date date) {
        Date = date;
    }



    public String getRepeats() {
        return Repeats;
    }

    public String addRepeat(String repeat) {
        if(Repeats.length()<1){
            Repeats = repeat;
        }else{
            Repeats = Repeats +","+repeat;
        }
        return Repeats;
    }

    public void setRepeats(String repeats) {
        Repeats = repeats;
    }

    public int getYear() {
        return Date.getYear();
    }

    public int getMonth() {
        return Date.getMonth();
    }

    public int getDay() {
        return Date.getDay();
    }

    public int getHour() {
        return Date.getHour();
    }

    public int getMinute() {
        return Date.getMinute();
    }
    //Creates a string from alarm with the following format
/*
Alarm{Owner=Bob\Medication=Panadol\Description=Take with food\Date=10/05/1990\Time=13:30\, Repeats=N/A\}
 */

    @Override
    public String toString() {
        return "Alarm{" +
                "Owner='" + getOwner() + '\'' +
                "Medication='" + Medication + '\'' +
                ", Description='" + Description + '\'' +
                ", Date='" + Date.getDate() + '\'' +
                ", Time='" + Date.getTime()+ '\'' +
                ", Repeats='" + Repeats + '\'' +
                '}';
    }

    public String alarmInfo(){
        return "Alarm title: "+Medication+"\nAlarm Description: "+Description+"\nDate: "+Date.getDate()+"\nTime: " +Date.getTime()+"\nRepeats: " +Repeats;
    }
    public String alarmInfo2(){
        return "Alarm title: "+Medication+"\nAlarm Description: "+Description+"\nDate: "+Date.getDate2()+"\nTime: " +Date.getTime()+"\nRepeats: " +Repeats;
    }

    public boolean checkAlarm(){

        int Year = Integer.valueOf(getYear());

        int Month = Integer.valueOf(getMonth());

        int Day = Integer.valueOf(getDay());

        int Hour = Integer.valueOf(getHour());

        int Minute = Integer.valueOf(getMinute());

        ArrayList<Integer> Data = new ArrayList<>();
        Data.add(Year);
        Data.add(Month);
        Data.add(Day);
        Data.add(Hour);
        Data.add(Minute);


        java.util.Date d = new Date();
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


        if(Repeats.length()>0){
            Log.println(Log.INFO, "debug", "Is " +Calendar.getInstance().getTime().toString().substring(0,3)  + " in " + Repeats);
            if(Repeats.contains(Calendar.getInstance().getTime().toString().substring(0,3))) {

                    return checkTime(Data, CurrentData);

            }
        }
        return checkDate(Data,CurrentData);



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
    public boolean isCurrent(){
        com.example.medcheck.Date d = new com.example.medcheck.Date();
        d.setToCurrentDate();
        if(Repeats.contains(d.getDayOfWeek())||(getYear()==d.getYear()&&getMonth()==d.getMonth()&&getDay()==d.getDay())){
            return true;
        }
        else
            return false;
    }
    public boolean checkAcknowledgement(com.example.medcheck.Date DateAcknowledged){
        ArrayList<Integer> Data = new ArrayList<>();
        Data.add(DateAcknowledged.getYear());
        Data.add(DateAcknowledged.getMonth());
        Data.add(DateAcknowledged.getDay());
        Data.add(DateAcknowledged.getHour());
        Data.add(DateAcknowledged.getMinute());


        int CurrentYear =getYear();
        int CurrentMonth =getMonth();
        int CurrentDay =getDay();
        int CurrentHour =getHour();
        int CurrentMinute =getMinute();
        ArrayList<Integer> CurrentData = new ArrayList<>();
        CurrentData.add(CurrentYear);
        CurrentData.add(CurrentMonth);
        CurrentData.add(CurrentDay);
        CurrentData.add(CurrentHour);
        CurrentData.add(CurrentMinute);

        Log.println(Log.INFO, "debug", "Minute = " + CurrentMinute);


        if(Repeats.length()>0){
            Log.println(Log.INFO, "debug", "Is " +Calendar.getInstance().getTime().toString().substring(0,3)  + " in " + Repeats);
            if(Repeats.contains(Calendar.getInstance().getTime().toString().substring(0,3))) {

                return checkTime(Data, CurrentData);

            }
        }
        return checkDate(Data,CurrentData);


    }


    public boolean checkDate(ArrayList<Integer> Data, ArrayList<Integer> CurrentData){
        Log.println(Log.INFO, "debug", "Trying " + Data.get(0) + " " + CurrentData.get(0));
        if (Data.get(0).compareTo(CurrentData.get(0))>=0) {
            Log.println(Log.INFO, "debug", "Entered Here 1");
            Log.println(Log.INFO, "debug", "Trying " + Data.get(1) + " " + CurrentData.get(1));
            if (Data.get(1).compareTo(CurrentData.get(1))>=0) {
                Log.println(Log.INFO, "debug", "Entered Here 2");
                Log.println(Log.INFO, "debug", "Trying " + Data.get(2) + " " + CurrentData.get(2));
                if (Data.get(2).compareTo(CurrentData.get(2))>=0) {
                    return checkTime(Data,CurrentData);
                }
            }
        }
        return false;
    }
    public boolean checkTime(ArrayList<Integer> Data,ArrayList<Integer> CurrentData){
        Log.println(Log.INFO, "debug", "Entered Here 3");
        Log.println(Log.INFO, "debug", "Trying " + Data.get(3) + " " + CurrentData.get(3));
        if (Data.get(3).compareTo(CurrentData.get(3))>=0) {
            Log.println(Log.INFO, "debug", "Entered Here 4");
            Log.println(Log.INFO, "debug", "Trying " +Data.get(4) + " " + CurrentData.get(4));
            if (Data.get(4).intValue()+5 > CurrentData.get(4).intValue()) {
                return true;
            }
        }
        return false;
    }







    //Creates a JSON Object from alarm with the following format
/*
    Owner = Bob
    Medication = Panadol
    Description = Take with food
    Date = 10/05/1990
    Time = 13:30
    Repeats = N/A
 */
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Owner",getOwner());
        obj.put("Medication",getMedication());
        obj.put("Description",getDescription());
        obj.put("Date",getDate().getDate());
        obj.put("Time",getDate().getTime());
        obj.put("Repeats",getRepeats());
        return obj;
    }


}
