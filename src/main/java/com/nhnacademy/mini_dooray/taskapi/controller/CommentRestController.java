package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.exception.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.service.comment.CommentService;
import com.nhnacademy.mini_dooray.taskapi.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
public class CommentRestController {
    private final ProjectService projectService;
    private final CommentService commentService;

    @GetMapping
    public List<Comment> getComments(@PathVariable("projectId") Long projectId,
                                     @PathVariable("taskId") Long taskId) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException("프로젝트가 존재하지 않습니다.");
        }

        return this.commentService.getCommentsByTaskId(taskId);
    }

    @PostMapping
    public Comment createComment(@PathVariable("projectId") Long projectId,
                                 @PathVariable("taskId") Long taskId,
                                 @RequestBody CommentRegisterRequestDto requestDto) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException("프로젝트가 존재하지 않습니다.");
        }

        return this.commentService.saveComment(taskId, requestDto);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable("projectId") Long projectId,
                                 @PathVariable("taskId") Long taskId,
                                 @PathVariable("commentId") Long commentId,
                                 @RequestBody CommentModifyRequestDto requestDto) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException("프로젝트가 존재하지 않습니다.");
        }

        return this.commentService.updateComment(taskId, commentId, requestDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("projectId") Long projectId,
                              @PathVariable("taskId") Long taskId,
                              @PathVariable("commentId") Long commentId) {
        if (!this.projectService.isExist(projectId)) {
            throw new NotFoundProjectException("프로젝트가 존재하지 않습니다.");
        }

        if (!this.commentService.isExist(taskId)) {
            throw new NotFoundTaskException("게시글이 존재하지 않습니다.");
        }

        this.commentService.deleteComment(commentId);
    }

}
