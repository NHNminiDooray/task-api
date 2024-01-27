package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListInterfaceResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import com.nhnacademy.mini_dooray.taskapi.entity.ProjectMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    private ProjectMember projectMember;

    @BeforeEach
    void setUp() {
        project = new Project(1L, null, "projectName");
        projectMember = new ProjectMember(new ProjectMember.Pk(project.getProjectId(), "memberId"), project, "projectRole");
    }

    @Test
    void findProjectsIndexListByMemberId() {
        testEntityManager.merge(project);
        testEntityManager.merge(projectMember);

        List<ProjectIndexListInterfaceResponseDto> projectsIndexListByMemberId =
                this.projectRepository.findProjectsIndexListByMemberId(projectMember.getPk().getProjectMemberId());

        System.out.println(projectsIndexListByMemberId.get(0).getProjectId());
        System.out.println(projectsIndexListByMemberId.get(0).getProjectName());

        assertEquals(1, projectsIndexListByMemberId.size());
        assertEquals(project.getProjectId(), projectsIndexListByMemberId.get(0).getProjectId());
        assertEquals(project.getProjectName(), projectsIndexListByMemberId.get(0).getProjectName());
    }
}