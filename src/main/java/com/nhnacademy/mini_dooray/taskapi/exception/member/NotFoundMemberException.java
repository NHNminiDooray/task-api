package com.nhnacademy.mini_dooray.taskapi.exception.member;

public final class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException(String message) {
        super(message);
    }
}
