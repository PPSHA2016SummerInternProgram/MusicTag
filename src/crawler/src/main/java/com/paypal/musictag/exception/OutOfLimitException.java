package com.paypal.musictag.exception;

public class OutOfLimitException extends Exception{
    private static final long serialVersionUID = 1L;
    public OutOfLimitException(String msg) {
        super(msg);
    }
}
