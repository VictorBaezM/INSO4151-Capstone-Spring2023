package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Alarm_Info_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_info);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, View_Alarms_Activity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    //Gets info from text boxes and passes them to next activity
    public void changeView(View view){
        Intent i = new Intent(this, Add_Alarm_Date_Activity.class);
        String medication = "";
        String description = "";
        medication = ((EditText) findViewById(R.id.editTextMedication)).getText().toString();
        description = ((EditText) findViewById(R.id.editTextDescription)).getText().toString();
        i.putExtra("medication",medication);
        i.putExtra("description",description);
        if(medication=="" ||description==""){
            Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_LONG).show();
        }
        else{

            startActivity(i);
        }
    }

}
