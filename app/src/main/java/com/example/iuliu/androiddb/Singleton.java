package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-20.
 */
public class Singleton {
    private static Singleton mInstance = null;

    private String myListonJSON;
    private String itemOwn_id;
   // private String mySecondListJSON;
    private String itemId;
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
        return itemOwn_id;
    }

    public void setItem_id(String item_id) {
        this.itemOwn_id = item_id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    //  public String getMySecondListJSON() {
   //     return mySecondListJSON;
   // }

   // public void setMySecondListJSON(String mySecondListJSON) {
   //     this.mySecondListJSON = mySecondListJSON;
  //  }
}
