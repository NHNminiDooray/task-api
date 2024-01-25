package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService{
    private final MilestoneRepository milestoneRepository;

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
}
