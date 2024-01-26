package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;

    @Override
    public boolean isExist(Long milestoneId) {
        return this.milestoneRepository.existsById(milestoneId);
    }

    public List<Milestone> getMilestonesByProjectId(Long projectId) {
        return this.milestoneRepository.findAllByProject_ProjectId(projectId);
    }

    public Milestone getMilestoneByProjectIdAndTaskId(Long projectId, Long taskId) {
        return this.milestoneRepository.findByProject_ProjectIdAndTask_TaskId(projectId, taskId);
    }

    public Milestone saveMilestone(Milestone milestone) {
        return this.milestoneRepository.save(milestone);
    }

    public Milestone updateMilestone(Milestone milestone) {
        return this.milestoneRepository.save(milestone);
    }

    public void deleteMilestone(Long milestoneId) {
        this.milestoneRepository.deleteById(milestoneId);
    }


    @Override
    public MileStoneResponseDto getMilestoneByTaskId(Long taskId) {
        Milestone milestone = milestoneRepository.findById(taskId).orElseThrow(()-> new NotFoundMilestoneException("milestone을 찾을 수 없습니다"));
        return new MileStoneResponseDto(milestone.getStartPeriod(), milestone.getEndPeriod());
    }


    @Override
    public List<MileStoneIndexListResponseDto> getMileStonesListByProjectId(Long projectId) {
        List<Milestone> milestones = milestoneRepository.findAllByProject_ProjectId(projectId);
        return milestones.stream()
                .map(milestone -> new MileStoneIndexListResponseDto(milestone.getMilestoneId(), milestone.getMilestoneName(),milestone.getStartPeriod(),
                        milestone.getEndPeriod()))
                .collect(Collectors.toList());
    }
}
