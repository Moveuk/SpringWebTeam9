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

    private boolean isNotAlreadyLikeContent(Long userId, Contents contents) {
        return likeRepository.findByUserIdAndContents(userId, contents).isEmpty();
    }

    public boolean likeContent(Long userId, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        if (isNotAlreadyLikeContent(userId, contents)) {
            likeRepository.save(new Like(userId, contents));
            return true;
        }
        return false;
    }

    public void disLikeContent(Long userId, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Like like = likeRepository.findByUserIdAndContents(userId, contents).orElseThrow();
        likeRepository.delete(like);
    }

    public List<String> likeCountContent(Long userId, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Integer countByContents = likeRepository.countByContents(contents).orElse(0);

        List<String> result = new ArrayList<>(Arrays.asList(String.valueOf(countByContents)));

        if (Objects.nonNull(userId)) {
            result.add(String.valueOf(isNotAlreadyLikeContent(userId, contents)));
            return result;
        }
        return result;

    }

    private boolean isNotAlreadyLikeComment(Long userId, Comment comment) {
        return likeRepository.findByUserIdAndComment(userId, comment).isEmpty();
    }

    public boolean likeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (isNotAlreadyLikeComment(userId, comment)) {
            likeRepository.save(new Like(userId, comment));
            return true;
        }
        return false;
    }

    public void disLikeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Like like = likeRepository.findByUserIdAndComment(userId, comment).orElseThrow();
        likeRepository.delete(like);
    }


}