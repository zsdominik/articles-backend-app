package com.zsdominik.articlesbackendapp.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(Long id) {
        super("Article not found in the database with ID: " + id);
    }
}
