package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-09.
 */
public class Users {
    private String name;
    private String password;
    private String random;
    public Users(String name,String password,String random )
    {
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
}
