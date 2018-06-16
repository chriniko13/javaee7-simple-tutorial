package com.chriniko.example.posts.control;

public class PostValidationException extends RuntimeException {

    public PostValidationException(String message) {
        super(message);
    }
}
