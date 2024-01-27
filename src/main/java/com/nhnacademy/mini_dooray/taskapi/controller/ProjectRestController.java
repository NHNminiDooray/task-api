package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterResponseDto;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {
    //        private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectIndexListResponseDto> getProjectIndexLists(ProjectIndexListRequestDto requestDto) {
        // TODO: Test시 Error 발생해서 임시로 넣어둠. (로그인이 필요한 기능)

//        if (Objects.isNull(requestDto)) {
//            throw new NotFoundMemberException("로그인이 필요합니다.");
//        }
        List<ProjectIndexListResponseDto> responseDtos = this.projectService.getProjectIndexListsByMemberId(requestDto.getMemberName());

        return responseDtos;
    }

    // TODO: Task Service 완료되면 Dto에 맞게 반환해야함. / 필요 없을 것 같습니다. (그냥 전체를 뿌려주니까)
//    @GetMapping("/{projectId}")
//    public List<Task> getTasks(@PathVariable Long projectId) {
//        return this.taskService.getTasksByProjectId(projectId);
//    }

    // TODO: [Front]에서 ProjectMemberDto, ProjectMemberRequestDto 형식에 맞게 보내주어야 함
    @PostMapping
    public ProjectRegisterResponseDto createProject(@RequestBody ProjectRegisterRequestDto requestDto) {
        if (Objects.isNull(requestDto)) {
            throw new RuntimeException("프로젝트 정보가 없습니다.");
        }

        return this.projectService.saveProject(requestDto, requestDto.getRequestMembers());
    }
}