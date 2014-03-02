package com.mkyong.rest;

/**
 * Created by user on 01/03/14.
 */
public class AuthUser {
    public String name;
    public String password;

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

    public AuthUser(String name, String password) {

        this.name = name;
        this.password = password;
    }
}
