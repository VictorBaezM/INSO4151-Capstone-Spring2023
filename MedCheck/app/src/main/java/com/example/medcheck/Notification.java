package com.example.medcheck;

public class Notification {



     String Title;
     String Info;
    public Notification(){}

    public Notification(String title, String info) {
        Title = title;
        Info = info;
    }




    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
