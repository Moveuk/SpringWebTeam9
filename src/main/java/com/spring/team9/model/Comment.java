package com.spring.team9.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Component
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user; // 단방향

	@ManyToOne(fetch = FetchType.LAZY)
	private Contents post; // 단방향 // content 에서 OneToMany, 양방향으로 하더라도 content DB 에는 정보가 들어가지 않습니다.

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	private List<Comment> replies = new ArrayList<>(); // 양방향 // parent 정보를 갖고있지 않습니다.

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Comment parent; // 양방향 // DB 에는 Key 값이 저장되고, JAVA 에서는 객체로 표현됩니다.

	@Builder
	public Comment (User user, Contents post, String commentContent, Comment parent) {
		this.user = user;
		this.post = post;
		this.commentContent = commentContent;
		this.parent = parent;
	}

	public void update(String commentContent) {
		this.commentContent = commentContent;
	}
}