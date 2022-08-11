package com.spring.team9.controller;

import com.spring.team9.dto.CommentResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.User;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentRepository commentRepository;
	private final CommentService commentService;

	@GetMapping("/api/comments/{contentId}")
	public List<CommentResponseDto> getComment(@PathVariable Long contentId) {
		List<CommentResponseDto> returns = commentService.getComment(contentId);
		return returns;
	}

	@PostMapping("/api/comments")
	public Comment createComment(@RequestBody HashMap<String, Object> data,
								 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		return commentService.createComment(data, user);
	}

	@PatchMapping("/api/comments/{commentId}")
	public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody String commentContent,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		try {
			commentService.updateComment(commentId, commentContent, user);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("댓글 수정이 완료되었습니다", HttpStatus.OK);
	}

	@DeleteMapping("/api/comments/{commentId}")
	public Long deleteComment(@PathVariable Long commentId,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		commentService.deleteComment(commentId, user);
		return commentId;
	}
}