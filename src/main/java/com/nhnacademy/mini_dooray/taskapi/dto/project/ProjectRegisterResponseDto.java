package com.nhnacademy.mini_dooray.taskapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectRegisterResponseDto {
    private Long projectId;
    private Long projectStatusId;
    private String projectName;
}
