package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//Manages Log in of the application. This class needs to achieve the following functionality:
//     -Use text that is inserted by the user (Pending Implementation)
//     -Get information from database of user if credentials match (Pending Implementation)
//     -If user verification is valid write user data to disk (Pending Implementation)
//     -In case of wrong credentials or non-existent user, notify situation to user (Pending Implementation)

public class Log_In_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

    }

    //Changes perspective to register window.
    public void registerUser(View view) {
        startActivity(new Intent(Log_In_Activity.this, Sign_Up_Activity.class));
        finish();
    }

    //Changes perspective to Home page if certain conditions are met:
    //     -User exists in the database (Pending Implementation)
    //     -Username and password match credentials present in database (Pending Implementation)

    public void loginUser(View view) {
        startActivity(new Intent(Log_In_Activity.this, Home_Activity.class));
        finish();
    }

}