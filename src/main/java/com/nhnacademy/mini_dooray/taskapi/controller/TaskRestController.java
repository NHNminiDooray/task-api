package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskDetailResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.taskTag.TaskTagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task_milestone.TaskMilestoneDomainDto;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import com.nhnacademy.mini_dooray.taskapi.service.taskTag.TaskTagService;
import java.util.List;
import java.util.Objects;

import com.nhnacademy.mini_dooray.taskapi.service.task_milestone.TaskMilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskRestController {
    private final TaskService taskService;
    private final TaskTagService taskTagService;
    private final TaskMilestoneService taskMilestoneService;

    @GetMapping
    public List<TaskIndexListResponseDto> getTasks(@PathVariable("projectId") Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/{taskId}")
    public TaskDetailResponseDto getTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return taskService.getTaskByProjectIdAndTaskId(projectId, taskId);
    }

    @PostMapping
    public TaskResponseDto createTask(@PathVariable("projectId") Long projectId, @RequestBody TaskRequestDto taskRequest) {
        if (Objects.isNull(taskRequest)) {
            throw new RuntimeException("Task 정보가 없습니다.");
        }
        return taskService.saveTask(projectId, taskRequest);
    }

    @PostMapping("/{taskId}/tags/{tagId}")
    public TaskTagResponseDto saveTagAtTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                            @PathVariable("tagId") Long tagId) {
        return taskTagService.saveTaskTag(projectId, taskId, tagId);
    }

    @PostMapping("/{taskId}/milestones/{milestoneId}")
    public TaskMilestoneDomainDto saveMilestoneAtTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                                      @PathVariable("milestoneId") Long milestoneId) {
        return this.taskMilestoneService.createTaskMilestone(projectId, taskId, milestoneId);
    }

    @PutMapping("/{taskId}")
    public TaskResponseDto updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                      @RequestBody TaskRequestDto taskRequest) {
        return taskService.updateTask(projectId, taskId, taskRequest);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        taskService.deleteTask(projectId,taskId);
    }

    @DeleteMapping("/{taskId}/tags/{tagId}")
    public void deleteTagAtTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                @PathVariable("tagId") Long tagId) {
        taskTagService.deleteTagAtTask(projectId, taskId, tagId);
    }

    @DeleteMapping("/{taskId}/milestones/{milestoneId}")
    public void deleteMilestoneAtTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                @PathVariable("milestoneId") Long milestoneId) {
        this.taskMilestoneService.deleteTaskMilestone(projectId, taskId, milestoneId);
    }
}
