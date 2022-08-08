package com.spring.team9.scheduler;

import com.spring.team9.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class S3BucketScheduler {

    private final ContentsRepository contentsRepository;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    public void createContents() throws InterruptedException {
//        System.out.println("매 분마다 글써보기");
//        // 매 정각 분마다 글테스트
//        Contents contents = new Contents(2L,"title", "username","contents");
//        contentsRepository.save(contents);
    }
}
