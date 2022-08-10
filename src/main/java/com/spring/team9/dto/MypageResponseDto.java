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
    private List<Like> likeContentList;
    private List<Like> likeCommentList;

    @Builder
    public MypageResponseDto(List<Contents> contentList,List<Comment> commentList, List<Comment> replyList, List<Like> likeContentList, List<Like> likeCommentList) {
        this.contentList = contentList;
        this.commentList = commentList;
        this.replyList = replyList;
        this.likeContentList = likeContentList;
        this.likeCommentList = likeCommentList;
    }
}
