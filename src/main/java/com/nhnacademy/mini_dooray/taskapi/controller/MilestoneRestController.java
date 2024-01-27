package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.service.milestone.MilestoneService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneRestController {
    private final MilestoneService milestoneService;
    private final ProjectService projectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MileStoneDomainResponseDto> getMilestones(@PathVariable("projectId") Long projectId) {
        return this.milestoneService.getMilestonesByProjectId(projectId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MileStoneDomainResponseDto createMilestone(@PathVariable("projectId") Long projectId,
                                     @RequestBody MilestoneRequestDto milestoneRequest) {
        if (Objects.isNull(milestoneRequest)) {
            throw new NotFoundMilestoneException("마일스톤 정보가 없습니다.");
        }

        Project project = this.projectService.getProject(projectId);

        return this.milestoneService.saveMilestone(new Milestone(
                null, // Auto Increment
                project, milestoneRequest.getMilestoneName(),
                milestoneRequest.getStartPeriod(), milestoneRequest.getEndPeriod()));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{milestoneId}")
    public MileStoneDomainResponseDto updateMilestone(@PathVariable("projectId") Long projectId,
                                     @PathVariable("milestoneId") Long milestoneId,
                                     @RequestBody MilestoneRequestDto milestoneRequest) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException("프로젝트가 존재하지 않습니다.");
        }

        return this.milestoneService.updateMilestone(milestoneRequest, milestoneId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{milestoneId}")
    public void deleteMilestone(@PathVariable("projectId") Long projectId,
                                @PathVariable("milestoneId") Long milestoneId) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundMilestoneException("프로젝트가 존재하지 않습니다.");
        }
        if (!this.milestoneService.isExist(milestoneId)) {
            throw new NotFoundMilestoneException("마일스톤이 존재하지 않습니다.");
        }

        this.milestoneService.deleteMilestone(projectId, milestoneId);
    }
}
