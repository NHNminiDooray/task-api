package com.nhnacademy.mini_dooray.taskapi.service.manage;

import com.nhnacademy.mini_dooray.taskapi.dto.manage.ManageListResponseDto;

public interface ManageService {
    ManageListResponseDto getManageListByProjectId(Long projectId);
}
