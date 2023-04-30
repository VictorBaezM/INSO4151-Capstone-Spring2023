package com.example.medcheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

public class Home_Activity extends AppCompatActivity {

    public static User user;
    AlarmManager alarmManager;
//    PendingIntent pendingIntent;
//    public static ArrayList<Message> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setupAlarms();
    }

    public void setupAlarms(){
        ArrayList<Alarm> alarms = user.getAlarms();
        int size = alarms.size();
        Alarm_Scheduler alarm_scheduler = new Alarm_Scheduler();
        for (int i = 0; i < size ; i++) {
            Alarm alarm = alarms.get(i);
            alarm_scheduler.setUpAlarm(this,i,alarm);
        }
    }


    public void viewGroups(View view) {
        startActivity(new Intent(Home_Activity.this, View_Groups_Activity.class));

    }

    public void addAlarm(View view) {
        startActivity(new Intent(Home_Activity.this, View_Alarms_Activity.class)); //Place holder for future alarm implementation

    }

    public void LogOut(View view){

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Home_Activity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Data_Manager.deleteUserData();
            Home_Activity.user =null;
            startActivity(new Intent(Home_Activity.this, StartActivity.class));
            finish();


    }

}