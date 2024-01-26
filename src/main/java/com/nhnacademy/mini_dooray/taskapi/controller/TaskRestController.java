package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskDetailResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import java.util.List;
import java.util.Objects;
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

    @GetMapping
    public List<TaskIndexListResponseDto> getTasks(@PathVariable("projectId") Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/{taskId}")
    public TaskDetailResponseDto getTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return taskService.getTaskByProjectIdAndTaskId(projectId, taskId);
    }

    @PostMapping
    public Task createTask(@PathVariable("projectId") Long projectId,@RequestBody TaskRequestDto taskRequest) {
        if (Objects.isNull(taskRequest)) {
            throw new RuntimeException("Task 정보가 없습니다.");
        }
        return taskService.saveTask(projectId, taskRequest);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                           @RequestBody TaskRequestDto taskRequest) {
        return taskService.updateTask(projectId, taskId, taskRequest);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {

        taskService.deleteTask(projectId,taskId);
    }
}
