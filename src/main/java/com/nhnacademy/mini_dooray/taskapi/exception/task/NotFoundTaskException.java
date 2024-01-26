package com.nhnacademy.mini_dooray.taskapi.exception.task;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(String message) {
        super(message);
    }
}
