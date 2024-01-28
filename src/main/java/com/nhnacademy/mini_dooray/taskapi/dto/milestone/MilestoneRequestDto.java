package com.nhnacademy.mini_dooray.taskapi.dto.milestone;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter

@ToString
public class MilestoneRequestDto {
    private String milestoneName;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
