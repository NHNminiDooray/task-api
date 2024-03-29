package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.TaskTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {

    List<TaskTag> findAllByPkTaskId(Long taskId);

    boolean existsByPkTagId(Long tagId);
}
