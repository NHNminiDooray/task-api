package com.nhnacademy.mini_dooray.taskapi.dto.task;


import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import java.util.List;
import lombok.Getter;

@Getter
public class TaskDetailResponseDto {
    private Long taskId;
    private String taskTitle;
    private String taskContent;
    private String taskWriteMemberId;

    private List<TagResponseDto> tagList;
    private MileStoneResponseDto mileStone;
    private List<CommentResponseDto> commentList;

    public TaskDetailResponseDto(Long taskId, String taskTitle, String taskContent, String taskWriteMemberId,
                                 List<TagResponseDto> tagList, MileStoneResponseDto mileStoneRequest,
                                 List<CommentResponseDto> commentList) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskWriteMemberId = taskWriteMemberId;
        this.tagList = tagList;
        this.mileStone = mileStoneRequest;
        this.commentList = commentList;
    }
}
