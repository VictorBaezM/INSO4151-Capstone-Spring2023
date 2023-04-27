package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.errorprone.annotations.ForOverride;

import java.util.Calendar;

public class Add_Alarm_Date_Activity extends AppCompatActivity {
    String Day;
    String Month;
    String Year;
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_date);
        Calendar calendar = Calendar.getInstance();
        Day = ""+calendar.get(Calendar.DAY_OF_MONTH);
        Month = ""+calendar.get(Calendar.MONTH);
        Year = ""+calendar.get(Calendar.YEAR);
        if(Day.length()<=1){
            Day ="0"+Day;
        }
        if(Month.length()<=1){
            Month ="0"+Month;
        }
        CalendarView calendar_view = findViewById(R.id.calendarView);
        calendar_view.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Day = ""+dayOfMonth;
            Year = ""+year;
            Month = ""+month;
            if(Day.length()<=1){
                Day ="0"+Day;
            }
            if(Month.length()<=1){
                Month ="0"+Month;
            }
            Log.e("date", Day + "/" + Month + "/" + Year);
        });

    }


    //Gets info from previous activity and calendar-view to pass to next activity.
    public void changeView(View view){
        Intent i = new Intent(this, Add_Alarm_Time_Activity.class);
        i.putExtra("medication",(String) getIntent().getExtras().get("medication"));
        i.putExtra("description",(String)getIntent().getExtras().get("description"));
        i.putExtra("date",Day + "/" + Month + "/" + Year);
        startActivity(i);
        finish();
    }
}
