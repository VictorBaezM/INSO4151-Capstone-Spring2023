package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.GroupListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class View_Groups_Activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private User user = new User();
    private RecyclerView recyclerView;
    private ArrayList<String> groupList;
    private GroupListAdapter groupListAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_groups);
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView2);
        db = FirebaseFirestore.getInstance();
        groupList = new ArrayList<>();
        for ( Object g: loadGroups()
             ) {
            groupList.add((String) g);
        }
        groupListAdapter = new GroupListAdapter(this, groupList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(groupListAdapter);
    }

    private ArrayList loadGroups() {
        return user.getGroupNames();
    }


    public void createGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Create_Group_Activity.class));
        finish();
    }

    public void joinGroup(View view) {
        startActivity(new Intent(View_Groups_Activity.this, Group_Join_Activity.class));
        finish();
    }

    public void openGroupHub(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        String groupName = groupList.get(position);
        Intent i = new Intent(this, Chat_Activity.class);
        i.putExtra("GroupName", groupName);
        Log.d("View_Groups_Activity", "Opening group: " + groupName);
        startActivity(i);
    }
}
