package com.nhnacademy.mini_dooray.taskapi.dto.project_member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectMemberRequestDto {
    private String projectMemberId;
    private String projectRole;
}
