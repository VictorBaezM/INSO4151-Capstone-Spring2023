package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private EditText edit;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = edit.getText().toString();
                if (txt_name.isEmpty()){
                    Toast.makeText(MainActivity.this, "No name entered", Toast.LENGTH_SHORT).show();
                } else{
                    FirebaseDatabase.getInstance().getReference().child("ProgrammingKnowledge").push().child("name").setValue(txt_name);
                }
            }
        });

    }
}