package com.example.medcheck;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Data_Manager {

    private static File UserFile;


    public static String WriteUserData(User user,Context context) throws IOException, JSONException {
        JSONObject UserData = user.toJSON();
        String UserDataString = UserData.toString();
        Log.println(Log.INFO,"debug","This is the data in JSON format: "+UserData);
        Log.println(Log.INFO,"debug","This is the data in String format: "+UserData);
        UserFile = new File(context.getFilesDir(),"UserData.txt");
        FileWriter fileWriter = new FileWriter(UserFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(UserDataString);
        bufferedWriter.close();
        return UserDataString;
    }

    public static User readUserData(Context context) throws IOException, JSONException {
        UserFile = new File(context.getFilesDir(),"UserData.txt");
        FileReader fileReader = new FileReader(UserFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();// This responce will have Json Format String
        String responce = stringBuilder.toString();
        JSONObject JsonObject = new JSONObject(responce);
        Log.println(Log.INFO,"debug","This is the data read in JSON format: "+JsonObject);
        User user = new User(JsonObject.get("email").toString(),JsonObject.get("display name").toString(),JsonObject.get("country").toString(),JsonObject.get("user password").toString(),JsonObject.get("isAdmin").toString(),JsonObject.get("isDeleted").toString());




        return user;
    }

    public static boolean deleteUserData(Context context){
        UserFile.delete();
        boolean isDeleted =  UserFile.delete();
        Log.println(Log.INFO,"debug","User Data has been deleted!");
        return isDeleted;
    }








}
