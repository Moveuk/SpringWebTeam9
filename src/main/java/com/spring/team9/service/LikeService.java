package com.spring.team9.service;

import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ContentsRepository contentsRepository;
    private final CommentRepository commentRepository;

    // 게시글 좋아요 여부 확인
    private boolean isNotAlreadyLikeContent(Long userId, Contents contents) {
        return likeRepository.findByUserIdAndContents(userId, contents).isEmpty();
    }

    // 게시글 좋아요 등록
    public boolean likeContent(Long userId, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        if (isNotAlreadyLikeContent(userId, contents)) {
            likeRepository.save(new Like(userId, contents));
            return true;
        }
        return false;
    }

    // 게시글 좋아요 취소
    public void disLikeContent(Long userId, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Like like = likeRepository.findByUserIdAndContents(userId, contents).orElseThrow();
        likeRepository.delete(like);
    }

    // 댓글 좋아요 여부 확인
    private boolean isNotAlreadyLikeComment(Long userId, Comment comment) {
        return likeRepository.findByUserIdAndComment(userId, comment).isEmpty();
    }

    // 댓글 좋아요 등록
    public boolean likeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (isNotAlreadyLikeComment(userId, comment)) {
            likeRepository.save(new Like(userId, comment));
            return true;
        }
        return false;
    }

    // 댓글 좋아요 취소
    public void disLikeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Like like = likeRepository.findByUserIdAndComment(userId, comment).orElseThrow();
        likeRepository.delete(like);
    }


}