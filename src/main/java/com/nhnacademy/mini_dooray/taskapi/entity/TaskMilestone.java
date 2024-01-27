package com.nhnacademy.mini_dooray.taskapi.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_milestone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskMilestone {
    @EmbeddedId
    private Pk pk;

    @MapsId("milestoneId")
    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @MapsId("taskId")
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Pk implements Serializable {
        @Column(name = "task_id")
        private Long taskId;

        @Column(name = "milestone_id")
        private Long milestoneId;
    }
}
