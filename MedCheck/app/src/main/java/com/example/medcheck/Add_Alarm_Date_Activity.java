package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.errorprone.annotations.ForOverride;

import java.util.ArrayList;
import java.util.Calendar;

public class Add_Alarm_Date_Activity extends AppCompatActivity {
    String Day;
    String Month;
    String Year;
    CheckBox MondayCheckbox;
    CheckBox TuesdayCheckbox;
    CheckBox WednesdayCheckbox;
    CheckBox ThursdayCheckbox;
    CheckBox FridayCheckbox;
    CheckBox SaturdayCheckbox;
    CheckBox SundayCheckbox;

    ArrayList<CheckBox> checkboxes;

    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_date);
        Calendar calendar = Calendar.getInstance();
        MondayCheckbox = findViewById(R.id.MondayCheckbox);
        TuesdayCheckbox = findViewById(R.id.TuesdayCheckbox);
        WednesdayCheckbox = findViewById(R.id.WednesdayCheckbox);
        ThursdayCheckbox = findViewById(R.id.ThursdayCheckbox);
        FridayCheckbox = findViewById(R.id.FridayCheckbox);
        SaturdayCheckbox = findViewById(R.id.SaturdayCheckbox);
        SundayCheckbox = findViewById(R.id.SundayCheckbox);
        checkboxes = new ArrayList<>();
        checkboxes.add(MondayCheckbox);
        checkboxes.add(TuesdayCheckbox);
        checkboxes.add(WednesdayCheckbox);
        checkboxes.add(ThursdayCheckbox);
        checkboxes.add(FridayCheckbox);
        checkboxes.add(SaturdayCheckbox);
        checkboxes.add(SundayCheckbox);
        Day = ""+calendar.get(Calendar.DAY_OF_MONTH);
        int TempMonth = calendar.get(Calendar.MONTH)+1;
        Month = ""+TempMonth;
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    //Gets info from previous activity and calendar-view to pass to next activity.
    public void changeView(View view){
        String repeats = "";
        for (CheckBox c: checkboxes){
            if(c.isChecked()){
                if(repeats.length()<1){
                    repeats = c.getText().toString().substring(0,3);
                }else{
                    repeats = repeats+","+c.getText().toString().substring(0,3);
                }
            }

        }
        Intent i = new Intent(this, Add_Alarm_Time_Activity.class);
        i.putExtra("medication",(String) getIntent().getExtras().get("medication"));
        i.putExtra("description",(String)getIntent().getExtras().get("description"));
        i.putExtra("repeats",repeats);
        i.putExtra("Day",Integer.parseInt(Day) );
        i.putExtra("Month",Integer.parseInt(Month));
        i.putExtra("Year",Integer.parseInt(Year));
        startActivity(i);
    }
}
