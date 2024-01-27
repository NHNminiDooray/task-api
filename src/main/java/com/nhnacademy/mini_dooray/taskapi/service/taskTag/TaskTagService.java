package com.nhnacademy.mini_dooray.taskapi.service.taskTag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.taskTag.TaskTagResponseDto;
import java.util.List;

public interface TaskTagService {

    List<TagResponseDto> getTagResponseDtoByTaskId(Long taskId);

    TaskTagResponseDto saveTaskTag(Long projectId, Long taskId, Long tagId);

    void deleteTagAtTask(Long projectId, Long taskId, Long tagId);
}
