package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.MilestoneAccessDenyException;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.task.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;

    @Override
    public boolean isExist(Long milestoneId) {
        return this.milestoneRepository.existsById(milestoneId);
    }

    public List<MileStoneDomainResponseDto> getMilestonesByProjectId(Long projectId) {
        List<Milestone> milestones = this.milestoneRepository.findAllByProject_ProjectId(projectId);
        return milestones.stream().map(milestone -> new MileStoneDomainResponseDto(
                        milestone.getMilestoneId(), milestone.getProject().getProjectId(), milestone.getTask().getTaskId(),
                        milestone.getMilestoneName(), milestone.getStartPeriod(), milestone.getEndPeriod()))
                .collect(Collectors.toList());
    }

    public MileStoneDomainResponseDto getMilestoneByProjectIdAndTaskId(Long projectId, Long taskId) {
        Milestone milestone = this.milestoneRepository.findByProject_ProjectIdAndTask_TaskId(projectId, taskId);

        return new MileStoneDomainResponseDto(milestone.getMilestoneId(), milestone.getProject().getProjectId(),
                milestone.getTask().getTaskId(), milestone.getMilestoneName(), milestone.getStartPeriod(),
                milestone.getEndPeriod());
    }

    public MileStoneDomainResponseDto saveMilestone(Milestone milestone) {
        Milestone savedMilestone = this.milestoneRepository.save(milestone);

        return new MileStoneDomainResponseDto(savedMilestone.getMilestoneId(), savedMilestone.getProject().getProjectId(),
                savedMilestone.getTask().getTaskId(), savedMilestone.getMilestoneName(), savedMilestone.getStartPeriod(),
                savedMilestone.getEndPeriod());
    }

    public MileStoneDomainResponseDto updateMilestone(MilestoneRequestDto requestDto, Long milestoneId) {
        Milestone milestone = this.milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundMilestoneException("마일스톤 정보가 없습니다."));

        if (Objects.isNull(requestDto)) {
            throw new MilestoneAccessDenyException("마일스톤의 수정 정보가 없습니다. 업데이트 할 수 없습니다.");
        }
        Task task = this.taskRepository.findById(requestDto.getTaskId())
                .orElseThrow(() -> new NotFoundTaskException("등록된 task가 없습니다. 마일스톤을 수정할 수 없습니다."));

        milestone.setTask(task);
        milestone.setMilestoneName(requestDto.getMilestoneName());
        milestone.setStartPeriod(requestDto.getStartPeriod());
        milestone.setEndPeriod(requestDto.getEndPeriod());
        Milestone updatedMilestone = this.milestoneRepository.save(milestone);

        return new MileStoneDomainResponseDto(updatedMilestone.getMilestoneId(), updatedMilestone.getProject().getProjectId(),
                updatedMilestone.getTask().getTaskId(), updatedMilestone.getMilestoneName(), updatedMilestone.getStartPeriod(),
                updatedMilestone.getEndPeriod());
    }

    public void deleteMilestone(Long projectId, Long milestoneId) {
        List<Task> tasks = taskRepository.findAllByProjectProjectId(projectId);

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
