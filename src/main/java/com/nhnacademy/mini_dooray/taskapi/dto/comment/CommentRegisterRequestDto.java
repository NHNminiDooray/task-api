package com.nhnacademy.mini_dooray.taskapi.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentRegisterRequestDto {
    private LocalDateTime commentCreatedAt;
    private String commentWriterMemberId;
    private String commentContent;
}
