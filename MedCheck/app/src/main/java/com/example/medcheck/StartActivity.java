package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class StartActivity extends AppCompatActivity {
    private Button login;
    private Button signup;



    //private BottomNavigationView navigationBar;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        //navigationBar = findViewById(R.id.button_navigation);
        auth = FirebaseAuth.getInstance();

        try {
            User user = Data_Manager.readUserData();
            Log.println(Log.INFO,"debug","The user loaded from disk is " + user.toString());
            if(user!=null){
                try {
                    auth.signInWithEmailAndPassword(user.getEmail_address(), user.getUser_password()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Login l = new Login();
                            l.loginHelper(StartActivity.this,StartActivity.this);
                        }
                    });
                }catch(IllegalArgumentException e){
                    Toast.makeText(StartActivity.this, "Please provide valid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Signup.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                startActivity(new Intent(StartActivity.this , Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

    }



    public void getNextActivity(){
        startActivity(new Intent(StartActivity.this, Home_Activity.class));
        finish();
    }

}