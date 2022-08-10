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

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final ContentsRepository contentsRepository;
	private final LikeRepository likeRepository;

	// test
	public List<CommentResponseDto> getComment(Long contentId) {
		List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc(contentId);
		responseDtoList.clear();
		return getLike(comments);
	}
	// test

	List<CommentResponseDto> responseDtoList = new ArrayList<>();
	List<CommentResponseDto> temp = new ArrayList<>();
	public List<CommentResponseDto> getLike(List<Comment> comments) {
		for(Comment comment : comments) {
			if(comment.getReplies().size() != 0) {
				getLike(comment.getReplies()); // 재귀함수로 replies 가 없는 comment 까지 들어가기
			}
			int countLike = likeRepository.countByCommentCommentId(comment.getCommentId()); // 좋아요 개수 가져오기
			CommentResponseDto commentResponseDto = CommentResponseDto.builder() // dto 로 바꾸기
					.comment(comment)
					.countLike(countLike)
					.build();
			if(comment.getReplies().size() != 0) { // 최하위 comment 만 수행
				commentResponseDto.getReplies().addAll(temp); // temp List 를 dto 의 replies 에 새로 넣어주기
			}
			temp.clear();
			temp.add(commentResponseDto); // temp List 비우고 dto 넣기
			if(comment.getParent() == null) {
				responseDtoList.addAll(temp); // 최상위 comment 일 경우에만 return 해줄 List 에 추가
			}
		}
		return responseDtoList;
	}

	public Comment createComment(HashMap data, User user) {
		Long parentId;
		Comment parent;
		if(data.get("parentId")!=null) {
			parentId = ((Integer) data.get("parentId")).longValue();
			parent = commentRepository.findById(parentId)
					.orElseThrow(() -> new IllegalArgumentException(""));
		} else{ parent = null; }
		Long contentId = ((Integer) data.get("contentId")).longValue();
		String commentContent = (String) data.get("commentContent");

		Contents contents = contentsRepository.findById(contentId)
						.orElseThrow(() -> new IllegalArgumentException(""));

		Comment comment = Comment.builder()
								.content(contents)
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