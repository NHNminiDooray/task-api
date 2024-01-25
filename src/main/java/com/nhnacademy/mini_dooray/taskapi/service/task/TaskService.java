package com.nhnacademy.mini_dooray.taskapi.service.task;

import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.util.List;

public interface TaskService {
    List<Task> getTasksByProjectId(Long projectId);

    Task getTaskByProjectIdAndTaskId(Long projectId, Long taskId);

    Task getTask(Long taskId);

    void deleteTask(Long taskId);

    Task saveTask(Task task);

    Task updateTask(Task task);
}
