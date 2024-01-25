package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.member.NotFoundMemberException;
import com.nhnacademy.mini_dooray.taskapi.service.ProjectMemberService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectServiceImpl;
import com.nhnacademy.mini_dooray.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {
    private final ProjectMemberService projectMemberService;
    private final TaskService taskService;
    private final ProjectServiceImpl projectService;

    @GetMapping
    public List<Project> getProjects(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotFoundMemberException("로그인이 필요합니다.");
        }
        String memberId = (String) session.getAttribute("login_member_id");
        // TODO [ProjectMemberService]에서 getProjectIdByMemberId 구현되어야 함. (MemberId로 등록된 모든 ProjectId를 가져옴)
        List<Long> projectIds = projectMemberService.getProjectIdByMemberId(memberId);

        List<Project> projects = projectIds.stream()
                .map(projectId -> this.projectService.getProject(projectId))
                .collect(Collectors.toList());
        return projects;
    }

    @GetMapping("/{projectId}")
    public List<Task> getTasks(@PathVariable Long projectId) {
        // TODO: [TaskService]에서 getTasksByProjectId가 구현되어야 함. (ProjectId로 등록된 모든 Task를 가져옴)
        return this.taskService.getTasksByProjectId(projectId);
    }

    // TODO: [Front]에서 ProjectMemberDto 형식에 맞게 보내주어야 함
    @PostMapping
    public Project createProject(Project project, List<ProjectMemberRequestDto> requestMembers) {
        if(Objects.isNull(project)) {
            throw new RuntimeException("프로젝트 정보가 없습니다.");
        }

        Project savedProject = this.projectService.saveProject(project);

        for (ProjectMemberRequestDto requestMember : requestMembers) {
            // TODO: [ProjectMemberService]에서 saveMember가 구현되어야 함. (프로젝트를 등록할 때 멤버들도 같이 저장하기 때문에)
            projectMemberService.saveMember(requestMember, savedProject.getProjectId());
        }

        return savedProject;
    }
}
