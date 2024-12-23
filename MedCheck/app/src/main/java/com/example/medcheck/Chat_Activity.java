package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medcheck.Adapters.MessageListAdapter;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Chat_Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public static ArrayList<Message> chatList;
    public String GroupName;

    public Thread MessageThread;

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

        txtMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().indexOf('\n')!=-1&&s.toString().indexOf('\n')!=0){
                    Message chat = new Message(Objects.requireNonNull(auth.getCurrentUser()).getUid(), s.toString().substring(0,s.toString().indexOf('\n')));
                    txtMessage.setText("");
                    try {
                        sendMessage(chat);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        MessageThread = new Thread(()->{
           while(true){
            loadMessages();
               try {
                   Thread.sleep(10000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
        MessageThread.start();
//        loadMessages();
        //reverse(chatList)
        MessageListAdapter = new MessageListAdapter(this, chatList);

        recyclerView.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
        recyclerView.setAdapter(MessageListAdapter);
        recyclerView.scrollToPosition(chatList.size()-1);


        sendTxt.setOnClickListener(view -> {
            String message = txtMessage.getText().toString();


                if (!message.equals("")){
                    Message chat = new Message(Objects.requireNonNull(auth.getCurrentUser()).getUid(), message);
                    txtMessage.setText("");
                    try {
                        sendMessage(chat);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(Chat_Activity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();

            }
//                txtMessage.setText("");
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(Chat_Activity.this, Home_Activity.class));
                    finish();
                    return true;
                case R.id.nav_addPerson:
                    Intent i =   new Intent(this, Add_Person_Activity.class);
                    i.putExtra("GroupName",GroupName);
                    startActivity(i);
                    finish();
                    return true;

                case R.id.nav_addAlarm:
                    startActivity(new Intent(Chat_Activity.this, View_Alarms_Activity.class));
                    finish();
                    return true;
                case R.id.nav_exitGroup:
                    showPopup(findViewById(R.id.nav_exitGroup));
                    return true;
                default:
                    return false;
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
//                    reverse(chatList);
                    Log.println(Log.INFO,"debug","Got all messages from group on db and reversed"+chatList.toString());
                    MessageListAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatList.size()-1);

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
//                    reverse(chatList);
                    Log.println(Log.INFO,"debug","Got all messages from group on db and reversed"+chatList.toString());
                    MessageListAdapter.notifyDataSetChanged();
                    group.get().addMessage(chat);
//                    chatList.add(chat);
//                    reverse(chatList);
                    MessageListAdapter.notifyItemInserted(chatList.size());
                    recyclerView.scrollToPosition(chatList.size()-1);
                    group.get().uploadGroup();
                    loadMessages();
                }
            }
        });

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.Yes:
                Toast.makeText(this, "Left group successfully", Toast.LENGTH_SHORT).show();
                Home_Activity.user.getGroupNames().remove(GroupName);
                Home_Activity.user.uploadUser();
                startActivity(new Intent(Chat_Activity.this,Home_Activity.class));
                finish();
                return true;
            case R.id.No:
                Toast.makeText(this, "Decided to stay in group", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return false;
        }

    }
}