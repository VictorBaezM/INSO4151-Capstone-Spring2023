package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Select extends AppCompatActivity {

    public String GroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_select);
        GroupName = (String) getIntent().getExtras().get("GroupName");
    }

    public void joinGroup(View view){
        String passwword_given = ((EditText)findViewById(R.id.EntryPassword)).getText().toString();
        Group.verifypassword(GroupName,passwword_given,Group_Select.this);
    }

    public void getNextActivity(){
        if(!Home_Activity.user.getGroupNames().contains(GroupName)){
        Home_Activity.user.addGroupNames(GroupName);
        Home_Activity.user.uploadUser();
        }
        Intent i = new Intent(this, Chat_Activity.class);
        i.putExtra("GroupName",GroupName);
        startActivity(i);
        finish();
    }

}




