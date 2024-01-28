package com.nhnacademy.mini_dooray.taskapi.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class TaskResponseDto {
    private Long projectId;
    private String taskTitle;
    private String taskContent;
    private String taskWriteMemberId;
}
