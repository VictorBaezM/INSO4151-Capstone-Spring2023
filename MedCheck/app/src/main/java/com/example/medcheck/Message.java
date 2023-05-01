package com.example.medcheck;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Message {

    private String Uid;
    private String Content;
    private String Send_Date;
    private String Send_Time;
    private String User_Name;


    public Message(String Uid,String Content){
        this.Uid = Uid;
        this.Content =Content;
        SimpleDateFormat SDF =  new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = SDF.format(c.getTime());
        Date time = Calendar.getInstance().getTime();
        Send_Date = date;
        Send_Time = time.toString().substring(11,19);

    }

    public Message() {

    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getSend_Date() {
        return Send_Date;
    }

    public void setSend_Date(String send_Date) {
        Send_Date = send_Date;
    }

    public String getSend_Time() {
        return Send_Time;
    }

    public void setSend_Time(String send_Time) {
        Send_Time = send_Time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Uid='" + Uid + '\'' +
                ", Content='" + Content + '\'' +
                ", Send_Date='" + Send_Date + '\'' +
                ", Send_Time='" + Send_Time + '\'' +
                '}';
    }

    public Map<String,String> toMap(){
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("Uid",Uid);
        obj.put("Content",Content);
        obj.put("Send_Date",Send_Date);
        obj.put("Send_Time",Send_Time);
        return obj;
    }
    public static Message fromMap(Map<String,String> map){
        Log.println(Log.INFO,"debug","The map received is "+map);
        Message message= new Message();
        message.setUid(map.get("Uid"));
        message.setContent(map.get("Content"));
        message.setSend_Date(map.get("Send_Date"));
        message.setSend_Time(map.get("Send_Time"));
        return message;
    }
}