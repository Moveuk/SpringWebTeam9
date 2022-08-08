package com.spring.team9.dto;

import com.spring.team9.model.Comment;
import com.spring.team9.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentResponseDto {
	private User user;
	private String commentContent;
	private List<Comment> replies;

	public List<CommentResponseDto> ResponseDtoList (List<Comment> comment) {
		return comment.stream()
						.map(CommentResponseDto::new)
						.collect(Collectors.toList());
	}

	public CommentResponseDto (Comment comment) {
		this.user = comment.getUser();
		this.commentContent = comment.getCommentContent();
		this.replies = comment.getReplies();
	}
}