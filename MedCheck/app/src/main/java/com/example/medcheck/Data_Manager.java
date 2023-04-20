package com.example.medcheck;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Data_Manager {

    private static String filename = "MyAppData.txt";
    private static File UserData;


    public static void WriteUserData(User user) throws IOException {
        UserData = new File("/data/data/com.example.medcheck/files/"+filename);
        FileOutputStream fileOutputStream = new FileOutputStream(UserData);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        fileOutputStream.close();

    }

    public static User readUserData() throws IOException, ClassNotFoundException {
        UserData = new File("/data/data/com.example.medcheck/files/"+filename);
        FileInputStream fileInputStream = new FileInputStream(UserData);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User u = (User) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return u;
    }

    public static boolean deleteUserData(){
        if(UserData==null){
            return true;
        }
        boolean isDeleted =  UserData.delete();
        Log.println(Log.INFO,"debug","User Data has been deleted!");
        return isDeleted;
    }








}
