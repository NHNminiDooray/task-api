package com.nhnacademy.mini_dooray.taskapi.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
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
