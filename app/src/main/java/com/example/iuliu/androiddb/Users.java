package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-09.
 */
public class Users {
    private String id;
    private String name;
    private String password;
    private String random;
    public Users(String id,String name,String password,String random )
    {   this.setId(id);
        this.setName(name);
        this.setPassword(password);
        this.setRandom(random);

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
