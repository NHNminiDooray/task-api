package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findAllByProjectProjectId(Long projectId);


    // TODO: 에러 떠서 수정하겠습니다. - 김영웅
//    Task findByProjectIdAndTaskId(Long projectId, Long taskId);
    Task findByProject_ProjectIdAndTaskId(Long projectId, Long taskId);
}
