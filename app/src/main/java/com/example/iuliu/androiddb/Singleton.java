package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-20.
 */
public class Singleton {
    private static Singleton mInstance = null;

    private String myListonJSON;

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
}
