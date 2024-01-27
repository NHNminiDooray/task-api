package com.nhnacademy.mini_dooray.taskapi.dto.task_milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskMilestoneDomainDto {
    private Long taskId;
    private Long milestoneId;
}
