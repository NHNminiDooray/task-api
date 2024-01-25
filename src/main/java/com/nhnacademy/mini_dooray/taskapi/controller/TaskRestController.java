package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectServiceImpl;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskRestController {
    private final TaskService taskService;
    private final ProjectServiceImpl projectService;


    @GetMapping
    public List<Task> getTasks(@PathVariable("projectId") Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        Task task = taskService.getTaskByProjectIdAndTaskId(projectId, taskId);

        if (Objects.isNull(task)) {
            throw new NotFoundTaskException("Task을 찾을 수 없습니다");
        }
        return task;
    }

    @PostMapping
    public Task createTask(@PathVariable("projectId") Long projectId, TaskRequestDto taskRequest) {
        if (Objects.isNull(taskRequest)) {
            throw new RuntimeException("Task 정보가 없습니다.");
        }
        Project project = projectService.getProject(projectId);

        Task newTask = new Task(null, project, taskRequest.getTaskTitle(), taskRequest.getTaskContent(),
                taskRequest.getTaskWriteMemberId());
        return taskService.saveTask(newTask);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                           TaskRequestDto taskRequest) {
        Task task = taskService.getTask(taskId);
        task.setTaskTitle(taskRequest.getTaskTitle());
        task.setTaskContent(taskRequest.getTaskContent());
        task.setTaskWriteMemberId(taskRequest.getTaskWriteMemberId());
        return taskService.updateTask(task);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
