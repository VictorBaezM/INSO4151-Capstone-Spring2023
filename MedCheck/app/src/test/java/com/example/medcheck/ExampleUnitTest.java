package com.example.medcheck;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void User_isNotCreated() {
        FirebaseUser newuser = null;
        assertNull(newuser);
    }



    @Test
    public void send100messages(){
        Group G = new Group("TestGroup","P@$$W0rd");
        for (int i = 0; i < 100; i++) {
            G.addMessage(new Message("User "+i,"","This is my message subject "+i));
        }
        assertEquals(100,G.getMessages().size());
    }

    @Test
    public void getMessageFromGroup(){
        Message m1 = new Message("User "+1,"","This is my message subject "+1);
        Message m2 = new Message("User "+2,"","This is my message subject "+2);
        Message m3 = new Message("User "+3,"","This is my message subject "+3);
        Group G = new Group("TestGroup","P@$$W0rd");
        G.addMessage(m1);
        G.addMessage(m2);
        G.addMessage(m3);
        assertEquals(m2,G.getMessages().get(1));
    }










}