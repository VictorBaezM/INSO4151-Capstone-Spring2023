package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Create_Group_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

    }

    public void createGroup(View view) {
        startActivity(new Intent(Create_Group_Activity.this, Group_Hub_Activity.class));
        finish();
    }


}