package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.dto.project.ProjectIndexListInterfaceResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p.projectId as projectId, p.projectName as projectName from Project p join ProjectMember pm ON p.projectId = pm.project.projectId where pm.pk.projectMemberId = ?1")
    List<ProjectIndexListInterfaceResponseDto> findProjectsIndexListByMemberId(String projectMemberId);
}
