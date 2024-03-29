package com.nhnacademy.mini_dooray.taskapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ManageRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TagRepository tagRepository;

    @MockBean
    MilestoneRepository milestoneRepository;
    @MockBean
    TaskTagRepository taskTagRepository;

    @Test
    @DisplayName("Manage_Response")
    void testGetManages() throws Exception {
        Long projectId = 1L;
        Project project = new Project(projectId, null, "project1");
        given(tagRepository.findAllByProjectProjectId(projectId)).willReturn(List.of(
                new Tag(1L, project, "tag1"),
                new Tag(2L, project, "tag2")
        ));
        given(milestoneRepository.findAllByProjectProjectId(projectId)).willReturn(List.of(
                new Milestone(1L, project, "milestone1", LocalDateTime.parse("2016-01-01T12:34:56"),
                        LocalDateTime.parse("2016-01-01T12:34:57")),
                new Milestone(2L, project, "milestone2", LocalDateTime.parse("2016-01-01T12:34:58"),
                        LocalDateTime.parse("2016-01-01T12:34:59"))
        ));
        mockMvc.perform(get("/projects/{projectId}/manage", projectId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagList[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$.tagList[0].tagName", equalTo("tag1")))
                .andExpect(jsonPath("$.tagList[1].tagId", equalTo(2)))
                .andExpect(jsonPath("$.tagList[1].tagName", equalTo("tag2")))
                .andExpect(jsonPath("$.milestoneList[0].milestoneId", equalTo(1)))
                .andExpect(jsonPath("$.milestoneList[0].milestoneName", equalTo("milestone1")))
                .andExpect(jsonPath("$.milestoneList[0].startPeriod", equalTo("2016-01-01T12:34:56")))
                .andExpect(jsonPath("$.milestoneList[0].endPeriod", equalTo("2016-01-01T12:34:57")))
                .andExpect(jsonPath("$.milestoneList[1].milestoneId", equalTo(2)))
                .andExpect(jsonPath("$.milestoneList[1].milestoneName", equalTo("milestone2")))
                .andExpect(jsonPath("$.milestoneList[1].startPeriod", equalTo("2016-01-01T12:34:58")))
                .andExpect(jsonPath("$.milestoneList[1].endPeriod", equalTo("2016-01-01T12:34:59")))
        ;
    }
    @Test
    @DisplayName("Task_Manage_Response")
    void testGetTaskManages() throws Exception {
        Long projectId = 1L;
        Long taskId = 1L;
        Project project = new Project(projectId, null, "project1");
        Tag tag1 = new Tag(1L, project, "tag1");
        Tag tag2 = new Tag(2L, project, "tag2");

        Task task = new Task(taskId, project, "task1", "task1Content", "account1");

        TaskTag taskTag1 = new TaskTag(new TaskTag.Pk(taskId, 1L), tag1, task);
        TaskTag taskTag2 = new TaskTag(new TaskTag.Pk(taskId, 2L), tag2, task);

        given(tagRepository.findAllByProjectProjectId(projectId)).willReturn(List.of(tag1, tag2));
        given(taskTagRepository.findAllByPkTaskId(taskId)).willReturn(List.of(taskTag1, taskTag2));

        mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}/manage", projectId, taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectTagList[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$.projectTagList[0].tagName", equalTo("tag1")))
                .andExpect(jsonPath("$.projectTagList[1].tagId", equalTo(2)))
                .andExpect(jsonPath("$.projectTagList[1].tagName", equalTo("tag2")))
                .andExpect(jsonPath("$.taskTagList[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$.taskTagList[0].tagName", equalTo("tag1")))
                .andExpect(jsonPath("$.taskTagList[1].tagId", equalTo(2)))
                .andExpect(jsonPath("$.taskTagList[1].tagName", equalTo("tag2")))
        ;
    }

}