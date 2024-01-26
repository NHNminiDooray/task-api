package com.nhnacademy.mini_dooray.taskapi.service.tag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagIndexRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import java.util.List;

public interface TagService {
    List<TagIndexRequestDto> getTagListByProjectId(Long projectId);
    List<TagRequestDto> getTagsByProjectId(Long projectId);

    TagRequestDto getTagByTagId(Long tagId);

    Tag saveTag(Long projectId, TagRequestDto tagRequest);

    Tag updateTag(Long projectId, Long tagId, TagRequestDto tagRequest);

    void deleteTag(Long projectId, Long tagId);
}
