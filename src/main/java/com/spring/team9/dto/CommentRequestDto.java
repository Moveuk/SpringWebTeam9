package com.spring.team9.dto;

import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
	private User user;
	private Contents post;
	private String commentContent;
	private Comment parent;

	public Comment toEntity() {
		return Comment.builder()
				.user(user)
					.post(post)
					.commentContent(commentContent)
					.parent(parent)
					.build();
	}
}