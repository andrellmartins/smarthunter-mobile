package com.example.smarthunter.Model;

public class Course {
    public String link;
    public String nome;

    public Course(String link, String nome) {
        this.link = link;
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
