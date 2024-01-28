package com.nhnacademy.mini_dooray.taskapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.NotFoundCommentException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.repository.CommentRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private ProjectRepository projectRepository;

    private Task task;
    private Comment comment;

    @BeforeEach
    void setUp() {
        task = new Task(1L, null, "task title", "task content", "writer id");
        comment = new Comment(1L, task, LocalDateTime.now(), "writer id", "comment content");
    }

    @Test
    void createCommentNotFoundProjectExceptionErrorTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(false);

        try {
            mockMvc.perform(post("/projects/1/tasks/1/comments")
                    .content(objectMapper.writeValueAsString(comment))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundProjectException.class);
        }
    }

    @Test
    void createCommentSuccessTest() throws Exception {
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(projectRepository.existsById(anyLong())).willReturn(true);

        MvcResult result = this.mockMvc.perform(post("/projects/1/tasks/1/comments")
                        .content(objectMapper.writeValueAsString(comment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.commentId").value(comment.getCommentId()),
                        jsonPath("$.taskId").value(comment.getTask().getTaskId()),
                        jsonPath("$.commentWriterMemberId").value(comment.getCommentWriterMemberId()),
                        jsonPath("$.commentContent").value(comment.getCommentContent()))
                .andReturn();
        assertNotNull(result.getResponse());

    }

    @Test
    void updateCommentNotFoundProjectExceptionTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(false);

        try {
            mockMvc.perform(put("/projects/1/tasks/1/comments/1")
                    .content(objectMapper.writeValueAsString(comment))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundProjectException.class);
        }
    }

    @Test
    void updateCommentSuccessTest() throws Exception{
        given(this.projectRepository.existsById(anyLong())).willReturn(true);
        given(this.taskRepository.existsById(anyLong())).willReturn(true);
        given(this.commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        given(this.commentRepository.save(any(Comment.class))).willReturn(comment);

        MvcResult result = mockMvc.perform(put("/projects/1/tasks/1/comments/1")
                        .content(objectMapper.writeValueAsString(new CommentModifyRequestDto(LocalDateTime.now(),
                                comment.getCommentWriterMemberId(), comment.getCommentContent())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.commentId").value(comment.getCommentId()),
                        jsonPath("$.taskId").value(comment.getTask().getTaskId()),
                        jsonPath("$.commentWriterMemberId").value(comment.getCommentWriterMemberId()),
                        jsonPath("$.commentContent").value(comment.getCommentContent())
                ).andReturn();
        assertNotNull(result.getResponse());
    }

    @Test
    void deleteCommentNotFoundProjectErrorTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(false);

        try {
            mockMvc.perform(delete("/projects/1/tasks/1/comments/1")
                    .content(objectMapper.writeValueAsString(comment))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundProjectException.class);
        }
    }

    @Test
    void deleteCommentNotFoundCommentErrorTest() throws Exception {
        given(this.commentRepository.existsById(anyLong())).willReturn(false);

        try {
            mockMvc.perform(post("/projects/1/tasks/1/comments/1")
                    .content(objectMapper.writeValueAsString(comment))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            assertThat(e.getCause()).isInstanceOf(NotFoundCommentException.class);
        }
    }

    @Test
    void deleteCommentSuccessTest() throws Exception {
        given(this.projectRepository.existsById(anyLong())).willReturn(true);
        given(this.commentRepository.existsById(anyLong())).willReturn(true);

        mockMvc.perform(delete("/projects/1/tasks/1/comments/1"))
                .andExpect(status().isOk());
    }
}