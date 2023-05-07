package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class Add_Person_Activity extends AppCompatActivity {

    EditText SearchBar;
    String email;
    String GroupName;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        SearchBar = findViewById(R.id.SearchBar);
        GroupName = (String) getIntent().getExtras().get("GroupName");
    }

    void addToGroup(User user){
        email = SearchBar.getText().toString();
        user.addGroupNames(GroupName);
        user.uploadUser();
    }


}