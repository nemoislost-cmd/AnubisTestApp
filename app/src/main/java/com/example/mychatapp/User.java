package com.example.mychatapp;

import android.widget.EditText;

public class User {
    public EditText email;
    public EditText password;
    public User(){

    }


    public User(EditText email, EditText password)
    {
        this.email=email;
        this.password=password;
    }
}
