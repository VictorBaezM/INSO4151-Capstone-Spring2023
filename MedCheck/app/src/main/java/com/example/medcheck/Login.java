package com.example.medcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;

    private TextView signup_user;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        login = findViewById(R.id.login1);
        signup_user = findViewById(R.id.signup_user);
        auth = FirebaseAuth.getInstance();

        signup_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this , Signup.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_user.setEnabled(false);
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email,txt_password);
                signup_user.setEnabled(true);
            }
        });
    }
    //function that allows classes outside of login to access data of current user. Updates data stored locally and changes to home_activity
    // Supported classes: Login,StartActivity
    public void loginHelper(AppCompatActivity x,Context context){
        {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
            FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
            if (newuser!=null){
                Toast.makeText(context, "User is valid and is User#" + newuser.getUid(), Toast.LENGTH_LONG).show();
                Log.println(Log.INFO,"debug","User is valid and is User#" + newuser.getUid());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference Dref =  db.collection("Users").document(newuser.getUid());
                Dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task){
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            Home_Activity.user = doc.toObject(User.class);
                                try {
                                    Data_Manager.WriteUserData(Home_Activity.user);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                    Log.println(Log.INFO,"debug","User loaded from db has" + Home_Activity.user.toString());

                                if(x.getLocalClassName().equals("StartActivity")){
                                    ((StartActivity)x).getNextActivity();
                                }else{
                                    startActivity(new Intent(context, Home_Activity.class));
                                    finish();
                                }


                            }else{
                                Log.println(Log.INFO,"debug","User data not found");
                            }
                        }

                });

            }
            else{
                Toast.makeText(context, "User is invalid", Toast.LENGTH_LONG).show();

            }

//            //TODO implement email verification to activate this conditionals
//            //if(auth.getCurrentUser().isEmailVerified()){
//            startActivity(new Intent(Login.this, Home_Activity.class));
//            finish();
//            //   }else{
//            Toast.makeText(Login.this, "Please verify your email ", Toast.LENGTH_SHORT).show();
//            //  }

        }

    }

    private void loginUser(String email, String password) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                //Get info from DB and store it locally
                @Override
                public void onSuccess(AuthResult authResult){
                    loginHelper(Login.this,Login.this);}
            });
        }catch(IllegalArgumentException e){
            Toast.makeText(Login.this, "Please provide valid credentials", Toast.LENGTH_SHORT).show();
        }

    }
}