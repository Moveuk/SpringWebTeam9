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

	public Comment createComment(CommentRequestDto requestDto) {
		Comment comment = requestDto.toEntity();
		commentRepository.save(requestDto.toEntity());
		return comment;
	}
}