package com.nhnacademy.mini_dooray.taskapi.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String commentWriteMemberId;
    private String commentContent;
}
