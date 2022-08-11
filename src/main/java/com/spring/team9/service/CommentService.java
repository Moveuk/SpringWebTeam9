package com.spring.team9.service;

import com.spring.team9.dto.CommentResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final ContentsRepository contentsRepository;
	private final LikeRepository likeRepository;

	public List<CommentResponseDto> getComment(Long contentId) {
		List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc(contentId);
		if(comments == null) {
			throw new IllegalArgumentException("댓글 없음");
		}
		return toDto(comments);
	}

	public List<CommentResponseDto> toDto(List<Comment> comments) {
		List<CommentResponseDto> commentDto = comments.stream() // 들어온 댓글 리스트를 dto 로 변환
				.map(c -> new CommentResponseDto(c, likeRepository.countByCommentCommentId(c.getCommentId())))
				.collect(Collectors.toList());
		for(int i=0; i<comments.size(); i++) { // 댓글 리스트의 각 댓글별로
			if (comments.get(i).getReplies().size() != 0) { // 대댓글이 존재한다면
				List<CommentResponseDto> cRD = toDto(comments.get(i).getReplies()); // 재귀함수로 대댓글 리스트를 보냄 -> dto 리스트로 바뀌어서 돌아옴
				commentDto.get(i).getReplies().addAll(cRD); // 받아온 대댓글 dto 리스트를 그 자리에 추가
			}
		}
		return commentDto;
	}

	public void createComment(HashMap data, User user) {
		Long parentId;
		Comment parent;
		if(data.get("parentId")!=null) {
			parentId = ((Integer) data.get("parentId")).longValue();
			parent = commentRepository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다"));
		} else{ parent = null; }
		Long contentId = ((Integer) data.get("contentId")).longValue();
		String commentContent = (String) data.get("commentContent");

		Contents contents = contentsRepository.findById(contentId)
						.orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));

		Comment comment = Comment.builder()
								.content(contents)
								.commentContent(commentContent)
								.parent(parent)
								.user(user)
								.build();
		commentRepository.save(comment);
	}

	@Transactional
	public void updateComment(Long commentId, String commentContent, User user) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글"));
		Long writer = comment.getUser().getId();
		if(user.getId().equals(writer)) {
			comment.update(commentContent);
		} else {
			throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다");
		}
	}

	public void deleteComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글"));
		Long writer = comment.getUser().getId();
		if(user.getId().equals(writer)) {
			commentRepository.deleteById(commentId);
		} else {
			throw new IllegalArgumentException("댓글 작성자만 삭제 가능합니다");
		}
	}
}