package com.nhnacademy.mini_dooray.taskapi.service.projectmember;

import com.nhnacademy.mini_dooray.taskapi.dto.project_member.ProjectMemberRequestDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectMember;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectMemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService{
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public List<Long> getProjectIdByMemberId(String memberId) {
        return new ArrayList<>();
    }

    @Override
    public void saveMember(ProjectMemberRequestDto requestMember, Project project) {
        projectMemberRepository.save(new ProjectMember(project, requestMember.getProjectMemberId(),
                requestMember.getProjectRole()));
    }
}
