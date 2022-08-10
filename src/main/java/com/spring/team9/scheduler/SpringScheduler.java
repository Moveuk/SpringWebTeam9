package com.spring.team9.scheduler;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.spring.team9.S3.S3Service;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.mapping.ImgUrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class SpringScheduler {

    private final ContentsRepository contentsRepository;
    private final S3Service s3Service;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 2 * * *") // 매일 02시 스케쥴
    public void deleteOrphanImage() throws InterruptedException {
        List<String> dbImgList = contentsRepository.findAllBy(ImgUrlMapping.class) // url 주소 값만 받아옴.
                .stream()   // 스트림
                .filter(url -> url != null)   // url null 제거
                .map(url ->
                        url.getImgUrl() // DBImgUrl 타입을 Spring으로 변경
                                .split("/")[url.getImgUrl().split("/").length-1]) // url주소 -> 파일명.png 분리
                .collect(Collectors.toList());  // Stream 바이트를 List 객체로 collect

        List<DeleteObjectsRequest.KeyVersion> keys = s3Service.getImageKeys();  // s3로부터 img list 받아옴.

        keys.stream().forEach(key -> {
            if (dbImgList.contains(key.getKey())) { // key를 db에서 가지고 있으면 삭제 List에서 제거
                keys.remove(key);
            }
        });

        // S3 삭제요청
        s3Service.deleteObjects(keys);
    }
}
