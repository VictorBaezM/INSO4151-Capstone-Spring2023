package com.example.medcheck;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static String email_address;
    private static String display_name;
    private static String country;
    private static String user_password;
    private static String isAdmin;
    private static String isDeleted;

    public User(String email_address,String display_name,String country,String user_password,String isAdmin,String isDeleted){
        this.email_address = email_address;
        this.display_name = display_name;
        this.country = country;
        this.user_password = user_password;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
    }


    public String getEmail_address() {
        return email_address;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getCountry() {
        return country;
    }

    public String getUser_password() {
        return user_password;
    }

    public String isAdmin() {
        return isAdmin;
    }

    public String isDeleted() {
        return isDeleted;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }

    public void setDeleted(String deleted) {
        isDeleted = deleted;
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("email",email_address);
        obj.put("display name",display_name);
        obj.put("country",country);
        obj.put("user password",user_password);
        obj.put("isAdmin",isAdmin);
        obj.put("isDeleted",isDeleted);
        return obj;
    }

    public Map<String,String> toMap() {
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("email",email_address);
        obj.put("display name",display_name);
        obj.put("country",country);
        obj.put("user password",user_password);
        obj.put("isAdmin",isAdmin);
        obj.put("isDeleted",isDeleted);
        return obj;
    }

}