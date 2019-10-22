package com.zsdominik.articlesbackendapp.exception;

public class MoreThanOneFieldUpdateException extends RuntimeException {
    public MoreThanOneFieldUpdateException(String message) {
        super(message);
    }
}
