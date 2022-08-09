package com.spring.team9.service;

import com.spring.team9.dto.CommentResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final ContentsRepository contentsRepository;

	// test
	public List<CommentResponseDto> getComment(Long postId) {
		List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc(postId);
		List<CommentResponseDto> responseDtoList = new ArrayList<>();
		for(Comment comment : comments) {
			CommentResponseDto commentResponseDto = CommentResponseDto.builder()
							.comment(comment)
							.build();
			responseDtoList.add(commentResponseDto);
		}
		return responseDtoList;
	}
	//

	public Comment createComment(HashMap data, User user) {
		Long parentId;
		Comment parent;
		if(data.get("parentId")!=null) {
			parentId = ((Integer) data.get("parentId")).longValue();
			parent = commentRepository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException(""));
		} else{ parent = null; }
		Long postId = ((Integer) data.get("postId")).longValue();
		String commentContent = (String) data.get("commentContent");

		Contents contents = contentsRepository.findById(postId)
						.orElseThrow(() -> new IllegalArgumentException(""));

		Comment comment = Comment.builder()
								.post(contents)
								.commentContent(commentContent)
								.parent(parent)
								.user(user)
								.build();
		commentRepository.save(comment);
		return comment;
	}

	@Transactional
	public Long updateComment(Long commentId, String commentContent, User user) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글")
		);
		if(Objects.equals(user, comment.getUser())) {
			comment.update(commentContent);
		} else {
			throw new IllegalArgumentException("댓글 작성자만 수정 가능");
		}
		return comment.getCommentId();
	}
}