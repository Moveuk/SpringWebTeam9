package com.spring.team9.controller;

import com.spring.team9.dto.CommentRequestDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentRepository commentRepository;
	private final CommentService commentService;


	@PostMapping("/api/comments/{commentId}")
	public Comment createComment(@RequestBody CommentRequestDto requestDto,
								 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.createComment(requestDto);
	}

	@GetMapping("/api/comments/{commentId}")
	public Comment getDetailComment(@PathVariable Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

//	@PatchMapping("/api/comments/{commentId}")
//	public Long updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
//		commentService.update(commentId, requestDto);
//		return commentId;
//	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/api/comments/{commentId}")
	public Long deleteComment(@PathVariable Long commentId) {
		commentRepository.deleteById(commentId);
		return commentId;
	}
}