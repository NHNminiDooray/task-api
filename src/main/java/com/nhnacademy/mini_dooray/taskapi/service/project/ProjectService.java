package com.nhnacademy.mini_dooray.taskapi.service.project;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectMember;

import java.util.List;

public interface ProjectService {
    public Project saveProject(ProjectRegisterRequestDto project, List<ProjectMemberRequestDto> requestMembers);
    public Project getProject(Long projectId);
    public List<ProjectIndexListResponseDto> getProjectIndexListsByMemberId(String projectMemberId);
}
