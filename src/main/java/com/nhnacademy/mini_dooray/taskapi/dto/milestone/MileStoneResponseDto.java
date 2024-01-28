package com.nhnacademy.mini_dooray.taskapi.dto.milestone;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MileStoneResponseDto {
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
}
