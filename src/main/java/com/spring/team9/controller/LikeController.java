package com.spring.team9.controller;


import com.spring.team9.dto.LikeRequestDto;
import com.spring.team9.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/likes/contents/{contentId}")
    public ResponseEntity<LikeRequestDto> likeContent(@RequestBody LikeRequestDto requestDto) {
        likeService.likeContent(requestDto);
        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    @PostMapping("/api/likes/comments/{commentId}")
    public ResponseEntity<LikeRequestDto> likeComment(@RequestBody LikeRequestDto requestDto) {
        likeService.likeComment(requestDto);
        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/likes/contents/{contentId}")
    public ResponseEntity<LikeRequestDto> unlikeContent(@RequestBody LikeRequestDto requestDto) {
        likeService.unlikeContent(requestDto);
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/likes/comments/{commentId}")
    public ResponseEntity<LikeRequestDto> unlikeComment(@RequestBody LikeRequestDto requestDto) {
        likeService.unlikeComment(requestDto);
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

}
