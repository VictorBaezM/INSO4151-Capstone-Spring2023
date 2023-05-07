package com.example.medcheck;

import android.util.Log;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date implements Serializable {

    String DayOfWeek;
    int Year ;

    int Month ;

    int Day ;

    int Hour ;

    int Minute ;
    public Date(){
    }
    public Date( int year, int month, int day, int hour, int minute) {
        DayOfWeek = "";
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
    }

    public Date(String dayOfWeek, int year, int month, int day, int hour, int minute) {
        DayOfWeek = dayOfWeek;
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public void setToCurrentDate(){
        java.util.Date d = new java.util.Date();
        DayOfWeek = Calendar.getInstance().getTime().toString().substring(0,3);
        Format f = new SimpleDateFormat("YYYY");
        Year = Integer.parseInt(f.format(new java.util.Date()));
        f = new SimpleDateFormat("MM");
        Month = Integer.parseInt(f.format(new java.util.Date()));
        f = new SimpleDateFormat("dd");
        Day = Integer.parseInt(f.format(new java.util.Date()));
        f = new SimpleDateFormat("H");
        Hour = Integer.parseInt(f.format(new java.util.Date()));
        f = new SimpleDateFormat("mm");
        Minute = Integer.parseInt(f.format(new java.util.Date()));
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }
    public String getDate(){
        return Day+"/"+Month+"/"+Year;
    }
    public String getDate2(){
        return Day+"-"+Month+"-"+Year;
    }
    public String getTime(){
        return Hour+":"+Minute;
    }
}
