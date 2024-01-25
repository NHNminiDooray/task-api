package com.nhnacademy.mini_dooray.taskapi.service.project;

import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProject(Long projectId) {
        return this.projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException("프로젝트가 존재하지 않습니다."));
    }
}
