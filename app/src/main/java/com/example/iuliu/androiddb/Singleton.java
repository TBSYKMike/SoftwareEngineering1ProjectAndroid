package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-20.
 */
public class Singleton {
    private static Singleton mInstance = null;

    private String myListonJSON;
    private String item_id;
    private String mySecondListJSON;

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

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getMySecondListJSON() {
        return mySecondListJSON;
    }

    public void setMySecondListJSON(String mySecondListJSON) {
        this.mySecondListJSON = mySecondListJSON;
    }
}
