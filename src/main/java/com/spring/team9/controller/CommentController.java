package com.spring.team9.controller;

import com.spring.team9.dto.CommentResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.User;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.CommentService;
import lombok.RequiredArgsConstructor;
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

	// test 용
	@GetMapping("/api/comments/{postId}")
	public List<CommentResponseDto> getComment(@PathVariable Long postId) {
		return commentService.getComment(postId);
	}
	//

	@PostMapping("/api/comments")
	public Comment createComment(@RequestBody HashMap<String, Object> data,
								 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		return commentService.createComment(data, user);
	}

	@PatchMapping("/api/comments/{commentId}")
	public Long updateComment(@PathVariable Long commentId, @RequestBody String commentContent,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		commentService.updateComment(commentId, commentContent, user);
		return commentId;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/api/comments/{commentId}")
	public Long deleteComment(@PathVariable Long commentId) {
		commentRepository.deleteById(commentId);
		return commentId;
	}
}