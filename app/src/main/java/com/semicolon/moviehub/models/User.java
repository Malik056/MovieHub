package com.semicolon.moviehub.models;

public class User {

    public static final String viewer = "Viewer";
    public static final String uploader = "Uploader";
    public static final String premium = "Premium";
    public static final String admin = "admin";

    String email;
    String fullname;
    String password;
    String type;

    User(){}

    public User(String email, String fullname, String password, String type)
    {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String full) {
        fullname = full;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
