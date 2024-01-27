package com.nhnacademy.mini_dooray.taskapi.dto.milestone;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MileStoneIndexListResponseDto {
    private Long milestoneId;
    private String milestoneName;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
