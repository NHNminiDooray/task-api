package com.nhnacademy.mini_dooray.taskapi.service.comment;

import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.task.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.repository.CommentRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Override
    public boolean isExist(Long commentId) {
        return this.commentRepository.existsById(commentId);
    }


    @Override
    public Comment saveComment(Long taskId, CommentRegisterRequestDto requestDto) {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException("게시글이 존재하지 않습니다."));

        return this.commentRepository.save(new Comment(
                null, task, requestDto.getCommentCreatedAt(),
                requestDto.getCommentWriterMemberId(), requestDto.getCommentContent()));
    }

    @Override
    public Comment updateComment(Long taskId, Long commentId, CommentModifyRequestDto requestDto) {
        if (!this.commentRepository.existsById(commentId)) {
            throw new NotFoundTaskException("댓글이 존재하지 않습니다.");
        }

        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException("게시글이 존재하지 않습니다."));

        return this.commentRepository.save(new Comment(
                commentId, task, requestDto.getCommentCreatedAt(),
                requestDto.getCommentWriterMemberId(), requestDto.getCommentContent()));
    }

    @Override
    public void deleteComment(Long commentId) {
        if (!this.commentRepository.existsById(commentId)) {
            throw new NotFoundTaskException("댓글이 존재하지 않습니다.");
        }

        this.commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponseDto> getCommentsByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findAllByTaskTaskId(taskId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getCommentId(),
                        comment.getCommentWriterMemberId(),
                        comment.getCommentContent()))
                .collect(Collectors.toList());
    }
}
