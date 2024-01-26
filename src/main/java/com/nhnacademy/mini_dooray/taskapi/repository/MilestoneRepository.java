package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
//    @Query("select m from Milestone m where m.project.projectId = ?1")
//    List<Milestone> findAllByProjectId(Long projectId);
//  밑에 코드들이 가능한 코드인지 정확히 모르겠어서 위의 코드들 일단 남겨두겠습니다! 확인할 방법이 없어서요 ,, ㅠ ㅠ,,,,
    List<Milestone> findAllByProject_ProjectId(Long projectId);

    Milestone findByProject_ProjectIdAndTask_TaskId(Long projectId, Long taskId);

    List<Milestone> findAllByTaskTaskId(Long taskId);

}
