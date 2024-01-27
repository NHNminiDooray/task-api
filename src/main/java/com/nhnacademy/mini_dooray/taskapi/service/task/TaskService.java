package com.nhnacademy.mini_dooray.taskapi.service.task;

import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskDetailResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.util.List;

public interface TaskService {
    List<TaskIndexListResponseDto> getTasksByProjectId(Long projectId);

    List<Task> getAllTasksByProjectId(Long projectId);

    TaskDetailResponseDto getTaskByProjectIdAndTaskId(Long projectId, Long taskId);

    Task getTask(Long taskId);



    TaskResponseDto saveTask(Long projectId, TaskRequestDto taskRequest);

    TaskResponseDto updateTask(Long projectId, Long taskId, TaskRequestDto taskRequest);

    void deleteTask(Long projectId, Long taskId);

}
