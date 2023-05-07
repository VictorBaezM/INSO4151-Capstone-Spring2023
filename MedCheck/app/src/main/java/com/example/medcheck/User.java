package com.example.medcheck;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;
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

    public ArrayList<String> getAlarmsInfo(){
        ArrayList<String> AlarmsInfo = new ArrayList<String>();
        int count =0;
        for (Alarm a: alarms) {
            AlarmsInfo.add(count+" "+a.getMedication()+" "+a.getDate().getYear()+"/"+a.getDate().getMonth()+"/"+a.getDate().getDay()+" "+a.getDate().getHour()+":"+a.getDate().getMinute());
            count++;
        }
        Log.println(Log.INFO, "debug", "The alarms are " + AlarmsInfo.toString());
        return AlarmsInfo;

    }

    public void uploadUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Users").document(newuser.getUid()).set(this);
    }

    public User getUserFromDB(String id) throws ExecutionException, InterruptedException {
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

        return user1.get();
    }
    public static void sendNotificationHelper(Alarm a){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference Dref = db.collection("Users").document(a.getOwner());
        Dref.get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                DocumentSnapshot x = task.getResult();
                if(x.exists()){
                    User temp =  x.toObject(User.class);
                    ArrayList<String> gn =temp.getGroupNames();
                    for (String s:gn) {
                        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                        CollectionReference Dref1 = db1.collection("Groups");
                        Dref1.get().addOnCompleteListener(task1->{
                            if (task1.isSuccessful()){
                                QuerySnapshot y = task1.getResult();
                                List<DocumentSnapshot> z = y.getDocuments();
                                for (DocumentSnapshot i:z) {
                                    if(i.exists()){
                                        Group g =i.toObject(Group.class);
                                        if(gn.contains(g.getGroupName())){
                                            g.addMessage(new Message(a.getOwner(),"The medication titled "+a.getMedication()+" was missed by "+temp.getDisplay_name()+" for the date and time of "+ a.getDate().getDate()+" "+a.getDate().getTime()));
                                            g.uploadGroup();
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    public static void checkAlarms(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference Dref = db.collection("Users");
        Task<QuerySnapshot> var = Dref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot doc = task.getResult();
                List<DocumentSnapshot> d =doc.getDocuments();
                for (DocumentSnapshot ds:d){
                    if(ds.exists()){
                        ArrayList<Alarm> alarms = ds.toObject(User.class).getAlarms();
                        ArrayList<Alarm> AlarmsToCheck = new ArrayList<>();
                        for (Alarm a:alarms) {
                            if(a.isCurrent()){
                                Date Acknowledgement;
                                try{
                                    Acknowledgement =a.getDatesAcknowledged().get(a.getDatesAcknowledged().size()-1);
                                }catch (IndexOutOfBoundsException e){
                                    Log.println(Log.INFO, "debug", "Alarm "+a.toString()+" is not acknowledged");
                                    sendNotification(a);
                                    return;
                                }
                                if(a.checkAcknowledgement(Acknowledgement)){
                                    Log.println(Log.INFO, "debug", "Alarm "+a.toString()+" is acknowledged");
                                }else{
                                    sendNotification(a);
                                    Log.println(Log.INFO, "debug", "Alarm "+a.toString()+" is not acknowledged");
                                }

                            }
                            Log.println(Log.INFO, "debug", "I entered here");
//                            if(a.ge)
//                            boolean AlarmIsValid =a.checkAlarm();
//                            Log.println(Log.INFO, "debug", "For alarm " + a.toString()+" result is "+AlarmIsValid);
//                            if(!AlarmIsValid){
//                                Missed.add(a);
//                            }
                        }


                    }
                }

            }
        });

    }







    public static void sendNotification(Alarm a){
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        DocumentReference Dref1 = db1.collection("Notifications").document(a.getOwner()+a.alarmInfo2());
        Dref1.get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                DocumentSnapshot doc1 = task.getResult();
                if(doc1.exists()){
                    return;
                }
                Notification n = new Notification("Missed alarm for "+a.getMedication()+" that was due for "+a.getDate().getTime(),"This is an automated message to remind you to take your medication");



                Dref1.set(n);
                sendNotificationHelper(a);
            }
        });


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
