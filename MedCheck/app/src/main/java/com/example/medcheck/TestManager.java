package com.example.medcheck;

import static org.testng.AssertJUnit.assertEquals;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
//When running testmanager use a separate thread than the one used by the application you can do this by using thee following code.
/*
//        new Thread(() -> {
//            TestManager T = new TestManager();
//            try {
//                T.Test1();
//            } catch (InterruptedException e) {throw new RuntimeException(e);} catch (
//                    ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
 */
public class TestManager {
    public final Semaphore semaphore= new Semaphore(1);

    private static String Test1Group;
    public void assertEqualTest1(String ReceivedGroupString) {

            if(Test1Group.equals(ReceivedGroupString));
            else throw new RuntimeException("Group created does not match database instance received");

    }

    //This test creates a group and adds three alarms and three messages to it
    //After this it uploads it to the db and then deletes the version stored in the phone
    //Then it downloads the version present in the database and compares the two.
    public void Test1() throws InterruptedException, ExecutionException {
        Group Group1 =  new Group("LosCampeones","LosIntentos");
        Message Message1 = new Message("Pedro","","This is pedro wishing you all a good day");
        Message Message2 = new Message("Maria","","Remember to take you medication");
        Message Message3 = new Message("Julie","","On it");
        Group1.addMessage(Message1);
        Group1.addMessage(Message2);
        Group1.addMessage(Message3);
        Alarm Alarm1 =  new Alarm("Pedro","Epinephrine","must take with food","15/10/2200","13:20");
        Alarm Alarm2 =  new Alarm("Maria","Advil","","15/11/2200","16:30");
        Alarm Alarm3 =  new Alarm("Tito","Panadol","must take with food and water","12/12/2200","14:20");
        Group1.addAlarm(Alarm1);
        Group1.addAlarm(Alarm2);
        Group1.addAlarm(Alarm3);
        Group1.uploadGroup();
        Log.println(Log.INFO,"debug","The values for group1 before db interaction are: " +Group1.toString());
        Test1Group = Group1.toString()+"1";
        Group1.ClearInfo();
        Log.println(Log.INFO,"debug","The values for group1 when deleted are: " +Group1.toString());
        Group1.getGroupFromDB("LosCampeones");

        Group1.deleteGroup("LosCampeones");
    }

    public void Test2(){

    }
}
