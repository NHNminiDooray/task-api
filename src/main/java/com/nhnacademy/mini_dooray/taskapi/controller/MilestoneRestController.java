package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.service.milestone.MilestoneService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneRestController {
    private final MilestoneService milestoneService;
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public List<Milestone> getMilestones(@PathVariable("projectId") Long projectId) {
        return this.milestoneService.getMilestonesByProjectId(projectId);
    }

    @GetMapping("/task/{taskId}")
    public Milestone getMilestone(@PathVariable("projectId") Long projectId,
                                  @PathVariable("taskId") Long taskId) {
        Milestone milestone = this.milestoneService.getMilestoneByProjectIdAndTaskId(projectId, taskId);

        // TODO: Front에서 확인하고, 없어도 에러를 무시하도록 설정해야함.
        //  또는 Optional로 반환을 해주면 프론트에서 처리할 것. 물어보기
        if (Objects.isNull(milestone)) {
            throw new NotFoundMilestoneException("마일스톤은 없어도 되기 때문에 무시해도 되는 에러입니다.");
        }

        return milestone;
    }

    @PostMapping
    public Milestone createMilestone(@PathVariable("projectId") Long projectId,
                                     MilestoneRequestDto milestoneRequest) {
        if (Objects.isNull(milestoneRequest)) {
            throw new RuntimeException("마일스톤 정보가 없습니다.");
        }

        Project project = this.projectService.getProject(projectId);
        Task task = this.taskService.getTask(milestoneRequest.getTaskId());

        return this.milestoneService.saveMilestone(new Milestone(
                null, // Auto Increment
                project, task, milestoneRequest.getMilestoneName(),
                milestoneRequest.getStartPeriod(), milestoneRequest.getEndPeriod()));
    }

    @PutMapping("/{milestoneId}")
    public Milestone updateMilestone(@PathVariable("projectId") Long projectId,
                                     @PathVariable("milestoneId") Long milestoneId,
                                     MilestoneRequestDto milestoneRequest) {
        Task task = this.taskService.getTask(milestoneRequest.getTaskId());
        Project project = this.projectService.getProject(projectId);

        return this.milestoneService.updateMilestone(
                new Milestone(milestoneId, project, task, milestoneRequest.getMilestoneName(),
                    milestoneRequest.getStartPeriod(), milestoneRequest.getEndPeriod()));
    }

    @DeleteMapping("/{milestoneId}")
    public void deleteMilestone(@PathVariable("projectId") Long ignore,
                                @PathVariable("milestoneId") Long milestoneId) {
        this.milestoneService.deleteMilestone(milestoneId);
    }
}
