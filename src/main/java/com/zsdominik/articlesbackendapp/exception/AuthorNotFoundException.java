package com.zsdominik.articlesbackendapp.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long authorId) {
        super("Author not found with id: " + authorId);
    }
}
