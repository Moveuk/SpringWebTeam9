package com.spring.team9.dto;

import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {

    private List<Contents> contentList;
    private List<Comment> commentList;
    private List<Comment> replyList;
    private List<Contents> likeContentList;
    private List<Comment> likeCommentList;

    @Builder
    public MypageResponseDto(List<Contents> contentList,List<Comment> commentList, List<Comment> replyList, List<Contents> likeContentList, List<Comment> likeCommentList) {
        this.contentList = contentList;
        this.commentList = commentList;
        this.replyList = replyList;
        this.likeContentList = likeContentList;
        this.likeCommentList = likeCommentList;
    }
}
