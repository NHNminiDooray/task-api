package com.nhnacademy.mini_dooray.taskapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
        List<Comment> allByTaskTaskId = commentRepository.findAllByTaskTaskId(task.getTaskId());

        assertEquals(1L, allByTaskTaskId.size());
        assertEquals(comment.getCommentId(),allByTaskTaskId.get(0).getCommentId());
        assertEquals(comment.getCommentContent(), allByTaskTaskId.get(0).getCommentContent());
    }
}