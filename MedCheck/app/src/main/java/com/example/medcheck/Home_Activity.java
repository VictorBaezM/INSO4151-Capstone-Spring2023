package com.example.medcheck;

import static com.example.medcheck.NotificationHelper.CHANNEL_ID;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home_Activity extends AppCompatActivity {

    public static User user;
    AlarmManager alarmManager;
    Thread NotificationThread;
    static Context GlobalContext;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        createNotificationChannel();
        auth = FirebaseAuth.getInstance();
        NotificationThread =  new Thread(()->{
            checkForNotifications();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        NotificationThread.start();
        User.checkAlarms();



        GlobalContext = getApplicationContext();
        TextView welcomeText = findViewById(R.id.textView);
        welcomeText.setText("Welcome back, " + user.getDisplay_name());
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setupAlarms();

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(Home_Activity.this, "Already Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_groupadd:
                        startActivity(new Intent(Home_Activity.this, Create_Group_Activity.class));
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(Home_Activity.this, Group_Join_Activity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    private void createNotificationChannel(String title,String info) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notify User";
            String description = "Remember to take medicine";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_logo_foreground)
                .setContentTitle(title)
//                .setContentText(info)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(info))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, builder.build());

    }


    private void checkForNotifications() {
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        CollectionReference Dref1 = db1.collection("Notifications");
        Dref1.get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                QuerySnapshot doc1 = task.getResult();
                List<DocumentSnapshot> x = doc1.getDocuments();
                for (DocumentSnapshot d:x){
                    if(d.exists()&&d.getReference().getPath().contains(Objects.requireNonNull(auth.getUid()))){
                        Notification n =d.toObject(Notification.class);
                        if(n.getTitle().equals("3xcRead")){
                            return;
                        }
                        createNotificationChannel((String) d.get("title"), (String) d.get("info"));
                        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                        db2.document(d.getReference().getPath()).set(new Notification("3xcRead",""));
                    }
                }
            }
        });

    }

    public void setupAlarms(){
        ArrayList<Alarm> alarms = user.getAlarms();
        int size = alarms.size();
        Alarm_Scheduler alarm_scheduler = new Alarm_Scheduler();
        for (int i = 0; i < size ; i++) {
            Alarm alarm = alarms.get(i);
            alarm_scheduler.setUpAlarm(this,i,alarm);
        }
    }

    public void viewGroups(View view) {
        startActivity(new Intent(Home_Activity.this, View_Groups_Activity.class));

    }

    public void addAlarm(View view) {
        startActivity(new Intent(this, View_Alarms_Activity.class)); //Place holder for future alarm implementation

    }

    public void LogOut(View view){

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Home_Activity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Data_Manager.deleteUserData();
            Home_Activity.user =null;
            startActivity(new Intent(Home_Activity.this, StartActivity.class));
            finish();


    }

}