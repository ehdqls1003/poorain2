package com.kplo.myapplication;

public class star_Item {

    private String user_id;
    private String RECIPE_ID;
    /*
    private String user_name;
    private String user_address;
    private String user_email;*/

    public void setId(String id) {
        user_id = id;
    }

    public void setRECIPE_ID(String recipe_id) {
        RECIPE_ID = recipe_id;
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
    public String getRECIPE_ID() {
        return this.RECIPE_ID;
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
