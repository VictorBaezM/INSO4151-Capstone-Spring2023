package com.example.medcheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medcheck.Adapters.AdapterChat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Chat_Activity extends AppCompatActivity {
    public static ArrayList<Message> chatList = new ArrayList<>();
    public static String GroupName;
    public static Group group;

    private void loadMessages() {
        // TODO get reference to all messages in the group and load them
        Log.println(Log.INFO,"debug","Entered here1");
        DocumentReference ref = db.collection("Groups").document(GroupName);
        ref.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.println(Log.INFO,"debug","An error has occurred " +e.getMessage());

                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Group g = new Group();
                g = snapshot.toObject(Group.class);
                Log.println(Log.INFO,"debug","An event ocurred" + g.toString());
                chatList = g.getMessages();
                Log.println(Log.INFO,"debug","chatlist messages are" + chatList.toString());
                adapterChat.notifyDataSetChanged();

            } else {
                Log.println(Log.INFO,"debug","An event ocurred but something went wrong");
            }
        });

    }

    TextView txtMessage;
    AdapterChat adapterChat;
    ImageButton sendTxt;
    FirebaseAuth auth;
    FirebaseFirestore db;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyclerView);
        txtMessage = findViewById(R.id.messageText);
        sendTxt = findViewById(R.id.send_icon);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        adapterChat = new AdapterChat(this, chatList);
        loadMessages();

        recyclerView.setAdapter(adapterChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));

        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtMessage.getText().toString();

                if (!message.equals("")){
                    Message chat = new Message(auth.getCurrentUser().getUid(), message);
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

    private void sendMessage(Message chat) throws InterruptedException {
           while(group==null);
           FirebaseFirestore db = FirebaseFirestore.getInstance();
           group.addMessage(chat);
           db.collection("Groups").document(group.getGroupName()).set(group);

    }
}