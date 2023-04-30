package com.example.medcheck;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Alarm implements Serializable {
    String Owner;String Medication;String Description;String Date;String Time;
    String Repeats;

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
    public Alarm(String owner,String medication, String description, String date, String time ) {
        Owner = owner;
        Medication = medication;
        Description = description;
        Date = date;
        Time = time;
        Repeats = "";
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

    public String getYear() {
        return Date.substring(6);
    }

    public String getMonth() {
        return Date.substring(3,5);
    }

    public String getDay() {
        return Date.substring(0,2);
    }

    public String getHour() {
        return Time.substring(0,2);
    }

    public String getMinute() {
        return Time.substring(3);
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

    public String alarmInfo(){
        return "Alarm title: "+Medication+"\nAlarm Description: "+Description+"\nDate: "+Date+"\nTime: " +Time+"\nRepeats: " +Repeats;
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


}
