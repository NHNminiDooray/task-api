package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterResponseDto;
import com.nhnacademy.mini_dooray.taskapi.exception.member.NotFoundMemberException;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {
    private final ProjectService projectService;

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectIndexListResponseDto> getProjectIndexLists(@RequestBody ProjectIndexListRequestDto requestDto) {
        if (Objects.isNull(requestDto.getMemberName())) {
            throw new NotFoundMemberException("프로젝트 리스트를 찾기 위한 멤버 아이디가 입력되지 않았습니다.");
        }
        List<ProjectIndexListResponseDto> responseDtos = this.projectService.getProjectIndexListsByMemberId(requestDto.getMemberName());
        return responseDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectRegisterResponseDto createProject(@RequestBody ProjectRegisterRequestDto requestDto) {
        if (Objects.isNull(requestDto.getProjectName()) || Objects.isNull(requestDto.getProjectStatusId()) || Objects.isNull(requestDto.getRequestMembers())) {
            throw new RuntimeException("프로젝트 정보가 없습니다.");
        }

        return this.projectService.saveProject(requestDto, requestDto.getRequestMembers());
    }
}