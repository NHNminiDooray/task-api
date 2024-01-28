package com.nhnacademy.mini_dooray.taskapi.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskIndexListResponseDto {
    private Long taskId;
    private String taskTitle;
}
