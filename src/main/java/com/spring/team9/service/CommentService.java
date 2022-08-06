package com.spring.team9.service;

import com.spring.team9.dto.CommentRequestDto;
import com.spring.team9.model.Comment;
import com.spring.team9.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public Comment createComment(CommentRequestDto requestDto, Long userKey) {
		Comment comment = new Comment(requestDto, userKey);
		commentRepository.save(comment);
		return comment;
	}

	@Transactional
	public Long update(Long commentKey, CommentRequestDto requestDto) {
		Comment comment = commentRepository.findById(commentKey).orElseThrow(
				() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
		);
		comment.update(requestDto);
		return comment.getCommentKey();
	}
}