package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.AlarmListAdapter;
import com.example.medcheck.Adapters.GroupListAdapter;

import java.util.ArrayList;

public class View_Alarms_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> alarmList;
    private AlarmListAdapter AlarmListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alarms);
//        initializeButtons();
//        loadButtons();
        recyclerView = findViewById(R.id.RecyclerView_AlarmList);
        alarmList = new ArrayList<>();
        alarmList.addAll(Home_Activity.user.getAlarmsInfo());
        AlarmListAdapter = new AlarmListAdapter(this, alarmList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(AlarmListAdapter);
    }

//    private void loadButtons() {
//        int count =0;
//        for (Alarm a: Home_Activity.user.getAlarms()) {
//            if(count<5){
//                Button b = Buttons.get(count);
//                b.setText(a.getMedication()+" "+a.getDate()+" "+a.getTime());
//                b.setVisibility(View.VISIBLE);
//                b.setEnabled(true);
//                count++;
//            }
//            else break;
//        }
//    }
//
//    private void initializeButtons(){
//        Buttons = new ArrayList<>();
//        Button Alarm_Displayed_1 = findViewById(R.id.Alarm_Button1);
//        Button Alarm_Displayed_2 = findViewById(R.id.Alarm_Button2);
//        Button Alarm_Displayed_3 = findViewById(R.id.Alarm_Button3);
//        Button Alarm_Displayed_4 = findViewById(R.id.Alarm_Button4);
//        Button Alarm_Displayed_5 = findViewById(R.id.Alarm_Button5);
//        Buttons.add(Alarm_Displayed_1);
//        Buttons.add(Alarm_Displayed_2);
//        Buttons.add(Alarm_Displayed_3);
//        Buttons.add(Alarm_Displayed_4);
//        Buttons.add(Alarm_Displayed_5);
//    }
    public void switchView(View view) {

        startActivity(new Intent(this, Add_Alarm_Info_Activity.class));
        finish();
    }

    public void manageAlarm(View view){
        Intent i = new Intent(this, View_Alarm_Info_Activity.class);
        int Number = Integer.valueOf(getResources().getResourceEntryName(view.getId()).substring(12))-1;
        i.putExtra("AlarmNumber",Number);
        View_Alarm_Info_Activity.alarm =Home_Activity.user.getAlarms().get(Number);
//        Log.println(Log.INFO,"debug","The  "+getResources().getResourceEntryName(view.getId()).substring(12));
//        Home_Activity.user.getAlarms().get(((view.getTransitionName());
        startActivity(i);
        finish();
    }
}
