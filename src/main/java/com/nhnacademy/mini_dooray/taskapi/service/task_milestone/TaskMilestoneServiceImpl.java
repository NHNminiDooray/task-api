package com.nhnacademy.mini_dooray.taskapi.service.task_milestone;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMilestoneServiceImpl implements TaskMilestoneService {
    private final TaskMilestoneRepository taskMilestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;

    public TaskMilestoneDomainDto getTaskMilestone(Long projectId, Long taskId, Long milestoneId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException("존재하지 않는 프로젝트입니다.");
        }
        TaskMilestone.Pk pk = taskMilestoneRepository.findById(new TaskMilestone.Pk(taskId, milestoneId))
                .orElseThrow(() -> new NotFoundTaskMilestoneException("존재하지 않는 태스크 마일스톤입니다.")).getPk();

        return new TaskMilestoneDomainDto(pk.getTaskId(), pk.getMilestoneId());
    }

    public TaskMilestoneDomainDto createTaskMilestone(Long projectId, Long taskId, Long milestoneId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException("존재하지 않는 프로젝트입니다.");
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
            throw new NotFoundProjectException("존재하지 않는 프로젝트입니다.");
        }

        TaskMilestone.Pk pk = taskMilestoneRepository.findById(new TaskMilestone.Pk(taskId, milestoneId))
                .orElseThrow(() -> new NotFoundTaskMilestoneException("존재하지 않는 태스크 마일스톤입니다.")).getPk();
        taskMilestoneRepository.deleteById(pk);
    }
}
