package com.kplo.myapplication;

public class friend_Item {

    private String user_id;
    private String id_friend;
    /*
    private String user_name;
    private String user_address;
    private String user_email;*/

    public void setId(String id) {
        user_id = id;
    }

    public void setId_friend(String friend) {
        id_friend = friend;
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
    public String getId_friend() {
        return this.id_friend;
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
