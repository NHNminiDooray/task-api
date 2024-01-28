package com.nhnacademy.mini_dooray.taskapi.controller;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.Tag;
import com.nhnacademy.mini_dooray.taskapi.repository.TagRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TagRepository tagRepository;
    @MockBean
    TaskTagRepository taskTagRepository;



    @Test
    @Order(1)
    @DisplayName("전체 Tag조회")
    void testGetTags() throws Exception {
        Project project = new Project(1L, null, "projectname");
        given(tagRepository.findAllByProjectProjectId(1L)).willReturn(
                List.of(new Tag(1L, project, "tag1"), new Tag(2L, project, "tag2")));

        mockMvc.perform(get("/projects/{projectId}/tags", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tagName", equalTo("tag1")))
                .andExpect(jsonPath("$[1].tagName", equalTo("tag2")));
    }

    @Test
    @Order(2)
    @DisplayName("Project에 Tag생성")
    void testCreateTag() throws Exception {
        TagRequestDto tagRequestDto = new TagRequestDto("tag3");

        given(tagRepository.save(any(Tag.class))).willAnswer(invocation -> {
            Tag createdTag = invocation.getArgument(0);
            createdTag.setTagId(3L);
            return createdTag;
        });

        mockMvc.perform(post("/projects/{projectId}/tags", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagName", is("tag3")));
    }

    @Test
    @Order(3)
    @DisplayName("Tag 이름 수정")
    void testUpdateTag() throws Exception {
        Tag lastTag = new Tag(3L, new Project(1L, null, "projectname"), "tag3");

        given(tagRepository.findById(3L)).willReturn(Optional.of(lastTag));
        TagRequestDto tagRequestDto = new TagRequestDto("tag3Up");

        given(tagRepository.save(any(Tag.class))).willAnswer(invocation -> {
            Tag updatedTag = invocation.getArgument(0);
            updatedTag.setTagName(tagRequestDto.getTagName());
            return updatedTag;
        });

        mockMvc.perform(put("/projects/{projectId}/tags/{tagId}", 1L, 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tagName", equalTo("tag3Up")));
    }

    @Test
    @Order(4)
    @DisplayName("Project에서 Tag 삭제")
    void testDeleteTag() throws Exception {
        given(taskTagRepository.existsByPkTagId(3L)).willReturn(false);

        given(tagRepository.findById(3L)).willReturn(Optional.of(new Tag(3L, new Project(1L, null, "projectname"), "tag3")));

        mockMvc.perform(delete("/projects/{projectId}/tags/{tagId}", 1L, 3L))
                .andExpect(status().isOk());
    }

}