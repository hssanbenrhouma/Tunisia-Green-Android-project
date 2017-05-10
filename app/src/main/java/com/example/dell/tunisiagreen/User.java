package com.example.dell.tunisiagreen;

/**
 * Created by dell on 27/04/2016.
 */
public class User {
    private int id;
    private String username;
    private String Email;
    private String password;
    private String Type;
    private String Region;

    public User( String username, String email, String type, String region,String password) {

        this.username = username;
        Email = email;
        this.password = password;
        Type = type;
        Region = region;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                ", Type='" + Type + '\'' +
                ", Region='" + Region + '\'' +
                '}';
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
