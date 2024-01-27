package com.nhnacademy.mini_dooray.taskapi.controller;

import com.nhnacademy.mini_dooray.taskapi.dto.manage.ManageListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.service.manage.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/manage")
public class ManageRestController {
    private final ManageService manageService;

    @GetMapping
    public ManageListResponseDto getManages(@PathVariable("projectId") Long projectId) {
        return manageService.getManageListByProjectId(projectId);
    }



}
