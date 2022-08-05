package com.spring.team9.model;

import com.spring.team9.dto.ContentsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Contents extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    // 반드시 값을 가지도록 합니다.
    @Column(name = "content_title", nullable = false)
    private String title;

    @Column(name = "content_author", nullable = false)
    private String author;

    @Column(name = "content_contents", nullable = false)
    private String contents;

    public Contents(String title, String username, String contents) {
        this.title = title;
        this.author = username;
        this.contents = contents;
    }

    public Contents(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getName();
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.author = username;
        this.contents = requestDto.getContents();
    }

    public void update(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getName();
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username, String contents) {
        this.title = requestDto.getTitle();
        this.author = username;
        this.contents = contents;
    }
}