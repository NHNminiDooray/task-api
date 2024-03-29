package com.nhnacademy.mini_dooray.taskapi.service.tasktag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tasktag.TaskTagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import com.nhnacademy.mini_dooray.taskapi.exception.tasktag.NotFoundTaskTagException;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagServiceImpl implements TaskTagService{
    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;


    @Override
    public List<TagResponseDto> getTagResponseDtoByTaskId(Long taskId) {
        List<TaskTag> taskTags = taskTagRepository.findAllByPkTaskId(taskId);
        return taskTags.stream()
                .map(taskTag -> new TagResponseDto(taskTag.getTag().getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public TaskTagResponseDto saveTaskTag(Long projectId, Long taskId, Long tagId) {
        Task task = taskRepository.findByProjectProjectIdAndTaskId(projectId, taskId);
        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new NotFoundTaskTagException("Task가 Project에 속하지 않습니다.");
        }

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag를 찾을 수 없습니다."));
        if (!task.getProject().getProjectId().equals(tag.getProject().getProjectId())) {
            throw new NotFoundTaskTagException("Task와 Tag가 동일한 프로젝트에 속하지 않습니다.");
        }
        TaskTag taskTag = taskTagRepository.save(new TaskTag(new TaskTag.Pk(taskId, tagId), tag, task));


        return new TaskTagResponseDto(taskTag.getTask().getTaskId(), taskTag.getTag().getTagId());
    }

    @Override
    public void deleteTagAtTask(Long projectId, Long taskId, Long tagId) {
        Task task = taskRepository.findByProjectProjectIdAndTaskId(projectId, taskId);
        if (!task.getProject().getProjectId().equals(projectId)) {
            throw new NotFoundTaskTagException("Task가 Project에 속하지 않습니다.");
        }

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag를 찾을 수 없습니다."));
        if (!task.getProject().getProjectId().equals(tag.getProject().getProjectId())) {
            throw new NotFoundTaskTagException("Task와 Tag가 동일한 프로젝트에 속하지 않습니다.");
        }

        TaskTag.Pk pk = new TaskTag.Pk(taskId, tagId);
        TaskTag taskTag = taskTagRepository.findById(pk)
                .orElseThrow(() -> new NotFoundTaskTagException("TaskTag를 찾을 수 없습니다."));

        taskTagRepository.delete(taskTag);
    }
}
