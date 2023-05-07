package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.GroupJoinAdapter;
import com.example.medcheck.Adapters.GroupListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        LoadingIcon.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.RecyclerView_GroupJoin);
        groupList = new ArrayList<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Group_Join_Activity.this, Home_Activity.class));
                        return true;
                    case R.id.nav_groupadd:
                        startActivity(new Intent(Group_Join_Activity.this, Create_Group_Activity.class));
                        return true;
                    case R.id.nav_search:
                        Toast.makeText(Group_Join_Activity.this, "Already in Group search", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
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