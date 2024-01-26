package com.nhnacademy.mini_dooray.taskapi.dto.project;

import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectRegisterRequestDto {
    private Long projectStatusId;
    private String projectName;
    private List<ProjectMemberRequestDto> requestMembers;
}
