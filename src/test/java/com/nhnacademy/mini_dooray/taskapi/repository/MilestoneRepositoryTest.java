package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MilestoneRepositoryTest {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Milestone milestone;
    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setProjectName("project name");
        this.milestone = new Milestone(
                null, project, "name", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void findAllByProject_ProjectId() {
        this.testEntityManager.persist(project);
        this.testEntityManager.persist(milestone);
        testEntityManager.flush();

        List<Milestone> allByProjectProjectId = this.milestoneRepository
                .findAllByProjectProjectId(project.getProjectId());

        assertEquals(1L, allByProjectProjectId.size());
        assertEquals(milestone.getMilestoneId(), allByProjectProjectId.get(0).getMilestoneId());
        assertEquals(milestone.getMilestoneName(), allByProjectProjectId.get(0).getMilestoneName());
    }
}