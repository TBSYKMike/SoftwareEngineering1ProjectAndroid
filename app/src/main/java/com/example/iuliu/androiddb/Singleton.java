package com.example.iuliu.androiddb;

import android.content.SharedPreferences;

/**
 * Created by Iuliu on 2016-05-20.
 */
public class Singleton {

    private static Singleton mInstance = null;

    private String myListonJSON;
    private String itemOwn_id;
    private String stringLittlePictureUrl;
    private String stringBigPictureUrl;
    private String itemId;
    private String nameItem;
    public Singleton(){
    }
    public static Singleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new Singleton();

        }


        return mInstance;
    }

    public String getMyListonJSON() {
        return myListonJSON;
    }

    public void setMyListonJSON(String myListonJSON) {
        this.myListonJSON = myListonJSON;
    }

    public String getItemOwn_id() {
        return itemOwn_id;
    }

    public void setItemOwn_id(String item_id) {
        this.itemOwn_id = item_id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }


    public String getStringLittlePictureUrl() {
        return stringLittlePictureUrl;
    }

    public void setStringLittlePictureUrl(String stringLittlePictureUrl) {
        this.stringLittlePictureUrl = stringLittlePictureUrl;
    }

    public String getStringBigPictureUrl() {
        return stringBigPictureUrl;
    }

    public void setStringBigPictureUrl(String stringBigPictureUrl) {
        this.stringBigPictureUrl = stringBigPictureUrl;
    }
}

