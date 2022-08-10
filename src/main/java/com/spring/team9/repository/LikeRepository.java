package com.spring.team9.repository;

import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndContents(Long userId, Contents contents);
    Optional<Like> findByUserIdAndComment(Long userId, Comment comment);

    int countByContentsId(Long ContentId);
    int countByCommentCommentId(Long commentId);

}