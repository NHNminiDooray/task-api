package com.nhnacademy.mini_dooray.taskapi.service.project;

import com.nhnacademy.mini_dooray.taskapi.entity.Project;

public interface ProjectService {
    public Project saveProject(Project project);
    public Project getProject(Long projectId);
}
