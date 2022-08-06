package com.spring.team9.repository;

import com.spring.team9.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByOrderByModifiedAtDesc();
}