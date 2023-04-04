package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.IOException;

public class StartActivity extends AppCompatActivity {
    private Button login;
    private Button signup;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();
        try {
            User user = Data_Manager.readUserData(StartActivity.this);
            if(user!=null){
                try {
                    auth.signInWithEmailAndPassword(user.getEmail_address(), user.getUser_password()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(StartActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StartActivity.this, Home_Activity.class));
                            finish();
                        }
                    });
                }catch(IllegalArgumentException e){
                    Toast.makeText(StartActivity.this, "Please provide valid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {

        } catch (JSONException e) {

        }
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Signup.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Login.class));
                finish();
            }
        });

    }
}