package com.nhnacademy.mini_dooray.taskapi.service.comment;

import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Comment;

import java.util.List;

public interface CommentService {
    boolean isExist(Long commentId);
    List<Comment> getCommentsByTaskId(Long taskId);
    Comment saveComment(Long taskId, CommentRegisterRequestDto requestDto);
    Comment updateComment(Long taskId, Long commentId, CommentModifyRequestDto requestDto);
    void deleteComment(Long commentId);
}
