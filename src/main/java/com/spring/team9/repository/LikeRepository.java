package com.spring.team9.repository;

import com.spring.team9.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndContentId(Long userId, Long contentId);

    Optional<Like> findByUserIdAndCommentId(Long userId, Long commentId);
}
