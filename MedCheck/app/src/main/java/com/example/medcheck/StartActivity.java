package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button login;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Signup.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Login.class));
                finish();
            }
        });

    }
}