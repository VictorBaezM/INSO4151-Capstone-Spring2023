package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Date_for_Alarm_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_for_alarm);

    }

    public void createGroup(View view) {
        startActivity(new Intent(Date_for_Alarm_Activity.this, Group_Hub_Activity.class));
        finish();
    }


}