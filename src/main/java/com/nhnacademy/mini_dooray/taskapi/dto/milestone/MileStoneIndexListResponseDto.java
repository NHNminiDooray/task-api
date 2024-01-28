package com.nhnacademy.mini_dooray.taskapi.dto.milestone;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class MileStoneIndexListResponseDto {
    private Long milestoneId;
    private String milestoneName;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
