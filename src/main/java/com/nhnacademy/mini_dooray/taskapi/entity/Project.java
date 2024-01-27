package com.nhnacademy.mini_dooray.taskapi.entity;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @Column(name = "project_name")
    private String projectName;
}
