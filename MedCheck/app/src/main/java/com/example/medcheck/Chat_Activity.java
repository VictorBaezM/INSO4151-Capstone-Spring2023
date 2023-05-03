package com.example.medcheck;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.MessageListAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Chat_Activity extends AppCompatActivity {
    public static ArrayList<Message> chatList;
    public String GroupName;

    AtomicReference<Group> group;

    TextView txtMessage;
    MessageListAdapter MessageListAdapter;
    ImageButton sendTxt;
    FirebaseAuth auth;
    FirebaseFirestore db;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatList= new ArrayList<>();
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyclerView);
        txtMessage = findViewById(R.id.messageText);
        sendTxt = findViewById(R.id.send_icon);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        group = new AtomicReference<>(new Group());
        GroupName = (String) getIntent().getExtras().get("GroupName");
        loadMessages();
        MessageListAdapter = new MessageListAdapter(this, reverse(chatList));

        recyclerView.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
        recyclerView.setAdapter(MessageListAdapter);


        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtMessage.getText().toString();

                if (!message.equals("")){
                    Message chat = new Message(Objects.requireNonNull(auth.getCurrentUser()).getUid(), message);
                    try {
                        sendMessage(chat);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(Chat_Activity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
                }
//                txtMessage.setText("");
            }
        });
    }
    private ArrayList<Message> reverse(ArrayList<Message> chatList){
        Collections.reverse(chatList);
        return chatList;
    }
    private void loadMessages() {
        DocumentReference ref = db.collection("Groups").document(GroupName);
        Task<DocumentSnapshot> var = ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    group.set(doc.toObject(Group.class));
                    chatList.clear();
                    chatList.addAll(group.get().getMessages());
                    Log.println(Log.INFO,"debug","Got all messages from group on db "+chatList.toString());
                    reverse(chatList);
                    Log.println(Log.INFO,"debug","Got all messages from group on db and reversed"+chatList.toString());
                    MessageListAdapter.notifyDataSetChanged();

                }
            }
        });

    }

    private void sendMessage(Message chat) throws InterruptedException {
        DocumentReference ref = db.collection("Groups").document(GroupName);
        Task<DocumentSnapshot> var = ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    group.set(doc.toObject(Group.class));
                    chatList.clear();
                    chatList.addAll(group.get().getMessages());
                    Log.println(Log.INFO,"debug","Got all messages from group on db "+chatList.toString());
                    reverse(chatList);
                    Log.println(Log.INFO,"debug","Got all messages from group on db and reversed"+chatList.toString());
                    MessageListAdapter.notifyDataSetChanged();
                    group.get().addMessage(chat);
//                    chatList.add(chat);
//                    reverse(chatList);
                    MessageListAdapter.notifyItemInserted(chatList.size());
                    group.get().uploadGroup();
                    loadMessages();
                }
            }
        });

    }

}