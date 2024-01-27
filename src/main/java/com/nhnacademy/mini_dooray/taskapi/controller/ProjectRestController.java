package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterResponseDto;
import com.nhnacademy.mini_dooray.taskapi.exception.member.NotFoundMemberException;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import com.nhnacademy.mini_dooray.taskapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {
    private final ProjectService projectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectIndexListResponseDto> getProjectIndexLists(ProjectIndexListRequestDto requestDto) {
        // TODO: Test시 Error 발생해서 임시로 넣어둠. (로그인이 필요한 기능)

        if (Objects.isNull(requestDto)) {
            throw new NotFoundMemberException("로그인이 필요합니다.");
        }
        List<ProjectIndexListResponseDto> responseDtos = this.projectService.getProjectIndexListsByMemberId(requestDto.getMemberName());

        return responseDtos;
    }

    // TODO: [Front]에서 ProjectMemberDto, ProjectMemberRequestDto 형식에 맞게 보내주어야 함
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectRegisterResponseDto createProject(@RequestBody ProjectRegisterRequestDto requestDto) {
        if (Objects.isNull(requestDto)) {
            throw new RuntimeException("프로젝트 정보가 없습니다.");
        }

        return this.projectService.saveProject(requestDto, requestDto.getRequestMembers());
    }
}