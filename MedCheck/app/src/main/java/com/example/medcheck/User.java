package com.example.medcheck;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


public class User implements java.io.Serializable{

    private  String email_address;
    private  String display_name;
    private  String country;
    private  String user_password;

    private  ArrayList<String> GroupNames;

    public ArrayList<Alarm> alarms;

    public User(){}

    public User(String email_address,String display_name,String country,String user_password){
        this.email_address = email_address;
        this.display_name = display_name;
        this.country = country;
        this.user_password = user_password;
        this.alarms =  new ArrayList<>();
        GroupNames =  new ArrayList<>();

    }



    //Adds an alarm to the list of alarms for the user
    public void addAlarm(Alarm x){
        this.alarms.add(x);
    }

    public  ArrayList<String> getGroupNames() {
        return GroupNames;
    }

    public void setGroupNames(ArrayList<String> groupNames) {
        GroupNames = groupNames;
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void addGroupNames(String groupName) {
        GroupNames.add(groupName);
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getCountry() {
        return country;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }


    public int groupNumber(){
        return getGroupNames().size();
    }


    public void uploadUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(newuser.getUid()).set(this);
    }

    public String getUserFromDB(String id) throws ExecutionException, InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref = db.collection("Users").document(id);
        AtomicReference<User> user1 = new AtomicReference<>(new User());
        Monitor m = new Monitor();
        Task<DocumentSnapshot> var = Dref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    user1.set(doc.toObject(User.class));
                    Log.println(Log.INFO, "debug", "1The group acquired by the db is " + user1.toString());
                    m.setMonitor1(1);
                }
            }
        });
        Tasks.whenAllComplete(var);
        while (m.getMonitor1() == 0) ;
        m.setMonitor1(0);

        return user1.get().getDisplay_name();
    }
    @Override
    public String toString() {
        return "User{" +
                "email_address='" + email_address + '\'' +
                ", display_name='" + display_name + '\'' +
                ", country='" + country + '\'' +
                ", user_password='" + user_password + '\'' +
                ", GroupNames=" + GroupNames +
                ", alarms=" + alarms +
                '}';
    }
}
