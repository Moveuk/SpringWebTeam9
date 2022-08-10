package com.spring.team9.service;

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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ContentsService {

    private final ContentsRepository ContentsRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;


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
        ContentsRepository.save(contents);
        return ResponseDto.success(contents);
    }

    // 게시글 목록 리스트 조회
    public List<ContentsResponseDto> getContentsList() {
        List<Contents> contents = ContentsRepository.findAllByOrderByCreatedAtDesc();
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
    public ResponseDto<?> getContents(Long id) {
        Contents content;
        try {
            content = ContentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseDto.fail("Null_Content", e.getMessage());
        }
        int countLike = likeRepository.countByContentsId(content.getId());
        ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                .content(content)
                .countLike(countLike)
                .build();

        return ResponseDto.success(contentsResponseDto);
    }

    // 게시글 수정 기능 (사용 안함)
    @Transactional
    public Long update(Long id, ContentsRequestDto requestDto) {
        Contents Contents = ContentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Contents.update(requestDto.getTitle(), requestDto.getAuthor(), requestDto.getContents(), requestDto.getImgUrl());
        return Contents.getId();
    }

    // 게시글 삭제
    public Long deleteContents(Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Contents Contents = ContentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (userDetails.getUser().getUsername().equals(Contents.getAuthor())) {
            ContentsRepository.deleteById(id);
        }
        return id;
    }
}