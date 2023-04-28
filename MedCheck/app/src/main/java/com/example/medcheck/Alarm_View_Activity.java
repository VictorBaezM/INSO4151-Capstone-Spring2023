package com.example.medcheck;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Alarm_View_Activity extends AppCompatActivity {
    public MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_view);
        mp=MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();
    }

    public void StopAlarm(View view){
        mp.stop();
        this.finish();
    }

}
