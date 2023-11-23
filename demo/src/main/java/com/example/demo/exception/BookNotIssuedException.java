package com.example.demo.exception;

public class BookNotIssuedException extends RuntimeException {

    public BookNotIssuedException(String message) {
        super(message);
    }
}