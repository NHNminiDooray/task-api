package com.nhnacademy.mini_dooray.taskapi.dto.project;

import com.nhnacademy.mini_dooray.taskapi.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectRegisterResponseDto {
    private Long projectId;
    private Long projectStatusId;
    private String projectName;
}
