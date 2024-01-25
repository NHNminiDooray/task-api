package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.project.projectId = ?1")
    List<Task> findAllByProjectId(Long projectId);

    Task findByProjectIdAndTaskId(Long projectId, Long taskId);
}
