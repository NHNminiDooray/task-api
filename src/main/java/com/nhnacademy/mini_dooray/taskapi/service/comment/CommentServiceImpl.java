package com.nhnacademy.mini_dooray.taskapi.service.comment;

import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentDomainResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentModifyRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentRegisterRequestDto;
import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentResponseDto;
import com.nhnacademy.mini_dooray.taskapi.entity.Comment;
import com.nhnacademy.mini_dooray.taskapi.entity.Task;
import com.nhnacademy.mini_dooray.taskapi.exception.NotFoundCommentException;
import com.nhnacademy.mini_dooray.taskapi.exception.task.NotFoundTaskException;
import com.nhnacademy.mini_dooray.taskapi.repository.CommentRepository;
import com.nhnacademy.mini_dooray.taskapi.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Override
    public boolean isExist(Long commentId) {
        return this.commentRepository.existsById(commentId);
    }


    @Override
    public CommentDomainResponseDto saveComment(Long taskId, CommentRegisterRequestDto requestDto) {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException("게시글이 존재하지 않습니다."));

        Comment savedComment = this.commentRepository.save(new Comment(
                null, task, requestDto.getCommentCreatedAt(),
                requestDto.getCommentWriterMemberId(), requestDto.getCommentContent()));
        return new CommentDomainResponseDto(savedComment.getCommentId(), savedComment.getTask().getTaskId(),
                savedComment.getCommentCreatedAt(), savedComment.getCommentWriterMemberId(), savedComment.getCommentContent());
    }

    @Override
    public CommentDomainResponseDto updateComment(Long taskId, Long commentId, CommentModifyRequestDto requestDto) {
        if (!this.taskRepository.existsById(taskId)) {
            throw new NotFoundTaskException("게시글이 존재하지 않습니다.");
        }

        Comment savedComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException("댓글이 존재하지 않습니다."));

        savedComment.setCommentCreatedAt(requestDto.getCommentCreatedAt());
        savedComment.setCommentWriterMemberId(requestDto.getCommentWriterMemberId());
        savedComment.setCommentContent(requestDto.getCommentContent());
        Comment updatedComment = this.commentRepository.save(savedComment);

        return new CommentDomainResponseDto(updatedComment.getCommentId(), updatedComment.getTask().getTaskId(),
                updatedComment.getCommentCreatedAt(), updatedComment.getCommentWriterMemberId(),
                updatedComment.getCommentContent());
    }

    @Override
    public void deleteComment(Long commentId) {
        if (!this.commentRepository.existsById(commentId)) {
            throw new NotFoundCommentException("댓글이 존재하지 않습니다.");
        }

        this.commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponseDto> getCommentsByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findAllByTask_TaskId(taskId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getCommentId(),
                        comment.getCommentWriterMemberId(),
                        comment.getCommentContent()))
                .collect(Collectors.toList());
    }
}
