package com.spring.team9.controller;

import com.spring.team9.S3.S3Service;
import com.spring.team9.dto.ContentsRequestDto;
import com.spring.team9.dto.ContentsResponseDto;
import com.spring.team9.model.Contents;
import com.spring.team9.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class ContentsController {

    private final com.spring.team9.repository.ContentsRepository ContentsRepository;
    private final com.spring.team9.service.ContentsService ContentsService;

    private final S3Service s3Service;

    // 게시글 조회
    @GetMapping("/api/contents")
    public List<ContentsResponseDto> getContents() {
        return ContentsService.getContents();
    }

    // 게시글 디테일 조회
    @GetMapping("/api/contents/{id}")
    public Contents getContents(@PathVariable Long id) {
        Contents contents = ContentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 존재하지 않습니다."));
        return contents;
    }

    // 게시글 작성

    @PostMapping("/api/contents")
    public ResponseEntity<String> createContents(ContentsRequestDto requestDto, MultipartFile imageFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        String username = userDetails.getUser().getUsername();
        String imagePath;
        if (!Objects.isNull(imageFile) || !imageFile.isEmpty() || imageFile != null) {
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
        ContentsService.createContents(requestDto);
        return new ResponseEntity<>("컨텐츠 등록에 성공했습니다",HttpStatus.OK);
    }
}