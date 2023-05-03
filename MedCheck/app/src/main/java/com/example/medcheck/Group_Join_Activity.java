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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.GroupJoinAdapter;
import com.example.medcheck.Adapters.GroupListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Group_Join_Activity extends AppCompatActivity {

    EditText SearchBar;
    ProgressBar LoadingIcon;
    private RecyclerView recyclerView;
    private ArrayList<String> groupList;
    private GroupJoinAdapter groupJoinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_join);
        SearchBar = findViewById(R.id.SearchBar);
        LoadingIcon = findViewById(R.id.Search_LoadIcon);
        LoadingIcon.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.RecyclerView_GroupJoin);
        groupList = new ArrayList<>();





    }

    public void getPermission(View view){
        Intent i = new Intent(this, Group_Select.class);
        i.putExtra("GroupName",((Button)view).getText().toString());
        Log.println(Log.INFO,"debug","Writing in log the following group name "+((Button)view).getText().toString());
        startActivity(i);
    }

    public void startSearch(View view){
        Group g = new Group();
        LoadingIcon.setVisibility(View.VISIBLE);
        g.searchGroup(SearchBar.getText().toString(), this);
    }


    //Method that runs when a search is executed
    //Contains a list of the names that apply for the search
    public void showResults(ArrayList<String> groups) {
        groupList.clear();
        groupList.addAll(groups);
        groupJoinAdapter = new GroupJoinAdapter(this, groupList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(groupJoinAdapter);

        LoadingIcon.setVisibility(View.INVISIBLE);
    }
}