package com.zsdominik.articlesbackendapp.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    @Size(max = 100)
    private String title;

    @Column
    @Size(max = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    @Lob @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.TextType")
    private String text;

    @ManyToOne
    @JoinColumn(updatable = false)
    private Author author;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dateCreated;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dateUpdated;

    public Article() {}

    public Article(Long id, @Size(max = 100) String title, @Size(max = 255) String summary, String text, Author author, OffsetDateTime dateCreated, OffsetDateTime dateUpdated) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.text = text;
        this.author = author;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getText() {
        return text;
    }

    public Author getAuthor() {
        return author;
    }

    @CreationTimestamp
    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    @UpdateTimestamp
    public OffsetDateTime getDateUpdated() {
        return dateUpdated;
    }

}
