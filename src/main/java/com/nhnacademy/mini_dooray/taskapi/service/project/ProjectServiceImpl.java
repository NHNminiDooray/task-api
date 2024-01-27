package com.nhnacademy.mini_dooray.taskapi.service.project;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectMember;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectStatus;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.exception.project_status.NotFoundProjectStatusException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectStatusRepository projectStatusRepository;

    @Override
    public boolean isExist(Long projectId) {
        return this.projectRepository.existsById(projectId);
    }

    @Override
    public ProjectRegisterResponseDto saveProject(ProjectRegisterRequestDto requestDto, List<ProjectMemberRequestDto> requestMembers) {
        ProjectStatus projectStatus = this.projectStatusRepository.findById(requestDto.getProjectStatusId())
                .orElseThrow(() -> new NotFoundProjectStatusException("프로젝트 상태가 존재하지 않습니다."));

        Project project = new Project();
        project.setProjectStatus(projectStatus);
        project.setProjectName(requestDto.getProjectName());
        Project savedProject = this.projectRepository.save(project);

        for (ProjectMemberRequestDto member : requestMembers) {
            this.projectMemberRepository.save(new ProjectMember(
                    new ProjectMember.Pk(savedProject.getProjectId(), member.getProjectMemberId()),
                    savedProject, member.getProjectRole()));
        }

        return new ProjectRegisterResponseDto(savedProject.getProjectId(),
                savedProject.getProjectStatus().getProjectStatusId(), savedProject.getProjectName());
    }

    @Override
    public Project getProject(Long projectId) {
        return this.projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException("프로젝트가 존재하지 않습니다."));
    }

    @Override
    public List<ProjectIndexListResponseDto> getProjectIndexListsByMemberId(String projectMemberId) {
        return this.projectRepository.findProjectsIndexListByMemberId(projectMemberId);
    }
}
