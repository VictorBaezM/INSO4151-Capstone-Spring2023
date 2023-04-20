package com.example.medcheck;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Alarm {
    String Owner;String Medication;String Description;String Date;String Time;String Repeats;

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
    public Alarm(String owner,String medication, String description, String date, String time, String repeats) {
        Owner = owner;
        Medication = medication;
        Description = description;
        Date = date;
        Time = time;
        Repeats = repeats;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getRepeats() {
        return Repeats;
    }

    public void setRepeats(String repeats) {
        Repeats = repeats;
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
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", Repeats='" + Repeats + '\'' +
                '}';
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
        obj.put("Date",getDate());
        obj.put("Time",getTime());
        obj.put("Repeats",getRepeats());
        return obj;
    }
    //Maps an alarm with the following format
/*
    Owner = Bob
    Medication = Panadol
    Description = Take with food
    Date = 10/05/1990
    Time = 13:30
    Repeats = N/A
 */
    public Map<String,String> toMap() {
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("Owner",getOwner());
        obj.put("Medication",getMedication());
        obj.put("Description",getDescription());
        obj.put("Date",getDate());
        obj.put("Time",getTime());
        obj.put("Repeats",getRepeats());
        return obj;
    }
    //Returns an Alarm object from a Map<String,String>
    public static Alarm fromMap(Map<String,String> map){
        Log.println(Log.INFO,"debug","The alarm map received is "+map);
        Alarm alarm= new Alarm();
        alarm.setOwner(map.get("Owner"));
        alarm.setMedication(map.get("Medication"));
        alarm.setDescription(map.get("Description"));
        alarm.setDate(map.get("Date"));
        alarm.setTime(map.get("Time"));
        alarm.setRepeats(map.get("Repeats"));
        return alarm;
    }
}
