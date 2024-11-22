package com.example.tp7;

public class Teacher {
    private String nom;
    private String email;

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Teacher(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }
}
