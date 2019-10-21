package com.zsdominik.articlesbackendapp.exception;

public class ArticleNotFoundException extends Exception {

    public ArticleNotFoundException(Long id) {
        super("Article not found in the database with ID: " + id);
    }
}
