package com.nhnacademy.mini_dooray.taskapi.service.milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskMilestone;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.MilestoneAccessDenyException;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskMilestoneRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final TaskMilestoneRepository taskMilestoneRepository;

    @Override
    public boolean isExist(Long milestoneId) {
        return this.milestoneRepository.existsById(milestoneId);
    }

    public List<MileStoneDomainResponseDto> getMilestonesByProjectId(Long projectId) {
        List<Milestone> milestones = this.milestoneRepository.findAllByProject_ProjectId(projectId);
        return milestones.stream().map(milestone -> new MileStoneDomainResponseDto(
                        milestone.getMilestoneId(), milestone.getProject().getProjectId(),
                        milestone.getMilestoneName(), milestone.getStartPeriod(), milestone.getEndPeriod()))
                .collect(Collectors.toList());
    }

    public MileStoneDomainResponseDto saveMilestone(Milestone milestone) {
        Milestone savedMilestone = this.milestoneRepository.save(milestone);

        return new MileStoneDomainResponseDto(savedMilestone.getMilestoneId(), savedMilestone.getProject().getProjectId(),
                savedMilestone.getMilestoneName(), savedMilestone.getStartPeriod(), savedMilestone.getEndPeriod());
    }

    public MileStoneDomainResponseDto updateMilestone(MilestoneRequestDto requestDto, Long milestoneId) {
        Milestone milestone = this.milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundMilestoneException("마일스톤 정보가 없습니다."));

        if (Objects.isNull(requestDto)) {
            throw new MilestoneAccessDenyException("마일스톤의 수정 정보가 없습니다. 업데이트 할 수 없습니다.");
        }

        milestone.setMilestoneName(requestDto.getMilestoneName());
        milestone.setStartPeriod(requestDto.getStartPeriod());
        milestone.setEndPeriod(requestDto.getEndPeriod());
        Milestone updatedMilestone = this.milestoneRepository.save(milestone);

        return new MileStoneDomainResponseDto(updatedMilestone.getMilestoneId(), updatedMilestone.getProject().getProjectId(),
                updatedMilestone.getMilestoneName(), updatedMilestone.getStartPeriod(), updatedMilestone.getEndPeriod());
    }

    public void deleteMilestone(Long projectId, Long milestoneId) {
        List<TaskMilestone> taskMilestones = this.taskMilestoneRepository.findAllTaskMilestonesByMilestone_MilestoneId(milestoneId);

        this.milestoneRepository.findAllByProject_ProjectId(projectId).forEach(milestone -> {
            for (TaskMilestone taskMilestone : taskMilestones) {
                if (milestone.getMilestoneId().equals(taskMilestone.getMilestone().getMilestoneId())) {
                    throw new MilestoneAccessDenyException("마일스톤에 연결된 태스크가 있습니다.");
                }
            }
        });

        this.milestoneRepository.deleteById(milestoneId);
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
