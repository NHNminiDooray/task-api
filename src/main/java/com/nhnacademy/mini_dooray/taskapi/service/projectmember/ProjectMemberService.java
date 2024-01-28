package com.nhnacademy.mini_dooray.taskapi.service.projectmember;

import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import java.util.List;

public interface ProjectMemberService {
    List<Long> getProjectIdByMemberId(String memberId);

    void saveMember(ProjectMemberRequestDto requestMember, Project project);
}
