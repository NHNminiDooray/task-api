package com.nhnacademy.mini_dooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListInterfaceResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectMember;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectStatus;
import com.nhnacademy.mini_dooray.taskapi.exception.member.NotFoundMemberException;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectStatusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProjectRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ProjectStatusRepository projectStatusRepository;
    @MockBean
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectIndexListInterfaceResponseDto projectIndexListInterfaceResponseDto;

    @Test
    void getProjectIndexListsNotFoundMemberExceptionErrorTest() throws Exception {
        ProjectIndexListRequestDto requestDto = new ProjectIndexListRequestDto();
        requestDto.setMemberName(null);

        try {
            mockMvc.perform(post("/projects/list")
                            .content(objectMapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundMemberException.class);
        }
    }

    @Test
    void getProjectIndexListsSuccessTest() throws Exception {
        given(projectIndexListInterfaceResponseDto.getProjectId()).willReturn(1L);
        given(projectIndexListInterfaceResponseDto.getProjectName()).willReturn("project name");
        given(this.projectRepository.findProjectsIndexListByMemberId(anyString())).willReturn(List.of(projectIndexListInterfaceResponseDto));

        mockMvc.perform(post("/projects/list")
                        .content(objectMapper.writeValueAsString(new ProjectIndexListRequestDto("member name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$[0].projectId").value(1L),
                        jsonPath("$[0].projectName").value("project name")
                );
    }

    @Test
    void createProjectRuntimeExceptionErrorTest() throws Exception {
        ProjectRegisterRequestDto requestDto = new ProjectRegisterRequestDto();
        requestDto.setProjectName(null);

        try {
            mockMvc.perform(post("/projects")
                            .content(objectMapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is5xxServerError());
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(RuntimeException.class);
        }
    }

    @Test
    void createProject() throws Exception {

        ProjectRegisterRequestDto requestDto = new ProjectRegisterRequestDto(
                1L, "project name", List.of(
                        new ProjectMemberRequestDto("member id", "project role")));
        ProjectStatus projectStatus = new ProjectStatus(requestDto.getProjectStatusId(), "project status name");
        Project project = new Project(1L, projectStatus, "project name");

        given(this.projectStatusRepository.findById(anyLong())).willReturn(Optional.of(projectStatus));
        given(this.projectRepository.save(any(Project.class))).willReturn(project);
        given(this.projectMemberRepository.save(any(ProjectMember.class))).willReturn(new ProjectMember());

        mockMvc.perform(post("/projects")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.projectId").value(project.getProjectId()),
                        jsonPath("$.projectStatusId").value(requestDto.getProjectStatusId()),
                        jsonPath("$.projectName").value(requestDto.getProjectName())
                );
    }
}