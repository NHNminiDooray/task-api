package com.nhnacademy.mini_dooray.taskapi.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentModifyRequestDto {
    private LocalDateTime commentCreatedAt;
    private String commentWriterMemberId;
    private String commentContent;
}
