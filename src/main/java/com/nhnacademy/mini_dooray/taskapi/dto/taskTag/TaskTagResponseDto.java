package com.nhnacademy.mini_dooray.taskapi.dto.taskTag;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTagResponseDto {
    private Long taskId;
    private Long tagId;
}
