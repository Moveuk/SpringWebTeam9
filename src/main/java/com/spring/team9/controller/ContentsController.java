package com.spring.team9.controller;

import com.spring.team9.S3.S3Service;
import com.spring.team9.dto.ContentsRequestDto;
import com.spring.team9.dto.ContentsResponseDto;
import com.spring.team9.model.Contents;
import com.spring.team9.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContentsController {

    private final com.spring.team9.repository.ContentsRepository ContentsRepository;
    private final com.spring.team9.service.ContentsService ContentsService;

    private final S3Service s3Service;

    // 게시글 리스트 조회
    @GetMapping("/api/contents")
    public List<ContentsResponseDto> getContentsList() {
        return ContentsService.getContentsList();
    }

    // 게시글 조회
//    @GetMapping("/api/contents/{id}")
//    public Contents getContents(@PathVariable Long id) {
//        Contents contents = ContentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("id가 존재하지 않습니다."));
//        return contents;
//    }
    // 게시글 조회
    @GetMapping("/api/contents/{id}")
    public ContentsResponseDto getContent(@PathVariable Long id) {
        return ContentsService.getContents(id);
    }

    // 게시글 작성

    @PostMapping("/api/contents")
    public Contents createContents(ContentsRequestDto requestDto, MultipartFile imageFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        String username = userDetails.getUser().getUsername();
        String imagePath = s3Service.upload(imageFile);
        requestDto.setImgUrl(imagePath); // 받은 스트링값을 Url필드로 주입
        requestDto.setAuthor(username);
        return ContentsService.createContents(requestDto);
    }
}