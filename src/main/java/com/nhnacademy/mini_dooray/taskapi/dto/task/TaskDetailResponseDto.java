package com.nhnacademy.mini_dooray.taskapi.dto.task;


import com.nhnacademy.mini_dooray.taskapi.dto.comment.CommentResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.milestone.MileStoneResponseDto;
import com.nhnacademy.mini_dooray.taskapi.dto.tag.TagResponseDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDetailResponseDto {
    private Long taskId;
    private String taskTitle;
    private String taskContent;
    private String taskWriteMemberId;

    private List<TagResponseDto> tagList;
    private List<MileStoneResponseDto> mileStoneList;
    private List<CommentResponseDto> commentList;

    public TaskDetailResponseDto(Long taskId, String taskTitle, String taskContent, String taskWriteMemberId,
                                 List<TagResponseDto> tagList, List<MileStoneResponseDto> mileStoneList,
                                 List<CommentResponseDto> commentList) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskWriteMemberId = taskWriteMemberId;
        this.tagList = tagList;
        this.mileStoneList = mileStoneList;
        this.commentList = commentList;
    }
}
