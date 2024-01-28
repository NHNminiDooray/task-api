package com.nhnacademy.mini_dooray.taskapi.service.task_milestone;

import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.task_milestone.TaskMilestoneDomainDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Milestone;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.entity.TaskMilestone;
import com.nhnacademy.mini_dooray.taskapi.exception.NotFoundTaskMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.milestone.NotFoundMilestoneException;
import com.nhnacademy.mini_dooray.taskapi.exception.project.NotFoundProjectException;
import com.nhnacademy.mini_dooray.taskapi.exception.task.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskMilestoneRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMilestoneServiceImpl implements TaskMilestoneService {
    private final TaskMilestoneRepository taskMilestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;

    public MileStoneResponseDto getMilestoneResponseDtoByTaskId( Long taskId) {
        List<TaskMilestone> taskMilestone = taskMilestoneRepository.findAllByPkTaskId(taskId);
        return new MileStoneResponseDto(taskMilestone.get(0).getMilestone().getStartPeriod(),
                taskMilestone.get(0).getMilestone().getEndPeriod());
    }

    public TaskMilestoneDomainDto createTaskMilestone(Long projectId, Long taskId, Long milestoneId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException();
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException("존재하지 않는 태스크입니다."));
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundMilestoneException("존재하지 않는 마일스톤입니다."));
        taskMilestoneRepository.save(new TaskMilestone(new TaskMilestone.Pk(taskId, milestoneId), milestone, task));

        return new TaskMilestoneDomainDto(taskId, milestoneId);
    }

    public void deleteTaskMilestone(Long projectId, Long taskId, Long milestoneId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException();
        }

        TaskMilestone.Pk pk = taskMilestoneRepository.findById(new TaskMilestone.Pk(taskId, milestoneId))
                .orElseThrow(() -> new NotFoundTaskMilestoneException("존재하지 않는 태스크 마일스톤입니다.")).getPk();
        taskMilestoneRepository.deleteById(pk);
    }
}
