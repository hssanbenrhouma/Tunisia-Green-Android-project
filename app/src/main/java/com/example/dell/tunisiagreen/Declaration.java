package com.example.dell.tunisiagreen;

import java.util.Date;

/**
 * Created by dell on 28/04/2016.
 */
public class Declaration {
    private int id,idCitoyen,idresp;
    private String date,email;
    private String region,description,URLiamge;
    double longitute,latitude;

    public Declaration( int id,String email,String description, String date, double longitute, double latitude) {
        this.id = id;
        this.email=email;
        this.date = date;
        this.description = description;
        this.longitute = longitute;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCitoyen() {
        return idCitoyen;
    }

    public void setIdCitoyen(int idCitoyen) {
        this.idCitoyen = idCitoyen;
    }

    public int getIdresp() {
        return idresp;
    }

    public void setIdresp(int idresp) {
        this.idresp = idresp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }


    public String getURLiamge() {
        return URLiamge;
    }

    public void setURLiamge(String URLiamge) {
        this.URLiamge = URLiamge;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
