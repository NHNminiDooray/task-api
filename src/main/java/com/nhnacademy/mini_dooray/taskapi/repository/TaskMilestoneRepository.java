package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.TaskMilestone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, TaskMilestone.Pk> {

    List<TaskMilestone> findAllByPkTaskId(Long taskId);

    List<TaskMilestone> findAllTaskMilestonesByMilestoneMilestoneId(Long milestoneId);
}
