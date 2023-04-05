package com.example.medcheck;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Message {

    private String Uid;
    private String Subject;
    private String Body;
    private String Send_Date;
    private String Send_Time;
    private String isDeleted;

    public Message(String Uid,String Subject,String Body){
        this.Uid = Uid;
        this.Subject = Subject;
        this.Body = Body;
        SimpleDateFormat SDF =  new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = SDF.format(c.getTime());
        Date time = Calendar.getInstance().getTime();
        Send_Date = date;
        Send_Time = time.toString().substring(11,19);
        this.isDeleted = "false";

    }

    public Message() {

    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
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
                ", Subject='" + Subject + '\'' +
                ", Body='" + Body + '\'' +
                ", Send_Date='" + Send_Date + '\'' +
                ", Send_Time='" + Send_Time + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                '}';
    }

    public Map<String,String> toMap(){
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("Uid",Uid);
        obj.put("Subject",Subject);
        obj.put("Body",Body);
        obj.put("Send_Date",Send_Date);
        obj.put("Send_Time",Send_Time);
        obj.put("isDeleted",isDeleted);
        return obj;
    }
    public static Message fromMap(Map<String,String> map){
        Log.println(Log.INFO,"debug","The map received is "+map);
        Message message= new Message();
        message.setUid(map.get("Uid"));
        message.setSubject(map.get("Subject"));
        message.setBody(map.get("Body"));
        message.setSend_Date(map.get("Send_Date"));
        message.setSend_Time(map.get("Send_Time"));
        message.setIsDeleted(map.get("isDeleted"));
        return message;
    }
}