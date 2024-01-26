package com.nhnacademy.mini_dooray.taskapi.service.task;

import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskDetailResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task.TaskRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.task.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import com.nhnacademy.mini_dooray.taskapi.service.comment.CommentService;
import com.nhnacademy.mini_dooray.taskapi.service.milestone.MilestoneService;
import com.nhnacademy.mini_dooray.taskapi.service.taskTag.TaskTagService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskTagService taskTagService;
    private final CommentService commentService;
    private final MilestoneService milestoneService;


    @Override
    public Task saveTask(Long projectId, TaskRequestDto taskRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new NotFoundProjectException(projectId + "project를 찾을 수 없습니다"));
        return taskRepository.save(new Task(null, project, taskRequest.getTaskTitle(), taskRequest.getTaskContent(),
                taskRequest.getTaskWriteMemberId()));
    }

    @Override
    public Task updateTask(Long projectId, Long taskId, TaskRequestDto taskRequest) {
        if (checkProjectId(projectId, taskId)) {
            Task task = getTask(taskId);
            task.setTaskTitle(taskRequest.getTaskTitle());
            task.setTaskContent(taskRequest.getTaskContent());
            task.setTaskWriteMemberId(taskRequest.getTaskWriteMemberId());

            return taskRepository.save(task);
        }else{
            throw new NotFoundTaskException("Task를 찾을 수 없습니다");
        }

    }
    @Override
    public void deleteTask(Long projectId, Long taskId) {
        if (checkProjectId(projectId, taskId)) {
            taskRepository.deleteById(taskId);
        }else{
            throw new NotFoundTaskException("Task를 찾을 수 없습니다");
        }
    }
    @Override
    public List<TaskIndexListResponseDto> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findAllByProjectProjectId(projectId);
        return tasks.stream()
                .map(task -> new TaskIndexListResponseDto(task.getTaskId(), task.getTaskTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDetailResponseDto getTaskByProjectIdAndTaskId(Long projectId, Long taskId) {
        Task task = taskRepository.findByProjectIdAndTaskId(projectId, taskId);
        if (Objects.isNull(task)) {
            throw new NotFoundTaskException("Task를 찾을 수 없습니다");
        }

        return new TaskDetailResponseDto(task.getTaskId(), task.getTaskTitle(),
                task.getTaskContent(), task.getTaskWriteMemberId()
                , taskTagService.getTagResponseDtosByTaskId(taskId)
                , milestoneService.getMilestoneByTaskId(taskId)
                , commentService.getCommentsByTaskId(taskId)
        );
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(()->new NotFoundTaskException(taskId + "task를 찾을 수 없습니다"));
    }

    public boolean checkProjectId(Long projectId, Long taskId) {
        return (taskRepository.getById(taskId).getProject().getProjectId() == projectId) ? true : false;

    }
}
