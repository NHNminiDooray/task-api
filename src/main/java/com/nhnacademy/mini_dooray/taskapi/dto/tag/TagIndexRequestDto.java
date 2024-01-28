package com.nhnacademy.mini_dooray.taskapi.dto.tag;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class TagIndexRequestDto {
    private Long tagId;
    private String tagName;
}
