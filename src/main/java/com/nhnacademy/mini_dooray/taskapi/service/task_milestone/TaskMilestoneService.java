package com.nhnacademy.mini_dooray.taskapi.service.task_milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.task_milestone.TaskMilestoneDomainDto;

public interface TaskMilestoneService {
    TaskMilestoneDomainDto getTaskMilestone(Long projectId, Long taskId, Long milestoneId);
    TaskMilestoneDomainDto createTaskMilestone(Long projectId, Long taskId, Long milestoneId);
    void deleteTaskMilestone(Long projectId, Long taskId, Long milestoneId);
}
