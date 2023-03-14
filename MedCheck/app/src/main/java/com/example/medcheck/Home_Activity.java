package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

    }

    public void viewGroups(View view) {
        startActivity(new Intent(Home_Activity.this, View_Groups_Activity.class));
        finish();
    }

    public void addAlarm(View view) {
        startActivity(new Intent(Home_Activity.this, Pending_Implementation_Activity.class)); //Place holder for future alarm implementation
        finish();
    }

}