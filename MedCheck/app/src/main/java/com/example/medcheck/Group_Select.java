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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_select);
        //        new Thread(() -> {
//            TestManager T = new TestManager();
//            try {
//                T.Test1();
//            } catch (InterruptedException e) {throw new RuntimeException(e);} catch (
//                    ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

    }

    public void joinGroup(View view){
        String passwword_given = ((EditText)findViewById(R.id.EntryPassword)).getText().toString();
        Group.verifypassword(Group_Hub_Activity.GroupName,passwword_given,Group_Select.this);

    }

    public void getNextActivity(){
        if(!Home_Activity.user.getGroupNames().contains(Group_Hub_Activity.GroupName)){
        Home_Activity.user.addGroupNames(Group_Hub_Activity.GroupName);
        Home_Activity.user.uploadUser();
        }
        startActivity(new Intent(Group_Select.this, Group_Hub_Activity.class));
        finish();
    }

}




