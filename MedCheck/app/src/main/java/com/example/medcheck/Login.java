package com.example.medcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;

    public static User user;
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
                //Get info from DB and store it locally
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
                    if (newuser!=null){
                        Toast.makeText(Login.this, "User is valid and is User#" + newuser.getUid(), Toast.LENGTH_LONG).show();
                        Log.println(Log.INFO,"debug","User is valid and is User#" + newuser.getUid());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference Dref =  db.collection("Users").document(newuser.getUid());
                        Dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc = task.getResult();
                                    if(doc.exists()){
                                        Map<String,Object> map =doc.getData();
                                        Log.println(Log.INFO,"debug","User data is" + doc.getData().get("email"));
                                        user = new User(map.get("email").toString(),map.get("display name").toString(),map.get("country").toString(),map.get("user password").toString(),map.get("isAdmin").toString(),map.get("isDeleted").toString());
                                        user.setGroupNames(map.get("GroupNames").toString());
                                        try {
                                            Log.println(Log.INFO,"debug","User loaded from db has" + user.toJSON());
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }else{
                                        Log.println(Log.INFO,"debug","User data not found");
                                    }
                                }
                            }
                        });

                    }
                    else{
                        Toast.makeText(Login.this, "User is invalid", Toast.LENGTH_LONG).show();

                    }


                    try {
                        Data_Manager.WriteUserData(new User(email,"none","none",password,"none","none"),Login.this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    //TODO implement email verification to activate this conditionals
                    //if(auth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(Login.this, Home_Activity.class));
                        finish();
                 //   }else{
                        Toast.makeText(Login.this, "Please verify your email ", Toast.LENGTH_SHORT).show();
                  //  }

                }
            });
        }catch(IllegalArgumentException e){
            Toast.makeText(Login.this, "Please provide valid credentials", Toast.LENGTH_SHORT).show();
        }

    }
}