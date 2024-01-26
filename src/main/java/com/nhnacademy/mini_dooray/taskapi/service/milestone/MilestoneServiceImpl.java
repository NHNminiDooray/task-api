package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
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
    public List<MileStoneResponseDto> getMilestonesByTaskId(Long taskId) {
        List<Milestone> milestones = milestoneRepository.findAllByTaskTaskId(taskId);
        return milestones.stream()
                .map(milestone -> new MileStoneResponseDto(milestone.getStartPeriod(),
                        milestone.getEndPeriod()))
                .collect(Collectors.toList());
    }
}
