package com.nhnacademy.mini_dooray.taskapi.dto.comment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDomainResponseDto {
    private Long commentId;
    private Long taskId;
    private LocalDateTime commentCreatedAt;
    private String commentWriterMemberId;
    private String commentContent;
}
