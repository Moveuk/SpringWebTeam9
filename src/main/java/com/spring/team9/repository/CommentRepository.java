package com.spring.team9.repository;

import com.spring.team9.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c FROM Comment c WHERE c.parent IS NULL AND c.post.id = :postId")
	List<Comment> findAllByOrderByCreatedAtDesc(Long postId); // comment 리스트
	@Query("")
	List<Comment> findAllByOrderByCreatedAtAsc(); // replies 리스트
}