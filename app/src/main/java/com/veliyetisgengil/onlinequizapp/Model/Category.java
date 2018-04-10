package com.veliyetisgengil.onlinequizapp.Model;



/**
 * Created by veliyetisgengil on 29.03.2018.
 */

public class Category {
    private String Name;
    private String idImage;

    public Category() {
    }

    public Category(String name, String idImage) {
        Name = name;
        this.idImage = idImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }
}