package com.nhnacademy.mini_dooray.taskapi.dto.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MilestoneRequestDto {
    private Long taskId;
    private String milestoneName;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
