package com.spring.team9.controller;

import com.spring.team9.dto.CommentResponseDto;
import com.spring.team9.model.User;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/api/comments/{contentId}")
	public List<CommentResponseDto> getComment(@PathVariable Long contentId) {
		List<CommentResponseDto> returns = commentService.getComment(contentId);
		return returns;
	}

	@PostMapping("/api/comments")
	public ResponseEntity<String> createComment(@RequestBody HashMap<String, Object> data,
								 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		try {
			commentService.createComment(data, user);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("댓글이 등록되었습니다", HttpStatus.OK);
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
		return new ResponseEntity<>("댓글이 수정되었습니다", HttpStatus.OK);
	}

	@DeleteMapping("/api/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		try {
			commentService.deleteComment(commentId, user);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("댓글이 정상적으로 삭제되었습니다", HttpStatus.OK);
	}
}