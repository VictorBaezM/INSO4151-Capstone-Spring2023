package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class View_Alarms_Activity extends AppCompatActivity {
    ArrayList<Button> Buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alarms);
        initializeButtons();
        loadButtons();
    }

    private void loadButtons() {
        int count =0;
        for (Alarm a: Home_Activity.user.getAlarms()) {
            if(count<5){
                Button b = Buttons.get(count);
                b.setText(a.getMedication()+" "+a.getDate()+" "+a.getTime());
                b.setVisibility(View.VISIBLE);
                b.setEnabled(true);
            }
            else break;
        }
    }

    private void initializeButtons(){
        Buttons = new ArrayList<>();
        Button Alarm_Displayed_1 = findViewById(R.id.Alarm_Button1);
        Button Alarm_Displayed_2 = findViewById(R.id.Alarm_Button2);
        Button Alarm_Displayed_3 = findViewById(R.id.Alarm_Button3);
        Button Alarm_Displayed_4 = findViewById(R.id.Alarm_Button4);
        Button Alarm_Displayed_5 = findViewById(R.id.Alarm_Button5);
        Buttons.add(Alarm_Displayed_1);
        Buttons.add(Alarm_Displayed_2);
        Buttons.add(Alarm_Displayed_3);
        Buttons.add(Alarm_Displayed_4);
        Buttons.add(Alarm_Displayed_5);
    }
    public void switchView(View view) {

        startActivity(new Intent(this, Add_Alarm_Info_Activity.class));
        finish();
    }

    public void manageAlarm(View view){
        View_Alarm_Info_Activity.alarm =Home_Activity.user.getAlarms().get(Integer.valueOf(getResources().getResourceEntryName(view.getId()).substring(12))-1);
//        Log.println(Log.INFO,"debug","The  "+getResources().getResourceEntryName(view.getId()).substring(12));
//        Home_Activity.user.getAlarms().get(((view.getTransitionName());
        startActivity(new Intent(this, View_Alarm_Info_Activity.class));
        finish();
    }
}
