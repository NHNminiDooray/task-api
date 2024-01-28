package com.nhnacademy.mini_dooray.taskapi.service.task_milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task_milestone.TaskMilestoneDomainDto;

public interface TaskMilestoneService {
    MileStoneResponseDto getMilestoneResponseDtoByTaskId( Long taskId);
    TaskMilestoneDomainDto createTaskMilestone(Long projectId, Long taskId, Long milestoneId);
    void deleteTaskMilestone(Long projectId, Long taskId, Long milestoneId);
}
