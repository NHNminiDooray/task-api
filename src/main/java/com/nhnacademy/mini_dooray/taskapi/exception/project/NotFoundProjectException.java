package com.nhnacademy.mini_dooray.taskapi.exception.project;

public class NotFoundProjectException extends RuntimeException{
    public NotFoundProjectException() {
        super("프로젝트가 존재하지 않습니다");
    }
    public NotFoundProjectException(String message) {
        super(message);
    }
}
