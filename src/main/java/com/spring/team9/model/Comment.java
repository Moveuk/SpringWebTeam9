package com.spring.team9.model;

import com.spring.team9.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id")
	private Contents post;

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_parent_id")
	private Comment parent;

	public Comment(CommentRequestDto requestDto) {
		this.user = requestDto.getUser();
		this.post = requestDto.getPost();
		this.commentContent = requestDto.getCommentContent();
		this.parent = requestDto.getParent().getCommentId();
	}
}