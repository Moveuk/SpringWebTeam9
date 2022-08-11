package com.spring.team9.service;

import com.spring.team9.S3.S3Service;
import com.spring.team9.dto.ContentsRequestDto;
import com.spring.team9.dto.ContentsResponseDto;
import com.spring.team9.dto.ResponseDto;
import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import com.spring.team9.repository.UserRepository;
import com.spring.team9.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final S3Service s3Service;

    // 게시글 작성
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public ResponseDto<?> createContents(ContentsRequestDto requestDto) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script") || contentsCheck.contains("<") || contentsCheck.contains(">")) {
            throw new IllegalArgumentException("컨텐츠에는 script/</> 가 포함될 수 없습니다.");
        }
        if (titleCheck.contains("script") || titleCheck.contains("<") || titleCheck.contains(">")) {
            throw new IllegalArgumentException("제목에는 script/</> 가 포함될 수 없습니다.");
        }
        User user = userRepository.findByUsername(requestDto.getAuthor()).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        requestDto.setUserId(user.getId());
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Contents contents = requestDto.toContents(); //컨테츠객체 빌드
        contentsRepository.save(contents);
        return ResponseDto.success(contents);
    }

    // 게시글 목록 리스트 조회
    public List<ContentsResponseDto> getContentsList() {
        List<Contents> contents = contentsRepository.findAllByOrderByCreatedAtDesc();
        List<ContentsResponseDto> listContents = new ArrayList<>();
        for (Contents content : contents) {
            // + 좋아요 개수 카운팅
            int countLike = likeRepository.countByContentsId(content.getId());
            ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                    .content(content)
                    .countLike(countLike)
                    .build();
            listContents.add(contentsResponseDto);
        }
        return listContents;
    }
    // 게시글 조회
    public ContentsResponseDto getContents(Long id) {
        Contents content = contentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        int countLike = likeRepository.countByContentsId(content.getId());
        ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                .content(content)
                .countLike(countLike)
                .build();

        return contentsResponseDto;
    }


    // 게시글 수정
    @Transactional
    public void updateContents(Long id, ContentsRequestDto requestDto, MultipartFile imageFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalArgumentException, NullPointerException {
        Contents content = contentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if(!userDetails.getUser().getUsername().equals(content.getAuthor())){
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        if(requestDto.getTitle() == null || requestDto.getContents() == null || requestDto.getTitle().equals("") || requestDto.getContents().equals("")){
            throw new NullPointerException("제목과 내용을 채워주세요.");
        }
        String imagePath;
        if (!Objects.isNull(imageFile)) {
            imagePath = s3Service.uploadImage(imageFile);
            requestDto.setImgUrl(imagePath); // 받은 스트링값을 Url필드로 주입
        }
        content.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getImgUrl());
    }

    // 게시글 삭제
    public Long deleteContents(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Contents Contents = contentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (userDetails.getUser().getUsername().equals(Contents.getAuthor())) {
            contentsRepository.deleteById(id);
        }
        return id;
    }

}