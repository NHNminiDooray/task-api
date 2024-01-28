package com.nhnacademy.mini_dooray.taskapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectRegisterResponseDto {
    private Long projectId;
    private Long projectStatusId;
    private String projectName;
}
