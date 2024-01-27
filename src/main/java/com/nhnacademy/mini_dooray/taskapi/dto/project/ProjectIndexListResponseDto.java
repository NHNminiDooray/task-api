package com.nhnacademy.mini_dooray.taskapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectIndexListResponseDto {
    private Long projectId;
    private String projectName;
}
