package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.service.comment.CommentService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
public class CommentRestController {
    private final ProjectService projectService;
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDomainResponseDto createComment(@PathVariable("projectId") Long projectId,
                                                  @PathVariable("taskId") Long taskId,
                                                  @RequestBody CommentRegisterRequestDto requestDto) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException();
        }

        return this.commentService.saveComment(taskId, requestDto);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDomainResponseDto updateComment(@PathVariable("projectId") Long projectId,
                                 @PathVariable("taskId") Long taskId,
                                 @PathVariable("commentId") Long commentId,
                                 @RequestBody CommentModifyRequestDto requestDto) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException();
        }

        return this.commentService.updateComment(taskId, commentId, requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("projectId") Long projectId,
                              @PathVariable("taskId") Long taskId,
                              @PathVariable("commentId") Long commentId) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException();
        }

        this.commentService.deleteComment(commentId);
    }

}
