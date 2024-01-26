package com.nhnacademy.mini_dooray.taskapi.service.manage;

import com.nhnacademy.mini_dooray.taskapi.dto.manage.ManageListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.service.milestone.MilestoneService;
import com.nhnacademy.mini_dooray.taskapi.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {
    private final TagService tagService;
    private final MilestoneService milestoneService;

    @Override
    public ManageListResponseDto getManageListByProjectId(Long projectId) {
        return new ManageListResponseDto(tagService.getTagListByProjectId(projectId), milestoneService.getMileStonesListByProjectId(projectId));
    }
}
