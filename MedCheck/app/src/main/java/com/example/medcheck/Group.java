package com.example.medcheck;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Group  implements Serializable {

    private String GroupName;
    private String GroupPassword;
    private ArrayList<Message> messages;
    private ArrayList<Alarm> alarms;


    public Group(){}
    //Creates a group object
    /*
    A group object is composed of the following attributes:
    GroupName =  the name of the group
    GroupPassword = the password for that particular group
    messages = holds the messages that are part of the group
    alarms = holds the alarms that are part of the group
     */
    public Group(String GroupName, String GroupPassword) {
        this.GroupName = GroupName;
        this.GroupPassword = GroupPassword;
        messages = new ArrayList<Message>();
        alarms = new ArrayList<Alarm>();
    }
    //Receives a user and an arraylist of buttons.
    //Looks for the groups that the user provided is a part of
    //Takes the text of each button and changes it to the name of a group the user is part of
    //Each button will represent a different group.
    //When the buttons name is changed it will be made visible and will be enabled.
    //This method can only change the name of five buttons.
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

    public static void showFirst10Groups(ArrayList<Button> buttons) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Log.println(Log.INFO,"debug","Buttons "+buttons);
            Monitor m = new Monitor();
            Task<QuerySnapshot> Dref =  db.collection("Groups").get()



                    .addOnCompleteListener(task->{
                if (task.isSuccessful()){
                    int count =0;
                    for (QueryDocumentSnapshot i:task.getResult()){
                        if (i.exists()) {
                            String GroupName =i.toObject(Group.class).getGroupName();
                            if(Home_Activity.user.getGroupNames().contains(GroupName))
                                continue;
                        if(count < 10){
                            buttons.get(count).setEnabled(true);
                            buttons.get(count).setText(GroupName);
                            buttons.get(count).setVisibility(View.VISIBLE);
                            Log.println(Log.INFO,"debug","Group "+GroupName);
                            count++;}
                        Log.println(Log.INFO,"debug","This is message 1 in show first 10 groups");
    }}
                m.setMonitor2(1);
                }});
        Tasks.whenAllComplete(Dref);
        while(m.getMonitor2()==0);
        m.setMonitor2(0);
        Log.println(Log.INFO,"debug","This is message 2 in show first 10 groups");
    }

    public void searchGroup(String GName, Group_Join_Activity group_join_activity){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> Groups = new ArrayList<>();
        Task<QuerySnapshot> Dref =  db.collection("Groups").get().addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot i:task.getResult()){
                            if (i.exists()) {
                                String GroupName = (String) i.get("groupName");

                                if(GroupName.contains(GName))
                                    Groups.add(GroupName);
                            }}
                        Log.println(Log.INFO,"debug","These are the groups that match your query: "+Groups.toString());
                        group_join_activity.showResults(Groups);
                    }});
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
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

    public boolean addAlarm(Alarm a){
        return alarms.add(a);
    }



    //Converts the current group into a map and uploads it to the db

    public void uploadGroup(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Groups").document(this.getGroupName()).set(this);
    }



    //Deletes the group in the database with the given name, if it exists.
    public void deleteGroup(String name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Groups").document(name).delete()
                .addOnSuccessListener(task -> Log.println(Log.INFO,"debug","Could delete group "))
                .addOnFailureListener(task -> Log.println(Log.INFO,"debug","Could not delete group "));
    }









    //Gets all the groups present in the database and displays their names TESTING METHOD!!!
    public static void getAllGroups(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> Dref =  db.collection("Groups").get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot i:task.getResult()){
                    Log.println(Log.INFO,"debug","Group "+i.toObject(Group.class).getGroupName());
                }
            }
        });

    }


    //To use this method you first need to create an instance of group and then call it
    //Group g= new Group();
    //g.getGroupFromDB("example group");

    //This method gets the group from the db that has the same name as the one provided.
    // If it does not exist a null object reference will remain.

    public void getGroupFromDB(String name) throws ExecutionException, InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref =  db.collection("Groups").document(name);
        AtomicReference<Group> group1 = new AtomicReference<>(new Group());
        Monitor m = new Monitor();
        Task<DocumentSnapshot> var = Dref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    group1.set(doc.toObject(Group.class));
                    Log.println(Log.INFO,"debug","1The group acquired by the db is "+group1.toString());
                    m.setMonitor1(1);
                }
            }
        });
        Tasks.whenAllComplete(var);
        while(m.getMonitor1()==0);
        m.setMonitor1(0);
        setGroupName(group1.get().getGroupName());
        setMessages(group1.get().getMessages());
        setGroupPassword(group1.get().getGroupPassword());
        setAlarms(group1.get().getAlarms());
        Log.println(Log.INFO,"debug","2The group acquired by the db is "+group1.toString());
    }

    public static void verifypassword(String GroupName, String password, AppCompatActivity x){
        Log.println(Log.INFO,"debug","the group name is "+GroupName+" and the password provided was "+ password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref =  db.collection("Groups").document(GroupName);
        Dref.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if(doc.exists()){
                    Group Temp = doc.toObject(Group.class);
                    String OriginalPassword = Temp.getGroupPassword();
                    Log.println(Log.INFO,"debug","The password for the group "+GroupName+ " is " +OriginalPassword+" and the password provided was "+ password);
                    if(password.equals(OriginalPassword))
                    {if(x.getLocalClassName().equals("Group_Select")){
                        ((Group_Select)x).getNextActivity();
                    }else{
                        return;
                    }}

                }}});
    }

    @Override
    public String toString() {
        return "Group{" +
                "GroupName='" + GroupName + '\'' +
                ", GroupPassword='" + GroupPassword + '\'' +
                ", messages=" + messages +
                ", alarms=" + alarms +
                '}';
    }

    public void ClearInfo() {
        setAlarms(null);
        setMessages(null);
    }


}
