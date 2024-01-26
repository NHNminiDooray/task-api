package com.nhnacademy.mini_dooray.taskapi.service.tag;

import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagIndexRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.exception.tag.NotFoundTagException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final TaskTagRepository taskTagRepository;
    @Override
    public List<TagIndexRequestDto> getTagListByProjectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProject_ProjectId(projectId);
        return tags.stream()
                .map(tag -> new TagIndexRequestDto(tag.getTagId(), tag.getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagRequestDto> getTagsByProjectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProject_ProjectId(projectId);
        return tags.stream()
                .map(tag -> new TagRequestDto(tag.getTagName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagRequestDto> getTagsByProejctIdAndTaskId(Long projectId, Long taskId){
        List<Tag> tags = tagRepository.findAllByProject_ProjectId(projectId);
        List<TaskTag> taskTags = taskTagRepository.findAllByPk_TaskId(taskId);

        Set<Long> taskTagIds = taskTags.stream()
                .map(taskTag -> taskTag.getTag().getTagId())
                .collect(Collectors.toSet());

        return tags.stream()
                .filter(tag -> taskTagIds.contains(tag.getTagId()))
                .map(tag -> new TagRequestDto(tag.getTagName()))
                .collect(Collectors.toList());
    }


    @Override
    public Tag saveTag(Long projectId, @RequestBody TagRequestDto tagRequest) {
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
        if (taskTagRepository.existsByPk_TagId(tagId)) {
            throw new RuntimeException("태그를 사용하는 태스크가 있습니다");
        }
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

