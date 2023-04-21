package com.example.medcheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
//This activity runs when the phone boots up
public class Background_Activity extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Log.println(Log.INFO,"debug","This is MedCheck background activity");
            User user = null;
            try {
                user = Data_Manager.readUserData();
            } catch (IOException e) {
                Log.println(Log.INFO,"debug","There is no saved user in file.");
            } catch (ClassNotFoundException e) {
                Log.println(Log.INFO,"debug","Could not find class.");
            }
            Log.println(Log.INFO,"debug","The user loaded from disk is " + user.toString());
        }
    }
}
