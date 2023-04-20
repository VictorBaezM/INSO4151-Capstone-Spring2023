package com.example.medcheck;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Hub_Activity extends AppCompatActivity {
    public static Group group;
    TextView Messages;
    public static String GroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Need to change layout
        ArrayList<TextView> Messages = new ArrayList<>();// If there are multiple text views this is the way to go.
        // TODO finish recycle view and display messages
        //Messages.add(findViewById(R.id.messageTv));
//
//        try {
////            Group.getMessagesFromGroup(GroupName,Group_Hub_Activity.this,Messages);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }





}