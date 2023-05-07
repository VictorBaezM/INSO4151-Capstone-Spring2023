package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Create_Group_Activity extends AppCompatActivity {

    EditText GroupName;
    EditText Size;
    EditText GroupPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        GroupName = findViewById(R.id.GroupName);
        Size = findViewById(R.id.GroupSize);
        GroupPassword = findViewById(R.id.GroupPassword);

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Create_Group_Activity.this, Home_Activity.class));
                        return true;
                    case R.id.nav_groupadd:
                        Toast.makeText(Create_Group_Activity.this, "Already in group creation screen", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(Create_Group_Activity.this, Group_Join_Activity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void createGroup(View view) {
        if(GroupName==null||Size==null||GroupPassword==null||GroupName.getText().toString().equals("")||Size.getText().toString().equals("")||GroupPassword.getText().toString().equals("")){
            Toast.makeText(Create_Group_Activity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.println(Log.INFO,"debug","Group ");
        if(Home_Activity.user.groupNumber()+1>5){
            Toast.makeText(Create_Group_Activity.this, "Maximum amount of groups reached", Toast.LENGTH_LONG).show();
            return;
        }
        Task<QuerySnapshot> Dref =  db.collection("Groups").get().addOnCompleteListener(task->{
            if (task.isSuccessful()){

                for (QueryDocumentSnapshot i:task.getResult()){
                        if(i.getId().equals(GroupName.getText().toString())){
                        Log.println(Log.INFO,"debug","Group already exists in DB");
                        Toast.makeText(Create_Group_Activity.this, "Group already exists", Toast.LENGTH_LONG).show();
                            return;}


                }
                Log.println(Log.INFO,"debug","Group name is available");
                Group newGroup = new Group(GroupName.getText().toString(),GroupPassword.getText().toString());
                newGroup.uploadGroup();
                Home_Activity.user.addGroupNames(GroupName.getText().toString());
                Home_Activity.user.uploadUser();
                Intent i = new Intent(this, Chat_Activity.class);
                i.putExtra("GroupName",GroupName.getText().toString());
                startActivity(i);
                finish();

            }
        });

    }


}