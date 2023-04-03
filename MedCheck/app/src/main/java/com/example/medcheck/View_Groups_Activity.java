package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class View_Groups_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_groups);

    }

    public void createGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Create_Group_Activity.class));
        finish();
    }

    public void joinGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Group_Join_Activity.class));
        finish();
    }

}