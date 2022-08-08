package com.spring.team9.controller;


import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/like")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/contents/{contentsId}")
    public ResponseEntity<String> likeContent(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long contentsId) {
        boolean result = false;
        if (userDetails != null) {
            result = likeService.likeContent(userDetails.getUser().getId(), contentsId);
        }
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.OK);
    }

    @GetMapping("/contents/{contentsId}")
    public ResponseEntity<List<String>> likeCountContent(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long contentsId) {

        log.info("contentsId : {} ", contentsId);

        List<String> result = likeService.likeCountContent(contentsId, userDetails.getUser().getId());
        log.info("likeCountContent : {}", result);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/contents/{contentsId}")
    public ResponseEntity<String> disLikeContent(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long contentsId) {
        if (userDetails != null) {
            likeService.disLikeContent(userDetails.getUser().getId(), contentsId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> likeComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        boolean result = false;
        if (userDetails != null) {
            result = likeService.likeComment(userDetails.getUser().getId(), commentId);
        }
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> disLikeComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        if (userDetails != null) {
            likeService.disLikeComment(userDetails.getUser().getId(), commentId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}