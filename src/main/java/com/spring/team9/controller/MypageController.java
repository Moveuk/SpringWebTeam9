package com.spring.team9.controller;

import com.spring.team9.dto.MypageResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/")
    public MypageResponseDto getMyContentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getMyPageList(userDetails);
    }

//    @GetMapping("/comments")
//    public List<Comment> getMyCommentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return mypageService.getMyCommentList(userDetails);
//    }
//
//    @GetMapping("/contents/likes")
//    public List<Like> getMyLikeContentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return mypageService.getMyLikeContentList(userDetails);
//    }
//
//    @GetMapping("/comments/likes")
//    public List<Like> getMyLikeCommentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return mypageService.getMyLikeCommentList(userDetails);
//    }
}
