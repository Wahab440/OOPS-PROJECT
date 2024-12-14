package com.example.librarymanagementapp.Models;

public class User {

    private String Username;
    private String Password;

    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public String getUsername(){
        return this.Username;
    }

    public String getPassword(){
        return this.Password;
    }

    public void setUsername(String username){
        this.Username = username;
    }

    public void setPassword(String password){
        this.Password = password;
    }
}
