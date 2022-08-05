package com.spring.team9.service;

import com.spring.team9.dto.LikeRequestDto;
import com.spring.team9.jwt.JwtTokenProvider;
import com.spring.team9.model.Like;
import com.spring.team9.model.User;
import com.spring.team9.repository.LikeRepository;
import com.spring.team9.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public void likeContent(LikeRequestDto requestDto) {

        Like like = Like.builder()
                .userId(requestDto.getUserId())
                .contentId(requestDto.getContentId())
                .build();
        likeRepository.save(like);
    }

    public void likeComment(LikeRequestDto requestDto) {
        Like like = Like.builder()
                .userId(requestDto.getUserId())
                .commentId(requestDto.getCommentId())
                .build();
        likeRepository.save(like);
    }

    public void unlikeContent(LikeRequestDto requestDto) {

        Optional<Like> likeopt = findByUserIdAndContentId(requestDto);

        likeRepository.delete(likeopt.get());

    }

    public void unlikeComment(LikeRequestDto requestDto) {

        Optional<Like> likeopt = findByUserIdAndCommentId(requestDto);

        likeRepository.delete(likeopt.get());

    }


    private Optional<Like> findByUserIdAndContentId(LikeRequestDto requestDto) {
        return likeRepository
                .findByUserIdAndContentId(requestDto.getUserId(), requestDto.getContentId());
    }

    private Optional<Like> findByUserIdAndCommentId(LikeRequestDto requestDto) {
        return likeRepository
                .findByUserIdAndCommentId(requestDto.getUserId(), requestDto.getCommentId());
    }

}
