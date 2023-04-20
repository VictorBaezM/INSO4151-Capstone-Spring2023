package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class View_Groups_Activity extends AppCompatActivity {
    Button Group_Displayed_1;
    Button Group_Displayed_2;
    Button Group_Displayed_3;
    Button Group_Displayed_4;
    Button Group_Displayed_5;
    ArrayList<Button> Buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_groups);
        initializeButtons();
        showOwnedGroups();
    }

    private void showOwnedGroups(){
        Group.showOwnedGroups(Home_Activity.user,Buttons);
    }
    private void initializeButtons(){
        Buttons = new ArrayList<>();
        Group_Displayed_1 = findViewById(R.id.JoinGroup);
        Group_Displayed_2 = findViewById(R.id.Group_Displayed_2);
        Group_Displayed_3 = findViewById(R.id.Group_Displayed_3);
        Group_Displayed_4 = findViewById(R.id.Group_Displayed_4);
        Group_Displayed_5 = findViewById(R.id.Group_Displayed_6);
        Buttons.add(Group_Displayed_1);
        Buttons.add(Group_Displayed_2);
        Buttons.add(Group_Displayed_3);
        Buttons.add(Group_Displayed_4);
        Buttons.add(Group_Displayed_5);
    }
    public void createGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Create_Group_Activity.class));
        finish();
    }

    public void joinGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Group_Join_Activity.class));
        finish();
    }

    public void openGroupHub(View view){
        Group_Hub_Activity.GroupName = ((Button)view).getText().toString();
        Log.println(Log.INFO,"debug","Writing in log the following group name "+((Button)view).getText().toString());
        startActivity(new Intent(View_Groups_Activity.this, Group_Hub_Activity.class));

    }

}