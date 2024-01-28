package com.nhnacademy.mini_dooray.taskapi.service.tag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagIndexRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import java.util.List;

public interface TagService {
    List<TagIndexRequestDto> getTagListByProjectId(Long projectId);

    List<TagIndexRequestDto> getTagListByProjectIdAndTaskId(Long projectId, Long taskId);

    List<TagRequestDto> getTagsByProjectId(Long projectId);

    TagResponseDto saveTag(Long projectId, TagRequestDto tagRequest);

    TagResponseDto updateTag(Long projectId, Long tagId, TagRequestDto tagRequest);

    void deleteTag(Long projectId, Long tagId);
}
