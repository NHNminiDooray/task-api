package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save(Project project);
}
