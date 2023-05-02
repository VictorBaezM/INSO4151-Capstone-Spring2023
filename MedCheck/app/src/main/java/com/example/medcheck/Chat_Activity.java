package com.example.medcheck;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.MessageListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Chat_Activity extends AppCompatActivity {
    public static ArrayList<Message> chatList;
    public static String GroupName;
    public static Group group;


    TextView txtMessage;
    MessageListAdapter adapterChat;
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
        loadMessages();
        adapterChat = new MessageListAdapter(this, reverse(chatList));

        recyclerView.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
        recyclerView.setAdapter(adapterChat);


        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtMessage.getText().toString();

                if (!message.equals("")){
                    Message chat = new Message(Objects.requireNonNull(auth.getCurrentUser()).getUid(), message);
                    sendMessage(chat);
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
        // TODO get reference to all messages in the group and load them
       // Log.println(Log.INFO,"debug","Entered here1");
        DocumentReference ref = db.collection("Groups").document(GroupName);
        ref.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
               // Log.println(Log.INFO,"debug","An error has occurred " +e.getMessage());

                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Group g = new Group();
                g = snapshot.toObject(Group.class);
               // Log.println(Log.INFO,"debug","An event ocurred" + g.toString());
                for (Message m:g.getMessages()
                     ) {
                    chatList.add(m);
                }
                reverse(chatList);
             //   Log.println(Log.INFO,"debug","chatlist messages are" + chatList.toString());
                adapterChat.notifyDataSetChanged();

            } else {
          //      Log.println(Log.INFO,"debug","An event ocurred but something went wrong");
            }
        });

    }

    private void sendMessage(Message chat){
           FirebaseFirestore db = FirebaseFirestore.getInstance();
           group.addMessage(chat);
           adapterChat.notifyItemInserted(chatList.size()-1);
           db.collection("Groups").document(group.getGroupName()).set(group);

    }
}