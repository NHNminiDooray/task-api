package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.MilestoneAccessDenyException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final TaskService taskService;

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

    public void deleteMilestone(Long projectId, Long milestoneId) {
        List<Task> tasks = taskService.getAllTasksByProjectId(projectId);

        this.milestoneRepository.findAllByProject_ProjectId(projectId).forEach(milestone -> {
            for (Task task : tasks) {
                if (milestone.getTask().getTaskId().equals(task.getTaskId())) {
                    throw new MilestoneAccessDenyException("마일스톤에 연결된 태스크가 있습니다.");
                }
            }
        });

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

    @Override
    public List<MileStoneIndexListResponseDto> getMileStonesListByProjectId(Long projectId) {
        List<Milestone> milestones = milestoneRepository.findAllByProject_ProjectId(projectId);
        return milestones.stream()
                .map(milestone -> new MileStoneIndexListResponseDto(milestone.getMilestoneId(), milestone.getMilestoneName(),milestone.getStartPeriod(),
                        milestone.getEndPeriod()))
                .collect(Collectors.toList());
    }
}
