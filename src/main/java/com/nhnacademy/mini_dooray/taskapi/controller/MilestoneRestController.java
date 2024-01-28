package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.service.milestone.MilestoneService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
        try {
            Project project = this.projectService.getProject(projectId);

            return this.milestoneService.saveMilestone(new Milestone(
                    null, // Auto Increment
                    project, milestoneRequest.getMilestoneName(),
                    milestoneRequest.getStartPeriod(), milestoneRequest.getEndPeriod()));
        } catch (NullPointerException e) {
            throw new NotFoundMilestoneException("마일스톤 등록에 필요한 정보들이 부족합니다.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{milestoneId}")
    public MileStoneDomainResponseDto updateMilestone(@PathVariable("projectId") Long projectId,
                                     @PathVariable("milestoneId") Long milestoneId,
                                     @RequestBody MilestoneRequestDto milestoneRequest) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException();
        }

        return this.milestoneService.updateMilestone(milestoneRequest, milestoneId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{milestoneId}")
    public void deleteMilestone(@PathVariable("projectId") Long projectId,
                                @PathVariable("milestoneId") Long milestoneId) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException();
        }
        if (!this.milestoneService.isExist(milestoneId)) {
            throw new NotFoundMilestoneException();
        }

        this.milestoneService.deleteMilestone(projectId, milestoneId);
    }
}
