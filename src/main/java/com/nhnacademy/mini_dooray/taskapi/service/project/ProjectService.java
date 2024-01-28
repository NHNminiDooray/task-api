package com.nhnacademy.mini_dooray.taskapi.service.project;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;

import java.util.List;

public interface ProjectService {
    boolean isExist(Long projectId);
    ProjectRegisterResponseDto saveProject(ProjectRegisterRequestDto project, List<ProjectMemberRequestDto> requestMembers);
    Project getProject(Long projectId);
    List<ProjectIndexListResponseDto> getProjectIndexListsByMemberId(String projectMemberId);
}
