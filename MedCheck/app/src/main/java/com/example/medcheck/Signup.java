package com.example.medcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    private EditText email;
    private EditText password;

    private EditText display_name;
    private EditText country;
    private Button signup;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        email = findViewById(R.id.SignUp_email);
        password = findViewById(R.id.SignUp_password);
        display_name = findViewById(R.id.Username);
        country = findViewById(R.id.Country);

        signup = findViewById(R.id.SignUp);

        auth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_display_name = display_name.getText().toString();
                String txt_country = country.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Signup.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6){
                    Toast.makeText(Signup.this, "Password too short :(", Toast.LENGTH_SHORT).show();
                }else{
                    signupUser(txt_email,txt_password,txt_display_name,txt_country);

                }
            }
        });
    }

    private void signupUser(String email, String password,String display_name,String country) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Signup.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    User user =  new User(email,display_name,country,password,"false","false");
                    try {
                        Data_Manager.WriteUserData(user,Signup.this);
                    }catch (Exception e){
                        Toast.makeText(Signup.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(Signup.this , MainActivity.class));
                    finish();
                } else{
                    Toast.makeText(Signup.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}