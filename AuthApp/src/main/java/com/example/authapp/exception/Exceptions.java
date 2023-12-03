package com.example.authapp.exception;

public class Exceptions {

    public static class DuplicateLoginException extends RuntimeException {
        public DuplicateLoginException(String message) {
            super(message);
        }
    }
}
