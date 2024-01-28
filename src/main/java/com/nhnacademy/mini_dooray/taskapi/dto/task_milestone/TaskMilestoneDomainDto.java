package com.nhnacademy.mini_dooray.taskapi.dto.task_milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TaskMilestoneDomainDto {
    private Long taskId;
    private Long milestoneId;
}
