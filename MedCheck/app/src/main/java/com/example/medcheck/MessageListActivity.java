package com.example.medcheck;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.MessageListAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private FirebaseAuth auth;
    List<Message> messageList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the messageList
        auth = FirebaseAuth.getInstance();
        Log.println(Log.INFO,"debug","User id is " + auth.getUid());

        messageList = new ArrayList<Message>();

        // Add messages to the list

        setContentView(R.layout.activity_message_list);
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);

        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }
}