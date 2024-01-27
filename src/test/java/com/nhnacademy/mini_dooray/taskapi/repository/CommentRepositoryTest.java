package com.nhnacademy.mini_dooray.taskapi.repository;

import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTaskId(1L);
        comment = new Comment(1L, task,
                LocalDateTime.now(), "id", "content");
    }

    @Test
    void findAllByTask_TaskId() {
        entityManager.merge(task);
        entityManager.merge(comment);
        List<Comment> allByTaskTaskId = commentRepository.findAllByTask_TaskId(task.getTaskId());

        assertEquals(allByTaskTaskId.size(), 1L);
        assertEquals(allByTaskTaskId.get(0).getCommentId(), comment.getCommentId());
        assertEquals(allByTaskTaskId.get(0).getCommentContent(), comment.getCommentContent());
    }
}