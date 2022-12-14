package com.spring.team9.controller;

import com.spring.team9.S3.S3Service;
import com.spring.team9.dto.ContentsRequestDto;
import com.spring.team9.dto.ContentsResponseDto;
import com.spring.team9.dto.ResponseDto;
import com.spring.team9.security.UserDetailsImpl;
import com.spring.team9.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/contents")
@RequiredArgsConstructor
@RestController
public class ContentsController {

    private final com.spring.team9.service.ContentsService contentsService;
    private final CommentService commentService;
    private final S3Service s3Service;

    // 게시글 리스트 조회
    @GetMapping
    public List<ContentsResponseDto> getContentsList() {
        return contentsService.getContentsList();
    }

    // 게시글 조회
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseDto<?> getContent(@PathVariable Long id) {
        HashMap c = new HashMap();
        try {
            c.put("contents", contentsService.getContents(id));
            c.put("comment", commentService.getComment(id));
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail("", e.getMessage());
        }
        return ResponseDto.success(c);
    }


    // 게시글 작성
    @PostMapping
    public ResponseEntity<String> createContents(ContentsRequestDto requestDto, MultipartFile imageFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        String username;
        try {
            username = userDetails.getUser().getUsername();
        } catch (NullPointerException e) {
            return new ResponseEntity<>("로그인을 해주세요.", HttpStatus.OK);
        }
        String imagePath;
        if (!Objects.isNull(imageFile)) {
            try {
                imagePath = s3Service.uploadImage(imageFile);
                requestDto.setImgUrl(imagePath); // 받은 스트링값을 Url필드로 주입
            } catch (NullPointerException e) {
                return new ResponseEntity<>("파일 변환에 실패했습니다", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
            }
        }
        requestDto.setAuthor(username);
        contentsService.createContents(requestDto);
        return new ResponseEntity<>("컨텐츠 등록에 성공했습니다", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContents(@PathVariable Long id, ContentsRequestDto requestDto, MultipartFile imageFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {

            try {
                contentsService.updateContents(id, requestDto, imageFile, userDetails);
            } catch (NullPointerException | IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
            }
        return new ResponseEntity<>("컨텐츠 수정에 성공했습니다", HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public Long deleteContents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return contentsService.deleteContents(id, userDetails);
    }


}
