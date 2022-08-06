package com.spring.team9.controller;


import com.spring.team9.dto.LikeRequestDto;
import com.spring.team9.model.Like;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/{contentsId}")
    public ResponseEntity<String> likecontent(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long contentsId) {
        boolean result = false;
        if (userDetails != null) {
            result = likeService.likecontent(userDetails.getUser(), contentsId);
        }
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/like/{commentsId}")
    public ResponseEntity<String> likecomment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentsId) {
        boolean result = false;
        if (userDetails != null) {
            result = likeService.likecontent(userDetails.getUser(), commentsId);
        }
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
