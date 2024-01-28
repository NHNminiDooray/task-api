package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findAllByProjectProjectId(Long projectId);

    Task findByProjectProjectIdAndTaskId(Long projectId, Long taskId);
}
