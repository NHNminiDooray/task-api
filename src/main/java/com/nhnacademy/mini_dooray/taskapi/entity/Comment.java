package com.nhnacademy.mini_dooray.taskapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "comment_created_at")
    private LocalDateTime commentCreatedAt;

    @Column(name = "comment_writer_member_id")
    private String commentWriterMemberId;

    @Column(name = "comment_content")
    private String commentContent;
}
