package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.AlarmListAdapter;
import com.example.medcheck.Adapters.GroupListAdapter;

import java.util.ArrayList;

public class View_Alarms_Activity extends AppCompatActivity {
    private User user = Home_Activity.user;
    private RecyclerView recyclerView;
    private ArrayList<Alarm> alarmList;
    private AlarmListAdapter alarmListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_alarms);
        alarmList = new ArrayList<>();
        loadAlarms();
        alarmListAdapter = new AlarmListAdapter(this, alarmList);
        recyclerView = findViewById(R.id.recyclerViewAlarms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(alarmListAdapter);
    }

    private void loadAlarms() {
        for (Alarm a: Home_Activity.user.getAlarms()) {
             alarmList.add(a);
        }
    }

    public void switchView(View view) {

        startActivity(new Intent(this, Add_Alarm_Info_Activity.class));
        finish();
    }

   /* public void manageAlarm(View view){
        View_Alarm_Info_Activity.alarm =Home_Activity.user.getAlarms().get(Integer.valueOf(getResources().getResourceEntryName(view.getId()).substring(12))-1);
//        Log.println(Log.INFO,"debug","The  "+getResources().getResourceEntryName(view.getId()).substring(12));
//        Home_Activity.user.getAlarms().get(((view.getTransitionName());
        startActivity(new Intent(this, View_Alarm_Info_Activity.class));
        finish();
    }*/
}
