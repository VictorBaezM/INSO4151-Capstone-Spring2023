package com.example.medcheck;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Group {

    private String size;
    private String GroupName;
    private String isDeleted;
    private String GroupPassword;

    private ArrayList<Message> messages;

    public Group(String GroupName,String size, String GroupPassword) {
        this.size = size;
        this.GroupName = GroupName;
        this.isDeleted = "false";
        this.GroupPassword = GroupPassword;
        messages = new ArrayList<Message>();
    }

    public static void showOwnedGroups(User user,ArrayList<Button> buttons) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.println(Log.INFO,"debug","Buttons "+buttons);
        Task<QuerySnapshot> Dref =  db.collection("Groups").get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
                int count =0;
                for (QueryDocumentSnapshot i:task.getResult()){

                    if(user.getGroupNames().contains(i.getId()) && count < 5){
                        buttons.get(count).setEnabled(true);
                        buttons.get(count).setText(i.getId());
                        buttons.get(count).setVisibility(View.VISIBLE);
                        Log.println(Log.INFO,"debug","User Group Names "+user.getGroupNames());
                    Log.println(Log.INFO,"debug","Group### "+i.getId());
                        count++;}

                }
            }
        });
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getGroupPassword() {
        return GroupPassword;
    }

    public void setGroupPassword(String groupPassword) {
        GroupPassword = groupPassword;
    }

    public boolean addMessage(Message m){
        return messages.add(m);
    }

    public Map<String,String> toMap() {
        Map<String,String> obj = new HashMap<String,String>();
        int count = 0;
        obj.put("size",size);
        obj.put("GroupName",GroupName);
        obj.put("isDeleted",isDeleted);
        obj.put("GroupPassword",GroupPassword);
        for (Message m:messages){
            obj.put("Message "+ count,m.toMap().toString());
            count++;
        }
        return obj;
    }

    public void uploadGroup(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Groups").document(this.getGroupName()).set(this.toMap());
    }



    public static void getMessagesFromGroup(String name, Context context) throws InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref =  db.collection("Groups").document(name);
        ArrayList<Message> result= new ArrayList<Message>();
        Dref.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if(doc.exists()){
                    Map<String,Object> map = doc.getData();
                    for(int i = 0;i<map.size()-4;i++){
                        String m = map.get("Message "+i).toString();
                        Log.println(Log.INFO,"debug","Message "+i +map.get("Message "+i).getClass() );
                        Log.println(Log.INFO,"debug","Message "+i +Arrays.stream(m.substring(1, m.length() - 1).split(",")).map(s -> s.split("=", 2)).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim())).getClass());
                        result.add(Message.fromMap(Arrays.stream(m.substring(1, m.length() - 1).split(",")).map(s -> s.split("=", 2)).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()))));
                    }

                    Log.println(Log.INFO,"debug","The messages for "+name+" are " + doc.getData());
                    Log.println(Log.INFO,"debug","The messages for "+name+" are " + result);
                    Group.getMessagesFromGroupCallback(result,context);
                }else{
                    Log.println(Log.INFO,"debug","User data not found");
                }
            }
        });


    }

    public static void getAllGroups(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> Dref =  db.collection("Groups").get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot i:task.getResult()){
                    Log.println(Log.INFO,"debug","Group "+i.getId());
                }
            }
        });

    }

    private static void getMessagesFromGroupCallback(ArrayList<Message> result, Context context) {
        Home_Activity.messages = result;
        Toast.makeText(context, "Information updated", Toast.LENGTH_SHORT).show() ;
    }

}
