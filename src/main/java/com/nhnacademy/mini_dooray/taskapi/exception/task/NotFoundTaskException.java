package com.nhnacademy.mini_dooray.taskapi.exception.task;

public class NotFoundTaskException extends RuntimeException {

    public NotFoundTaskException() {
        super("Task를 찾을 수 없습니다");
    }
    public NotFoundTaskException(String message) {
        super(message);
    }
}
