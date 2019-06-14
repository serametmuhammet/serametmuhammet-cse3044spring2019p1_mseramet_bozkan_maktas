package com.example.neyesek;

public class User {

    private String name;
    private String surname;
    private String favRest[];

    public User() {
    }

    private String prevRest[];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String[] getFavRest() {
        return favRest;
    }

    public void setFavRest(String[] favRest) {
        this.favRest = favRest;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
