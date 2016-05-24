package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-09.
 */
public class Adds {
    private String item_id;
    private String item_name;
    private String item_info;
    private String item_picture_small;
    private String item_picture_large;
    private String item_condition;
    private String item_date;
    private String item_status;
    private String item_visit_count;
    private String item_winner_userID;
    private String item_user_userID;
    private String user_name;
    public Adds(String item_id, String item_name, String item_picture_small){
        this.setItem_id(item_id);
        this.setItem_name(item_name);
        this.setItem_picture_small(item_picture_small);
    }

    public Adds(String item_id, String item_name,String item_info, String item_picture_small, String item_picture_large,
                String item_condition, String item_date, String item_status, String item_visit_count, String item_winner_userID, String item_user_userID, String user_name) {
        this.setItem_id(item_id);
        this.setItem_name(item_name);
        this.setItem_info(item_info);
        this.setItem_picture_small(item_picture_small);
        this.setItem_picture_large(item_picture_large);
        this.setItem_condition(item_condition);
       this.setItem_visit_count(item_visit_count);
        this.setItem_date(item_date);
        this.setItem_status(item_status);
        this.setItem_winner_userID(item_winner_userID);
        this.setItem_user_userID(item_user_userID);
        this.setUser_name(user_name);
    }
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_info() {
        return item_info;
    }

    public void setItem_info(String item_info) {
        this.item_info = item_info;
    }

    public String getItem_picture_small() {
        return item_picture_small;
    }

    public void setItem_picture_small(String item_picture_small) {
        this.item_picture_small = item_picture_small;
    }

    public String getItem_picture_large() {
        return item_picture_large;
    }

    public void setItem_picture_large(String item_picture_large) {
        this.item_picture_large = item_picture_large;
    }

    public String getItem_condition() {
        return item_condition;
    }

    public void setItem_condition(String item_condition) {
        this.item_condition = item_condition;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }

    public String getItem_status() {
        return item_status;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public String getItem_visit_count() {
        return item_visit_count;
    }

    public void setItem_visit_count(String item_visit_count) {
        this.item_visit_count = item_visit_count;
    }

    public String getItem_winner_userID() {
        return item_winner_userID;
    }

    public void setItem_winner_userID(String item_winner_userID) {
        this.item_winner_userID = item_winner_userID;
    }

    public String getItem_user_userID() {
        return item_user_userID;
    }

    public void setItem_user_userID(String item_user_userID) {
        this.item_user_userID = item_user_userID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
