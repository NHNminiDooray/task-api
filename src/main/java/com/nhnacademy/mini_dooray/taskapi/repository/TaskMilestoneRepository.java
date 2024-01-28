package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.TaskMilestone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, TaskMilestone.Pk> {

    List<TaskMilestone> findAllByPk_TaskId(Long taskId);

    List<TaskMilestone> findAllTaskMilestonesByMilestone_MilestoneId(Long milestoneId);
}
