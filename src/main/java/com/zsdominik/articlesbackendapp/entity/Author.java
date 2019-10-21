package com.zsdominik.articlesbackendapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "authors")
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    public Author() { }

    public Author(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
