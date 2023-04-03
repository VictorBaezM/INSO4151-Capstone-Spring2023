package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//Takes care of getting the necessary information from user for account creation. This class must complete the following requirements:
//     - Use text that is inserted by the user (Pending Implementation)
//     - If any field is empty or has invalid input notify user and don't allow user to create account until valid info is provided (Pending Implementation)
//     - If all fields contain valid information create User in database (Pending Implementation)
//     - Write newly created user data to disk (Pending Implementation)
public class Sign_Up_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

    }

    public void createUser(View view) {
        //If user information is valid
        if(true){
        startActivity(new Intent(Sign_Up_Activity.this, Home_Activity.class));
        finish();}
        //notify user
        else{
        //use toast function
        }
    }


}