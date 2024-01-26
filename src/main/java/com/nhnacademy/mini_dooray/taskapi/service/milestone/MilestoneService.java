package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MilestoneService {
    public List<Milestone> getMilestonesByProjectId(Long projectId);
    public Milestone getMilestoneByProjectIdAndTaskId(Long projectId, Long taskId);

    public Milestone saveMilestone(Milestone milestone);

    public Milestone updateMilestone(Milestone milestone);

    public void deleteMilestone(Long milestoneId);
}
