package com.nhnacademy.mini_dooray.taskapi.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "milestone")
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long milestoneId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;

    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "start_period")
    private LocalDateTime startPeriod;

    @Column(name = "end_period")
    private LocalDateTime endPeriod;
}
