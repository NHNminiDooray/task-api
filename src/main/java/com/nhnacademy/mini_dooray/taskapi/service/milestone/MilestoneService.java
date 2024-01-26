package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import java.util.List;

public interface MilestoneService {
    boolean isExist(Long milestoneId);
    List<Milestone> getMilestonesByProjectId(Long projectId);
    Milestone getMilestoneByProjectIdAndTaskId(Long projectId, Long taskId);

    Milestone saveMilestone(Milestone milestone);

    Milestone updateMilestone(Milestone milestone);

    void deleteMilestone(Long milestoneId);

    List<MileStoneResponseDto> getMilestonesByTaskId(Long taskId);

}
