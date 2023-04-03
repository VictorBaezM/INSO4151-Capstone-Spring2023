package com.example.medcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        login = findViewById(R.id.login1);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email,txt_password);

            }
        });
    }

    private void loginUser(String email, String password) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Home_Activity.class));
                    finish();
                }
            });
        }catch(IllegalArgumentException e){
            Toast.makeText(Login.this, "Please provide valid credentials", Toast.LENGTH_SHORT).show();
        }

    }
}