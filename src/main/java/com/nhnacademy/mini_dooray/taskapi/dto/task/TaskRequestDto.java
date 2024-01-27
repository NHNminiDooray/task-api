package com.nhnacademy.mini_dooray.taskapi.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private Long projectId;
    private String taskTitle;
    private String taskContent;
    private String taskWriteMemberId;
}