package com.nhnacademy.mini_dooray.taskapi.dto.project_member;

import lombok.*;


@AllArgsConstructor
@Getter

@ToString
public class ProjectMemberRequestDto {
    private String projectMemberId;
    private String projectRole;
}
