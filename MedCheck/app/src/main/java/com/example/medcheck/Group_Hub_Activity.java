package com.example.medcheck;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Hub_Activity extends AppCompatActivity {
    public static String GroupName;
    TextView Messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_implementation); // Need to change layout
        ArrayList<TextView> Messages = new ArrayList<>();// If there are multiple text views this is the way to go.
        Messages.add(findViewById(R.id.foo));

        try {
            Group.getMessagesFromGroup(GroupName,Group_Hub_Activity.this,Messages);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }





}