package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.TaskMilestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, TaskMilestone.Pk> {
    List<TaskMilestone> findAllTaskMilestonesByMilestone_MilestoneId(Long milestoneId);
}
