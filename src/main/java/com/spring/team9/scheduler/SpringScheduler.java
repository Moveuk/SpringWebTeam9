package com.spring.team9.scheduler;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.spring.team9.S3.S3Service;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.mapping.ImgUrlMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class SpringScheduler {

    private final ContentsRepository contentsRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 2 * * *") // 매일 02시 스케쥴
    public void deleteOrphanImage() {
        log.info("s3 고아 사진 객체 제거 스케쥴러 시작");
        List<String> dbImgList = contentsRepository.findAllBy(ImgUrlMapping.class) // url 주소 값만 받아옴.
                .stream()   // 스트림
                .filter(url -> url != null)   // url null 제거
                .map(url ->
                        url.getImgUrl() // DBImgUrl 타입을 Spring으로 변경
                                .split("/")[url.getImgUrl().split("/").length - 1]) // url주소 -> 파일명.png 분리
                .collect(Collectors.toList());  // Stream 바이트를 List 객체로 collect

        List<DeleteObjectsRequest.KeyVersion> keys = s3Service.getImageKeys();  // s3로부터 img list 받아옴.

        if (keys.isEmpty()) {
            log.info("s3 고아 사진 객체 사진 없으므로 스케쥴러 종료"); // s3에 이미지 값이 없으면 메서드 종료
            return;
        }

        keys.stream().forEach(key -> {
            if (dbImgList.contains(key.getKey())) { // key를 db에서 가지고 있으면 삭제 List에서 제거
                keys.remove(key);
            }
        });

        // S3 삭제요청
        log.info("s3 버킷 고아 사진 객체 삭제");
        s3Service.deleteObjects(keys);
        log.info("s3 버킷 고아 사진 객체 스케줄러 종료");
    }

    @Scheduled(cron = "0 0 1 * * *") // 매일 01시 스케쥴
    public void deleteContentsHavaNotAnyComment() {
        log.info("댓글없는 게시글 삭제 스케쥴러 시작");
        Set<Long> contentIdList = contentsRepository.findAll().stream().map(Contents::getId).collect(Collectors.toSet()); //전체글 id 리스트
        Set<Long> contentHaveCommentList = commentRepository.findAll().stream().map(Comment::getContent).map(Contents::getId).collect(Collectors.toSet());  // 댓글 가지고 있는 id list

        List<Long> deleteList = contentIdList.stream().filter(contentId -> !contentHaveCommentList.contains(contentId)).collect(Collectors.toList());   // 전체리스트에서 댓글 가지고 있는 id 값 제거

        deleteList.stream().forEach(contentId -> {
            Optional<String> title = contentsRepository.findById(contentId).map(Contents::getTitle);
            contentsRepository.deleteById(contentId);
            title.ifPresent(t -> log.info("게시물 <" + t + ">이 삭제되었습니다."));
        });
        log.info("댓글없는 게시글 삭제 스케쥴러 종료");
    }
}
