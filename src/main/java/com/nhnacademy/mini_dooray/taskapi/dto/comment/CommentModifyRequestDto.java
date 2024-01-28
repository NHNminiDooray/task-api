package com.nhnacademy.mini_dooray.taskapi.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class CommentModifyRequestDto {
    private LocalDateTime commentCreatedAt;
    private String commentWriterMemberId;
    private String commentContent;
}
