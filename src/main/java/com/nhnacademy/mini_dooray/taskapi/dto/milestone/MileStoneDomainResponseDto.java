package com.nhnacademy.mini_dooray.taskapi.dto.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MileStoneDomainResponseDto {
    private Long milestoneId;
    private Long projectId;
    private Long taskId;
    private String name;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
