package com.example.dell.tunisiagreen;

import android.app.Activity;
import android.os.Bundle;

public class ListItem {
    String nom,description,date;
    int id;

    public ListItem(int id,String nom, String description,String date) {
        this.nom = nom;
        this.id=id;
        this.description = description;
        this.date=date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
}
