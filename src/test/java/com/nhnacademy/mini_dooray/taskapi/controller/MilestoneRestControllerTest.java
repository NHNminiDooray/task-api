package com.nhnacademy.mini_dooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MilestoneRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectStatus;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskMilestoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MilestoneRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MilestoneRepository milestoneRepository;
    @MockBean
    private TaskMilestoneRepository taskMilestoneRepository;
    @MockBean
    private ProjectRepository projectRepository;

    private Milestone milestone;
    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project(1L, new ProjectStatus(1L,
                "status name"), "project name");
        milestone = new Milestone(1L, project, "milestone name", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getMilestonesSuccessTest() throws Exception {
        given(this.milestoneRepository.findAllByProject_ProjectId(1L)).willReturn(List.of(milestone));

        mockMvc.perform(get("/projects/1/milestones"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andExpectAll(
                        jsonPath("$[0].milestoneId").value(milestone.getMilestoneId()),
                        jsonPath("$[0].projectId").value(milestone.getProject().getProjectId()),
                        jsonPath("$[0].name").value(milestone.getMilestoneName()));

    }

    @Test
    void createMilestoneNotEnoughDataException() throws Exception {
        milestone.setEndPeriod(null);
        given(this.projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        try {
            mockMvc.perform(post("/projects/1/milestones")
                            .content(objectMapper.writeValueAsString(milestone))
                            .contentType("application/json"))
                    .andExpect(status().is5xxServerError());
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(RuntimeException.class);
        }
    }

    @Test
    void createMilestoneSuccessTest() throws Exception {
        given(this.milestoneRepository.save(any(Milestone.class))).willReturn(milestone);
        given(this.projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        mockMvc.perform(post("/projects/1/milestones")
                        .content(objectMapper.writeValueAsString(milestone))
                        .contentType("application/json"))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType("application/json"))
                .andExpectAll(
                        jsonPath("$.milestoneId").value(milestone.getMilestoneId()),
                        jsonPath("$.projectId").value(milestone.getProject().getProjectId()),
                        jsonPath("$.name").value(milestone.getMilestoneName()));
    }

    @Test
    void updateMilestoneNotFoundProjectExceptionTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(false);

        try {
            mockMvc.perform(put("/projects/1/milestones/1")
                    .content(objectMapper.writeValueAsString(milestone))
                    .contentType("application/json"));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundProjectException.class);
        }
    }

    @Test
    void updateMilestoneSuccessTest() throws Exception {
        MilestoneRequestDto requestDto = new MilestoneRequestDto("new milestone name",
                LocalDateTime.now(), LocalDateTime.now());

        given(this.projectRepository.existsById(anyLong())).willReturn(true);
        given(this.milestoneRepository.findById(anyLong())).willReturn(Optional.of(milestone));
        given(this.milestoneRepository.save(any(Milestone.class))).willReturn(milestone);

        mockMvc.perform(put("/projects/1/milestones/1")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"))
                .andExpectAll(
                        jsonPath("$.milestoneId").value(milestone.getMilestoneId()),
                        jsonPath("$.projectId").value(milestone.getProject().getProjectId()),
                        jsonPath("$.name").value(requestDto.getMilestoneName()));
    }

    @Test
    void deleteMilestoneNotFoundProjectExceptionTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(false);
        try {
            mockMvc.perform(delete("/projects/1/milestones/1"));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundProjectException.class);
        }
    }

    @Test
    void deleteMilestoneNotFoundMilestoneExceptionTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(true);
        given(this.milestoneRepository.existsById(anyLong())).willReturn(false);
        try {
            mockMvc.perform(delete("/projects/1/milestones/1"));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundMilestoneException.class);
        }
    }

    @Test
    void deleteMilestoneSuccessTest() {
        given(this.projectRepository.existsById(anyLong())).willReturn(true);
        given(this.milestoneRepository.existsById(anyLong())).willReturn(true);

        given(this.taskMilestoneRepository.findAllTaskMilestonesByMilestone_MilestoneId(anyLong())).willReturn(List.of());
        given(this.milestoneRepository.findAllByProject_ProjectId(anyLong())).willReturn(List.of(milestone));

        assertDoesNotThrow(() -> mockMvc.perform(delete("/projects/1/milestones/1")));
    }
}