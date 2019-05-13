package com.example.projetoutil;

import java.sql.Blob;

//this is model class
public class Annonce {
    //variables
    int id;
    String name;
    String telephone;
    String ville;
    String Description;
    byte[] image;

    // Constructor with two parameters name and password
    public Annonce(String name,String telephone,String ville,String description,byte[] image)
    {
        this.name=name;
        this.telephone=telephone;
        this.ville = ville;
        this.Description= description;
        this.image= image;
    }
    //Parameter constructor containing all three parameters
    public Annonce(int id,String name,String telephone,String ville,String description,byte[] image)
    {
        this.id=id;
        this.name=name;
        this.telephone=telephone;
        this.ville = ville;
        this.Description= description;
        this.image= image;
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
    public String getTelephone() {
        return telephone;
    }
    //setting password
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }



    public String getVille() {
        return ville;
    }
    //setting password
    public void setVille (String ville) {
        this.ville = ville;
    }




    public String getDescription() {
        return Description;
    }
    //setting password
    public void setDescription(String decrip) {
        this.Description = decrip;
    }



    public byte[] getImage() {
        return image;
    }
    //setting password
    public void setImage(byte[] img) {
        this.image = img;
    }
}