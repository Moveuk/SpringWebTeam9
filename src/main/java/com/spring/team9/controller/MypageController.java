package com.spring.team9.controller;

import com.spring.team9.dto.MypageResponseDto;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;

    // 마이페이지 조회
    @GetMapping("/api/mypage")
    public MypageResponseDto getMyContentList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getMyPageList(userDetails);
    }
}
