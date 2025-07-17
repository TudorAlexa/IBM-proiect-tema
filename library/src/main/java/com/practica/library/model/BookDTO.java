package com.practica.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class BookDTO {

    private String name;
    private String author;
    private boolean lost;

    public BookDTO(String name, String author) {
        this.author = author;
        this.name = name;
        lost = false;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"author\":\"" + author + "\"" +
                "}";
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
