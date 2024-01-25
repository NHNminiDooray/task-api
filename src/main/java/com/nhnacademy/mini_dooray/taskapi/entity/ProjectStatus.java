package com.nhnacademy.mini_dooray.taskapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "project_status")
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_status_id")
    private Long projectStatusId;

    @Column(name = "project_status_name")
    private String projectStatusName;
}
