package com.nhnacademy.mini_dooray.taskapi.exception;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(String message) {
        super(message);
    }
}
