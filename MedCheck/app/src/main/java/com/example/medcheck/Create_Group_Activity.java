package com.example.medcheck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
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
    }

    public void createGroup(View view) {
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
                startActivity(new Intent(Create_Group_Activity.this, Group_Hub_Activity.class));
                finish();

            }
        });

    }


}