package com.nhnacademy.mini_dooray.taskapi.service.tag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagIndexRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.exception.tag.NotFoundTagException;
import com.nhnacademy.mini_dooray.taskapi.exception.tasktag.NotFoundTaskTagException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final TaskTagRepository taskTagRepository;

    @Override
    public List<TagIndexRequestDto> getTagListByProjectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProjectProjectId(projectId);
        return tags.stream()
                .map(tag -> new TagIndexRequestDto(tag.getTagId(), tag.getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagIndexRequestDto> getTagListByProjectIdAndTaskId(Long projectId, Long taskId) {
        List<TaskTag> taskTags = taskTagRepository.findAllByPkTaskId(taskId);

        return taskTags.stream()
                .filter(taskTag -> taskTag.getTag().getProject().getProjectId().equals(projectId))
                .map(taskTag -> new TagIndexRequestDto(taskTag.getTag().getTagId(), taskTag.getTag().getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagRequestDto> getTagsByProjectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProjectProjectId(projectId);
        return tags.stream()
                .map(tag -> new TagRequestDto(tag.getTagName()))
                .collect(Collectors.toList());
    }


    @Override
    public TagResponseDto saveTag(Long projectId, TagRequestDto tagRequest) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId + "project를 찾을 수 없습니다"));

        Tag tag = tagRepository.save(new Tag(null, project, tagRequest.getTagName()));
        return new TagResponseDto(tag.getTagName());

    }


    @Override
    public TagResponseDto updateTag(Long projectId, Long tagId, TagRequestDto tagRequest) {
        if (checkProjectId(projectId, tagId)) {
            Optional<Tag> tagOptional = tagRepository.findById(tagId);
            if (tagOptional.isPresent()) {
                Tag tag = tagOptional.get();
                tag.setTagName(tagRequest.getTagName());
                tagRepository.save(tag);
                return new TagResponseDto(tagRequest.getTagName());
            } else {
                throw new NotFoundTagException();
            }
        } else {
            throw new NotFoundTagException();
        }
    }


    @Override
    public void deleteTag(Long projectId, Long tagId) {
        if (taskTagRepository.existsByPkTagId(tagId)) {
            throw new NotFoundTaskTagException("태그를 사용하는 태스크가 있습니다");
        }
        if (checkProjectId(projectId, tagId)) {
            tagRepository.deleteById(tagId);
        } else {
            throw new NotFoundTagException();
        }
    }

    public boolean checkProjectId(Long projectId, Long tagId) {
        Optional<Tag> tagOptional = tagRepository.findById(tagId);
        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            return tag.getProject().getProjectId().equals(projectId);
        }
        return false;
    }

}

