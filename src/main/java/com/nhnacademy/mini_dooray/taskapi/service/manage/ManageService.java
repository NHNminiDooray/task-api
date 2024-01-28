package com.nhnacademy.mini_dooray.taskapi.service.manage;

import com.nhnacademy.mini_dooray.taskapi.dto.manage.ManageListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.manage.TaskManageListResponseDto;

public interface ManageService {
    ManageListResponseDto getManageListByProjectId(Long projectId);

    TaskManageListResponseDto getTaskManageListByProjectIdAndTaskId(Long projectId, Long taskId);
}
