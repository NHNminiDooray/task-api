package com.nhnacademy.mini_dooray.taskapi.exception.tag;

public class NotFoundTagException extends RuntimeException{
    public NotFoundTagException(String message) {
        super(message);
    }

    public NotFoundTagException() {
        super("태그를 찾을 수 없습니다");
    }
}
