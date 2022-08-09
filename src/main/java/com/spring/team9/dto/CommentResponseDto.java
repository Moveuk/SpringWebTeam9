package com.spring.team9.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.team9.model.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class CommentResponseDto {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime modifiedAt;
	private String username;
	private String commentContent;
	private List<Comment> replies;

	@Builder
	public CommentResponseDto(Comment comment) {
		this.modifiedAt = comment.getModifiedAt();
		this.username = comment.getUser().getUsername();
		this.commentContent = comment.getCommentContent();
		this.replies = comment.getReplies();
	}
}