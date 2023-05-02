package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Group_Join_Activity extends AppCompatActivity {

    EditText SearchBar;
    ProgressBar LoadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_join);
        SearchBar = findViewById(R.id.SearchBar);
        LoadingIcon = findViewById(R.id.Search_LoadIcon);
        LoadingIcon.setVisibility(View.VISIBLE);
        //Creates a seperate thread that initializes all the buttons to the different group names.
                new Thread(() -> {
                ArrayList<Button> Buttons=new ArrayList<Button>();
                Buttons.add(findViewById(R.id.GJ_Button_1));
                Buttons.add(findViewById(R.id.GJ_Button_2));
                Buttons.add(findViewById(R.id.GJ_Button_3));
                Buttons.add(findViewById(R.id.GJ_Button_4));
                Buttons.add(findViewById(R.id.GJ_Button_5));
                Buttons.add(findViewById(R.id.GJ_Button_6));
                Buttons.add(findViewById(R.id.GJ_Button_7));
                Buttons.add(findViewById(R.id.GJ_Button_8));
                Buttons.add(findViewById(R.id.GJ_Button_9));
                Buttons.add(findViewById(R.id.GJ_Button_10));
                Group.showFirst10Groups(Buttons);
                LoadingIcon.setVisibility(View.INVISIBLE);
        }).start();

    }

    public void getPermission(View view){
        Group_Hub_Activity.GroupName = ((Button)view).getText().toString();
        Log.println(Log.INFO,"debug","Writing in log the following group name "+((Button)view).getText().toString());
        startActivity(new Intent(Group_Join_Activity.this, Group_Select.class));
    }

    public void startSearch(View view){
        Group g = new Group();
        LoadingIcon.setVisibility(View.VISIBLE);
        g.searchGroup(SearchBar.getText().toString(), this);
    }


    //Method that runs when a search is executed
    //Contains a list of the names that apply for the search
    public void showResults(ArrayList<String> groups) {
        LoadingIcon.setVisibility(View.INVISIBLE);
    }
}