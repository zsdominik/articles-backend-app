package com.zsdominik.articlesbackendapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "authors")
@Data
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;
}
