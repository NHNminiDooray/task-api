package com.nhnacademy.mini_dooray.taskapi.service.tag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.exception.tag.NotFoundTagException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<TagRequestDto> getTagsByprojectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProjectId(projectId);
        return tags.stream()
                .map(tag -> new TagRequestDto(tag.getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public TagRequestDto getTagByTagId(Long tagId) {
        return new TagRequestDto(
                tagRepository.findById(tagId).orElseThrow(() -> new NotFoundTagException("태그를 찾을 수 없습니다"))
                        .getTagName());
    }

    @Override
    public Tag saveTag(Long projectId, TagRequestDto tagRequest) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId + "project를 찾을 수 없습니다"));

        return tagRepository.save(new Tag(null, project, tagRequest.getTagName()));
    }

    @Override
    public Tag updateTag(Long projectId, Long tagId, TagRequestDto tagRequest) {
        if (checkProjectId(projectId, tagId)) {
            Tag tag = tagRepository.getById(tagId);
            tag.setTagName(tagRequest.getTagName());
            return tagRepository.save(tag);
        } else {
            throw new NotFoundTagException("태그를 찾을 수 없습니다");
        }
    }

    @Override
    public void deleteTag(Long projectId, Long tagId) {
        if (checkProjectId(projectId, tagId)) {
            tagRepository.deleteById(tagId);
        } else {
            throw new NotFoundTagException("태그를 찾을 수 없습니다");
        }
    }

    public boolean checkProjectId(Long projectId, Long tagId) {
        return (tagRepository.getById(tagId).getProject().getProjectId() == projectId) ? true : false;
    }
}

