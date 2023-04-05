package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Home_Activity extends AppCompatActivity {


    public static ArrayList<Message> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        debug();





    }
    private void debug(){
        FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
        Group g = new Group("Helping Group","10","password");
        Group g1 = new Group("Assist Those in Need","10","password");
        Group g2 = new Group("Providing Aid","10","password");
        Message m;
        for(int i = 0;i<100;i++){
            m = new Message(newuser.getUid(),"Hello World"+i,"This is the body"+i);
            g.addMessage(m);
            g1.addMessage(m);
            g2.addMessage(m);
            Log.println(Log.INFO,"debug","This is the message created "+m.toMap());
        }
        g.uploadGroup();
        g1.uploadGroup();
        g2.uploadGroup();
        try {
            Group.getMessagesFromGroup("SpecialForce",Home_Activity.this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);}
        Group.getAllGroups();
        Log.println(Log.INFO,"debug","This is the group created "+g.toMap() );
    }

    public void viewGroups(View view) {
        startActivity(new Intent(Home_Activity.this, View_Groups_Activity.class));
        finish();
    }

    public void addAlarm(View view) {
        startActivity(new Intent(Home_Activity.this, Pending_Implementation_Activity.class)); //Place holder for future alarm implementation
        finish();
    }

    public void LogOut(View view){

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Home_Activity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Data_Manager.deleteUserData(Home_Activity.this);
            startActivity(new Intent(Home_Activity.this, StartActivity.class));
            finish();


    }

}