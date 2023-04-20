package com.example.medcheck;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.ArrayList;


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

}
