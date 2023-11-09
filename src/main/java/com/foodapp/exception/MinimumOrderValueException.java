package com.foodapp.exception;

public class MinimumOrderValueException extends RuntimeException {
    public MinimumOrderValueException(String s) {
        super(s);
    }
}
