package com.example.projetoutil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//this is model class
public class User {
    //variables
    int id;
    String name;
    String password;
    String email;
    String firstname;
    String country;
    byte[] image;

    // Constructor with two parameters name and password
    public User(String name,String password,String email,String firstname,String country,byte[] image)
    {
        this.name=name;
        this.password=password;
        this.email = email;
        this.firstname = firstname;
        this.country = country;
        this.image= image;
    }
    //Parameter constructor containing all three parameters
    public User(int id,String name,String psd,String mail, String prenom,String pays, byte[] img)
    {
        this.id=id;
        this.name=name;
        this.password=psd;
        this.email = mail;
        this.firstname= prenom;
        this.country = pays;
        this.image= img;
    }
    //getting id
    public int getId() {
        return id;
    }
    //setting id
    public void setId(int id) {
        this.id = id;
    }
    //getting name
    public String getName() {
        return name;
    }
    //setting name
    public void setName(String name) {
        this.name = name;
    }
    //getting password
    public String getPassword() {
        return password;
    }
    //setting password
    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }
    //setting password
    public void setEmail(String email) {
        this.email = email;
    }




    public String getFirstname() {
        return password;
    }
    //setting password
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }



    public String getCountry() {
        return password;
    }
    //setting password
    public void setCountry(String country) {
        this.country = country;
    }




    public byte[] getImage() {
        return image;
    }
    //setting password
    public void setImage(byte[] image) {
        this.image = image;
    }


}