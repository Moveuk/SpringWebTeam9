package com.spring.team9.repository;

import com.spring.team9.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c FROM Comment c WHERE c.parent IS NULL AND c.content.id = :contentId")
	List<Comment> findAllByOrderByCreatedAtDesc(Long contentId); // comment 리스트
}