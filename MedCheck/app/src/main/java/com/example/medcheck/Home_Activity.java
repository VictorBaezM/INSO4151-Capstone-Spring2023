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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();

        Group g = new Group("SpecialForce","10","password");
        Message m;
        for(int i = 0;i<100;i++){
            m = new Message(newuser.getUid(),"Hello World"+i,"This is the body"+i);
            g.addMessage(m);
            Log.println(Log.INFO,"debug","This is the message created "+m.toMap());
        }
        g.uploadGroup();

        Log.println(Log.INFO,"debug","This is the group messages from db "+getMessagesFromGroup("SpecialForce").size() );
        Log.println(Log.INFO,"debug","This is the group created "+g.toMap() );



    }

    public ArrayList<Message> getMessagesFromGroup(String name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref =  db.collection("Groups").document(name);
        ArrayList<Message> result= new ArrayList<Message>();
        Dref.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if(doc.exists()){
                    Map<String,Object> map =doc.getData();
                    for(int i = 0;i<map.size()-4;i++){
                        String m = map.get("Message "+i).toString();
                        Log.println(Log.INFO,"debug","Message "+i +map.get("Message "+i).getClass() );
                        Log.println(Log.INFO,"debug","Message "+i + Arrays.stream(m.substring(1, m.length() - 1).split(",")).map(s -> s.split("=", 2)).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim())).getClass());
                        result.add(Message.fromMap(Arrays.stream(m.substring(1, m.length() - 1).split(",")).map(s -> s.split("=", 2)).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()))));
                    }

                    Log.println(Log.INFO,"debug","The messages for "+name+" are " + doc.getData());
                    Log.println(Log.INFO,"debug","The messages for "+name+" are " + result);

                }else{
                    Log.println(Log.INFO,"debug","User data not found");
                }
            }
        });
        return result;
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