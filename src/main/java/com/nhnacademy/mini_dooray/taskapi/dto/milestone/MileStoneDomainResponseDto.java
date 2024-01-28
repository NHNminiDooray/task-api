package com.nhnacademy.mini_dooray.taskapi.dto.milestone;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class MileStoneDomainResponseDto {
    private Long milestoneId;
    private Long projectId;
    private String name;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
