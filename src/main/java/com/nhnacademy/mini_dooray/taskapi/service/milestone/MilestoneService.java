package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import java.util.List;

public interface MilestoneService {
    boolean isExist(Long milestoneId);
    List<MileStoneDomainResponseDto> getMilestonesByProjectId(Long projectId);
    MileStoneDomainResponseDto getMilestoneByProjectIdAndTaskId(Long projectId, Long taskId);

    MileStoneDomainResponseDto saveMilestone(Milestone milestone);

    MileStoneDomainResponseDto updateMilestone(MilestoneRequestDto milestone, Long milestoneId);

    void deleteMilestone(Long projectId, Long milestoneId);

    MileStoneResponseDto getMilestoneByTaskId(Long taskId);

    List<MileStoneIndexListResponseDto> getMileStonesListByProjectId(Long projectId);

}
