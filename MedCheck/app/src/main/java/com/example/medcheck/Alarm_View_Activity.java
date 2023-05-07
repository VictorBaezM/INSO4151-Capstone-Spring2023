package com.example.medcheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.beust.jcommander.Strings;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import java.util.concurrent.ExecutionException;

public class Alarm_View_Activity extends AppCompatActivity {
    public MediaPlayer mp;
    int AlarmID;

    PendingIntent pendingIntent;

    FirebaseAuth auth;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_view);
        auth = FirebaseAuth.getInstance();
        AlarmID =(int)getIntent().getExtras().get("AlarmID");
        ((TextView) findViewById(R.id.MedicationName)).setText(Home_Activity.user.getAlarms().get(AlarmID).getMedication());
        ((TextView)findViewById(R.id.Description)).setText(Home_Activity.user.getAlarms().get(AlarmID).getDescription());
        mp=MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mp.start();
    }

    public void StopAlarm(View view){
        mp.stop();
        Intent intent = new Intent(Home_Activity.GlobalContext, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Home_Activity.GlobalContext, AlarmID, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        notifyGroups();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        Intent intent = new Intent(Home_Activity.GlobalContext, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Home_Activity.GlobalContext, AlarmID, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        notifyGroups();
        super.onBackPressed();
    }

    private void notifyGroups() {
        new Thread(()->{
            ArrayList<String> GroupNames = Home_Activity.user.getGroupNames();
            for (int i = 0; i < GroupNames.size(); i++) {
                Group g  = new Group();
                try {
                    g.getGroupFromDB(GroupNames.get(i));
                    g.addMessage(new Message(auth.getCurrentUser().getUid(),"Alarm for "+Home_Activity.user.getAlarms().get(AlarmID).getMedication()+ " has activated and was acknowledged by "+Home_Activity.user.getDisplay_name()));
                    g.uploadGroup();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            Date d =  new Date();
            d.setToCurrentDate();
            Home_Activity.user.getAlarms().get(AlarmID).getDatesAcknowledged().add(d);
            Home_Activity.user.uploadUser();
        }).start();




    }

}
