package com.nhnacademy.mini_dooray.taskapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_write_member_id")
    private String taskWriteMemberId;
}
