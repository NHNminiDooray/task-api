package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.service.tag.TagService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectId}/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagRequestDto> getTags(@PathVariable("projectId") Long projectId) {
        return tagService.getTagsByProjectId(projectId);
    }

    @PostMapping
    public TagResponseDto createTag(@PathVariable("projectId") Long projectId, @RequestBody TagRequestDto tagRequest) {
        if (Objects.isNull(tagRequest)) {
            throw new RuntimeException("Task 정보가 없습니다.");
        }
        return tagService.saveTag(projectId, tagRequest);
    }


    @PutMapping("/{tagId}")
    public TagResponseDto updateTag(@PathVariable("projectId") Long projectId, @PathVariable("tagId") Long tagId,
                         @RequestBody TagRequestDto tagRequest) {

        return tagService.updateTag(projectId, tagId, tagRequest);

    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable("projectId") Long projectId, @PathVariable("tagId") Long tagId) {
        tagService.deleteTag(projectId, tagId);
    }
}
