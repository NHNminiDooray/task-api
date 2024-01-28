package com.nhnacademy.mini_dooray.taskapi.exception.milestone;

public class NotFoundMilestoneException extends RuntimeException{

    public NotFoundMilestoneException() {
        super("마일스톤이 존재하지 않습니다.");
    }
    public NotFoundMilestoneException(String message) {
        super(message);
    }
}
