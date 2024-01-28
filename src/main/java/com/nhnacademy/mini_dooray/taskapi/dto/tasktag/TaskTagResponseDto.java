package com.nhnacademy.mini_dooray.taskapi.dto.tasktag;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskTagResponseDto {
    private Long taskId;
    private Long tagId;
}
