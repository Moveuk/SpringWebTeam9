package com.spring.team9.model;

import com.spring.team9.dto.CommentRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user; // 단방향

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Contents post; // 어떻게 할지... 단방향...((양방향으로 하고싶다

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_parent_id")
	private Comment parent; // 양방향...구현어케해미친.. mappedby 써야하는가?

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	private List<Comment> children = new ArrayList<>();

	@Builder
	public Comment (User user, Contents post, String commentContent, Comment parent) {
		this.user = user;
		this.post = post;
		this.commentContent = commentContent;
		this.parent = parent;
	}
}