package com.example.medcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

public class Signup extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private ProgressDialog pd;


    private EditText display_name;
    private TextView login_user;
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
        login_user = findViewById(R.id.login_user);
        pd = new ProgressDialog(this);
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
        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this , Login.class));
            }
        });
    }
//TODO implement email verification on signup
    private void signupUser(String email, String password,String display_name,String country) {
        pd.setMessage("please wait!");
        pd.show();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Signup.this, "Signup successful, please verify your email id.", Toast.LENGTH_SHORT).show();
                    User user =  new User(email,display_name,country,password,"false","false");
                    FirebaseUser newuser = FirebaseAuth.getInstance().getCurrentUser();
                    if (newuser!=null){
                        Toast.makeText(Signup.this, "User is valid and is User#" + newuser.getUid(), Toast.LENGTH_LONG).show();
                        Log.println(Log.INFO,"debug","User is valid and is User#" + newuser.getUid());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").document(newuser.getUid()).set(user.toMap());

                    }
                    else{
                        Toast.makeText(Signup.this, "User is invalid", Toast.LENGTH_LONG).show();

                    }
                    try {
                        Data_Manager.WriteUserData(user,Signup.this);
                    }catch (Exception e){
                        Toast.makeText(Signup.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(Signup.this, Home_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(Signup.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}