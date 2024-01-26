package com.nhnacademy.mini_dooray.taskapi.service.taskTag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import java.util.List;

public interface TaskTagService {

    List<TagResponseDto> getTagResponseDtosByTaskId(Long taskId);
}
