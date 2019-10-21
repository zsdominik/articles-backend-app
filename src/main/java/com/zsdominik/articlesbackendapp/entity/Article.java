package com.zsdominik.articlesbackendapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity(name = "articles")
@Data
@NoArgsConstructor
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

    @Column
    @Lob @Basic(fetch = FetchType.LAZY)
    private String text;

    @ManyToOne
    private Author author;

    @Column(columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreated;

    @Column(columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateUpdated;
}
