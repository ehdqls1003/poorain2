package com.kplo.myapplication;

public class Signup_Item {

    private String user_id;
    private String user_pw;/*
    private String user_name;
    private String user_address;
    private String user_email;*/

    public void setId(String id) {
        user_id = id;
    }

    public void setPw(String pw) {
        user_pw = pw;
    }/*
    public void setName(String name) {
        user_name = name;
    }
    public void setAdd(String add) {
        user_address = add;
    }

    public void setEmail(String email) {
        user_email = email;
    }*/

    public String getId() {
        return this.user_id;
    }
    public String getPw() {
        return this.user_pw;
    }
/*

    public String getName() {
        return this.user_name;
    }

    public String getAdd() {
        return this.user_address;
    }
    public String getEmail() {
        return this.user_email;
    }
*/

}
