package com.nhnacademy.mini_dooray.taskapi.service.taskTag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagServiceImpl implements TaskTagService{
    private final TaskTagRepository taskTagRepository;

    @Override
    public List<TagResponseDto> getTagResponseDtosByTaskId(Long taskId) {
        List<TaskTag> taskTags = taskTagRepository.findAllByPk_TaskId(taskId);
        return taskTags.stream()
                .map(taskTag -> new TagResponseDto(taskTag.getTag().getTagName()))
                .collect(Collectors.toList());
    }
}
