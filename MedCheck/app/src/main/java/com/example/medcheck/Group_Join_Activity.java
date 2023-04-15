package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Join_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_join);

        ArrayList<Button> Buttons=new ArrayList<Button>();
        Buttons.add(findViewById(R.id.GJ_Button_1));
        Buttons.add(findViewById(R.id.GJ_Button_2));
        Buttons.add(findViewById(R.id.GJ_Button_3));
        Buttons.add(findViewById(R.id.GJ_Button_4));
        Buttons.add(findViewById(R.id.GJ_Button_5));
        Buttons.add(findViewById(R.id.GJ_Button_6));
        Buttons.add(findViewById(R.id.GJ_Button_7));
        Buttons.add(findViewById(R.id.GJ_Button_8));
        Buttons.add(findViewById(R.id.GJ_Button_9));
        Buttons.add(findViewById(R.id.GJ_Button_10));
        Group.showFirst10Groups(Buttons);
    }

    public void getPermission(View view){
        Group_Hub_Activity.GroupName = ((Button)view).getText().toString();
        Log.println(Log.INFO,"debug","Writing in log the following group name "+((Button)view).getText().toString());
        startActivity(new Intent(Group_Join_Activity.this, Group_Select.class));
    }


}